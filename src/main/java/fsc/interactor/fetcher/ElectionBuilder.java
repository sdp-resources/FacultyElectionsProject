package fsc.interactor.fetcher;

import fsc.entity.*;
import fsc.gateway.ElectionGateway;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;
import fsc.utils.builder.DelegatingBuilder;

import java.util.Collection;
import java.util.function.Function;

public class ElectionBuilder extends DelegatingBuilder<Election, Response> {

  ElectionBuilder(Builder<Election, Response> builder) {
    super(builder);
  }

  public ElectionBuilder reportImproperStateIf(Function<State, Boolean> pred) {
    return newWithBuilder(builder.escapeIf(e -> pred.apply(e.getState()),
                                           ResponseFactory.improperElectionState()));
  }

  public ElectionBuilder reportImproperStateUnless(Function<State, Boolean> pred) {
    return newWithBuilder(builder.escapeUnless(e -> pred.apply(e.getState()),
                                               ResponseFactory.improperElectionState()));
  }

  @Override
  protected ElectionBuilder newWithBuilder(Builder<Election, Response> builder) {
    return new ElectionBuilder(builder);
  }

  public Builder<Candidate, Response> retrieveCandidate(String username) {
    return mapThrough(election -> {
      try {
        return Builder.ofValue(election.getCandidateByUsername(username));
      } catch (ElectionGateway.NoProfileInBallotException e) {
        return Builder.ofResponse(ResponseFactory.invalidCandidate());
      }
    });
  }

  public Builder<Collection<Profile>, Response> getCandidateProfiles() {
    return builder.mapThrough(Builder.lift(Election::getCandidateProfiles));
  }
}
