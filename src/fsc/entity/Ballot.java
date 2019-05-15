package fsc.entity;

import java.util.ArrayList;
import java.util.AbstractList;

public class Ballot extends AbstractList {

  private AbstractList<Profile> profiles = new ArrayList<>();
  private int size = 0;

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return profiles.isEmpty();
  }

  public void add(Profile profile) {
    profiles.add(profile);
    size++;
  }

  public Profile get(int i) {
    return profiles.get(i);
  }

  public void remove(Profile profile) {
    profiles.remove(profile);
    size--;
  }

}
