package fsc.interactor.election;

import fsc.app.RequestFactory;
import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.request.Request;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableVoteRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewAllVotesTest extends ElectionTest {

  private Request request;
  private Election election;
  private ElectionInteractor interactor;
  private ElectionGateway electionGateway;
  private ProfileGateway profileGateway;
  private List<Profile> profiles;
  private RequestFactory requestFactory = new RequestFactory();

  @Before
  public void setUp() {
    profiles = List.of(EntityStub.getProfile(0), EntityStub.getProfile(1),
                       EntityStub.getProfile(2));
    election = EntityStub.simpleElectionWithCandidates(profiles.get(1), profiles.get(2));
    request = requestFactory.viewAllVotes(election.getID());
    electionGateway = new ProvidedElectionGatewaySpy(election);
    profileGateway = new ProfileGatewayStub(profiles.toArray(new Profile[]{}));
  }

  @Test
  public void whenElectionDoesNotExist_returnError() {
    interactor = new ElectionInteractor(new RejectingElectionGatewaySpy(), null, profileGateway,
                                        entityFactory);
    Response response = interactor.handle(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void whenVotesArePresent_returnViewableRecord() {
    List<Profile> votes1 = List.of(profiles.get(2), profiles.get(1));
    List<Profile> votes2 = List.of(profiles.get(1));
    electionGateway.addVoteRecord(
          entityFactory.createVoteRecord(
                entityFactory.createVoter(profiles.get(0), election).getElection(), votes1
          ));
    electionGateway.addVoteRecord(
          entityFactory.createVoteRecord(
                entityFactory.createVoter(profiles.get(1), election).getElection(), votes2
          ));
    electionGateway.save();
    interactor = new ElectionInteractor(electionGateway, null, profileGateway,
                                        entityFactory);
    Response<List<ViewableVoteRecord>> response = interactor.handle(request);
    assertTrue(response.isSuccessful());
    List<ViewableVoteRecord> result = response.getValues();
    ViewableEntityConverter entityConverter = new ViewableEntityConverter();
    assertEquals(entityConverter.getUsernames(votes1), result.get(0).votes);
    assertEquals(entityConverter.getUsernames(votes2), result.get(1).votes);
  }

}