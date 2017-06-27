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

import java.util.Calendar;
import java.util.Date;

/**
 * An <code>OffsetIterator</code> modifies the dates returned by a {@link ScheduleIterator} by a
 * constant calendar offset.
 */
public class OffsetIterator implements ScheduleIterator {

  private final ScheduleIterator scheduleIterator;
  private final int field;
  private final int offset;
  private final Calendar calendar = Calendar.getInstance();

  /**
   * Create a <code>OffsetIterator</code> object.
   * 
   * @param scheduleIterator An iterator object that conforms to the <code>ScheduleIterator</code>
   *        interface.
   * @param field The time unit affected in {@link java.util.Calendar} such as
   *        {@link java.util.Calendar#HOUR} e.t.c.
   * @param offset How many units of the time unit to count to the next occurrence of the task.
   */
  public OffsetIterator(ScheduleIterator scheduleIterator, int field, int offset) {
    this.scheduleIterator = scheduleIterator;
    this.field = field;
    this.offset = offset;
  }

  public Date next() {
    Date date = scheduleIterator.next();
    if (date == null) {
      return null;
    }
    calendar.setTime(date);
    calendar.add(field, offset);
    return calendar.getTime();
  }


}
