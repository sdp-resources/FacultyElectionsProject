package fsc;

import gateway.InMemoryGateway;

public class Main {
  public static InMemoryGateway gateway = new InMemoryGateway();

  public InMemoryGateway getGateway() {
    return gateway;
  }
}
