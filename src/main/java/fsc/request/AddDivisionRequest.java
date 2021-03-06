package fsc.request;

public class AddDivisionRequest extends Request {

  public final String name;

  public AddDivisionRequest(String name) {
    this.name = name;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}

