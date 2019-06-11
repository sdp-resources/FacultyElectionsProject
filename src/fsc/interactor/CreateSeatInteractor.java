package fsc.interactor;

import fsc.entity.Committee;
import fsc.entity.Seat;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.request.CreateSeatRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;
import fsc.service.query.QueryStringConverter;

public class CreateSeatInteractor extends Interactor<CreateSeatRequest> {
  private CommitteeGateway gateway;

  public CreateSeatInteractor(CommitteeGateway gateway) {
    this.gateway = gateway;
  }

  public Response execute(CreateSeatRequest request) {
    try {
      Committee committee = gateway.getCommittee(request.committeeName);
      Query query = new QueryStringConverter().fromString(request.queryString);
      Seat seat = new Seat(request.seatName, query);
      if (committee.hasSeat(request.seatName)) {
        return ErrorResponse.resourceExists();
      }

      committee.addSeat(seat);
      gateway.save();
      return new SuccessResponse();
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return ErrorResponse.unknownCommitteeName();
    }
  }
}
