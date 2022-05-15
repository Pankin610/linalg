package linalg.algorithm.comp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import linalg.algorithm.comp.AlgorithmStatsTest;
import java.time.Duration;

public class AlgorithmStatsTest {
  @Test
  void AverageTimeIsRight() {
    AlgorithmStats stats = new AlgorithmStats();
    stats.AddRun(Duration.ofMillis(5));
    stats.AddRun(Duration.ofMillis(3));
    stats.AddRun(Duration.ofMillis(7));
    Assertions.assertEquals(Duration.ofMillis(5), stats.AverageRunTime());
  }
}