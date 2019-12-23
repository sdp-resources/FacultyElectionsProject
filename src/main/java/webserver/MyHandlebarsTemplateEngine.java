package webserver;

import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.context.*;
import com.github.jknack.handlebars.helper.AssignHelper;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import fsc.voting.VoteTarget;
import fsc.voting.VotingRoundResult;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static fsc.voting.VotingRoundResult.*;

public class MyHandlebarsTemplateEngine extends HandlebarsTemplateEngine {
  private final String templatePath = new AssetLoader().pathTo("/templates");

  public MyHandlebarsTemplateEngine() {
    super();
    TemplateLoader templateLoader = new ClassPathTemplateLoader(templatePath, "");
    handlebars.with(templateLoader);
    handlebars.registerHelpers(ConditionalHelpers.class);
    handlebars.registerHelpers(StringHelpers.class);
    handlebars.registerHelper("datetime", new DateTimeHelper());
    registerHelper("path", HandlebarsHelpers.path());
    registerHelper("assign", new AssignHelper());
    registerHelper("remainder", new RemainderHelper());
    registerHelper("formatResult", new ResultFormattingHelper());
    registerHelper("length", new LengthHelper());
    handlebars.registerHelpers(ElectionStateHelpers.class);
  }

  public <T> void registerHelper(String name, Helper<T> helper) {
    if (handlebars.helper(name) == null) {
      handlebars.registerHelper(name, helper);
    }
  }

  public Handlebars getHandlebars() { return handlebars; }

  String render(HashMap<Object, Object> model, String templatePath) {
    return render(new ModelAndView(contextOf(model), templatePath));
  }

  private Context contextOf(HashMap<Object, Object> model) {
    return Context.newBuilder(model)
                  .resolver(MapValueResolver.INSTANCE,
                            JavaBeanValueResolver.INSTANCE,
                            FieldValueResolver.INSTANCE)
                  .build();
  }

  private static class RemainderHelper implements Helper<Integer> {
    public Object apply(Integer number, Options options) {
      Integer modulo = options.param(0);
      return number % modulo;
    }
  }

  private static class DateTimeHelper implements Helper<LocalDateTime> {
    private static final String DEFAULT_FORMAT = "dd-mm-yyyy";

    public Object apply(LocalDateTime datetime, Options options) {
      String format = options.param(0, DEFAULT_FORMAT);

      return datetime.format(DateTimeFormatter.ofPattern(format));
    }
  }

  private class ResultFormattingHelper implements Helper<VotingRoundResult> {
    public Object apply(VotingRoundResult context, Options options) throws IOException {
      if (context == null) {
        throw new RuntimeException("FormatHelper context should not be null");
      }
      if (context instanceof WinVotingRoundResult) {
        WinVotingRoundResult winResult = (WinVotingRoundResult) context;
        return "Won: " + winResult.candidate;
      }
      if (context instanceof EliminationVotingRoundResult) {
        EliminationVotingRoundResult elimResult = (EliminationVotingRoundResult) context;
        return "Eliminated: " + elimResult.candidate;
      }
      if (context instanceof DrawVotingResult) {
        DrawVotingResult tiedResult = (DrawVotingResult) context;
        return "Tied: " + tiedResult.candidates
                                .stream()
                                .map(VoteTarget::toString)
                                .collect(Collectors.joining(" "));
      }
      if (context instanceof TiedForFirstResult) {
        TiedForFirstResult tiedForFirst = (TiedForFirstResult) context;
        return "Tied for first: " + tiedForFirst.candidates
                                          .stream()
                                          .map(VoteTarget::toString)
                                          .collect(Collectors.joining(" "));
      }
      throw new IOException("Unknown class for voting round result: " + context.getClass());
    }
  }

  private class LengthHelper implements Helper<List> {
    public Object apply(List context, Options options) {
      return context.size();
    }
  }
}
