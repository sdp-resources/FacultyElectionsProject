package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.ViewAllVotesRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableVoteRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewAllVotesTest {

  private ViewAllVotesRequest request;
  private Election election;
  private ElectionInteractor interactor;
  private ElectionGateway electionGateway;
  private ProfileGateway profileGateway;
  private List<Profile> profiles;

  @Before
  public void setUp() {
    profiles = List.of(EntityStub.getProfile(0), EntityStub.getProfile(1),
                       EntityStub.getProfile(2));
    Ballot ballot = new Ballot();
    ballot.addCandidates(List.of(profiles.get(1), profiles.get(2)));
    election = EntityStub.simpleBallotElection(ballot);
    request = new ViewAllVotesRequest(election.getID());
    electionGateway = new ProvidedElectionGatewaySpy(election);
    profileGateway = new ProfileGatewayStub(profiles.toArray(new Profile[]{}));
  }

  @Test
  public void whenElectionDoesNotExist_returnError() {
    interactor = new ElectionInteractor(new RejectingElectionGatewaySpy(), null, profileGateway);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void whenVotesArePresent_returnViewableRecord() {
    List<Profile> votes1 = List.of(profiles.get(2), profiles.get(1));
    List<Profile> votes2 = List.of(profiles.get(1));
    electionGateway.recordVote(new VoteRecord(profiles.get(0), votes1, election));
    electionGateway.recordVote(new VoteRecord(profiles.get(1), votes2, election));
    electionGateway.save();
    interactor = new ElectionInteractor(electionGateway, null, profileGateway);
    Response response = interactor.execute(request);
    assertTrue(response.isSuccessful());
    List<ViewableVoteRecord> result = response.getValues();
    ViewableEntityConverter entityConverter = new ViewableEntityConverter();
    assertEquals(entityConverter.convertProfiles(votes1), result.get(0).votes);
    assertEquals(entityConverter.convertProfiles(votes2), result.get(1).votes);
  }
}