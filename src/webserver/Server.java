package webserver;

import gateway.InMemoryGateway;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;

import static spark.Spark.*;

// By default serving at http://localhost:4567
public class Server {

  private static HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();
  private static InMemoryGateway gateway = InMemoryGateway.fromJSONFile("assets/data/sample.json");
  private static InteractionController controller = new InteractionController(gateway);

  public static void main(String[] args) {
    staticFiles.location("/public");
    get("/", Server::serveIndex);
    post("/login", Server::processLogin);
    get("/profile", Server::showAllProfilesPage);
    post("/profile", Server::createProfile);
    get("/profile/:username", Server::getProfile);
    get("/committee", Server::showAllCommitteesPage);
    post("/election", Server::createElection);
    get("/election", Server::showElections);
  }

  private static Object showElections(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<>();
    returnedHash.put("values", controller.getAllElections());
    return serveTemplate("/electionList.handlebars", returnedHash);
  }

  private static Object createElection(Request req, Response res) {
    fsc.response.Response response = controller.createElection(req::queryParams);
    System.out.println(response);
    res.redirect("/election");
    return null;
  }

  private static Object getProfile(Request req, Response res) {
    // TODO
    return null;
  }

  private static Object createProfile(Request req, Response res) {
    fsc.response.Response response = controller.createProfile(req::queryParams);
    System.out.println(response);
    // TODO: Handle error response
    res.redirect("/profile");
    return null;
  }

  private static Object showAllCommitteesPage(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<>();
    returnedHash.put("values", controller.getAllCommittees());
    return serveTemplate("/committeeList.handlebars", returnedHash);
  }

  private static Object showAllProfilesPage(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<>();
    returnedHash.put("values", controller.getAllProfiles());
    returnedHash.put("contractTypes", controller.getAllContractTypes());
    returnedHash.put("divisions", controller.getAllDivisions());
    return serveTemplate("/profilesList.handlebars", returnedHash);
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
