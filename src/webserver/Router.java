package webserver;

import dbGateway.DatabaseBackedGatewayFactory;
import fsc.app.AppContext;
import fsc.gateway.Gateway;
import fsc.viewable.ViewableElection;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;

public class Router {
  protected Gateway gateway;
  private AppContext appContext;

  public void setupRoutes(String resourcePath) {
    String path = resourcePath + "/data/sample.json";
    // TODO: Move to a app-context pool
    //    gateway = InMemoryGateway.fromJSONFile(path);
    gateway = DatabaseBackedGatewayFactory.getInstance("org.skiadas.local")
                                          .obtainGateway();
    appContext = new AppContext(gateway);

    Spark.staticFiles.location("/public");
    Spark.get(Path.root(), this::getIndexPage);
    Spark.get(Path.login(), this::getLogin);
    Spark.post(Path.login(), this::postLogin);
    Spark.get(Path.logout(), this::getLogout);
    Spark.get(Path.user(), this::showUserIndexPage);
    Spark.post(Path.decideToStand(), this::handleDecideToStand);
    Spark.get(Path.ballot(), this::showBallotPage);
    Spark.post(Path.ballot(), this::postBallot);
    Spark.get(Path.admin(), this::showAdminIndexPage);
    Spark.get(Path.queryAll(), this::showNamedQueries);
    Spark.get(Path.queryNamed(), this::getNamedQuery);
    Spark.post(Path.queryNamed(), this::editNamedQuery);
    Spark.get(Path.adminProfile(), this::showAllProfiles);
    Spark.get(Path.adminCommittee(), this::showAllCommitteesPage);
    Spark.get(Path.adminElection(), this::showAllElections);
    Spark.get(Path.validate(), this::validateQuery);
    Spark.post("/admin/profile", this::createProfile);
    Spark.get("/admin/profile/:username", this::getProfile);
    Spark.post("/admin/profile/:username", this::editProfile);
    Spark.get("/admin/election/:electionid", this::showSingleElection);
    Spark.post("/admin/election/:electionid", this::editElection);
    Spark.post("/admin/election", this::createElection);
    Spark.exception(RequestHandler.RequireAuthenticationException.class,
                    this::handleUnauthorizedAccess);
    Spark.exception(UserRequestHandler.FailedRequestException.class,
                    this::handleFailedRequestException);
  }

  private Object editNamedQuery(Request req, Response res) {
    return new QueryValidationHandler(req, res, appContext).processEditNamedQuery();
  }

  private Object getNamedQuery(Request request, Response response) {
    return null;
  }

  private Object showNamedQueries(Request req, Response res) {
    return new QueryValidationHandler(req, res, appContext).processGetQueries();
  }

  private Object validateQuery(Request req, Response res) {
    return new QueryValidationHandler(req, res, appContext).validateQuery();
  }

  private void handleFailedRequestException(UserRequestHandler.FailedRequestException e,
                                                                  Request req, Response res) {
    new RequestHandler(req, res, appContext)
          .redirectWithFlash(e.getRedirectPath(), e.getMessage());
  }


  private void handleUnauthorizedAccess(Exception e, Request req, Response res) {
    new RequestHandler(req, res, appContext)
          .redirectWithFlash(Path.login(), "You must log in to access that page");

  }

  private Object editProfile(Request request, Response response) {
    return null;
  }

  private Object editElection(Request request, Response response) {
    return null;
  }

  private Object showSingleElection(Request request, Response response) {
    return null;
  }

  private Object showAllProfiles(Request req, Response res) {
    return new AdminRequestHandler(req, res, appContext).processGetProfiles();
  }

  private Object postBallot(Request req, Response res) {
    return new ElectionRequestHandler(req, res, appContext).processPostBallot();
  }

  private Object showBallotPage(Request req, Response res) {
    return new ElectionRequestHandler(req, res, appContext).processGetBallot();
  }

  private Object handleDecideToStand(Request req, Response res) {
    return new ElectionRequestHandler(req,  res, appContext).processPostDecideToStand();
  }

  private Object showUserIndexPage(Request req, Response res) {
    return new UserRequestHandler(req, res, appContext).processGetIndex();
  }

  private Object getIndexPage(Request req, Response res) {
    return new LoginRequestHandler(req, res, appContext).processGetIndex();
  }

  private Object getLogin(Request req, Response res) {
    return new LoginRequestHandler(req, res, appContext).processGetLogin();
  }

  private Object postLogin(Request req, Response res) {
    return new LoginRequestHandler(req, res, appContext).processPostLogin();
  }

  private Object getLogout(Request req, Response res) {
    return new LoginRequestHandler(req, res, appContext).processGetLogout();
  }

  private Object showAdminIndexPage(Request req, Response res) {
    return new AdminRequestHandler(req, res, appContext).processGetIndex();
  }

  private Object showAllElections(Request req, Response res) {
    HashMap<Object, Object> returnedHash = new HashMap<>();
    returnedHash.put("values", appContext.getAllElections());
    return new LoginRequestHandler(req, res, appContext)
                 .oldServeTemplate("/electionList.handlebars", returnedHash);
  }

  private Object createElection(Request req, Response res) {
    fsc.response.Response response = appContext.createElection(getParam(req, "committee"),
                                                               getParam(req, "seat"));
    ViewableElection election = response.getValues();
    res.redirect("/election");
    return null;
  }

  private String getParam(Request req, String committee) {
    return req.queryParams(committee);
  }

  private Object getProfile(Request req, Response res) {
    // TODO
    return null;
  }

  private Object createProfile(Request req, Response res) {
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

  private Object showAllCommitteesPage(Request req, Response res) {
    return new CommitteeRequestHandler(req, res, appContext).processGetAdminCommittee();
  }

//  private Object showUserProfile(Request req, Response res) {
//    HashMap<Object, Object> returnedHash = new HashMap<>();
//    returnedHash.put("profiles", appContext.getProfilesMatchingQuery("all", ).getValues());
//    returnedHash.put("contractTypes", appContext.getAllContractTypes(null).getValues());
//    returnedHash.put("divisions", appContext.getAllDivisions().getValues());
//    return new LoginRequestHandler(req, res, appContext)
//                 .oldServeTemplate("/profilesList.handlebars", returnedHash);
//  }

}