package fsc.viewable;

import java.util.Objects;

public class ViewableSeat {
  public String name;
  public String query;
  public String id;
  public ViewableProfile profile;

  public ViewableSeat(String id, String name, String query, ViewableProfile profile) {
    this.id = id;
    this.name = name;
    this.query = query;
    this.profile = profile;
  }

  public String getName() {
    return name;
  }

  public String getQuery() {
    return query;
  }

  public String getId() {
    return id;
  }

  public ViewableProfile getProfile() {
    return profile;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewableSeat that = (ViewableSeat) o;
    return Objects.equals(name, that.name) &&
                 Objects.equals(query, that.query) &&
                 Objects.equals(id, that.id) &&
                 Objects.equals(profile, that.profile);
  }

  public int hashCode() {
    return Objects.hash(name, query, id, profile);
  }
}
