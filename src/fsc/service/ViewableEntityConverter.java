package fsc.service;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.service.query.QueryStringConverter;
import fsc.viewable.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ViewableEntityConverter {

  private QueryStringConverter queryStringConverter = new QueryStringConverter();
  private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  public ViewableProfile convert(Profile profile) {
    return new ViewableProfile(profile.getName(), profile.getUsername(), profile.getDivision(),
                               profile.getContract());
  }

  private ViewableCommittee convert(Committee committee) {
    return new ViewableCommittee(committee.getName(), committee.getDescription(),
                                 convertSeats(committee.getSeats()));
  }

  private ViewableSeat convert(Seat seat) {
    return new ViewableSeat(Long.toString(seat.getId()),
                            seat.getName(),
                            new QueryStringConverter().toString(seat.getCandidateQuery()),
                            nullOrConvert(seat.getProfile()));
  }

  public ViewableCandidate convert(Candidate candidate) {
    return new ViewableCandidate(convert(candidate.getProfile()), candidate.getStatus());

  }

  private String convert(LocalDateTime date) {
    return date.format(dateTimeFormatter);
  }

  public ViewableVoteRecord convert(VoteRecord voteRecord) {
    return new ViewableVoteRecord(
          convert(voteRecord.getDate()),
          voteRecord.getRecordId(),
          convertProfiles(voteRecord.getVotes()));
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
                                convertBallot(election.getCandidates()));
  }

  public List<ViewableProfile> convertProfiles(List<Profile> profiles) {
    return profiles.stream().map(this::convert).collect(Collectors.toList());
  }

  private List<ViewableSeat> convertSeats(Set<Seat> seats) {
    return seats.stream().map(this::convert).collect(Collectors.toList());
  }

  public List<ViewableCommittee> convertCommittees(List<Committee> committees) {
    return committees.stream().map(this::convert).collect(Collectors.toList());
  }

  public List<ViewableVoteRecord> convertVoteRecordList(List<VoteRecord> voteRecordList) {
    return voteRecordList.stream().map(this::convert).collect(Collectors.toList());
  }

  public Map<String, String> convertQueries(Map<String, Query> queries) {
    HashMap<String, String> returnedQueries = new HashMap<>();
    queries.forEach((k, q) -> returnedQueries.put(k, convert(q)));
    return returnedQueries;
  }

  public List<String> convertDivisions(List<Division> divisions) {
    return divisions.stream().map(Division::getName).collect(Collectors.toList());
  }

  public List<String> convertContractTypes(List<ContractType> contractTypes) {
    return contractTypes.stream().map(ContractType::getContract).collect(Collectors.toList());
  }

  private List<ViewableCandidate> convertBallot(Collection<Candidate> ballot) {
    return ballot.stream().map(this::convert).collect(Collectors.toList());
  }

  private ViewableProfile nullOrConvert(Profile profile) {
    return profile == null ? null : convert(profile);
  }

  public ViewableVoter convert(Voter voter) {
    return new ViewableVoter(voter.getVoterId(),
                             voter.hasVoted(),
                             convert(voter.getProfile()),
                             convert(voter.getElection()));
  }
}
