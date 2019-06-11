package fsc.interactor;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;
import fsc.request.ViewCommitteeListRequest;
import fsc.response.Response;
import fsc.response.ViewResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ViewCommitteeListInteractorTest {

  private ViewCommitteeListRequest request;
  private CommitteeGateway committeeGateway;
  private Committee fec;
  private List<Committee> committeeList;

  @Before
  public void setup() {
    request = new ViewCommitteeListRequest();
    fec = new Committee("FEC", "Faculty Evaluation Committee");
    committeeList = List.of(fec);
    committeeGateway = new CommitteeGateway() {
      public List<Committee> getCommittees() { return committeeList; }

      public Committee getCommittee(String name) { return null; }

      public void addCommittee(Committee committee) { }
      public void save() { }
      public boolean hasCommittee(String name) { return false; }
    };
  }

  @Test
  public void responseHasAllProvidedProfiles() {
    ViewCommitteeListInteractor interactor = new ViewCommitteeListInteractor(committeeGateway);
    Response response = interactor.handle(request);
    Response expectedResponse = ViewResponse.ofCommitteeList(committeeGateway.getCommittees());
    assertEquals(expectedResponse, response);
  }

}