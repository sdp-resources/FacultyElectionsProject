package fsc.interactor.election;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.committee.ProvidedCommitteeGatewaySpy;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.request.ViewActiveElectionsRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ViewActiveElectionsRequestTest extends ElectionTest {

  private ViewActiveElectionsRequest request;
  private ElectionInteractor interactor;
  private Response response;
  private Election electionSetup;
  private Election electionClosed;
  private Election electionVote;
  private Election electionDTS;
  private Committee committee = null;
  private Profile[] profiles;
  private List<Election> elections;
  private List<Election> activeElections;

  @Before
  public void setUp() {
    profiles = new Profile[]{EntityStub.getProfile(1), EntityStub.getProfile(2)};
    electionSetup = EntityStub.simpleElectionWithCandidates(profiles[0]);
    electionDTS = EntityStub.simpleElectionWithCandidates(profiles[0]);
    electionDTS.setState(State.DecideToStand);
    electionVote = EntityStub.simpleElectionWithCandidates(profiles[0]);
    electionVote.setState(State.Vote);
    electionClosed = EntityStub.simpleElectionWithCandidates(profiles[0]);
    electionClosed.setState(State.Closed);
    elections = List.of(electionSetup, electionDTS, electionVote, electionClosed);
    activeElections = List.of(electionDTS, electionVote);
    request = new ViewActiveElectionsRequest();
  }

  @Test
  public void allElectionThatAreNotClosedAreShown() {
    ElectionGateway electionGateway = new SimpleElectionGateway();
    for (Election election : elections) {
      electionGateway.addElection(election);
    }
    ProvidedCommitteeGatewaySpy committeeGateway = new ProvidedCommitteeGatewaySpy(committee);
    ProfileGateway profileGateway = new ExistingProfileGatewaySpy(profiles);
    interactor = new ElectionInteractor(electionGateway, committeeGateway,
                                        profileGateway, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.ofElectionList(activeElections), response);
  }

  private static class SimpleElectionGateway implements ElectionGateway {
    private List<Election> elections = new ArrayList<>();

    public void save() {

    }

    public void addElection(Election election) {
      elections.add(election);
    }

    public void addVoteRecord(VoteRecord voteRecord) {

    }

    public VoteRecord getVoteRecord(long recordId) {
      return null;
    }

    public Election getElection(long electionID) throws InvalidElectionIDException {
      for (Election election : elections) {
        if (election.getID().equals(electionID)) { return election; }
      }
      throw new InvalidElectionIDException();
    }

    public Collection<VoteRecord> getAllVotes(Election election) {
      return null;
    }

    public Collection<Election> getAllElections() {
      return elections;
    }

    public Voter getVoter(long voterId) {
      return null;
    }

    public void addVoter(Voter voter) {

    }

    public void removeCandidate(Candidate candidate) {

    }
  }
}