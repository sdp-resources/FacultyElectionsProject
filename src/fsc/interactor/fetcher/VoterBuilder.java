package fsc.interactor.fetcher;

import fsc.entity.Voter;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;
import fsc.utils.builder.DelegatingBuilder;

import java.util.function.Function;

public class VoterBuilder extends DelegatingBuilder<Voter, Response> {
  public VoterBuilder(Builder<Voter, Response> builder) {
    super(builder);
  }

  public VoterBuilder escapeIfVoted() {
    return newWithBuilder(builder.escapeIf(Voter::hasVoted,
                                           ResponseFactory.alreadyVoted()));
  }

  protected VoterBuilder newWithBuilder(Builder<Voter, Response> builder) {
    return new VoterBuilder(builder);
  }

  public VoterBuilder reportImproperStateUnless(Function<Voter, Boolean> canVote) {
    return newWithBuilder(builder.escapeUnless(canVote,
                                               ResponseFactory.improperElectionState()));
  }
}
