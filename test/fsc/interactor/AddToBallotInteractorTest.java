package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.request.AddToBallotRequest;
import fsc.response.AddToBallotResponse;
import org.junit.Test;

import java.util.AbstractList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddToBallotInteractorTest {

  private Profile profile;
  private AbstractList<Profile> profiles;

  @Test
  public void NoBallot() {
    Ballot ballot = new Ballot();
    AddToBallotRequest request = new AddToBallotRequest(ballot.getID(), profile);
    NoBallotGatewaySpy gateway = new NoBallotGatewaySpy();
    AddToBallotInteractor inter = new AddToBallotInteractor(gateway);
    AddToBallotResponse response = inter.execute(request);

    assertThat(response.noBallot(), is("There is no ballot to add to."));
  }

}
