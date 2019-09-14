package webserver;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class HandlebarsHelpers {

  private static final PathHelper PATH_HELPER = new PathHelper();

  public static PathHelper path() {
    return PATH_HELPER;
  }

  public static class PathHelper implements Helper<String> {
    private PathHelper() { }

    public String apply(String path, Options options) throws IOException {
      Class<Path> pathClass = Path.class;
      Class[] paramClasses = new Class[options.params.length];
      for (int i = 0; i < options.params.length; i++) {
        paramClasses[i] = options.params[i].getClass();
        if (paramClasses[i].equals(Integer.class)) {
          paramClasses[i] = Long.class;
          options.params[i] = (long) (int) options.params[i];
        }
      }
      try {
        return (String) pathClass.getDeclaredMethod(path, paramClasses)
                                 .invoke(pathClass, options.params);
      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException("Could not resolve path: " + path + "\n");
      }
    }
  }
}
