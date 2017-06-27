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

package org.jschedule.scheduling.util.iterators;

import java.util.*;

/**
 * Public interface for an iterator that performs a task every day at the same time.
 */
public class DailyIterator implements ScheduleIterator {

  private final Calendar calendar = Calendar.getInstance();

  /**
   * A <code>DailyIterator</code> returns a sequence of dates on subsequent days representing the
   * same time each day.
   * 
   * @param hourOfDay
   * @param minute
   * @param second The time of the day in which the task is performed.
   */
  public DailyIterator(int hourOfDay, int minute, int second) {
    this(hourOfDay, minute, second, new Date());
  }

  /**
   * A <code>DailyIterator</code> returns a sequence of dates on subsequent days representing the
   * same time each day.
   * 
   * @param hourOfDay
   * @param minute
   * @param second The time of the day in which the task is performed.
   * @param date The date in which to start. Default is the object <code>new Date()</code>.
   */
  public DailyIterator(int hourOfDay, int minute, int second, Date date) {
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
    calendar.set(Calendar.MINUTE, minute);
    calendar.set(Calendar.SECOND, second);
    calendar.set(Calendar.MILLISECOND, 0);
    if (!calendar.getTime().before(date)) {
      calendar.add(Calendar.DATE, -1);
    }
  }

  public Date next() {
    calendar.add(Calendar.DATE, 1);
    return calendar.getTime();
  }

}
