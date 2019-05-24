package fsc.interactor;

import fsc.entity.Profile;
import fsc.entity.VoteRecord;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.NoProfileWithThatUsernameProfileGatewaySpy;
import fsc.mock.ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy;
import fsc.mock.VoteRecordGatewaySpy;
import fsc.request.VoteRecordRequest;
import fsc.response.ProfileDoesNotExistResponse;
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
    calendar.set(2019,11,22);
    date = calendar.getTime();
    vote = "Haris Skiadas";
    electionID = 1;
    request = new VoteRecordRequest(username, date, vote, electionID);
    electionGateway = new VoteRecordGatewaySpy();
    providedProfile = new Profile("Bob Ross", "rossB12","Arts and Letters", "Tenured");
  }

  @Test
  public void canExecuteGoodID() throws Exception {
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);
    VoteInteractor interactor = new VoteInteractor(electionGateway, profileGateway);

    assertTrue(interactor.execute(request) instanceof SuccessResponse);
  }

  @Test
  public void canExecuteBadID() throws Exception {
    ProfileGateway profileGateway = new NoProfileWithThatUsernameProfileGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(electionGateway, profileGateway);

    assertTrue(interactor.execute(request) instanceof ProfileDoesNotExistResponse);
  }

  @Test
  public void voteRecordSpyFunctionality() throws Exception {
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);
    VoteInteractor interactor = new VoteInteractor(electionGateway, profileGateway);
    interactor.execute(request);

    assertTrue(((VoteRecordGatewaySpy) electionGateway).boolTestVar);
  }

  @Test
  public void canRecordAVote() throws Exception {
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy(providedProfile);
    VoteInteractor interactor = new VoteInteractor(electionGateway, profileGateway);
    interactor.execute(request);
    Profile profile = profileGateway.getProfile(username);
    VoteRecord testRecord = ((VoteRecordGatewaySpy) electionGateway).voteRecord;

    assertEquals(testRecord.getProfile(), profile);
    assertEquals(testRecord.getDate(), date);
    assertEquals(testRecord.getVote(), vote);
    assertEquals(testRecord.getElectionID(), electionID);
  }

}
