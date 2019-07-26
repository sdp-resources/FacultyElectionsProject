package fsc.gateway;

import fsc.entity.EntityFactory;

public interface Gateway
      extends ProfileGateway, ContractTypeGateway, DivisionGateway, ElectionGateway,
                    CommitteeGateway, QueryGateway, PasswordGateway,  SessionGateway {
  EntityFactory getEntityFactory();
  void begin();
  void commit();
  void close();
  void rollback();
  void shutdown();
}
