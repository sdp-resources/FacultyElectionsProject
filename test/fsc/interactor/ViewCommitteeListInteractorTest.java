package fsc.interactor;

import fsc.entity.Committee;
import fsc.entity.EntityFactory;
import fsc.entity.SimpleEntityFactory;
import fsc.gateway.CommitteeGateway;
import fsc.request.ViewCommitteeListRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ViewCommitteeListInteractorTest {

  private ViewCommitteeListRequest request;
  private CommitteeGateway committeeGateway;
  private Committee fec;
  private List<Committee> committeeList;
  private EntityFactory entityFactory = new SimpleEntityFactory();

  @Before
  public void setup() {
    request = new ViewCommitteeListRequest();
    fec = entityFactory.createCommittee("FEC", "Faculty Evaluation Committee");
    committeeList = List.of(fec);
    committeeGateway = new CommitteeGateway() {
      public List<Committee> getCommittees() { return committeeList; }

      public Committee getCommittee(String name) { return null; }

      public void addCommittee(Committee committee) { }

      public void save() { }

      public boolean hasCommittee(String name) { return false; }

      public Collection<Committee> getAllCommittees() {
        return null;
      }
    };
  }

  @Test
  public void responseHasAllProvidedProfiles() {
    CommitteeInteractor interactor = new CommitteeInteractor(committeeGateway,
                                                             entityFactory);
    Response response = interactor.handle(request);
    Response expectedResponse = ResponseFactory.ofCommitteeList(committeeGateway.getCommittees());
    assertEquals(expectedResponse, response);
  }

}
