package fsc.entity;

import fsc.entity.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleEntityFactory implements EntityFactory {

  public SimpleEntityFactory() {}

  public Election createElection(
        Seat seat, Committee committee, Query query, Ballot ballot
  ) {
    return new Election(seat, committee, query, ballot);
  }

  public Seat createSeat(String name, Query defaultQuery) {
    return new Seat(name, defaultQuery);
  }

  public Vote createVote(Profile... profiles) {
    return createVote(List.of(profiles));
  }

  public Vote createVote(List<Profile> profiles) {
    return new Vote(profiles);
  }

  public List<Vote> createVoteSnapshot(List<Vote> votes) {
    return votes.stream().filter(Vote::isNonEmpty)
                .map(this::createVote).collect(Collectors.toList());
  }

  public Voter createVoter(Profile voter, Election election) {
    return new Voter(voter, election);
  }

  public VoteRecord createVoteRecord(Voter voter, List<Profile> votes) {
    return new VoteRecord(voter, votes);
  }

  public Division createDivision(String newDivisionName) {
    return new Division(newDivisionName);
  }

  public ContractType createContractType(String contractType) {
    return new ContractType(contractType);
  }

  public Committee createCommittee(String name, String description) {
    return new Committee(name, description);
  }

  public Candidate createCandidate(Profile profile, Ballot ballot) {
    return new Candidate(profile, ballot);
  }

  public Profile createProfile(
        String name, String username, String division, String contract
  ) {
    return new Profile(name, username, division, contract);
  }

  public Ballot createBallot() { return new Ballot(); }
}
