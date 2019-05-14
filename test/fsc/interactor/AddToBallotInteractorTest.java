package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.gateway.Gateway;
import fsc.request.AddToBallotRequest;
import org.junit.Test;

import java.util.AbstractList;

public class AddToBallotInteractorTest {

  private Profile profile;
  private AbstractList<Profile> profiles;

  @Test
  public void NoBallet() {
    Ballot ballot = new Ballot();
    AddToBallotRequest request = new AddToBallotRequest(ballot.getID(), profile);
    NoBallotGatewaySpy gateway = new NoBallotGatewaySpy();
    AddToBallotInteractor inter = new AddToBallotInteractor(gateway);
    AddToBallotResponse response = inter.execute(request);

  }

}
