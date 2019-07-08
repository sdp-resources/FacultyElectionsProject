package fsc.entity;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Vote extends AbstractList<Profile> {

  public final List<Profile> order = new ArrayList<>();

  Vote(List<Profile> profiles) {
    order.addAll(profiles);
  }

  public boolean isNonEmpty() { return !isEmpty(); }

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

}
