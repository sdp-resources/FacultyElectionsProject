package fsc.mock;

import fsc.entity.*;
import fsc.entity.query.Query;

public class EntityStub {

  private static EntityFactory entityFactory = new SimpleEntityFactory();
  private static long seatId = 0;

  public static Election simpleBallotElection() {
    return entityFactory.createElection(seat(), query(), entityFactory.createBallot());
  }

  public static Query query() {
    return Query.always();
  }

  public static Seat seat() {
    Seat seat = new Seat("seat name", query(), committee());
    seat.setId(seatId++);
    return seat;
  }

  public static Committee committee() {
    return new Committee("committee name", "committee description", query());
  }

  public static Election simpleBallotElection(Ballot ballot) {
    return entityFactory.createElection(null, null, ballot);
  }

  public static Profile getProfile(int i) {
    return entityFactory
                     .createProfile("name" + i, "username" + i, "division", "contract");
  }

  public static Profile getProfile(String username) {
    return entityFactory.createProfile(username, username, "division", "contract");
  }
}
