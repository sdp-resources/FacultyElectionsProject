package webserver;

import fsc.entity.Profile;
import fsc.gateway.Gateway;
import fsc.interactor.CreateProfileInteractor;
import fsc.interactor.ViewProfilesListInteractor;
import fsc.request.CreateProfileRequest;
import fsc.request.ViewProfilesListRequest;
import fsc.response.ViewProfilesListResponse;
import fsc.viewable.ViewableProfile;
import gateway.InMemoryGateway;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

// By default serving at http://localhost:4567
public class Server {

  private static HandlebarsTemplateEngine templateEngine = new HandlebarsTemplateEngine();
  private static InMemoryGateway inMemoryGateway = new InMemoryGateway();

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

  private static Gateway getGateway() {
    return inMemoryGateway;
  }

  private static Object createProfile(Request req, Response res) {
    CreateProfileRequest request = new CreateProfileRequest(req.queryParams("fullName"),
                                                            req.queryParams("username"),
                                                            req.queryParams("department"),
                                                            req.queryParams("contract"));
    CreateProfileInteractor interactor = new CreateProfileInteractor(getGateway());
    try {
      System.out.println(req.queryParams("fullName"));
      System.out.println(req.queryParams("username"));
      System.out.println(req.queryParams("department"));
      System.out.println(req.queryParams("contract"));
      fsc.response.Response response = interactor.execute(request);
      System.out.println(response);
      res.redirect("/profile");
      //      if (message instanceof FailedAddedProfileResponse) {
      //      } else if (message instanceof SuccessfullyAddedProfileResponse) {
      //        res.redirect("")
      //      }
    } catch (Exception e) {

    }
    return null;
  }

  private static Object showAllProfilesPage(Request req, Response res) {
    // TODO: No profiles yet
    ViewProfilesListInteractor interactor = new ViewProfilesListInteractor(
          getGateway());
    ViewProfilesListRequest request = new ViewProfilesListRequest();
    fsc.response.Response response = interactor.execute(request);
    HashMap<Object, Object> returnedHash = new HashMap<>();
    returnedHash.put("values", ((ViewProfilesListResponse) response).viewableProfiles);
    return serveTemplate("/profilesList.handlebars", returnedHash);
  }

  private static List<ViewableProfile> getProfiles(Gateway gateway) {
    List<ViewableProfile> list = new ArrayList<>();
    for (Profile profile : gateway.getAllProfiles()) {
      System.out.println(profile);
      list.add(ViewableProfile.from(profile));
    }
    return list;
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
