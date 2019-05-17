package fsc.interactor;

import fsc.entity.Election;
import fsc.entity.Seat;
import fsc.gateway.ElectionGateway;
import fsc.request.CreateElectionRequest;
import fsc.response.*;

public class CreateElectionInteractor {
  public ElectionGateway gateway;

  public CreateElectionInteractor(ElectionGateway gateway){
    this.gateway = gateway;
  }

  public CreateElectionResponse execute(CreateElectionRequest request) {
    try{ Seat seat = gateway.getSeatFromSeatName(request.seat); }
    catch (Exception e){
      gateway.addElection(makeElectionFromRequest(request));
      return new SuccessfullyCreatedElectionResponse();
    }
    return new FailedToCreateElectionResponse();
  }

  private Election makeElectionFromRequest(CreateElectionRequest request) {
    return new Election(request.seat, request.committee);
  }

}
