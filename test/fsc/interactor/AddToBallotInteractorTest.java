package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.mock.NoBallotGatewayStub;
import fsc.request.AddToBallotRequest;
import fsc.response.AddToBallotResponse;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.AbstractList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddToBallotInteractorTest {

  private Profile profile;
  private AbstractList<Profile> profiles;

  @Test
  public void addingToNoBallot() throws AddToBallotInteractor.NoBallotException {
    Ballot ballot = new Ballot();
    AddToBallotRequest request = new AddToBallotRequest(ballot, profile);
    NoBallotGatewayStub gateway = new NoBallotGatewayStub();
    AddToBallotInteractor inter = new AddToBallotInteractor(gateway);
    Response response = inter.execute(request);
    assertEquals( "No ballot", ((ErrorResponse) response).response);
  }

}
