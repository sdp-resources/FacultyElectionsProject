package fsc.request;

public interface RequestVisitor {
  default Object doVisit(Request request) {
    return request.accept(this);
  }
  Object visit(ViewAllVotesRequest request);
  Object visit(EditProfileRequest request);
  Object visit(CreateCommitteeRequest request);
  Object visit(AddDivisionRequest request);
  Object visit(LoginRequest request);
  Object visit(CreateProfileRequest request);
  Object visit(ViewProfileRequest request);
  Object visit(ViewProfilesListRequest request);
  Object visit(ViewDivisionListRequest request);
  Object visit(ViewDTSRequest request);
  Object visit(ViewCandidatesRequest request);
  Object visit(AddToBallotRequest request);
  Object visit(AddContractTypeRequest request);
  Object visit(ViewContractsRequest request);
  Object visit(AddVoterRequest request);
  Object visit(ViewAllElectionsRequest request);
  Object visit(RemoveFromBallotRequest request);
  Object visit(EditCommitteeRequest request);
  Object visit(CreateNamedQueryRequest request);
  Object visit(EditSeatRequest request);
  Object visit(ViewNamedQueryListRequest request);
  Object visit(QueryValidationRequest request);
  Object visit(ViewCommitteeListRequest request);
  Object visit(SubmitVoteRecordRequest request);
  Object visit(CreateSeatRequest request);
  Object visit(ViewVoteRecordRequest request);
  Object visit(CreateElectionRequest request);
  Object visit(EditBallotQueryRequest request);
  Object visit(ViewElectionRequest request);
  Object visit(SetDTSRequest request);
  Object visit(EditElectionStateRequest request);
  Object visit(AddPasswordRecordRequest request);
  Object visit(ViewActiveElectionsRequest request);
  Object visit(ViewVoterRequest request);
}
