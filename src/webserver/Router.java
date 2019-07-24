package webserver;

import fsc.gateway.Gateway;
import gateway.InMemoryGateway;
import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;

public class Router {
  static final HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();//  private static final Gateway gateway = InMemoryGateway.fromJSONFile("assets/data/sample.json");
  static Gateway gateway;
  static InteractionController controller;

  public static void setupRoutes(String resourcePath) {
    String path = resourcePath + "/data/sample.json";
    gateway = InMemoryGateway.fromJSONFile(path);
    // TODO: Move towards using AppContext
    controller = new InteractionController(gateway);

    Spark.staticFiles.location("/public");
    Spark.get("/", Router::serveIndex);
    Spark.post("/login", Router::processLogin);
    Spark.get("/profile", Router::showAllProfilesPage);
    Spark.post("/profile", Router::createProfile);
    Spark.get("/profile/:username", Router::getProfile);
    Spark.get("/committee", Router::showAllCommitteesPage);
    Spark.post("/election", Router::createElection);
    Spark.get("/election", Router::showElections);
  }

  static Object showElections(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<Object, Object>();
    returnedHash.put("values", controller.getAllElections());
    return serveTemplate("/electionList.handlebars", returnedHash);
  }

  static Object createElection(Request req, Response res) {
    fsc.response.Response response = controller.createElection(req::queryParams);
    System.out.println(response);
    res.redirect("/election");
    return null;
  }

  static Object getProfile(Request req, Response res) {
    // TODO
    return null;
  }

  static Object createProfile(Request req, Response res) {
    fsc.response.Response response = controller.createProfile(req::queryParams);
    System.out.println(response);
    // TODO: Handle error response
    res.redirect("/profile");
    return null;
  }

  static Object showAllCommitteesPage(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<Object, Object>();
    returnedHash.put("committees", controller.getAllCommittees());
    return serveTemplate("/committeeList.handlebars", returnedHash);
  }

  static Object showAllProfilesPage(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<Object, Object>();
    returnedHash.put("profiles", controller.getAllProfiles());
    returnedHash.put("contractTypes", controller.getAllContractTypes());
    returnedHash.put("divisions", controller.getAllDivisions());
    return serveTemplate("/profilesList.handlebars", returnedHash);
  }

  static Object serveIndex(Request req, Response res) {
    return serveTemplate("/login.handlebars", new HashMap<Object, Object>());
  }

  static Object processLogin(Request req, Response res) {
    System.out.println(req.queryParams("username"));
    System.out.println(req.queryParams("password"));
    return "null";
  }

  static String serveTemplate(String templatePath, HashMap<Object, Object> model) {
    return templateEngine.render(new ModelAndView(model, templatePath));
  }
}