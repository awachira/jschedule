/*
 * Copyright 2008 Tom White (tom at tiling dot org)
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

package examples;

import java.text.SimpleDateFormat;
import java.util.*;

import org.jschedule.scheduling.*;
import org.jschedule.scheduling.util.iterators.*;

public class HeatingController {

  private final Scheduler scheduler = new Scheduler();
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS");
  private final int weekdayHourOfDay, weekdayMinute, weekdaySecond;
  private final int weekendHourOfDay, weekendMinute, weekendSecond;

  public HeatingController(int weekdayHourOfDay, int weekdayMinute, int weekdaySecond,
      int weekendHourOfDay, int weekendMinute, int weekendSecond) {
    this.weekdayHourOfDay = weekdayHourOfDay;
    this.weekdayMinute = weekdayMinute;
    this.weekdaySecond = weekdaySecond;
    this.weekendHourOfDay = weekendHourOfDay;
    this.weekendMinute = weekendMinute;
    this.weekendSecond = weekendSecond;
  }

  public void start() {
    int[] weekdays = new int[] {Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
        Calendar.THURSDAY, Calendar.FRIDAY};
    int[] weekend = new int[] {Calendar.SATURDAY, Calendar.SUNDAY};
    ScheduleIterator i = new CompositeIterator(new ScheduleIterator[] {
        new RestrictedDailyIterator(weekdayHourOfDay, weekdayMinute, weekdaySecond, weekdays),
        new RestrictedDailyIterator(weekendHourOfDay, weekendMinute, weekendSecond, weekend)});
    scheduler.schedule(new SchedulerTask() {
      public void run() {
        switchHeatingOn();
      }

      private void switchHeatingOn() {
        System.out.println("Switch heating on at " + dateFormat.format(new Date()));
        // Start a new thread to switch the heating on...
      }
    }, i);
  }

  public static void main(String[] args) {
    HeatingController heatingController = new HeatingController(8, 0, 0, 9, 0, 0);
    heatingController.start();
  }
}
