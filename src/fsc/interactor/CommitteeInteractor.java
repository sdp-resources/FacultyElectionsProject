package fsc.interactor;

import fsc.entity.Committee;
import fsc.entity.EntityFactory;
import fsc.entity.Seat;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ProfileGateway;
import fsc.interactor.fetcher.CommitteeFetcher;
import fsc.interactor.fetcher.QueryFetcher;
import fsc.request.*;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class CommitteeInteractor extends Interactor {
  private CommitteeFetcher committeeFetcher;
  private QueryFetcher queryFetcher;

  public CommitteeInteractor(
        CommitteeGateway committeeGateway, ProfileGateway profileGateway,
        EntityFactory entityFactory
  ) {
    committeeFetcher = new CommitteeFetcher(committeeGateway, profileGateway, entityFactory);
    queryFetcher = new QueryFetcher();
  }

  public Response execute(CreateCommitteeRequest request) {
    return committeeFetcher.makeCommittee(request.name, request.description)
                           .escapeIf(committeeFetcher::hasCommittee,
                                     ResponseFactory.resourceExists())
                           .perform(committeeFetcher::addCommittee)
                           .perform(committeeFetcher::save)
                           .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(ViewCommitteeListRequest request) {
    return ResponseFactory.ofCommitteeList(committeeFetcher.getCommittees());
  }

  public Response execute(EditCommitteeRequest request) {
    return committeeFetcher.fetchCommittee(request.name)
                           .perform(performUpdates(request.changes))
                           .perform(committeeFetcher::save)
                           .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(CreateSeatRequest request) {
    return committeeFetcher.fetchCommittee(request.committeeName)
                           .escapeIf(c -> c.hasSeat(request.seatName),
                                     ResponseFactory.resourceExists())
                           // TODO: Don't like how this is done
                           // should build seat in fetcher from query+seat step
                           .bindWith(queryFetcher.createFromString(request.queryString),
                                     Builder.lift(committeeFetcher.createSeat(request.seatName)))
                           .perform(committeeFetcher::addSeat)
                           .perform(committeeFetcher::save)
                           .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(EditSeatRequest request) {
    return committeeFetcher.fetchSeat(request.committeeName, request.seatName)
                           .bindWith(prepareSeatChanges(request.changes),
                                     this::performSeatUpdates)
                           .perform(committeeFetcher::save)
                           .resolveWith(s -> ResponseFactory.success());
  }

  private Builder<Map<String, Object>, Response> prepareSeatChanges(Map<String, String> changes) {
    return changes.entrySet()
                  .stream()
                  .map(this::resolveEntry)
                  .reduce(Builder.ofValue(new HashMap<>()),
                          (b, entry) -> b.bindWith(entry, Builder.lift(this::addEntry)),
                          (b1, b2) -> b1.bindWith(b2, Builder.lift(this::mergeMaps)));
  }

  private Map<String, Object> mergeMaps(Map<String, Object> m1, Map<String, Object> m2) {
    m1.putAll(m2);
    return m1;
  }

  private Builder<Map.Entry<String, Object>, Response> resolveEntry(
        Map.Entry<String, String> entry
  ) {
    switch (entry.getKey()) {
      case EditSeatRequest.EDIT_SEAT_PROFILE:
        return committeeFetcher.fetchProfile(entry.getValue())
                               .mapThrough(makeEntryWithKey(entry.getKey()));
      case EditSeatRequest.EDIT_SEAT_QUERY:
        return queryFetcher.createFromString(entry.getValue())
              .mapThrough(makeEntryWithKey(entry.getKey()));
      default:
        return Builder.ofValue(Map.entry(entry.getKey(), entry.getValue()));
    }
  }

  private <T> Function<T, Builder<Map.Entry<String, Object>, Response>> makeEntryWithKey(
        String key
  ) {
    return Builder.lift(q -> Map.entry(key, q));
  }

  private Map<String, Object> addEntry(
        Map<String, Object> result,
        Map.Entry<String, Object> resolvedEntry
  ) {
    result.put(resolvedEntry.getKey(), resolvedEntry.getValue());
    return result;
  }

  private Builder<Seat, Response> performSeatUpdates(
        Seat seat,
        Map<String, Object> changes
  ) {
    for (String field : changes.keySet()) {
      seat.update(field, changes.get(field));
    }
    return Builder.ofValue(seat);
  }

  private Consumer<Committee> performUpdates(Map<String, Object> changes) {
    return committee -> {
      for (String field : changes.keySet()) {
        committee.update(field, changes.get(field));
      }
    };
  }
}
