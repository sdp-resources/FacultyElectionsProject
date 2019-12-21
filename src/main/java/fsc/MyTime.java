package fsc;

import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;
import static org.apache.commons.lang3.time.DateUtils.MILLIS_PER_MINUTE;

public class MyTime {
  public static final int STANDARD_SESSION_MINUTES = 30;

  public static long minutesFromNow(long minutes) {
    return nowZDT().plusMinutes(minutes).toEpochSecond();
  }

  public static long minutesAgo(long minutes) {
    return nowZDT().minusMinutes(minutes).toEpochSecond();
  }

  public static boolean withinOneMinute(long time1, long time2) {
    return Math.abs(time1 - time2) / MILLIS_PER_MINUTE <= 1;
  }

  public static long standardExpirationTime() {
    return nowZDT().plusMinutes(STANDARD_SESSION_MINUTES)
                   .toEpochSecond();
  }

  public static boolean hasExpired(long expirationTime) {
    return expirationTime < nowZDT().toEpochSecond();
  }

  private static ZonedDateTime nowZDT() {
    return ZonedDateTime.now(UTC);
  }

}
