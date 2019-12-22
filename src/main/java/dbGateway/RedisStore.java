package dbGateway;

import fsc.entity.session.AuthenticatedSession;
import fsc.gateway.SessionGateway;
import redis.clients.jedis.Jedis;

public class RedisStore implements SessionGateway {

  private Jedis jedis;
  private JSONSessionConverter sessionConverter = new JSONSessionConverter();

  public RedisStore() {
    jedis = new Jedis("localhost");
  }

  public void set(String key, String value) {
    jedis.set(key, value);
  }

  public String get(String key) {
    return jedis.get(key);
  }

  public void addSession(AuthenticatedSession session) {
    jedis.set(session.getToken(), sessionConverter.serialize(session));
    jedis.expireAt(session.getToken(), session.getExpirationTime().toEpochSecond());
  }

  public AuthenticatedSession getSession(String token) {
    return sessionConverter.getSession(jedis.get(token));
  }

  public void renew(AuthenticatedSession session) {
    session.setStandardExpirationTime();
    addSession(session);
  }
}
