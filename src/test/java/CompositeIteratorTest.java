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

import static org.junit.Assert.*;
import org.junit.*;

import org.jschedule.scheduling.util.iterators.*;

public class CompositeIteratorTest {

  @Test
  public void testZero() {
    ScheduleIterator compositeIterator = new CompositeIterator(new ScheduleIterator[0]);
    assertEquals(null, compositeIterator.next());
    assertEquals(null, compositeIterator.next());
  }

  @Test
  public void testOne() {
    ScheduleIterator t1 = new ArithmeticProgressionScheduleIterator(0, 3);
    ScheduleIterator compositeIterator = new CompositeIterator(new ScheduleIterator[] {t1});
    assertEquals(0, compositeIterator.next().getTime());
    assertEquals(3, compositeIterator.next().getTime());
    assertEquals(6, compositeIterator.next().getTime());
  }

  @Test
  public void testTwo() {
    ScheduleIterator t1 = new ArithmeticProgressionScheduleIterator(0, 3);
    ScheduleIterator t2 = new ArithmeticProgressionScheduleIterator(1, 2);
    ScheduleIterator compositeIterator = new CompositeIterator(new ScheduleIterator[] {t1, t2});
    assertEquals(0, compositeIterator.next().getTime());
    assertEquals(1, compositeIterator.next().getTime());
    assertEquals(3, compositeIterator.next().getTime());
    assertEquals(5, compositeIterator.next().getTime());
    assertEquals(6, compositeIterator.next().getTime());
    assertEquals(7, compositeIterator.next().getTime());
    assertEquals(9, compositeIterator.next().getTime());
    assertEquals(11, compositeIterator.next().getTime());
    assertEquals(12, compositeIterator.next().getTime());
  }

  @Test
  public void testTwoWithNull() {
    ScheduleIterator t1 = new ArithmeticProgressionScheduleIterator(0, 3, 3);
    ScheduleIterator t2 = new ArithmeticProgressionScheduleIterator(1, 2, 7);
    ScheduleIterator compositeIterator = new CompositeIterator(new ScheduleIterator[] {t1, t2});
    assertEquals(0, compositeIterator.next().getTime());
    assertEquals(1, compositeIterator.next().getTime());
    assertEquals(3, compositeIterator.next().getTime());
    assertEquals(5, compositeIterator.next().getTime());
    assertEquals(7, compositeIterator.next().getTime());
    assertNull(compositeIterator.next());
    assertNull(compositeIterator.next());
  }
}
