package fsc.voting;

import fsc.entity.Profile;

import java.util.Objects;

public class VoteTarget {
  private String username;

  public VoteTarget(String username) {
    this.username = username;
  }

  public static VoteTarget from(Profile profile) {
    return VoteTarget.from(profile.getUsername());
  }

  public static VoteTarget from(String username) {
    return new VoteTarget(username);
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VoteTarget that = (VoteTarget) o;
    return Objects.equals(username, that.username);
  }

  public int hashCode() {
    return Objects.hash(username);
  }

  public String toString() {
    return username;
  }
}
