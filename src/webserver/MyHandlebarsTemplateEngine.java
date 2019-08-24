package webserver;

import com.github.jknack.handlebars.Helper;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class MyHandlebarsTemplateEngine extends HandlebarsTemplateEngine {
  public <T> void registerHelper(String name, Helper<T> helper) {
    if (handlebars.helper(name) == null) {
      handlebars.registerHelper(name, helper);
    }
  }
}
