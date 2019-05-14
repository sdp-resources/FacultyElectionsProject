package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.request.AddToBallotRequest;
import org.junit.Test;

public class AddToBallotRequestTest {

  @Test
  public void makeAddToBallotRequest() {
    Profile profile = new Profile("name1", "username1", "department1", "contract1");
    Ballot ballot = new Ballot();
    AddToBallotRequest request = new AddToBallotRequest(ballot.getID(), profile);
  }



}
