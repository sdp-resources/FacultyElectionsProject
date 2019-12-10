package fsc;

import fsc.gateway.Gateway;
import gateway.InMemoryGateway;

public class Main {
  public static Gateway gateway = new InMemoryGateway();

  public Gateway getGateway() {
    return gateway;
  }
}
