package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.mock.NoBallotGatewayStub;
import fsc.request.AddToBallotRequest;
import fsc.response.AddToBallotResponse;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.AbstractList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddToBallotInteractorTest {

  private String ballotID = "98705439870539870";
  private String profileUsername = "hayfieldj";

  @Test
  public void addingToNoBallot() {
    AddToBallotRequest request = new AddToBallotRequest(ballotID, profileUsername);
    NoBallotGatewayStub gateway = new NoBallotGatewayStub();
    AddToBallotInteractor inter = new AddToBallotInteractor(gateway);
    Response response = inter.execute(request);
    assertEquals( "No ballot", ((ErrorResponse) response).response);
  }

  @Ignore
  @Test
  public void addingNotRealProfile()
  {
    var AddToBallotRequest = new AddToBallotRequest(ballotID, profileUsername);
  }
}
