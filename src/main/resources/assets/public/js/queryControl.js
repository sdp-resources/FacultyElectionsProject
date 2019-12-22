(function ($, Q) {
    // add in console we use in case it's missing
    window.console = window.console || {
        log: function () {
        }, error: function () {
        }
    };

    let controlHTML = `<div class="input-group w-100">
  <input class="form-control" name="queryString" type="text">
  <div class="input-group-append"></div></div>
  <span class="form-text queryValidationMessage"></span>`;

    function buttonHTML(symbol, klasses) {
        return `<button class="btn btn-outline-secondary ${klasses}" 
          type="button"><span class="fas fa-${symbol}"></span></button>`;
    }

    let cancelHTML = buttonHTML("times", "text-danger");
    let saveHTML = buttonHTML("check", "text-success");
    let editHTML = buttonHTML("edit", "text-primary");
    let spinner = `<div class="spinner-border" role="status">
  <span class="sr-only">Loading...</span></div>`;

    function validationError(message) {
        return `<span class="text-danger">${message}</span>`;
    }

    function validationSuccess(message) {
        return `<span class="text-success">${message}</span>`;
    }

    $.queryControl = function (options) {
        options = options || {};
        return $('.queryControl').each(function (i, control) {
            Q.init(control, options);
        });
    };

    Q.init = function (element, options) {
        return new Q.Control(element, options);
    };

    Q.Control = function Control(element, options) {
        this.setupElements(element);
        this.setupHandlers();
    };

    Q.Control.prototype.setupElements = function setupElements(element) {
        this.element = $(element);
        this.input = $(controlHTML).appendTo(element)
            .find('[type="text"]').val(this.element.data('value')).prop('disabled', true);
        this.appendGroup = $(element).find(".input-group-append");
        this.cancelButton = $(cancelHTML);
        this.saveButton = $(saveHTML);
        this.editButton = $(editHTML).appendTo(this.appendGroup);
        this.queryValidationEl = this.element.find('.queryValidationMessage');
    };

    Q.Control.prototype.startValidation = function startValidation() {
        this.queryValidationEl.html(spinner);
        this.saveButton.prop('disabled', true);
        this.validateInput();
    };

    Q.Control.prototype.validateInput =  function validateInput() {
        let requestObject = {query: this.input.val()};
        this.jqXHR = $.ajax({
            url: "/validate",
            dataType: "json",
            data: requestObject,
            error: (jqXHR, error, o) => this.handleValidationError(error, o),
            success: (data) => this.handleValidationSuccess(data),
            complete: () => this.jqXHR = null
        });
    };

    Q.Control.prototype.handleValidationError = function handleValidationError(error, message) {
        console.log("error: ", error, message);
    };

    Q.Control.prototype.handleValidationSuccess = function handleValidationSuccess(success) {
        if (this.jqXHR == null) {
            this.cancelEdit();
            return;
        }
        if (success.hasOwnProperty("error")) {
            this.queryValidationEl.html(validationError(success.error));
        } else {
            this.validEntry = success.string;
            this.queryValidationEl.html(validationSuccess(success.string));
            this.saveButton.prop('disabled', false);
        }
    };

    Q.Control.prototype.setupHandlers = function setupHandlers() {
        this.editButton.on('click', () => {
            this.originalValue = this.input.val();
            this.enableEdit();
        });
        this.cancelButton.on('click', () => {
            this.cancelEdit();
        });
        this.saveButton.on('click', () => this.saveTheEdit());
        this.input.on('input', () => this.validEntry = null);
        this.input.on('input', $.debounce(500, () => this.startValidation()));
        this.input.on('keypress', (e) => this.handleKeyPress(e));
    };

    Q.Control.prototype.cancelEdit = function cancelEdit() {
        if (this.jqXHR != null) {
            this.jqXHR.abort();
        }
        this.input.val(this.originalValue);
        this.validEntry = null;
        this.disableEdit();
    };

    Q.Control.prototype.handleKeyPress = function handleKeyPress(e) {
        if (e.keyCode === 27) { this.cancelEdit(); }
        if (e.keyCode === 13) {
            e.preventDefault();
            if (this.validEntry != null) {
                this.saveTheEdit();
            }
        }
    };
    Q.Control.prototype.saveTheEdit = function saveTheEdit() {
        this.input.val(this.validEntry);
        this.disableEdit();
        this.input.trigger('saveQuery');
    };

    Q.Control.prototype.enableEdit = function enableEdit() {
        this.cancelButton.appendTo(this.appendGroup);
        this.saveButton.appendTo(this.appendGroup);
        this.editButton.detach();
        this.input.prop('disabled', false).focus();
    };

    Q.Control.prototype.disableEdit = function disableEdit() {
        this.cancelButton.detach();
        this.saveButton.detach();
        this.editButton.appendTo(this.appendGroup);
        this.input.prop('disabled', true);
        this.queryValidationEl.text("");
    };

})(
    jQuery,
    QueryControl = window.QueryControl || {}
);