package fsc.interactor;

import fsc.entity.Ballot;
import fsc.request.SeeElectionCandidatesRequest;
import org.junit.Test;

public class SeeElectionCandidatesInteractorTest {
  @Test
  public void returnsElectionCandidates() {
    Ballot ballot = new Ballot();
    SeeElectionCandidatesRequest request = new SeeElectionCandidatesRequest(ballot);
  }
}
