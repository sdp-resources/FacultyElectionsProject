package fsc.response;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.service.Context;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableValidationResult;

import java.util.List;
import java.util.Map;

public class ResponseFactory {
  static ViewableEntityConverter entityConverter = Context.instance.viewableEntityConverter;

  public static ViewableEntityConverter getEntityConverter() {
    return entityConverter;
  }

  public static void setEntityConverter(ViewableEntityConverter entityConverter) {
    ResponseFactory.entityConverter = entityConverter;
  }

  public static Response ofProfileList(List<Profile> profiles) {
    return new ViewResponse<>(entityConverter.convertProfiles(profiles));
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

  public static Response ofQueryResult(QueryValidationResult result) {
    return new ViewResponse<>(new ViewableValidationResult(result));
  }

  public static Response ofVoteRecord(VoteRecord voteRecord) {
    return new ViewResponse<>(entityConverter.convert(voteRecord));
  }

  public static Response ofVoteRecordList(List<VoteRecord> voteRecordList) {
    return new ViewResponse<>(entityConverter.convertVoteRecordList(voteRecordList));
  }

  public static Response ofString(String string) {
    return new ViewResponse<>(string);
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

  public static Response notAuthorized() {
    return new ErrorResponse(ErrorResponse.NOT_AUTHORIZED);
  }

  public static Response invalidCandidate() {
    return new ErrorResponse(ErrorResponse.INVALID_CANDIDATE);
  }

  public static Response cannotHandle() {
    return new ErrorResponse(ErrorResponse.NO_HANDLERS);
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
}
