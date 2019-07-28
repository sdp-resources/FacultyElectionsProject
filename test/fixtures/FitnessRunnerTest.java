package fixtures;

import fitnesse.junit.FitNesseRunner;
import org.junit.runner.RunWith;

@RunWith(FitNesseRunner.class)
@FitNesseRunner.Suite("FacultyVoting")
@FitNesseRunner.FitnesseDir("./fitnesse")
@FitNesseRunner.OutputDir("./out/fitnesse-results")
public class FitnessRunnerTest {
}
