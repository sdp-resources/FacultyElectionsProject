package fsc.entity;

import java.util.Objects;

public class Division {
  private String name;

  public Division() {
  }

  public Division(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Division division = (Division) o;
    return name.equals(division.name);
  }

  public int hashCode() {
    return Objects.hash(name);
  }
}
