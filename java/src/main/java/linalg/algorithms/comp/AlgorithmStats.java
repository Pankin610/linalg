package linalg.algorithm.comp;

import java.time.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import linalg.matrix.Matrix;

public class AlgorithmStats {
  
  public AlgorithmStats() {
    num_runs_ = 0;
    average_run_time_ = Duration.ZERO;
  }

  public int NumRuns() {
    return num_runs_;
  }

  public Duration AverageRunTime() {
    return average_run_time_;
  }

  public void AddRun(Duration run_duration) {
    average_run_time_ = average_run_time_
      .multipliedBy(num_runs_)
      .plus(run_duration)
      .dividedBy(num_runs_ + 1);
    num_runs_++;
  }

  public static AlgorithmStats GetAlgoStats(Supplier<Matrix> data, Consumer<Matrix> algo, int num_runs) {
    Clock clock = Clock.systemDefaultZone();
    AlgorithmStats stats = new AlgorithmStats();

    for (int i = 0; i < num_runs; i++) {
      Instant start = clock.instant();
      algo.accept(data.get());
      Instant end = clock.instant();

      stats.AddRun(Duration.between(start, end));
    }
    return stats;
  }

  private int num_runs_;
  private Duration average_run_time_;
}