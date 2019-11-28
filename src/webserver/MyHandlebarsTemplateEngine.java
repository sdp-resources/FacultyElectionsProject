package webserver;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.io.File;

public class MyHandlebarsTemplateEngine extends HandlebarsTemplateEngine {
  public MyHandlebarsTemplateEngine(File basedir) {
    super();
    TemplateLoader templateLoader = new FileTemplateLoader(basedir, "");
    handlebars.with(templateLoader);
  }

  public <T> void registerHelper(String name, Helper<T> helper) {
    if (handlebars.helper(name) == null) {
      handlebars.registerHelper(name, helper);
    }
  }
}
