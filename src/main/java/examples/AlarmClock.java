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
import java.util.Date;

import org.jschedule.scheduling.*;
import org.jschedule.scheduling.util.iterators.*;

public class AlarmClock {

  private final Scheduler scheduler = new Scheduler();
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS");
  private final int hourOfDay, minute, second;

  public AlarmClock(int hourOfDay, int minute, int second) {
    this.hourOfDay = hourOfDay;
    this.minute = minute;
    this.second = second;
  }

  public void start() {
    scheduler.schedule(new SchedulerTask() {
      public void run() {
        soundAlarm();
      }

      private void soundAlarm() {
        System.out.println("Wake up! " + "It's " + dateFormat.format(new Date()));
        // Start a new thread to sound an alarm...
      }
    }, new DailyIterator(hourOfDay, minute, second));
  }

  public static void main(String[] args) {
    AlarmClock alarmClock = new AlarmClock(17, 46, 50);
    alarmClock.start();
  }
}
