package webserver;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;

import static spark.Spark.*;

// By default serving at http://localhost:4567
public class Server {

  private static HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();

  public static void main(String[] args) {
    staticFiles.location("/public");
    get("/", Server::serveIndex);
    post("/login", Server::processLogin);
    get("/profile", Server::showAllProfilesPage);
    post("/profile", Server::createProfile);
    get("/profile/:username", Server::getProfile);
  }

  private static Object getProfile(Request req, Response res) {
    // TODO
    return null;
  }

  private static Object createProfile(Request req, Response res) {
    // TODO
    return null;
  }

  private static Object showAllProfilesPage(Request req, Response res) {
    return serveTemplate("/profilesList.handlebars", new HashMap<>());
  }

  private static Object serveIndex(Request req, Response res) {
    return serveTemplate("/login.handlebars", new HashMap<>());
  }

  private static Object processLogin(Request req, Response res) {
    System.out.println(req.queryParams("username"));
    System.out.println(req.queryParams("password"));
    return "null";
  }

  private static String serveTemplate(String templatePath, HashMap<Object, Object> model) {
    return templateEngine.render(new ModelAndView(model, templatePath));
  }

}
