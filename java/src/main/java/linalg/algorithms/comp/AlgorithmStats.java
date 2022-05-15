package linalg.algorithm.comp;

import java.time.*;

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

  private int num_runs_;
  private Duration average_run_time_;
}