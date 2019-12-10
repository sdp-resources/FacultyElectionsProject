package fsc.service;

import fsc.gateway.Gateway;
import fsc.request.*;

public class AuthorizingRequestVisitor implements RequestVisitor {
  private Gateway gateway;

  public AuthorizingRequestVisitor(Gateway gateway) {this.gateway = gateway;}

  public boolean isAuthorized(Request request) {
    return (boolean) doVisit(request);
  }

  public Object visit(QueryValidationRequest request) {
    return true;
  }

  public Object visit(ViewElectionRequest request) {
    // TODO
    return true;
  }

  public Object visit(LoginRequest request) {
    // TODO
    return true;
  }

  public Object visit(ViewProfileRequest request) {
    return isAuthorizedAsAdminOrUser(request, request.username);
  }

  public Object visit(SetDTSRequest request) {
    return isAuthorizedAsAdminOrUser(request, request.username);
  }

  public Object visit(ViewDTSRequest request) {
    return isAuthorizedAsAdminOrUser(request, request.username);
  }

  public Object visit(ViewVoterRequest request) {
    // TODO: Test it
    return isAuthorizedAsAdminOrUser(request, request.username);
  }

  public Object visit(ViewCandidatesRequest request) {
    return isAuthenticated(request);
  }

  public Object visit(SubmitVoteRecordRequest request) {
    return isAuthorizedAsAdminOrUser(request, request.username);
  }

  public Object visit(ViewActiveElectionsRequest request) {
    return isAuthenticated(request);
  }

  // Admin-only actions
  public Object visit(CreateProfileRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(ViewProfilesListRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(ViewDivisionListRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(ViewAllElectionsRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(RemoveFromBallotRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(AddToBallotRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(AddContractTypeRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(ViewContractsRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(AddVoterRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(EditCommitteeRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(CreateNamedQueryRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(EditNamedQueryRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(EditSeatRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(ViewNamedQueryListRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(ViewCommitteeListRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(CreateSeatRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(ViewVoteRecordRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(CreateElectionRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(EditBallotQueryRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(ViewAllVotesRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(EditProfileRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(CreateCommitteeRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(AddDivisionRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(EditElectionStateRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  public Object visit(AddPasswordRecordRequest request) {
    return isAuthorizedAsAdmin(request);
  }

  private boolean isAuthorizedAsAdminOrUser(Request request, String username) {
    return isAuthorizedAsAdmin(request) ||
                 isAuthorizedAsUser(request, username);
  }

  private boolean isAuthorizedAsAdmin(Request request) {
    return isAuthorizedForRole(request, Authorizer.Role.ROLE_ADMIN);
  }

  private boolean isAuthorizedAsUser(Request request, String username) {
    return request.getSession().matchesUser(username);
  }

  private boolean isAuthorizedForRole(Request request, Authorizer.Role role) {
    return request.getSession().isAuthorizedForRole(role);
  }

  private boolean isAuthenticated(Request request) {
    return request.getSession().isAuthenticated();
  }
}