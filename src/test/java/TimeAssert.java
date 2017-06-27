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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;


/**
 * Public interface for testing time with various Date formats.
 *
 */
public class TimeAssert extends Assert {

  private String format;

  /**
   * 
   */
  public TimeAssert() {
    this("HH:mm:ss");
  }

  /**
   * @param format
   */
  public TimeAssert(String format) {
    this.format = format;
  }

  /**
   * @param expectedTimeString
   * @param time
   * @param format
   */
  private static void assertWithFormat(String expectedTimeString, Date time, String format) {
    assertEquals(expectedTimeString, new SimpleDateFormat(format).format(time));
  }


  /**
   * @param expectedCustomDateTimeString
   * @param dateTime
   */
  public void assertCustomDateTimeEquals(String expectedCustomDateTimeString, Date dateTime) {
    assertWithFormat(expectedCustomDateTimeString, dateTime, format);
  }

  /**
   * @param expectedTimeString
   * @param time
   */
  public static void assertTimeEquals(String expectedTimeString, Date time) {
    assertWithFormat(expectedTimeString, time, "HH:mm:ss.SSS");
    /* assertEquals(expectedTimeString, new SimpleDateFormat("HH:mm:ss.SSS").format(time)); */
  }

  /**
   * @param expectedDateString
   * @param date
   */
  public static void assertDateEquals(String expectedDateString, Date date) {
    assertWithFormat(expectedDateString, date, "yyyy.MM.dd");
    /* assertEquals(expectedDateString, new SimpleDateFormat("yyyy.MM.dd").format(date)); */
  }

  /**
   * @param expectedDateTimeString
   * @param dateTime
   */
  public static void assertDateTimeEquals(String expectedDateTimeString, Date dateTime) {
    assertWithFormat(expectedDateTimeString, dateTime, "yyyy.MM.dd'T'HH:mm:ss.SSS");
    /*
     * assertEquals(expectedDateTimeString, new
     * SimpleDateFormat("yyyy.MM.dd'T'HH:mm:ss.SSS").format(dateTime));
     */
  }
}
