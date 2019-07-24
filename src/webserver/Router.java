package webserver;

import fsc.app.AppContext;
import fsc.gateway.Gateway;
import fsc.viewable.ViewableElection;
import gateway.InMemoryGateway;
import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;

public class Router {
  static final HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();//  private static final Gateway gateway = InMemoryGateway.fromJSONFile("assets/data/sample.json");
  static Gateway gateway;
  static AppContext appContext;

  public static void setupRoutes(String resourcePath) {
    String path = resourcePath + "/data/sample.json";
    gateway = InMemoryGateway.fromJSONFile(path);
    // TODO: Move towards using AppContext
    appContext = new AppContext(gateway);

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
    returnedHash.put("values", appContext.getAllElections());
    return serveTemplate("/electionList.handlebars", returnedHash);
  }

  static Object createElection(Request req, Response res) {
    ViewableElection election = appContext.createElection(getParam(req, "committee"),
                                                          getParam(req, "seat"));
    System.out.println(election);
    res.redirect("/election");
    return null;
  }

  private static String getParam(Request req, String committee) {
    return req.queryParams(committee);
  }

  static Object getProfile(Request req, Response res) {
    // TODO
    return null;
  }

  static Object createProfile(Request req, Response res) {
    boolean profileCreated = appContext.addProfile(
          getParam(req, "fullName"), getParam(req, "username"),
          getParam(req, "division"), getParam(req, "contractType")
    );
    if (profileCreated) {
      res.redirect("/profile");
    } else {
      // TODO: Handle error response
    }
    return null;
  }

  static Object showAllCommitteesPage(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<Object, Object>();
    returnedHash.put("committees", appContext.getAllCommittees());
    return serveTemplate("/committeeList.handlebars", returnedHash);
  }

  static Object showAllProfilesPage(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<Object, Object>();
    returnedHash.put("profiles", appContext.getProfilesForQuery("all"));
    returnedHash.put("contractTypes", appContext.getAllContractTypes());
    returnedHash.put("divisions", appContext.getAllDivisions());
    return serveTemplate("/profilesList.handlebars", returnedHash);
  }

  static Object serveIndex(Request req, Response res) {
    return serveTemplate("/login.handlebars", new HashMap<Object, Object>());
  }

  static Object processLogin(Request req, Response res) {
    System.out.println(getParam(req, "username"));
    System.out.println(getParam(req, "password"));
    return "null";
  }

  static String serveTemplate(String templatePath, HashMap<Object, Object> model) {
    return templateEngine.render(new ModelAndView(model, templatePath));
  }
}