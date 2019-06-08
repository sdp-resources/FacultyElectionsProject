package fsc.entity;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vote extends AbstractList<Profile> {

  public final List<Profile> order = new ArrayList<>();

  public Vote() { }

  public Vote(List<Profile> profiles) {
    order.addAll(profiles);
  }

  private static boolean isNonEmpty(Vote v) { return !v.isEmpty(); }

  public static Vote of(Profile... profiles) {
    return new Vote(List.of(profiles));
  }

  public int size() {
    return order.size();
  }

  public Profile get(int index) {
    return order.get(index);
  }

  public boolean remove(Profile profile) {
    return order.remove(profile);
  }

  public Profile remove(int index) {
    return order.remove(index);
  }

  public static List<Vote> createVoteSnapshot(List<Vote> votes) {
    return votes.stream().filter(Vote::isNonEmpty)
                .map(Vote::clone).collect(Collectors.toList());
  }

  public Vote clone() {
    return new Vote(this);
  }

}
