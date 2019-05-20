package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.gateway.VoteRecordGateway;
import fsc.mock.ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy;
import fsc.mock.VoteRecordGatewaySpy;
import org.junit.Test;

public class VoteInteractorTest {

  @Test
  public void createVoteInteractorTest() {
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy();
    VoteRecordGateway voteGateway = new VoteRecordGatewaySpy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);
  }
}
