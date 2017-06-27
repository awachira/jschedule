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

/**
 * The public interface for a task that can be scheduled for recurring execution by a
 * {@link Scheduler}.
 */
public abstract class SchedulerTask implements Runnable {

  final Object lock = new Object();

  public static enum STATE {
    VIRGIN, SCHEDULED, CANCELLED
  }

  protected STATE state = STATE.VIRGIN;

  protected TimerTask timerTask;


  /**
   * Create a <code>SchedulerTask</code> object.
   */
  protected SchedulerTask() {}

  /**
   * The action to be performed by this scheduler task.
   */

  public abstract void run();

  /**
   * Cancels this scheduler task.
   * <p>
   * This method may be called repeatedly; the second and subsequent calls have no effect.
   * 
   * @return true if this task was already scheduled to run
   */

  public boolean cancel() {
    synchronized (lock) {
      if (timerTask != null) {
        timerTask.cancel();
      }
      boolean result = (state == STATE.SCHEDULED);
      state = STATE.CANCELLED;
      return result;
    }
  }

  /**
   * Returns the <i>scheduled</i> execution time of the most recent actual execution of this task.
   * (If this method is invoked while task execution is in progress, the return value is the
   * scheduled execution time of the ongoing task execution.)
   * 
   * @return the time at which the most recent execution of this task was scheduled to occur, in the
   *         format returned by <code>Date.getTime()</code>. The return value is undefined if the
   *         task has yet to commence its first execution.
   */

  public long scheduledExecutionTime() {
    synchronized (lock) {
      return timerTask == null ? 0 : timerTask.scheduledExecutionTime();
    }
  }

}
