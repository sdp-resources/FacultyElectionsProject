package fsc.interactor;

import fsc.entity.Profile;
import fsc.entity.VoteRecord;
import fsc.gateway.ProfileGateway;
import fsc.gateway.VoteRecordGateway;
import fsc.mock.*;
import fsc.request.VoteRecordRequest;
import fsc.response.AddedNewVoteResponse;
import fsc.response.ProfileDoesNotExistResponse;
import fsc.response.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class VoteInteractorTest {
  String username;
  Date date;
  String vote;
  int electionID;

  @Before
  public void setup() {
    username = "wilsonT";
    date = new Date(2019,11,22);
    vote = "Haris Skiadas";
    electionID = 1;
  }

  @Test
  public void canExecuteGoodID() throws Exception {
    VoteRecordRequest request = new VoteRecordRequest(username, date, vote, electionID);
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy();
    VoteRecordGateway voteGateway = new VoteRecordGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof AddedNewVoteResponse);
  }

  @Test
  public void canExecuteBadID() throws Exception {
    VoteRecordRequest request = new VoteRecordRequest(username, date, vote, electionID);
    ProfileGateway profileGateway = new NoProfileWithThatUsernameProfileGatewaySpy();
    VoteRecordGateway voteGateway = new VoteRecordGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);
    Response response = interactor.execute(request);

    assertTrue(response instanceof ProfileDoesNotExistResponse);
  }

  @Test
  public void voteRecordSpyFunctionality() throws Exception {
    VoteRecordRequest request = new VoteRecordRequest(username, date, vote, electionID);
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy();
    VoteRecordGateway voteGateway = new VoteRecordGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);
    interactor.execute(request);

    assertTrue(((VoteRecordGatewaySpy) voteGateway).boolTestVar);
  }

  @Ignore
  public void canRecordAVote() throws Exception {
    VoteRecordRequest request = new VoteRecordRequest(username, date, vote, electionID);
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy();
    VoteRecordGateway voteGateway = new VoteRecordGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);
    interactor.execute(request);

    Profile profile = profileGateway.getProfile(username);
    VoteRecord testRecord = new VoteRecord(profile,vote,electionID);
    System.out.println(testRecord.toString());

    assertTrue(((VoteRecordGatewaySpy) voteGateway).voteRecord.equals(testRecord));
  }

}
