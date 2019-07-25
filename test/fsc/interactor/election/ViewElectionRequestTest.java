package fsc.interactor.election;

import fsc.entity.Committee;
import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.committee.ProvidedCommitteeGatewaySpy;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.ExistingProfileGatewaySpy;
import fsc.request.ViewElectionRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewElectionRequestTest extends ElectionTest {

  private ViewElectionRequest request;
  private ElectionInteractor interactor;
  private Response response;
  private Election election;
  private Committee committee = null;
  private Profile[] profiles;

  @Before
  public void setUp() {
    profiles = new Profile[]{ EntityStub.getProfile(1), EntityStub.getProfile(2) };
    election = EntityStub.simpleElectionWithCandidates(profiles[0]);
    request = new ViewElectionRequest(election.getID());
  }

  @Test
  public void whenElectionIdIsInvalid_throwAnError() {
    RejectingElectionGatewaySpy electionGateway = new RejectingElectionGatewaySpy();
    interactor = new ElectionInteractor(electionGateway, null,
                                        null, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.unknownElectionID(), response);
    assertElectionIdEquals(electionGateway.requestedElectionId, election.getID());
  }

  @Test
  public void whenElectionIdIsValid_returnElectionInformation() {
    ProvidedElectionGatewaySpy electionGateway = new ProvidedElectionGatewaySpy(election);
    ProvidedCommitteeGatewaySpy committeeGateway = new ProvidedCommitteeGatewaySpy(committee);
    ProfileGateway profileGateway = new ExistingProfileGatewaySpy(profiles);
    interactor = new ElectionInteractor(electionGateway, committeeGateway,
                                        profileGateway, entityFactory);
    response = interactor.handle(request);
    assertEquals(ResponseFactory.ofElection(election), response);
    assertEquals(false, electionGateway.hasSaved);
  }
}