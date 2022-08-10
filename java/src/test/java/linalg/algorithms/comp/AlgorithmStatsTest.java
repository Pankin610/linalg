package linalg.algorithm.comp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import linalg.algorithm.comp.AlgorithmStatsTest;
import java.time.Duration;
import java.time.*;
import linalg.matrix.Matrix;
import java.util.function.Consumer;
import linalg.algorithm.comp.TimeMeasureUtil;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class AlgorithmStatsTest {
  @Test
  void AverageTimeIsRight() {
    AlgorithmStats stats = new AlgorithmStats();
    stats.AddRun(Duration.ofMillis(5));
    stats.AddRun(Duration.ofMillis(3));
    stats.AddRun(Duration.ofMillis(7));
    Assertions.assertEquals(Duration.ofMillis(5), stats.AverageRunTime());
  }

  @Test
  void WorstRunIsRight() {
    AlgorithmStats stats = new AlgorithmStats();
    stats.AddRun(Duration.ofMillis(5));
    stats.AddRun(Duration.ofMillis(3));
    stats.AddRun(Duration.ofMillis(7));
    Assertions.assertEquals(Duration.ofMillis(7), stats.WorstRun());
  }

  @Test
  void BestRunIsRight() {
    AlgorithmStats stats = new AlgorithmStats();
    stats.AddRun(Duration.ofMillis(5));
    stats.AddRun(Duration.ofMillis(3));
    stats.AddRun(Duration.ofMillis(7));
    Assertions.assertEquals(Duration.ofMillis(3), stats.BestRun());
  }
  
  @Test
  void TimeMeasuredCorrectly() {
    final MockedStatic<TimeMeasureUtil> the_mock = Mockito.mockStatic(TimeMeasureUtil.class);
    Consumer<Matrix> fake_algo = (mat) -> {};
    the_mock.when(() -> TimeMeasureUtil.MeasureTime(null, fake_algo)).thenReturn(Duration.ofMillis(5));
    try{
      AlgorithmStats stats = AlgorithmStats.GetAlgoStats(
        () -> {
          return null;
        }, 
        fake_algo,
        1
      );
      Assertions.assertEquals(Duration.ofMillis(5), stats.BestRun());
      Assertions.assertEquals(Duration.ofMillis(5), stats.WorstRun());
    }catch(Exception ignore){}
  }
}