package webserver;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.helper.AssignHelper;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class MyHandlebarsTemplateEngine extends HandlebarsTemplateEngine {
  private final String templatePath = new AssetLoader().pathTo("/templates");

  public MyHandlebarsTemplateEngine() {
    super();
    TemplateLoader templateLoader = new ClassPathTemplateLoader(templatePath, "");
    handlebars.with(templateLoader);
    handlebars.registerHelpers(ConditionalHelpers.class);
    registerHelper("path", HandlebarsHelpers.path());
    registerHelper("assign", new AssignHelper());

  }

  public <T> void registerHelper(String name, Helper<T> helper) {
    if (handlebars.helper(name) == null) {
      handlebars.registerHelper(name, helper);
    }
  }
}
