package fsc.response;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.entity.session.AuthenticatedSession;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.*;
import fsc.voting.FullElectionRecord;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ResponseFactory {
  static ViewableEntityConverter entityConverter = new ViewableEntityConverter();

  public static Response<Collection<ViewableProfile>> ofProfileList(Collection<Profile> profiles) {
    return new ViewResponse<>(entityConverter.convertProfiles(profiles));
  }

  public static Response ofFullElectionRecord(FullElectionRecord record) {
    return new ViewResponse<>(record);
  }

  public static Response ofProfile(Profile profile) {
    return new ViewResponse<>(entityConverter.convert(profile));
  }

  public static Response ofStrings(List<String> strings) {
    return new ViewResponse<>(strings);
  }

  public static Response ofNamedQueries(Map<String, Query> queries) {
    return new ViewResponse<>(entityConverter.convertQueries(queries));
  }

  public static Response ofCandidate(Candidate candidate) {
    return new ViewResponse<>(entityConverter.convert(candidate));
  }

  public static Response ofCommitteeList(List<Committee> committees) {
    return new ViewResponse<>(entityConverter.convertCommittees(committees));
  }

  public static Response<ViewableCommittee> ofCommittee(Committee committee) {
    return new ViewResponse<>(entityConverter.convert(committee));
  }

  public static Response<ViewableSeat> ofSeat(Seat seat) {
    return new ViewResponse<>(entityConverter.convert(seat));
  }

  public static Response ofQueryResult(QueryValidationResult result) {
    return new ViewResponse<>(new ViewableValidationResult(result));
  }

  public static Response ofVoteRecord(VoteRecord voteRecord) {
    return new ViewResponse<>(entityConverter.convert(voteRecord));
  }

  public static Response ofVoteRecordList(Collection<VoteRecord> voteRecordList) {
    return new ViewResponse<>(entityConverter.convertVoteRecordList(voteRecordList));
  }

  public static Response success() {
    return new ViewResponse<>(null);
  }

  public static Response unknownCommitteeName() {
    return new ErrorResponse(ErrorResponse.UNKNOWN_COMMITTEE_NAME);
  }

  public static Response unknownSeatName() {
    return new ErrorResponse(ErrorResponse.UNKNOWN_SEAT_NAME);
  }

  public static Response unknownElectionID() {
    return new ErrorResponse(ErrorResponse.UNKNOWN_ELECTION_ID);
  }

  public static Response unknownProfileName() {
    return new ErrorResponse(ErrorResponse.NO_PROFILE_WITH_THAT_USERNAME);
  }

  public static Response resourceExists() {
    return new ErrorResponse(ErrorResponse.RESOURCE_EXISTS);
  }

  public static <S> Response<S> notAuthorized() {
    return new ErrorResponse<>(ErrorResponse.NOT_AUTHORIZED);
  }

  public static Response invalidCandidate() {
    return new ErrorResponse(ErrorResponse.INVALID_CANDIDATE);
  }

  public static <S> Response<S> cannotHandle() {
    return new ErrorResponse<>(ErrorResponse.NO_HANDLERS);
  }

  public static Response alreadyVoted() {
    return new ErrorResponse(ErrorResponse.VOTER_ALREADY_VOTED);
  }

  public static Response multipleRanksForCandidate() {
    return new ErrorResponse(ErrorResponse.MULTIPLE_RANKS_FOR_CANDIDATE);
  }

  public static Response noVote() {
    return new ErrorResponse(ErrorResponse.NO_VOTE_RECORDED);
  }

  public static Response invalidQuery(String errorMessage) {
    return new ErrorResponse(errorMessage);
  }

  public static Response ofDivisions(List<Division> divisions) {
    return ofStrings(entityConverter.convertDivisions(divisions));
  }

  public static Response ofContractTypes(List<ContractType> contractTypes) {
    return ofStrings(entityConverter.convertContractTypes(contractTypes));
  }

  public static Response invalidElectionState() {
    return new ErrorResponse(ErrorResponse.INVALID_ELECTION_STATE);
  }

  public static Response ofElection(Election election) {
    return new ViewResponse<>(entityConverter.convert(election));
  }

  public static Response invalidVoter() {
    return new ErrorResponse(ErrorResponse.INVALID_VOTER);
  }

  public static Response voterExists() {
    return new ErrorResponse(ErrorResponse.VOTER_EXISTS);
  }

  public static Response ofVoter(Voter voter) {
    return new ViewResponse<>(entityConverter.convert(voter));
  }

  public static Response improperElectionState() {
    return new ErrorResponse(ErrorResponse.IMPROPER_ELECTION_STATE);
  }

  public static Response invalidCredentials() {
    return new ErrorResponse(ErrorResponse.INVALID_CREDENTIALS);
  }

  public static Response ofElectionList(Collection<Election> elections) {
    return new ViewResponse<>(entityConverter.convertElectionList(elections));
  }

  public static Response ofAuthenticatedSession(AuthenticatedSession session) {
    return new ViewResponse<>(entityConverter.convert(session));
  }

  public static <T> Response<T> invalidSession() {
    return new ErrorResponse<>(ErrorResponse.INVALID_SESSION);
  }

  public static Response invalidCandidateStatus() {
    return new ErrorResponse(ErrorResponse.INVALID_CANDIDATE_STATE);
  }

  public static Response invalidQueryName() {
    return new ErrorResponse(ErrorResponse.INVALID_QUERY_NAME);
  }

  public static Response invalidKey(String key) {
    return new ErrorResponse("Invalid Key: " + key);
  }

  public static Response voterMissing() {
    return new ErrorResponse(ErrorResponse.VOTER_MISSING);
  }
}
