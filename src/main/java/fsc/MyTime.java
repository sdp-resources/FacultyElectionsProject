package fsc;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.time.ZoneOffset.UTC;
import static org.apache.commons.lang3.time.DateUtils.MILLIS_PER_MINUTE;

public class MyTime {
  public static final int STANDARD_SESSION_MINUTES = 30;
  private ZonedDateTime zdt;

  public MyTime(ZonedDateTime zdt) {
    this.zdt = zdt;
  }

  private static MyTime now() {
    return new MyTime(ZonedDateTime.now(UTC));
  }

  public static MyTime fromNow(long minutes) {
    return now().plusMinutes(minutes);
  }

  public static MyTime beforeNow(long minutes) {
    return now().minusMinutes(minutes);
  }

  public static MyTime sessionStandard() {
    return fromNow(STANDARD_SESSION_MINUTES);
  }

  public static boolean withinOneMinute(MyTime time1, MyTime time2) {
    return Math.abs(time1.toEpochSecond() - time2.toEpochSecond()) / MILLIS_PER_MINUTE <= 1;
  }

  public static MyTime fromEpoch(long epoch) {
    return new MyTime(ZonedDateTime.ofInstant(Instant.ofEpochSecond(epoch), UTC));
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MyTime myTime = (MyTime) o;
    return toEpochSecond() == myTime.toEpochSecond();
  }

  public int hashCode() {
    return Objects.hash(zdt);
  }

  public long toEpochSecond() {
    return zdt.toEpochSecond();
  }

  private MyTime plusMinutes(long minutes) {
    return new MyTime(zdt.plusMinutes(minutes));
  }

  private MyTime minusMinutes(long minutes) {
    return new MyTime(zdt.minusMinutes(minutes));
  }

  public static boolean hasExpired(MyTime expirationTime) {
    return expirationTime.isBefore(now());
  }

  private boolean isBefore(MyTime other) {
    return zdt.isBefore(other.zdt);
  }

  public String format() {
    return null;
  }

  public String toString() {
    return zdt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }
}
