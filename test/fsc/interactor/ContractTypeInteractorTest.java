package fsc.interactor;

import fsc.entity.ContractType;
import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.mock.gateway.contractType.ContractTypesGatewayStub;
import fsc.request.AddContractTypeRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ContractTypeInteractorTest {

  private static final String EXISTING_CONTRACT = "tenured";
  private static final String ADDED_CONTRACT = "sabbatical";
  private AddContractTypeRequest request;
  private ContractTypeInteractor interactor;
  private Response response;
  private ContractTypesGatewayStub contractTypeGateWay;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setUp() {
    contractTypeGateWay = new ContractTypesGatewayStub(EXISTING_CONTRACT);
  }

  @Test
  public void succesfullyAddedContract() {
    request = new AddContractTypeRequest(ADDED_CONTRACT);
    interactor = new ContractTypeInteractor(contractTypeGateWay, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.success(), response);
    assertEquals(true,
                 contractTypeGateWay.contractTypes.contains(new ContractType(ADDED_CONTRACT)));
    assertEquals(
          List.of("has contract type: " + ADDED_CONTRACT, "add contract type: " + ADDED_CONTRACT,
                  "save"), contractTypeGateWay.events);
  }

  @Test
  public void correctlyErrorsOnAddingExistingContract() {
    request = new AddContractTypeRequest(EXISTING_CONTRACT);
    interactor = new ContractTypeInteractor(contractTypeGateWay, entityFactory);
    response = interactor.execute(request);
    assertEquals(ResponseFactory.resourceExists(), response);
    assertEquals(List.of("has contract type: " + EXISTING_CONTRACT), contractTypeGateWay.events);
  }
}
