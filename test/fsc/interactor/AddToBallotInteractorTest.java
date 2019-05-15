package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.request.AddToBallotRequest;
import fsc.response.AddToBallotResponse;
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
    BallotGatewaySpy gateway = new BallotGatewaySpy();
    AddToBallotInteractor inter = new AddToBallotInteractor(gateway);
    AddToBallotResponse response = inter.execute(request);
    assertEquals("No ballot", response);
  }

}
