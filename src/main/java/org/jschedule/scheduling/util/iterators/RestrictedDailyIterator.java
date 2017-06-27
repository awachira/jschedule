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
 * Public interface for scheduling weekly events.
 */
public class RestrictedDailyIterator implements ScheduleIterator {
  private final int[] days;
  private final Calendar calendar = Calendar.getInstance();

  private static int[] RemoveDuplicates(int[] arr) {
    if (arr.length < 2)
      return arr;

    Arrays.sort(arr);

    int i = 1, j = 0;
    while (i < arr.length) {
      if (arr[i] == arr[j])
        i++;
      else {
        j++;
        arr[j] = arr[i];
        i++;
      }
    }
    return Arrays.copyOfRange(arr, 0, (j + 1));
  }

  /**
   * A <code>RestrictedDailyIterator</code> returns a sequence of dates on subsequent days
   * (restricted to a set of days, e.g. weekdays only) representing the same time each day.
   * 
   * @param hourOfDay
   * @param minute
   * @param second The time of day in which to start the task.
   * @param days An array of the days of the week in which to run the task using the numbers that
   *        {@link java.util.Calendar#DAY_OF_WEEK} can take.
   */
  public RestrictedDailyIterator(int hourOfDay, int minute, int second, int[] days) {
    this(hourOfDay, minute, second, days, new Date());
  }

  /**
   * A <code>RestrictedDailyIterator</code> returns a sequence of dates on subsequent days
   * (restricted to a set of days, e.g. weekdays only) representing the same time each day.
   * 
   * @param hourOfDay
   * @param minute
   * @param second The time of day in which to start the task.
   * @param days An array of the days of the week in which to run the task using the numbers that
   *        {@link java.util.Calendar#DAY_OF_WEEK} can take.
   * @param date A date to start the task. Default (if left out from constructor call) is the object
   *        returned from <code>new Date()</code>.
   */
  public RestrictedDailyIterator(int hourOfDay, int minute, int second, int[] days, Date date) {

    this.days = RemoveDuplicates(days);

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
    do {
      calendar.add(Calendar.DATE, 1);
    } while (Arrays.binarySearch(days, calendar.get(Calendar.DAY_OF_WEEK)) < 0);
    return calendar.getTime();
  }

}
