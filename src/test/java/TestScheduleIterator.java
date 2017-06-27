
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

import java.util.Calendar;
import java.util.Date;

import org.jschedule.scheduling.util.iterators.ScheduleIterator;

public class TestScheduleIterator implements ScheduleIterator {
  private final Calendar calendar = Calendar.getInstance();

  private int nextCount = 0;
  private final int maxCount;

  public TestScheduleIterator() {
    this(-1);
  }

  public TestScheduleIterator(int maxCount) {
    this.maxCount = maxCount;
  }

  public Date next() {
    nextCount++;

    if (maxCount == -1 || nextCount <= maxCount) {
      calendar.add(Calendar.SECOND, 1);
      return calendar.getTime();
    } else {
      return null;
    }

  }

}
