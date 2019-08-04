package fsc.interactor;

import fsc.request.*;

class ExecutingRequestVisitor implements RequestVisitor {
  private final Interactor interactor;

  ExecutingRequestVisitor(Interactor interactor) { this.interactor = interactor; }

  public Object visit(ViewAllVotesRequest request) {
    return interactor.execute(request);
  }

  public Object visit(EditProfileRequest request) {
    return interactor.execute(request);
  }

  public Object visit(CreateCommitteeRequest request) {
    return interactor.execute(request);
  }

  public Object visit(AddDivisionRequest request) {
    return interactor.execute(request);
  }

  public Object visit(LoginRequest request) {
    return interactor.execute(request);
  }

  public Object visit(CreateProfileRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewProfileRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewProfilesListRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewDivisionListRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewDTSRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewCandidatesRequest request) {
    return interactor.execute(request);
  }

  public Object visit(AddToBallotRequest request) {
    return interactor.execute(request);
  }

  public Object visit(AddContractTypeRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewContractsRequest request) {
    return interactor.execute(request);
  }

  public Object visit(AddVoterRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewAllElectionsRequest request) {
    return interactor.execute(request);
  }

  public Object visit(RemoveFromBallotRequest request) {
    return interactor.execute(request);
  }

  public Object visit(EditCommitteeRequest request) {
    return interactor.execute(request);
  }

  public Object visit(CreateNamedQueryRequest request) {
    return interactor.execute(request);
  }

  public Object visit(EditSeatRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewNamedQueryListRequest request) {
    return interactor.execute(request);
  }

  public Object visit(QueryValidationRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewCommitteeListRequest request) {
    return interactor.execute(request);
  }

  public Object visit(SubmitVoteRecordRequest request) {
    return interactor.execute(request);
  }

  public Object visit(CreateSeatRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewVoteRecordRequest request) {
    return interactor.execute(request);
  }

  public Object visit(CreateElectionRequest request) {
    return interactor.execute(request);
  }

  public Object visit(EditBallotQueryRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewElectionRequest request) {
    return interactor.execute(request);
  }

  public Object visit(SetDTSRequest request) {
    return interactor.execute(request);
  }

  public Object visit(EditElectionStateRequest request) {
    return interactor.execute(request);
  }

  public Object visit(AddPasswordRecordRequest request) {
    return interactor.execute(request);
  }

  public Object visit(ViewActiveElectionsRequest request) {
    return interactor.execute(request);
  }
}
