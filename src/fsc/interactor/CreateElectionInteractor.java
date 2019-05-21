package fsc.interactor;

import fsc.entity.Election;
import fsc.gateway.ElectionGateway;
import fsc.request.CreateElectionRequest;
import fsc.response.*;

public class CreateElectionInteractor {
  public ElectionGateway gateway;

  public CreateElectionInteractor(ElectionGateway gateway){
    this.gateway = gateway;
  }

  public CreateElectionResponse execute(CreateElectionRequest request) {
    try{ gateway.getSeat(request.seatName); }
    catch (Exception e){
      gateway.createElection(makeElectionFromRequest(request));
      return new SuccessfullyCreatedElectionResponse();
    }
    return new FailedToCreateElectionResponse();

  }

  private Election makeElectionFromRequest(CreateElectionRequest request) {
    return new Election(request.seatName, request.committeeName);
  }

}
