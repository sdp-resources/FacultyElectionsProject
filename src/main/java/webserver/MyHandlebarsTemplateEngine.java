package webserver;

import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.helper.AssignHelper;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class MyHandlebarsTemplateEngine extends HandlebarsTemplateEngine {
  private final String templatePath = new AssetLoader().pathTo("/templates");

  public MyHandlebarsTemplateEngine() {
    super();
    TemplateLoader templateLoader = new ClassPathTemplateLoader(templatePath, "");
    handlebars.with(templateLoader);
    handlebars.registerHelpers(ConditionalHelpers.class);
    handlebars.registerHelpers(StringHelpers.class);
    handlebars.registerHelper("datetime", new DateTimeHelper());
    registerHelper("path", HandlebarsHelpers.path());
    registerHelper("assign", new AssignHelper());
    registerHelper("remainder", (Helper<Integer>) (number, options) -> {
      Integer modulo = options.param(0);
      return number % modulo;
    });
  }

  public <T> void registerHelper(String name, Helper<T> helper) {
    if (handlebars.helper(name) == null) {
      handlebars.registerHelper(name, helper);
    }
  }

  public Handlebars getHandlebars() { return handlebars; }

  String render(HashMap<Object, Object> model, String templatePath) {
    return render(new ModelAndView(contextOf(model), templatePath));
  }

  private Context contextOf(HashMap<Object, Object> model) {
    return Context.newBuilder(model)
                  .resolver(MapValueResolver.INSTANCE,
                            JavaBeanValueResolver.INSTANCE,
                            FieldValueResolver.INSTANCE)
                  .build();
  }

  private class DateTimeHelper implements Helper<LocalDateTime> {
    public Object apply(LocalDateTime datetime, Options options) throws IOException {
      String format = options.param(0, "dd-mm-yyyy");

      return datetime.format(DateTimeFormatter.ofPattern(format));
    }
  }
}
