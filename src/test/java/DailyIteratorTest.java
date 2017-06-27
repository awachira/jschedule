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

public class DailyIteratorTest {

  @Test
  public void testDailyIterator() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, Calendar.JUNE);
    cal.set(Calendar.DATE, 21);
    cal.set(Calendar.YEAR, 2003);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);

    ScheduleIterator iterator = new DailyIterator(9, 0, 0, cal.getTime());
    TimeAssert.assertDateTimeEquals("2003.06.21T09:00:00.000", iterator.next());
    TimeAssert.assertDateTimeEquals("2003.06.22T09:00:00.000", iterator.next());
  }
}
