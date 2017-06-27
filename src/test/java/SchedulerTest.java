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

import org.junit.*;
import static org.junit.Assert.*;

import org.jschedule.scheduling.Scheduler;

public class SchedulerTest {

  @Test
  public void testCancelScheduler() {
    Scheduler scheduler = new Scheduler();
    TestSchedulerTask task = new TestSchedulerTask(this);
    TestScheduleIterator iterator = new TestScheduleIterator();
    scheduler.schedule(task, iterator);
    waitFor(task, 2);

    scheduler.cancel();
    try {
      scheduler.schedule(new TestSchedulerTask(this), new TestScheduleIterator());
      fail("Can't schedule using a cancelled Scheduler.");
    } catch (IllegalStateException e) {
      // expected
    }
  }

  @Test
  public void testCancelSchedulerTask() {
    Scheduler scheduler = new Scheduler();
    TestSchedulerTask task = new TestSchedulerTask(this);
    assertTrue(
        "Cancel method does not prevent further executions, " + "since it has not been scheduled",
        !task.cancel());
    task = new TestSchedulerTask(this);
    TestScheduleIterator iterator = new TestScheduleIterator();
    scheduler.schedule(task, iterator);
    waitFor(task, 2);

    assertTrue("Cancel method prevents further executions", task.cancel());
    try {
      scheduler.schedule(task, new TestScheduleIterator());
      fail("Can't schedule a cancelled task.");
    } catch (IllegalStateException e) {
      // expected
    }

    scheduler.schedule(new TestSchedulerTask(this), new TestScheduleIterator());
    waitFor(task, 2);

    scheduler.cancel();

  }

  @Test
  public void testCancelScheduleIterator() {
    Scheduler scheduler = new Scheduler();
    TestSchedulerTask task = new TestSchedulerTask(this);
    TestScheduleIterator iterator = new TestScheduleIterator(2);
    scheduler.schedule(task, iterator);
    waitFor(task, 2);

    try {
      scheduler.schedule(task, new TestScheduleIterator());
      fail("Can't schedule a cancelled task.");
    } catch (IllegalStateException e) {
      // expected
    }

    scheduler.schedule(new TestSchedulerTask(this), new TestScheduleIterator());
    waitFor(task, 2);

    scheduler.cancel();
  }

  @Test
  public void testDoubleSchedule() {
    Scheduler scheduler = new Scheduler();
    TestSchedulerTask task = new TestSchedulerTask(this);
    scheduler.schedule(task, new TestScheduleIterator());
    try {
      scheduler.schedule(task, new TestScheduleIterator());
      fail("Can't schedule a task twice.");
    } catch (IllegalStateException e) {
      // expected
    }
    scheduler.cancel();
  }

  private void waitFor(TestSchedulerTask task, int iterations) {
    synchronized (this) {
      while (task.runCount() < iterations) {
        try {
          wait();
        } catch (InterruptedException e1) {
          return;
        }
      }
    }
  }

}
