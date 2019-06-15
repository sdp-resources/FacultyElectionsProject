package fsc.service.query;

import fsc.entity.query.Query;
import fsc.gateway.Gateway;

public class GatewayBackedQueryValidator implements NameValidator {
  private Gateway gateway;

  public GatewayBackedQueryValidator(Gateway gateway) {
    this.gateway = gateway;
  }

  public boolean isValidValueForKey(String key, String value) {
    if (key.equals("contract")) {
      return gateway.hasContractType(value);
    }
    if (key.equals("division")) {
      return gateway.hasDivision(value);
    }
    if (key.equals("status")) {
      return value.equals("active") || value.equals("inactive");
    }
    return false;
  }

  public boolean hasQueryNamed(String name) {
    return gateway.hasQuery(name);
  }

  public Query getQueryNamed(String name) {
    return gateway.getAllQueries().get(name);
  }
}
