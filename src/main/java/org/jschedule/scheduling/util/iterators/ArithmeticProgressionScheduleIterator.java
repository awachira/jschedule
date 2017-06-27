/*
 * Copyright 2008 - 2009 Tom White (tom at tiling dot org) 
 * Copyright 2009 -      Andrew Wachira (andrewachira at gmail dot com)
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

package org.jschedule.scheduling.util.iterators;

import java.util.Date;

/**
 * A public interface for an Arithmetic Progression of task time calculated in milliseconds.
 */
public class ArithmeticProgressionScheduleIterator implements ScheduleIterator {

  private long time;
  private final long gap;
  private final long end;

  /**
   * Create a <code>ArithmeticProgressionScheduleIterator</code> object.
   * 
   * @param start Time in milliseconds since the epoch: 1 Jan 1970 00:00:00 GMT
   * @param gap Number of milliseconds until the next task.
   */
  public ArithmeticProgressionScheduleIterator(long start, long gap) {
    this(start, gap, -1);
  }

  /**
   * Create a <code>ArithmeticProgressionScheduleIterator</code> object.
   * 
   * @param start Time in milliseconds since the epoch: 1 Jan 1970 00:00:00 GMT
   * @param gap Number of milliseconds until the next task.
   * @param end Specify when to end. Default is -1 which means never end.
   */
  public ArithmeticProgressionScheduleIterator(long start, long gap, long end) {
    this.time = start;
    this.gap = gap;
    this.end = end;
  }

  public Date next() {
    if (end != -1 && time > end) {
      return null;
    } else {
      Date d = new Date(time);
      time += gap;
      return d;
    }
  }
}
