package fsc.interactor.fetcher;

import fsc.entity.Election;
import fsc.entity.Profile;
import fsc.entity.SimpleEntityFactory;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
import fsc.mock.gateway.election.RejectingElectionGatewaySpy;
import fsc.mock.gateway.profile.InvalidProfileGatewaySpy;
import fsc.mock.gateway.profile.ProfileGatewayStub;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FetcherTest {

  private Profile profile;
  private ProfileGateway profileGateway;
  private ProfileFetcher profileFetcher;
  private Election election;
  private ElectionGateway electionGateway;
  private ElectionFetcher electionFetcher;

  @Before
  public void setUp() {
    profile = EntityStub.getProfile(0);
    election = EntityStub.simpleElectionWithCandidates();
  }

  @Test
  public void whenGivenValidProfileId_fetcherReturnsNormalResponse() {
    profileGateway = new ProfileGatewayStub(profile);
    profileFetcher = new ProfileFetcher(profileGateway, null);
    Response response = profileFetcher.fetchProfile(profile.getUsername())
                                      .resolveWith(ResponseFactory::ofProfile);
    assertEquals(ResponseFactory.ofProfile(profile), response);
  }

  @Test
  public void whenGivenInvalidProfileId_fetcherReturnsError() {
    profileGateway = new InvalidProfileGatewaySpy();
    profileFetcher = new ProfileFetcher(profileGateway, null);
    Response response = profileFetcher.fetchProfile("something else")
                                      .resolveWith(ResponseFactory::ofProfile);
    assertEquals(ResponseFactory.unknownProfileName(), response);
  }

  @Test
  public void whenGivenValidElectionId_fetcherReturnsElection() {
    electionGateway = new ProvidedElectionGatewaySpy(election);
    electionFetcher = new ElectionFetcher(electionGateway, profileGateway, null, null);
    Response response = electionFetcher.fetchElection(election.getID())
                                       .resolveWith(election1 -> {
                                         assertEquals(election, election1);
                                         return ResponseFactory.success();
                                       });
    assertEquals(ResponseFactory.success(), response);
  }

  @Test
  public void whenGivenInvalidElectionId_fetcherThrowsError() {
    electionGateway = new RejectingElectionGatewaySpy();
    electionFetcher = new ElectionFetcher(electionGateway, profileGateway, null, null);
    election.setID((long) 3);
    Response response = electionFetcher.fetchElection(election.getID())
                                       .resolveWith(election1 -> failTest());
    assertEquals(ResponseFactory.unknownElectionID(), response);
  }

  @Test
  public void whenGivenInvalidVoterId_voterFetcherReturnsError() {
    profileGateway = new InvalidProfileGatewaySpy();
    electionGateway = new ProvidedElectionGatewaySpy(election);
    electionFetcher = new ElectionFetcher(electionGateway, profileGateway, null, new SimpleEntityFactory());
    Response response = electionFetcher.fetchVoter(5)
                                      .resolveWith(voter -> failTest());
    assertEquals(ResponseFactory.invalidVoter(), response);
  }

  public Response failTest() {
    fail();
    return null;
  }
}