package fsc.interactor;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.ViewVoteRecordRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableVoteRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewVoteRecordTest {

  private ViewVoteRecordRequest request;
  private Profile voter;
  private Election election;
  private ElectionInteractor interactor;
  private ElectionGateway electionGateway;
  private ProfileGateway profileGateway;
  private List<Profile> profiles;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setUp() {
    profiles = List.of(EntityStub.getProfile(0), EntityStub.getProfile(1),
                       EntityStub.getProfile(2));
    voter = profiles.get(0);
    Ballot ballot = entityFactory.createBallot();
    ballot.add(entityFactory.createCandidate(profiles.get(1), ballot));
    ballot.add(entityFactory.createCandidate(profiles.get(2), ballot));
    election = EntityStub.simpleBallotElection(ballot);
    request = new ViewVoteRecordRequest(voter.getUsername(), election.getID());
    electionGateway = new ProvidedElectionGatewaySpy(election);
    profileGateway = new ProfileGatewayStub(profiles.toArray(new Profile[]{}));
  }

  @Test
  public void whenVoterDoesNotExist_returnError() {
    interactor = new ElectionInteractor(electionGateway, null,
                                        new InvalidProfileGatewaySpy(), entityFactory);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownProfileName(), response);
  }

  @Test
  public void whenElectionDoesNotExist_returnError() {
    interactor = new ElectionInteractor(new RejectingElectionGatewaySpy(), null,
                                        profileGateway, entityFactory);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void whenNoVoteHasBeenCast_returnError() {
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    Response response = interactor.execute(request);
    assertEquals(ResponseFactory.noVote(), response);
  }

  @Test
  public void whenVoteRecordIsPresent_returnViewableRecord() {
    List<Profile> votes = List.of(profiles.get(2), profiles.get(1));
    electionGateway.recordVote(
          entityFactory.createVoteRecord(
                entityFactory.createVoter(voter, election), votes));
    electionGateway.save();
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    Response response = interactor.execute(request);
    assertTrue(response.isSuccessful());
    ViewableEntityConverter entityConverter = new ViewableEntityConverter();
    ViewableVoteRecord resultRecord = response.getValues();
    assertEquals(entityConverter.convertProfiles(votes), resultRecord.votes);
    assertEquals(election.getID(), resultRecord.electionID);
    assertNotNull(resultRecord.timestamp);
  }
}