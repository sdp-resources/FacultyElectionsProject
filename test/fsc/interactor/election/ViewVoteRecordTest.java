package fsc.interactor.election;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.gateway.ProfileGateway;
import fsc.interactor.ElectionInteractor;
import fsc.mock.EntityStub;
import fsc.mock.gateway.election.ProvidedElectionGatewaySpy;
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

public class ViewVoteRecordTest extends ElectionTest {

  public static final long RECORD_ID = 1;
  private ViewVoteRecordRequest request;
  private Election election;
  private ElectionInteractor interactor;
  private ElectionGateway electionGateway;
  private ProfileGateway profileGateway;
  private List<Profile> profiles;

  @Before
  public void setUp() {
    profiles = List.of(EntityStub.getProfile(0), EntityStub.getProfile(1),
                       EntityStub.getProfile(2));
    election = EntityStub.simpleElectionWithCandidates(profiles.get(1), profiles.get(2));
    request = new ViewVoteRecordRequest(RECORD_ID);
    electionGateway = new ProvidedElectionGatewaySpy(election);
    profileGateway = new ProfileGatewayStub(profiles.toArray(new Profile[]{}));
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
    VoteRecord voteRecord = entityFactory.createVoteRecord(election, votes);
    voteRecord.setRecordId(RECORD_ID);
    electionGateway.addVoteRecord(voteRecord);
    electionGateway.save();
    interactor = new ElectionInteractor(electionGateway, null,
                                        profileGateway, entityFactory);
    Response response = interactor.execute(request);
    System.out.println(response);
    assertTrue(response.isSuccessful());
    ViewableEntityConverter entityConverter = new ViewableEntityConverter();
    ViewableVoteRecord resultRecord = response.getValues();
    assertEquals(entityConverter.convertProfiles(votes), resultRecord.votes);
    assertEquals(RECORD_ID, resultRecord.recordId.longValue());
    assertNotNull(resultRecord.timestamp);
  }
}