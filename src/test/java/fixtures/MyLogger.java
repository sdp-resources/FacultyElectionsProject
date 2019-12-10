package fixtures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {
  final static Logger logger = LoggerFactory.getLogger(MyLogger.class);

  public static void warn(String msg) {
    logger.warn(msg);
  }

  public static void info(String msg) {
    logger.info(msg);
  }

}
