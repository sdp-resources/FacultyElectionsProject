package fsc.service;

public class Context {
  public ViewableEntityConverter viewableEntityConverter = new ViewableEntityConverter();

  private Context() {

  }

  public static final Context instance = new Context();
}
