package fsc.request;

import java.util.Map;

public class EditCommitteeRequest extends Request {
  public final long id;
  public final Map<String, Object> changes;

  public EditCommitteeRequest(long id, Map<String, Object> changes) {
    this.id = id;
    this.changes = changes;
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }
}
