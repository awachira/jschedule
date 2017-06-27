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

import java.util.Calendar;

import org.junit.*;

import org.jschedule.scheduling.util.iterators.*;

public class RestrictedDailyIteratorTest {

  @Test
  public void testRestrictedDailyIterator() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, Calendar.JUNE);
    cal.set(Calendar.DATE, 21);
    cal.set(Calendar.YEAR, 2003);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);

    int[] weekdays = new int[] {Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
        Calendar.THURSDAY, Calendar.FRIDAY};
    ScheduleIterator iterator = new RestrictedDailyIterator(9, 0, 0, weekdays, cal.getTime());
    // N.B. 21 June 2003 was a Saturday
    TimeAssert.assertDateTimeEquals("2003.06.23T09:00:00.000", iterator.next());
    TimeAssert.assertDateTimeEquals("2003.06.24T09:00:00.000", iterator.next());
    TimeAssert.assertDateTimeEquals("2003.06.25T09:00:00.000", iterator.next());
    TimeAssert.assertDateTimeEquals("2003.06.26T09:00:00.000", iterator.next());
    TimeAssert.assertDateTimeEquals("2003.06.27T09:00:00.000", iterator.next());
    TimeAssert.assertDateTimeEquals("2003.06.30T09:00:00.000", iterator.next());
  }
}
