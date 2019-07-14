package fsc.viewable;

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
}
