package webserver;

import fsc.app.AppContext;
import fsc.gateway.Gateway;
import fsc.viewable.ViewableElection;
import gateway.InMemoryGateway;
import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;

public class Router {
  static final HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();
  static Gateway gateway;
  static AppContext appContext;

  public static void setupRoutes(String resourcePath) {
    String path = resourcePath + "/data/sample.json";
    // TODO: Move to a app-context pool
    gateway = InMemoryGateway.fromJSONFile(path);
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
    fsc.response.Response response = appContext.createElection(getParam(req, "committee"),
                                                                getParam(req, "seat"));
    ViewableElection election = response.getValues();
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
    fsc.response.Response response = appContext.addProfile(
          getParam(req, "fullName"), getParam(req, "username"),
          getParam(req, "division"), getParam(req, "contractType")
    );
    boolean profileCreated = response.isSuccessful();
    if (profileCreated) {
      res.redirect("/profile");
    } else {
      // TODO: Handle error response
    }
    return null;
  }

  static Object showAllCommitteesPage(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<>();
    returnedHash.put("committees", appContext.getAllCommittees());
    return serveTemplate("/committeeList.handlebars", returnedHash);
  }

  static Object showAllProfilesPage(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<Object, Object>();
    returnedHash.put("profiles", appContext.getProfilesMatchingQuery("all").getValues());
    returnedHash.put("contractTypes", appContext.getAllContractTypes(null).getValues());
    returnedHash.put("divisions", appContext.getAllDivisions().getValues());
    return serveTemplate("/profilesList.handlebars", returnedHash);
  }

  static Object serveIndex(Request req, Response res) {
    return serveTemplate("/login.handlebars", new HashMap<>());
  }

  static Object processLogin(Request req, Response res) {
    return "null";
  }

  static String serveTemplate(String templatePath, HashMap<Object, Object> model) {
    return templateEngine.render(new ModelAndView(model, templatePath));
  }
}