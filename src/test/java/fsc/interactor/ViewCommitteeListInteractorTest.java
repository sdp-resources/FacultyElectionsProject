package fsc.interactor;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.request.ViewCommitteeListRequest;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import org.junit.Before;
import org.junit.Test;

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
    fec = entityFactory.createCommittee("FEC", "Faculty Evaluation Committee", Query.never());
    committeeList = List.of(fec);
    committeeGateway = new CommitteeGateway() {
      public List<Committee> getCommittees() { return committeeList; }

      public Committee getCommitteeByName(String name) { return null; }

      public Committee getCommittee(Long id) {
        return null;
      }

      public Seat getSeat(Long seatId) throws UnknownSeatNameException {
        return null;
      }

      public Seat getSeatByCommitteeAndSeatName(String committeeName, String seatName) {
        return null;
      }

      public void addCommittee(Committee committee) { }

      public void save() { }

      public boolean hasCommittee(String name) { return false; }

      public void addSeat(Seat seat) { }

    };
  }

  @Test
  public void responseHasAllProvidedCommittees() {
    CommitteeInteractor interactor = new CommitteeInteractor(committeeGateway, null,
                                                             entityFactory, null);
    Response response = interactor.handle(request);
    Response expectedResponse = ResponseFactory.ofCommitteeList(committeeGateway.getCommittees());
    assertEquals(expectedResponse, response);
  }

}
