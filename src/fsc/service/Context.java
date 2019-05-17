package fsc.service;

public class Context {
  public ProfileToViewableProfileConverter profileToViewableProfileConverter =
        new ProfileToViewableProfileConverter();

  private Context()
  {

  }

  public static Context instance = new Context();
}
