package fsc.service.query;

public class ValidatingQueryStringParserFactory extends SimpleQueryStringParserFactory {

  private NameValidator dbValidator;

  public ValidatingQueryStringParserFactory(NameValidator dbValidator) {
    this.dbValidator = dbValidator;
  }

  public QueryStringParser createQueryStringParser(String string) {
    return new ValidatingQueryStringParser(string, dbValidator);
  }
}
