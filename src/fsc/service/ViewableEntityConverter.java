package fsc.service;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.service.query.QueryStringConverter;
import fsc.viewable.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ViewableEntityConverter {

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

  private ViewableCommittee convert(Committee committee) {
    return new ViewableCommittee(committee.getName(), committee.getDescription(),
                                 convert(committee.getVoterQuery()),
                                 convertSeats(committee.getSeats()));
  }

  private ViewableSeat convert(Seat seat) {
    return new ViewableSeat(Long.toString(seat.getId()),
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
                             voter.hasVoted(),
                             convert(voter.getProfile()),
                             convert(voter.getElection()));
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
    // TODO  Fill in Votes and Voters once they are added
    return new ViewableElection(election.getID(),
                                election.getState().toString(),
                                convert(election.getSeat()),
                                convert(election.getCandidateQuery()),
                                convertCandidates(election.getCandidates()));
  }

  private <T, S> List<T> convertList(Collection<S> items, Function<S, T> f) {
    return items.stream().map(f).collect(Collectors.toList());
  }

  private Collection<ViewableVoter> convertVoters(Collection<Voter> voters) {
    return convertList(voters, this::convert);
  }

  public Collection<ViewableProfile> convertProfiles(Collection<Profile> profiles) {
    return convertList(profiles, this::convert);
  }

  private List<ViewableSeat> convertSeats(Set<Seat> seats) {
    return convertList(seats, this::convert);
  }

  public List<ViewableCommittee> convertCommittees(List<Committee> committees) {
    return convertList(committees, this::convert);
  }

  public List<ViewableVoteRecord> convertVoteRecordList(Collection<VoteRecord> voteRecordList) {
    return convertList(voteRecordList, this::convert);
  }

  public Map<String, String> convertQueries(Map<String, Query> queries) {
    HashMap<String, String> returnedQueries = new HashMap<>();
    queries.forEach((k, q) -> returnedQueries.put(k, convert(q)));
    return returnedQueries;
  }

  public List<String> convertDivisions(List<Division> divisions) {
    return convertList(divisions, this::convert);
  }

  public List<String> convertContractTypes(List<ContractType> contractTypes) {
    return convertList(contractTypes, this::convert);
  }

  private List<ViewableCandidate> convertCandidates(Collection<Candidate> candidates) {
    return convertList(candidates, this::convert);
  }

  private ViewableProfile nullOrConvert(Profile profile) {
    return profile == null ? null : convert(profile);
  }

  public List<String> getUsernames(List<Profile> profiles) {
    return convertList(profiles, Profile::getUsername);
  }
}
