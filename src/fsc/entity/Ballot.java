package fsc.entity;

import java.util.ArrayList;
import java.util.AbstractList;
import java.util.UUID;

public class Ballot extends AbstractList {

  private AbstractList<Profile> profiles = new ArrayList<>();
  private int size = 0;
  private String id = UUID.randomUUID().toString();

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

  public String getID() {
    return id;
  }
}
