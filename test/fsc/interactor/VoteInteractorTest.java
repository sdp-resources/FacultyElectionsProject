package fsc.interactor;

import fsc.gateway.ProfileGateway;
import fsc.gateway.VoteRecordGateway;
import fsc.mock.ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy;
import fsc.mock.VoteRecordGatewayDummy;
import org.junit.Test;

public class VoteInteractorTest {

  @Test
  public void createVoteInteractorTest() {
    ProfileGateway profileGateway = new ProfileWithThatUsernameAlreadyExistsProfileGatewaySpy();
    VoteRecordGateway voteGateway = new VoteRecordGatewayDummy();
    VoteInteractor interactor = new VoteInteractor(voteGateway, profileGateway);
  }
}
