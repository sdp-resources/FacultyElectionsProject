package fixtures;

import fitnesse.junit.FitNesseRunner;
import org.junit.runner.RunWith;

@RunWith(FitNesseRunner.class)
@FitNesseRunner.Suite("FacultyVoting")
@FitNesseRunner.FitnesseDir("./src/fitnesse")
@FitNesseRunner.OutputDir("./build/fitnesse-results")
public class FitnessRunnerTest {
}
