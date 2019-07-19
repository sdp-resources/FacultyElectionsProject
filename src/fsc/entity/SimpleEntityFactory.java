package fsc.entity;

import fsc.entity.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleEntityFactory implements EntityFactory {

  public SimpleEntityFactory() {}

  public Election createElection(Seat seat) {
    return new Election(seat);
  }

  public Seat createSeat(String name, Query defaultQuery, Committee committee) {
    return new Seat(name, defaultQuery, committee);
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

  public VoteRecord createVoteRecord(Election election, List<Profile> votes) {
    return new VoteRecord(election, votes);
  }

  public Division createDivision(String newDivisionName) {
    return new Division(newDivisionName);
  }

  public ContractType createContractType(String contractType) {
    return new ContractType(contractType);
  }

  public Committee createCommittee(
        String name, String description, Query voterQuery
  ) {
    return new Committee(name, description, voterQuery);
  }

  public Candidate createCandidate(Profile profile, Election election) {
    return new Candidate(profile, election);
  }

  public Profile createProfile(
        String name, String username, String division, String contract
  ) {
    return new Profile(name, username, division, contract);
  }

  public List<Candidate> createBallot() { return new ArrayList<>(); }
}
