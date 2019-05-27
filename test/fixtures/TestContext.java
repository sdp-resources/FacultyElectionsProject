package fixtures;

import fsc.entity.Profile;
import fsc.gateway.Gateway;
import fsc.gateway.ProfileGateway;
import fsc.interactor.*;
import fsc.request.*;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import fsc.response.ViewResponse;
import gateway.InMemoryGateway;

import java.util.List;

public class TestContext {
  private static Gateway gateway;
  private static Interactor interactor;

  public TestContext() {
    gateway = new InMemoryGateway();
    interactor = loadInteractors(gateway);
  }

  private Interactor loadInteractors(Gateway gateway) {
    return new ViewDivisionInteractor(gateway).append(new AddDivisionInteractor(gateway))
                                              .append(new ViewContractsInteractor(gateway))
                                              .append(new AddContractTypeInteractor(gateway));
  }

  public static void addProfile(
        String fullname, String username, String contractType, String division
  ) {
    gateway.addProfile(new Profile(fullname, username, division, contractType));
  }

  public static Profile getProfile(String username)
        throws ProfileGateway.InvalidProfileUsernameException {
    return gateway.getProfile(username);
  }

  public static boolean addDivision(String division) {
    return isSuccessful(new AddDivisionRequest(division));
  }

  public static boolean addContractType(String contractType) {
    return isSuccessful(new AddContractTypeRequest(contractType));
  }

  public static boolean hasDivision(String division) {
    return getStringListResult(new ViewDivisionRequest()).contains(division);
  }

  public static boolean hasContractType(String contractType) {
    return getStringListResult(new ViewContractsRequest()).contains(contractType);
  }

  private static boolean isSuccessful(Request request) {
    Response response = interactor.handle(request);
    return response.equals(new SuccessResponse());
  }

  private static List<String> getStringListResult(Request request) {
    Response response = interactor.handle(request);
    return ((ViewResponse<List<String>>) response).values;
  }
}
