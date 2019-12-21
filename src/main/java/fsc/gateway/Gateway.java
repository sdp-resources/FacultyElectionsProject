package fsc.gateway;

import fsc.entity.EntityFactory;
import fsc.service.query.NameValidator;

public interface Gateway
      extends ProfileGateway, ContractTypeGateway, DivisionGateway, ElectionGateway,
                    CommitteeGateway, QueryGateway, PasswordGateway {
  EntityFactory getEntityFactory();
  void begin();
  void commit();
  void close();
  void rollback();
  void shutdown();
  NameValidator getNameValidator();
}
