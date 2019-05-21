package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Vote;
import fsc.gateway.ProfileGateway;
import fsc.gateway.VoteRecordGateway;
import fsc.mock.ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy;
import fsc.mock.VoteRecordGatewaySpy;
import fsc.request.VoteRecordRequest;
import fsc.response.AddedNewVoteResponse;
import fsc.response.ProfileDoesNotExistResponse;
import fsc.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;

public class VoteInteractorTest {
  String username;
  Date date;
  Vote vote;
  int electionID;

  @Before
  public void setup() {
    username = "wilsonT";
    date = new Date(2019,11,22);
    vote = new Vote(new Ballot());
    electionID = 1;
  }

  @Test
  public void canExecuteGoodID() throws Exception{
    VoteRecordRequest request = new VoteRecordRequest(username, date, vote, electionID);
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy();
    VoteRecordGateway voteGateway = new VoteRecordGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);
    Response response = interactor.executeGoodID(request);

    assertTrue(response instanceof AddedNewVoteResponse);
  }
}
