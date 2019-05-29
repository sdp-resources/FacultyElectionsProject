package fsc.interactor;

import fsc.entity.Profile;
import fsc.entity.VoteRecord;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.EntityStub;
import fsc.mock.VoteRecordGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.VoteRecordRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class VoteInteractorTest {
  private String username;
  private Date date;
  private String vote;
  private int electionID;
  private VoteRecordRequest request;
  private ElectionGateway electionGateway;
  private Profile providedProfile;

  @Before
  public void setup() {
    username = "wilsonT";
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.set(2019, 11, 22);
    date = calendar.getTime();
    vote = "Haris Skiadas";
    electionID = 1;
    request = new VoteRecordRequest(username, date, vote, electionID);
    electionGateway = new VoteRecordGatewaySpy();
    providedProfile = EntityStub.getProfile(0);
  }

  @Test
  public void canExecuteGoodID() {
    ProfileGateway profileGateway = new ProfileGatewayStub(providedProfile);
    VoteInteractor interactor = new VoteInteractor(electionGateway, profileGateway);

    assertTrue(interactor.execute(request) instanceof SuccessResponse);
  }

  @Test
  public void canExecuteBadID() {
    VoteInteractor interactor = new VoteInteractor(electionGateway, new InvalidProfileGatewaySpy());
    Response response = interactor.execute(request);
    assertEquals(ErrorResponse.unknownProfileName(), response);
  }

  @Test
  public void voteRecordSpyFunctionality() {
    ProfileGateway profileGateway = new ProfileGatewayStub(providedProfile);
    VoteInteractor interactor = new VoteInteractor(electionGateway, profileGateway);
    interactor.execute(request);

    assertTrue(((VoteRecordGatewaySpy) electionGateway).boolTestVar);
  }

  @Test
  public void canRecordAVote() {
    ProfileGateway profileGateway = new ProfileGatewayStub(providedProfile);
    VoteInteractor interactor = new VoteInteractor(electionGateway, profileGateway);
    interactor.execute(request);
    VoteRecord testRecord = ((VoteRecordGatewaySpy) electionGateway).voteRecord;

    assertEquals(testRecord.getProfile(), providedProfile);
    assertEquals(testRecord.getDate(), date);
    assertEquals(testRecord.getVote(), vote);
    assertEquals(testRecord.getElectionID(), electionID);
  }

}
