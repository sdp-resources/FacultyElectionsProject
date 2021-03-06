package fsc.interactor;

import fsc.entity.Committee;
import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.entity.query.Query;
import fsc.mock.gateway.committee.AcceptingCommitteeGatewaySpy;
import fsc.mock.gateway.committee.RejectingCommitteeGatewaySpy;
import fsc.request.CreateCommitteeRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.viewable.ViewableCommittee;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommitteeInteractorTest {
  public static final String COMMITTEE_NAME = "cccc";
  public static final String COMMITTEE_DESCRIPTION = "xxxx";
  private CreateCommitteeRequest request;
  private Committee expectedCommittee;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    request = new CreateCommitteeRequest(COMMITTEE_NAME, COMMITTEE_DESCRIPTION, "all");
    expectedCommittee = entityFactory.createCommittee(COMMITTEE_NAME, COMMITTEE_DESCRIPTION,
                                                      Query.all());
  }

  @Test
  public void ExecuteCreatesCorrectResponseType() {
    RejectingCommitteeGatewaySpy gateway = new RejectingCommitteeGatewaySpy();
    CommitteeInteractor interactor = new CommitteeInteractor(gateway, null, entityFactory, null);
    Response<ViewableCommittee> response = interactor.handle(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertNotNull(gateway.committeeAdded);
    assertEquals(expectedCommittee, gateway.committeeAdded);
    assertTrue(gateway.hasSaved);
    ViewableCommittee createdCommittee = response.getValues();
    assertEquals(COMMITTEE_NAME, createdCommittee.name);
    assertEquals(COMMITTEE_DESCRIPTION, createdCommittee.description);
  }

  @Test
  public void ExecuteCreatesAFailedResponse() {
    AcceptingCommitteeGatewaySpy gateway = new AcceptingCommitteeGatewaySpy();
    CommitteeInteractor interactor = new CommitteeInteractor(gateway, null, entityFactory, null);
    Response response = interactor.handle(request);

    assertEquals(COMMITTEE_NAME, gateway.submittedCommitteeName);
    assertEquals(ResponseFactory.resourceExists(), response);
  }

}
