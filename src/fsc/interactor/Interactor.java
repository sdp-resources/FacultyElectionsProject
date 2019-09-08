package fsc.interactor;

import fsc.request.*;
import fsc.response.Response;
import fsc.response.ResponseFactory;

public abstract class Interactor {
  private final RequestVisitor visitor = new ExecutingRequestVisitor(this);
  Interactor next = null;

  public Interactor append(Interactor other) {
    next = next == null ? other : next.append(other);
    return this;
  }

  public <T extends Request> Response handle(T request) {
    return (Response) visitor.doVisit(request);
  }

  public Response execute(CreateProfileRequest request) {
    return delegate(request);
  }

  public Response execute(ViewProfileRequest request) {
    return delegate(request);
  }

  public Response execute(ViewProfilesListRequest request) {
    return delegate(request);
  }

  public Response execute(EditProfileRequest request) {
    return delegate(request);
  }

  public Response execute(AddDivisionRequest request) {
    return delegate(request);
  }

  public Response execute(ViewDivisionListRequest request) {
    return delegate(request);
  }

  public Response execute(AddContractTypeRequest request) {
    return delegate(request);
  }

  public Response execute(ViewContractsRequest request) {
    return delegate(request);
  }

  public Response execute(CreateNamedQueryRequest request) {
    return delegate(request);
  }

  public Response execute(ViewNamedQueryListRequest request) {
    return delegate(request);
  }

  public Response execute(QueryValidationRequest request) {
    return delegate(request);
  }

  public Response execute(CreateCommitteeRequest request) {
    return delegate(request);
  }

  public Response execute(EditCommitteeRequest request) {
    return delegate(request);
  }

  public Response execute(ViewCommitteeListRequest request) {
    return delegate(request);
  }

  public Response execute(CreateSeatRequest request) {
    return delegate(request);
  }

  public Response execute(EditSeatRequest request) {
    return delegate(request);
  }

  public Response execute(EditElectionStateRequest request) {
    return delegate(request);
  }

  public Response execute(LoginRequest request) {
    return delegate(request);
  }

  public Response execute(ViewAllElectionsRequest request) {
    return delegate(request);
  }

  public Response execute(ViewCandidatesRequest request) {
    return delegate(request);
  }

  public Response execute(ViewElectionRequest request) {
    return delegate(request);
  }

  public Response execute(AddToBallotRequest request) {
    return delegate(request);
  }

  public Response execute(AddVoterRequest request) {
    return delegate(request);
  }

  public Response execute(ViewAllVotesRequest request) {
    return delegate(request);
  }

  public Response execute(ViewDTSRequest request) {
    return delegate(request);
  }

  public Response execute(SubmitVoteRecordRequest request) {
    return delegate(request);
  }

  public Response execute(CreateElectionRequest request) {
    return delegate(request);
  }

  public Response execute(RemoveFromBallotRequest request) {
    return delegate(request);
  }

  public Response execute(EditBallotQueryRequest request) {
    return delegate(request);
  }

  public Response execute(SetDTSRequest request) {
    return delegate(request);
  }

  public Response execute(ViewVoteRecordRequest request) {
    return delegate(request);
  }

  public Response execute(AddPasswordRecordRequest request) {
    return delegate(request);
  }

  public Response execute(ViewActiveElectionsRequest request) {
    return delegate(request);
  }

  public Response execute(ViewVoterRequest request) {
    return delegate(request);
  }

  Response delegate(Request request) {
    return next == null ? ResponseFactory.cannotHandle() : next.handle(request);
  }

  public Response execute(EditNamedQueryRequest request) {
    return delegate(request);
  }
}
