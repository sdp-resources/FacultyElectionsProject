package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.request.AddToBallotRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractList;
import java.util.ArrayList;

public class EditBallotInteractorTest {

  private Profile profile;
  private AbstractList<Profile> profiles;

  @Before
  public void makeAddToBallotRequest() {
    Profile profile = new Profile("name1", "username1", "department1", "contract1");
    Ballot ballot = new Ballot();
    AddToBallotRequest request = new AddToBallotRequest(ballot.getID(), profile);
  }

  @Test
  public void testExecute() {
    profiles = EditBallotInteractor.execute(profile);
  }

}
