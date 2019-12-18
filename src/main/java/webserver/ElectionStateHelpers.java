package webserver;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import fsc.entity.State;

import java.io.IOException;

public enum ElectionStateHelpers implements Helper<State> {
  canChangeCandidates {
    public Object apply(State state, Options options) throws IOException {
      return state.canChangeCandidates();
    }

  },
  canChangeVoters {
    public Object apply(State state, Options options) throws IOException {
      return state.canChangeVoters();
    }

  };
}
