package fsc.interactor;

import fsc.entity.Profile;
import fsc.entity.VoteRecord;
import fsc.gateway.ProfileGateway;
import fsc.gateway.VoteRecordGateway;
import fsc.mock.NoProfileWithThatUsernameProfileGatewaySpy;
import fsc.mock.ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy;
import fsc.mock.VoteRecordGatewaySpy;
import fsc.request.VoteRecordRequest;
import fsc.response.AddedNewVoteResponse;
import fsc.response.ProfileDoesNotExistResponse;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class VoteInteractorTest {
  String username;
  Date date;
  String vote;
  int electionID;
  private VoteRecordRequest request;
  private VoteRecordGateway voteGateway;

  @Before
  public void setup() {
    username = "wilsonT";
    date = new Date(2019,11,22);
    vote = "Haris Skiadas";
    electionID = 1;
    request = new VoteRecordRequest(username, date, vote, electionID);
    voteGateway = new VoteRecordGatewaySpy();
  }

  @Test
  public void canExecuteGoodID() throws Exception {
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);

    assertTrue(interactor.execute(request) instanceof AddedNewVoteResponse);
  }

  @Test
  public void canExecuteBadID() throws Exception {
    ProfileGateway profileGateway = new NoProfileWithThatUsernameProfileGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);

    assertTrue(interactor.execute(request) instanceof ProfileDoesNotExistResponse);
  }

  @Test
  public void voteRecordSpyFunctionality() throws Exception {
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);
    interactor.execute(request);

    assertTrue(((VoteRecordGatewaySpy) voteGateway).boolTestVar);
  }

  @Test
  public void canRecordAVote() throws Exception {
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);
    interactor.execute(request);
    Profile profile = profileGateway.getProfile(username);
    VoteRecord testRecord = ((VoteRecordGatewaySpy) voteGateway).voteRecord;

    assertEquals(testRecord.getProfile(), profile);
    assertEquals(testRecord.getDate(), date);
    assertEquals(testRecord.getVote(), vote);
    assertEquals(testRecord.getElectionID(), electionID);
  }

}
