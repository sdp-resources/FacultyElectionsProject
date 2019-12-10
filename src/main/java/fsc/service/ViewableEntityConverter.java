package fsc.service;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.entity.session.AuthenticatedSession;
import fsc.service.query.QueryStringConverter;
import fsc.viewable.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ViewableEntityConverter {
  public ViewableEntityConverter() { }

  private QueryStringConverter queryStringConverter = new QueryStringConverter();
  private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  private String convert(Division division) {
    return division.getName();
  }

  private String convert(ContractType contractType) {
    return contractType.getContract();
  }

  public ViewableProfile convert(Profile profile) {
    return new ViewableProfile(profile.getName(), profile.getUsername(), profile.getDivision(),
                               profile.getContract());
  }

  public ViewableCommittee convert(Committee committee) {
    return new ViewableCommittee(committee.getId(),
                                 committee.getName(), committee.getDescription(),
                                 convert(committee.getVoterQuery()),
                                 convertSeats(committee.getSeats()));
  }

  public ViewableSeat convert(Seat seat) {
    return new ViewableSeat(seat.getId(),
                            seat.getName(),
                            convert(seat.getCandidateQuery()),
                            nullOrConvert(seat.getProfile()));
  }

  public ViewableCandidate convert(Candidate candidate) {
    return new ViewableCandidate(convert(candidate.getProfile()), candidate.getStatus());
  }

  private String convert(LocalDateTime date) {
    return date.format(dateTimeFormatter);
  }

  public ViewableVoter convert(Voter voter) {
    return new ViewableVoter(voter.getVoterId(),
                             voter.getElection().getID(),
                             voter.hasVoted(),
                             convert(voter.getProfile()));
  }

  public ViewableVoteRecord convert(VoteRecord voteRecord) {
    return new ViewableVoteRecord(
          convert(voteRecord.getDate()),
          voteRecord.getRecordId(),
          voteRecord.getVotes());
  }

  private String convert(Query query) {
    return queryStringConverter.toString(query);
  }

  public ViewableElection convert(Election election) {
    return new ViewableElection(election.getID(),
                                election.getState().toString(),
                                convert(election.getSeat()),
                                convert(election.getCandidateQuery()),
                                convertCandidates(election.getCandidates()),
                                convertVoters(election.getVoters()),
                                convertVoteRecordList(election.getVoteRecords()));
  }

  public ViewableSession convert(AuthenticatedSession session) {
    return new ViewableSession(session.getUsername(),
                               session.getRole().toString(),
                               session.getToken(),
                               session.getExpirationTime().toString());
  }

  private Collection<ViewableVoter> convertVoters(Collection<Voter> voters) {
    return MyUtils.convertList(voters, this::convert);
  }

  public Collection<ViewableProfile> convertProfiles(Collection<Profile> profiles) {
    return MyUtils.convertList(profiles, this::convert);
  }

  private List<ViewableSeat> convertSeats(Set<Seat> seats) {
    return MyUtils.convertList(seats, this::convert);
  }

  public List<ViewableCommittee> convertCommittees(List<Committee> committees) {
    return MyUtils.convertList(committees, this::convert);
  }

  public List<ViewableVoteRecord> convertVoteRecordList(Collection<VoteRecord> voteRecordList) {
    return MyUtils.convertList(voteRecordList, this::convert);
  }

  public Map<String, String> convertQueries(Map<String, Query> queries) {
    HashMap<String, String> returnedQueries = new HashMap<>();
    queries.forEach((k, q) -> returnedQueries.put(k, convert(q)));
    return returnedQueries;
  }

  public List<String> convertDivisions(List<Division> divisions) {
    return MyUtils.convertList(divisions, this::convert);
  }

  public List<String> convertContractTypes(List<ContractType> contractTypes) {
    return MyUtils.convertList(contractTypes, this::convert);
  }

  private List<ViewableCandidate> convertCandidates(Collection<Candidate> candidates) {
    return MyUtils.convertList(candidates, this::convert);
  }

  private ViewableProfile nullOrConvert(Profile profile) {
    return profile == null ? null : convert(profile);
  }

  public List<String> getUsernames(List<Profile> profiles) {
    return MyUtils.convertList(profiles, Profile::getUsername);
  }

  public List<ViewableElection> convertElectionList(Collection<Election> elections) {
    return MyUtils.convertList(elections, this::convert);
  }
}
