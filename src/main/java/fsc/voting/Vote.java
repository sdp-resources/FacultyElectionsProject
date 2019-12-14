package fsc.voting;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vote extends AbstractList<VoteTarget> {

  public final List<VoteTarget> order = new ArrayList<>();

  public static List<Vote> snapshot(List<Vote> votes) {
    return votes.stream()
                .filter(Vote::isNonEmpty)
                .map(Vote::new) // makes a copy
                .collect(Collectors.toList());
  }

  static Vote of(List<VoteTarget> targets) {
    return new Vote(targets);
  }

  public static Vote of(VoteTarget... targets) {
    return of(List.of(targets));
  }

  public Vote(List<? extends VoteTarget> targets) {
    order.addAll(targets);
  }

  public static Vote fromUsernames(List<String> usernames) {
    List<VoteTarget> voteTargets = usernames.stream().map(VoteTarget::new)
                                            .collect(Collectors.toList());
    return new Vote(voteTargets);
  }

  public boolean isNonEmpty() { return !isEmpty(); }

  public int size() {
    return order.size();
  }

  public VoteTarget get(int index) {
    return order.get(index);
  }

  public boolean remove(VoteTarget profile) {
    return order.remove(profile);
  }

  public VoteTarget remove(int index) {
    return order.remove(index);
  }

}
