package fsc.entity;

import fsc.app.AppContext;
import fsc.entity.query.Query;
import fsc.gateway.ProfileGateway;

import java.util.List;
import java.util.stream.Collectors;

public class EntityFactory {

  public EntityFactory() {}

  public Election createElection(
        Seat seat, Committee committee, Query query, Ballot ballot
  ) {
    return new Election(seat, committee, query, ballot);
  }

  public Seat createSeat(String name, Query defaultQuery) {
    return new Seat(name, defaultQuery);
  }

  public Vote createVote(Profile... profiles) {
    return AppContext.getEntityFactory().createVote(List.of(profiles));
  }

  Vote createVote(List<Profile> profiles) {
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

  public Committee createCommittee(String name, String description) {
    return new Committee(name, description);
  }

  public Candidate createCandidate(Profile profile) {
    return new Candidate(profile);
  }

  public BallotCreator createBallotCreator(ProfileGateway profileGateway) {
    return new BallotCreator(profileGateway);
  }

  public Profile createProfile(
        String name, String username, String division, String contract
  ) {
    return new Profile(name, username, division, contract);
  }
}
