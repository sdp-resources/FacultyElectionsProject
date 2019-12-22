package webserver;

import dbGateway.DatabaseBackedGatewayFactory;
import dbGateway.RedisStore;
import fsc.app.AppContext;
import fsc.gateway.Gateway;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Router {
  private String persistenceUnit = "inmemoryH2";
  private String redishost = "localhost";

  public void setupRoutes(String resourcePath) {
    // TODO: add edit committee path for changing committee details
    // TODO: Add edit seat path for changing seat details
    Spark.staticFiles.location(new AssetLoader().pathTo("/public"));

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
    Spark.get(Path.adminAllCommittees(), this::showAllCommitteesPage);
    Spark.post(Path.committee(), this::editCommitteeSettings);
    Spark.get(Path.adminElections(), this::showAllElections);
    Spark.get(Path.adminElection(), this::showSingleElection);
    Spark.get(Path.validate(), this::validateQuery);
    Spark.post("/admin/profile", this::createProfile);
    Spark.get("/admin/profile/:username", this::getProfile);
    Spark.post("/admin/profile/:username", this::editProfile);
    Spark.post(Path.candidateDelete(), this::deleteCandidate);
    Spark.post(Path.candidateAdd(), this::addCandidate);
    Spark.post(Path.voterDelete(), this::deleteVoter);
    Spark.post(Path.voterAdd(), this::addVoter);
    Spark.post(Path.adminElection(), this::editElectionState);
    Spark.post(Path.adminElections(), this::createElection);
    Spark.exception(RequestHandler.RequireAuthenticationException.class,
                    this::handleUnauthorizedAccess);
    Spark.exception(UserRequestHandler.FailedRequestException.class,
                    this::handleFailedRequestException);
  }

  private Object addVoter(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).addVoter();
  }

  private Object deleteVoter(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).deleteVoter();
  }

  private Object addCandidate(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).addCandidate();
  }

  private Object deleteCandidate(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).deleteCandidate();
  }

  private Object editCommitteeSettings(Request req, Response res) {
    return new CommitteeRequestHandler(req, res, getAppContext()).processPostCommittee();
  }

  private Object editNamedQuery(Request req, Response res) {
    return new QueryValidationHandler(req, res, getAppContext()).processEditNamedQuery();
  }

  private Object getNamedQuery(Request request, Response response) {
    return null;
  }

  private Object showNamedQueries(Request req, Response res) {
    return new QueryValidationHandler(req, res, getAppContext()).processGetQueries();
  }

  private Object validateQuery(Request req, Response res) {
    return new QueryValidationHandler(req, res, getAppContext()).validateQuery();
  }

  private void handleFailedRequestException(
        UserRequestHandler.FailedRequestException e,
        Request req, Response res
  ) {
    new RequestHandler(req, res, getAppContext())
          .redirectWithFlash(e.getRedirectPath(), e.getMessage());
  }

  private void handleUnauthorizedAccess(Exception e, Request req, Response res) {
    new RequestHandler(req, res, getAppContext())
          .redirectWithFlash(Path.login(), "You must log in to access that page");

  }

  private Object editProfile(Request request, Response response) {
    return null;
  }

  private Object editElectionState(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).changeElectionState();
  }

  private Object showSingleElection(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).showElection();
  }

  private Object showAllProfiles(Request req, Response res) {
    return new AdminRequestHandler(req, res, getAppContext()).processGetProfiles();
  }

  private Object postBallot(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).processPostBallot();
  }

  private Object showBallotPage(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).processGetBallot();
  }

  private Object handleDecideToStand(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).processPostDecideToStand();
  }

  private Object showUserIndexPage(Request req, Response res) {
    return new UserRequestHandler(req, res, getAppContext()).processGetIndex();
  }

  private Object getIndexPage(Request req, Response res) {
    return new LoginRequestHandler(req, res, getAppContext()).processGetIndex();
  }

  private Object getLogin(Request req, Response res) {
    return new LoginRequestHandler(req, res, getAppContext()).processGetLogin();
  }

  private Object postLogin(Request req, Response res) {
    return new LoginRequestHandler(req, res, getAppContext()).processPostLogin();
  }

  private Object getLogout(Request req, Response res) {
    return new LoginRequestHandler(req, res, getAppContext()).processGetLogout();
  }

  private Object showAdminIndexPage(Request req, Response res) {
    return new AdminRequestHandler(req, res, getAppContext()).processGetIndex();
  }

  private Object createElection(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).createElection();
  }

  private Object showAllElections(Request req, Response res) {
    return new ElectionRequestHandler(req, res, getAppContext()).showElections();
  }

  private String getParam(Request req, String committee) {
    return req.queryParams(committee);
  }

  private Object getProfile(Request req, Response res) {
    // TODO
    return null;
  }

  private Object createProfile(Request req, Response res) {
    fsc.response.Response response = getAppContext().addProfile(
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
    return new CommitteeRequestHandler(req, res, getAppContext()).processGetAdminCommittee();
  }

  protected void setPersistenceUnit(String persistenceUnit) {
    this.persistenceUnit = persistenceUnit;
  }

  public void setRedishost(String redishost) {
    this.redishost = redishost;
  }

  public AppContext getAppContext() {
    return new AppContext(getGateway(), new RedisStore(redishost));
  }

  protected Gateway getGateway() {
    return DatabaseBackedGatewayFactory
                            .getInstance(persistenceUnit)
                            .obtainGateway();
  }
}