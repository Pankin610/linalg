package linalg.algorithm.comp;

import java.time.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import linalg.matrix.Matrix;
import linalg.algorithm.comp.TimeMeasureUtil;

public class AlgorithmStats {
  
  public AlgorithmStats() {
    num_runs_ = 0;
    average_run_time_ = Duration.ZERO;
    best_run_ = Duration.ofHours(610);
    worst_run_ = Duration.ZERO;
  }

  public int NumRuns() {
    return num_runs_;
  }

  public Duration AverageRunTime() {
    if (NumRuns() == 0) {
      throw new UnsupportedOperationException("No runs added.");
    }
    return average_run_time_;
  }

  public Duration BestRun() {
    if (NumRuns() == 0) {
      throw new UnsupportedOperationException("No runs added.");
    }
    return best_run_;
  }

  public Duration WorstRun() {
    if (NumRuns() == 0) {
      throw new UnsupportedOperationException("No runs added.");
    }
    return worst_run_;
  }

  public void AddRun(Duration run_duration) {
    average_run_time_ = average_run_time_
      .multipliedBy(num_runs_)
      .plus(run_duration)
      .dividedBy(num_runs_ + 1);
    num_runs_++;

    if (run_duration.compareTo(worst_run_) > 0) {
      worst_run_ = run_duration;
    }
    if (run_duration.compareTo(best_run_) < 0) {
      best_run_ = run_duration;
    }
  }

  public static AlgorithmStats GetAlgoStats(
    Supplier<Matrix> data, 
    Consumer<Matrix> algo, 
    int num_runs 
  ) {
    AlgorithmStats stats = new AlgorithmStats();

    for (int i = 0; i < num_runs; i++) {
      Matrix mat = data.get();
      stats.AddRun(TimeMeasureUtil.MeasureTime(mat, algo));
    }
    return stats;
  }

  private int num_runs_;
  private Duration worst_run_;
  private Duration best_run_;
  private Duration average_run_time_;
}