/*
 * Copyright 2008 - 2009 Tom White (tom at tiling dot org) Copyright 2009 - Andrew Wachira
 * (andrewachira at gmail dot com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.jschedule.scheduling;

import java.util.*;

import org.jschedule.scheduling.util.iterators.*;

/**
 * A facility for threads to schedule recurring tasks for future execution in a background thread.
 * <p>
 * This class is thread-safe: multiple threads can share a single <code>Scheduler</code> object
 * without the need for external synchronization.
 * <p>
 * Implementation note: internally <code>Scheduler</code> uses a {@link java.util.Timer} to schedule
 * tasks.
 */
public class Scheduler {

  class SchedulerTimerTask extends TimerTask {
    private SchedulerTask schedulerTask;
    private ScheduleIterator iterator;

    public SchedulerTimerTask(SchedulerTask schedulerTask, ScheduleIterator iterator) {
      this.schedulerTask = schedulerTask;
      this.iterator = iterator;
    }

    public void run() {
      schedulerTask.run();
      reschedule(schedulerTask, iterator);
    }
  }

  private final Timer timer = new Timer();

  /**
   * Create a <code>Scheduler</code> object.
   */
  public Scheduler() {}

  /**
   * Terminates this <code>Scheduler</code>, discarding any currently scheduled tasks. Does not
   * interfere with a currently executing task (if it exists). Once a scheduler has been terminated,
   * its execution thread terminates gracefully, and no more tasks may be scheduled on it.
   * <p>
   * Note that calling this method from within the run method of a scheduler task that was invoked
   * by this scheduler absolutely guarantees that the ongoing task execution is the last task
   * execution that will ever be performed by this scheduler.
   * <p>
   * This method may be called repeatedly; the second and subsequent calls have no effect.
   */

  public void cancel() {
    timer.cancel();
  }

  /**
   * Schedules the specified task for execution according to the specified schedule. If times
   * specified by the <code>ScheduleIterator</code> are in the past they are scheduled for immediate
   * execution.
   * <p>
   * 
   * @param schedulerTask Task to be scheduled.
   * @param iterator Iterator that describes the schedule.
   * @throws IllegalStateException Thrown if task was already scheduled or cancelled, scheduler was
   *         cancelled, or scheduler thread terminated.
   */

  public void schedule(SchedulerTask schedulerTask, ScheduleIterator iterator)
      throws IllegalStateException {

    Date time = iterator.next();
    if (time == null) {
      schedulerTask.cancel();
    } else {
      synchronized (schedulerTask.lock) {
        if (schedulerTask.state != SchedulerTask.STATE.VIRGIN) {
          throw new IllegalStateException("Task already scheduled " + "or cancelled");
        }
        schedulerTask.state = SchedulerTask.STATE.SCHEDULED;
        schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask, iterator);
        timer.schedule(schedulerTask.timerTask, time);
      }
    }
  }

  private void reschedule(SchedulerTask schedulerTask, ScheduleIterator iterator) {

    Date time = iterator.next();
    if (time == null) {
      schedulerTask.cancel();
    } else {
      synchronized (schedulerTask.lock) {
        if (schedulerTask.state != SchedulerTask.STATE.CANCELLED) {
          schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask, iterator);
          timer.schedule(schedulerTask.timerTask, time);
        }
      }
    }
  }

}

