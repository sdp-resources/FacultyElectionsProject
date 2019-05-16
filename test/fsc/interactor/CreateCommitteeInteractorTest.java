package fsc.interactor;

import fsc.gateway.CommitteeGateway;
import fsc.mock.CommitteeGatewayStub;
import fsc.request.CreateCommitteeRequest;
import fsc.response.CreateCommitteeResponse;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CreateCommitteeInteractorTest {
  @Test
  public void ExecuteCreatesCorrectResponseType(){
    CreateCommitteeRequest request = new CreateCommitteeRequest("cccc", "xxxx");
    CommitteeGatewayStub gateway = new CommitteeGatewayStub();
    CreateCommitteeInteractor interactor = new CreateCommitteeInteractor(gateway);
    CreateCommitteeResponse response = interactor.execute(request);

    assertTrue(response instanceof CreateCommitteeResponse);
  }
}
