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
 * A <code>CompositeIterator</code> combines a number of {@link ScheduleIterator}s into a single
 * {@link ScheduleIterator}. Duplicate dates are removed.
 */
public class CompositeIterator implements ScheduleIterator {

  private List<Date> orderedTimes = new ArrayList<Date>();
  private List<ScheduleIterator> orderedIterators = new ArrayList<ScheduleIterator>();

  /**
   * Create a <code>CompositeIterator</code> object.
   * 
   * @param scheduleIterator An array of {@link ScheduleIterator}s that are to be combined into a
   *        single {@link ScheduleIterator}.
   */

  public CompositeIterator(ScheduleIterator[] scheduleIterators) {
    for (int i = 0; i < scheduleIterators.length; i++) {
      insert(scheduleIterators[i]);
    }
  }

  private void insert(ScheduleIterator scheduleIterator) {
    Date time = scheduleIterator.next();
    if (time == null) {
      return;
    }
    int index = Collections.binarySearch(orderedTimes, time);
    if (index < 0) {
      index = -index - 1;
    }
    orderedTimes.add(index, time);
    orderedIterators.add(index, scheduleIterator);
  }

  public synchronized Date next() {
    Date next = null;
    while (!orderedTimes.isEmpty() && (next == null || next.equals((Date) orderedTimes.get(0)))) {
      next = (Date) orderedTimes.remove(0);
      insert((ScheduleIterator) orderedIterators.remove(0));
    }
    return next;
  }


}
