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

import java.util.Calendar;

import org.jschedule.scheduling.util.iterators.*;

import static org.junit.Assert.*;
import org.junit.*;

public class OffsetIteratorTest {

  @Test
  public void testOffsetIterator() {
    ScheduleIterator t1 = new ArithmeticProgressionScheduleIterator(10, 1, 12);
    ScheduleIterator iterator = new OffsetIterator(t1, Calendar.MILLISECOND, -5);
    assertEquals(5, iterator.next().getTime());
    assertEquals(6, iterator.next().getTime());
    assertEquals(7, iterator.next().getTime());
    assertEquals(null, iterator.next());
  }
}
