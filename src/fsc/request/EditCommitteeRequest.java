package fsc.request;

import java.util.Map;

public class EditCommitteeRequest extends Request {
  public final String name;
  public final Map<String, Object> changes;

  public EditCommitteeRequest(String name, Map<String, Object> changes) {
    this.name = name;
    this.changes = changes;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}
