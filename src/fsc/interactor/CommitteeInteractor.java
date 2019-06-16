package fsc.interactor;

import fsc.entity.Committee;
import fsc.entity.Seat;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.request.*;
import fsc.response.*;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;

import java.util.Map;

public class CommitteeInteractor extends Interactor {
  private CommitteeGateway gateway;

  public CommitteeInteractor(CommitteeGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(CreateCommitteeRequest request) {
    if (gateway.hasCommittee(request.name)) {
      return ResponseFactory.resourceExists();
    }
    gateway.addCommittee(makeCommitteeFromRequest(request));
    gateway.save();
    return ResponseFactory.success();
  }

  public Response execute(ViewCommitteeListRequest request) {
    return ResponseFactory.ofCommitteeList(gateway.getCommittees());
  }

  public Response execute(EditCommitteeRequest request) {
    try {
      Committee committee = gateway.getCommittee(request.name);
      performUpdates(committee, request.changes);
      gateway.save();
      return ResponseFactory.success();
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return ResponseFactory.unknownCommitteeName();
    }
  }

  public Response execute(CreateSeatRequest request) {
    try {
      Committee committee = gateway.getCommittee(request.committeeName);
      Query query = new QueryStringConverter().fromString(request.queryString);
      Seat seat = new Seat(request.seatName, query);
      if (committee.hasSeat(request.seatName)) {
        return ResponseFactory.resourceExists();
      }

      committee.addSeat(seat);
      gateway.save();
      return ResponseFactory.success();
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return ResponseFactory.unknownCommitteeName();
    } catch (QueryStringParser.QueryParseException e) {
      return ResponseFactory.invalidQuery(e.getMessage());
    }
  }

  private void performUpdates(Committee committee, Map<String, Object> changes) {
    for (String field : changes.keySet()) {
      updateProfile(committee, field, changes.get(field));
    }
  }

  public void updateProfile(Committee committee, String field, Object value) {
    switch (field) {
      case "name":
        committee.setName((String) value);
        break;
      case "description":
        committee.setDescription((String) value);
        break;
    }
  }

  private Committee makeCommitteeFromRequest(CreateCommitteeRequest request) {
    return new Committee(request.name, request.description);
  }
}
