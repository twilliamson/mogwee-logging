/*
 * Copyright 2011 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.mogwee.logging;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.BrokenBarrierException;

public class TestLogger
{
    private static final Logger LOG = Logger.getLogger();

    // the following ridiculous static contortions ironically are to ensure tests are thread-safe:
    // each thread's output is logged to a different queue
    private static class CapturingAppender extends AppenderSkeleton
    {
        private static final CapturingAppender INSTANCE = new CapturingAppender();
        private static final org.apache.log4j.Logger LOG4J_LOGGER = org.apache.log4j.Logger.getLogger(TestLogger.class.getName());
        private static final ThreadLocal<Queue<LoggingEvent>> EVENTS = new ThreadLocal<Queue<LoggingEvent>>()
        {
            @Override
            protected Queue<LoggingEvent> initialValue()
            {
                return new ArrayDeque<LoggingEvent>();
            }
        };

        static {
            LOG4J_LOGGER.addAppender(INSTANCE);
            org.apache.log4j.Logger.getLogger(Logger.class.getName()).addAppender(INSTANCE);
        }

        @Override
        protected void append(LoggingEvent event)
        {
            EVENTS.get().add(event);
        }

        @Override
        public boolean requiresLayout()
        {
            return false;
        }

        @Override
        public void close()
        {
        }

        public static LoggingEvent pollEvent()
        {
            return EVENTS.get().poll();
        }

        public static void reset()
        {
            EVENTS.get().clear();
            setLogLevel(Level.ALL);
        }

        public static void setLogLevel(Level level)
        {
            LOG4J_LOGGER.setLevel(level);
        }
    }

    private static class ExplodingArgument
    {
        @Override
        public String toString()
        {
            throw new IllegalStateException("I was doomed to fail...");
        }
    }

    private static class ExplodingArgumentWithFuse
    {
        @Override
        public String toString()
        {
            throw new ExplodingException();
        }
    }

    private static class ExplodingException extends RuntimeException
    {
        @Override
        public String toString()
        {
            throw new IllegalStateException("I was doomed to fail...");
        }
    }

    private void log(Level level, String message)
    {
        if (Level.DEBUG.equals(level)) {
            LOG.debug(message);
        }
        else if (Level.INFO.equals(level)) {
            LOG.info(message);
        }
        else if (Level.WARN.equals(level)) {
            LOG.warn(message);
        }
        else if (Level.ERROR.equals(level)) {
            LOG.error(message);
        }
        else {
            Assert.fail("Unexpected level " + level);
        }
    }

    @SuppressWarnings("deprecation")
    private void logf(Level level, String message)
    {
        if (Level.DEBUG.equals(level)) {
            LOG.debugf(message);
        }
        else if (Level.INFO.equals(level)) {
            LOG.infof(message);
        }
        else if (Level.WARN.equals(level)) {
            LOG.warnf(message);
        }
        else if (Level.ERROR.equals(level)) {
            LOG.errorf(message);
        }
        else {
            Assert.fail("Unexpected level " + level);
        }
    }

    private void logf(Level level, String message, Object... args)
    {
        if (Level.DEBUG.equals(level)) {
            LOG.debugf(message, args);
        }
        else if (Level.INFO.equals(level)) {
            LOG.infof(message, args);
        }
        else if (Level.WARN.equals(level)) {
            LOG.warnf(message, args);
        }
        else if (Level.ERROR.equals(level)) {
            LOG.errorf(message, args);
        }
        else {
            Assert.fail("Unexpected level " + level);
        }
    }

    private void log(Level level, Throwable cause, String message)
    {
        if (Level.DEBUG.equals(level)) {
            LOG.debug(cause, message);
        }
        else if (Level.INFO.equals(level)) {
            LOG.info(cause, message);
        }
        else if (Level.WARN.equals(level)) {
            LOG.warn(cause, message);
        }
        else if (Level.ERROR.equals(level)) {
            LOG.error(cause, message);
        }
        else {
            Assert.fail("Unexpected level " + level);
        }
    }

    @SuppressWarnings("deprecation")
    private void logf(Level level, Throwable cause, String message)
    {
        if (Level.DEBUG.equals(level)) {
            LOG.debugf(cause, message);
        }
        else if (Level.INFO.equals(level)) {
            LOG.infof(cause, message);
        }
        else if (Level.WARN.equals(level)) {
            LOG.warnf(cause, message);
        }
        else if (Level.ERROR.equals(level)) {
            LOG.errorf(cause, message);
        }
        else {
            Assert.fail("Unexpected level " + level);
        }
    }

    private void logf(Level level, Throwable cause, String message, Object... args)
    {
        if (Level.DEBUG.equals(level)) {
            LOG.debugf(cause, message, args);
        }
        else if (Level.INFO.equals(level)) {
            LOG.infof(cause, message, args);
        }
        else if (Level.WARN.equals(level)) {
            LOG.warnf(cause, message, args);
        }
        else if (Level.ERROR.equals(level)) {
            LOG.errorf(cause, message, args);
        }
        else {
            Assert.fail("Unexpected level " + level);
        }
    }

    private void logDebug(Level level, Throwable cause, String message)
    {
        if (Level.INFO.equals(level)) {
            LOG.infoDebug(cause, message);
        }
        else if (Level.WARN.equals(level)) {
            LOG.warnDebug(cause, message);
        }
        else if (Level.ERROR.equals(level)) {
            LOG.errorDebug(cause, message);
        }
        else {
            Assert.fail("Unexpected level " + level);
        }
    }

    @SuppressWarnings("deprecation")
    private void logDebugf(Level level, Throwable cause, String message)
    {
        if (Level.INFO.equals(level)) {
            LOG.infoDebugf(cause, message);
        }
        else if (Level.WARN.equals(level)) {
            LOG.warnDebugf(cause, message);
        }
        else if (Level.ERROR.equals(level)) {
            LOG.errorDebugf(cause, message);
        }
        else {
            Assert.fail("Unexpected level " + level);
        }
    }

    private void logDebugf(Level level, Throwable cause, String message, Object... args)
    {
        if (Level.INFO.equals(level)) {
            LOG.infoDebugf(cause, message, args);
        }
        else if (Level.WARN.equals(level)) {
            LOG.warnDebugf(cause, message, args);
        }
        else if (Level.ERROR.equals(level)) {
            LOG.errorDebugf(cause, message, args);
        }
        else {
            Assert.fail("Unexpected level " + level);
        }
    }

    private void assertEvent(boolean expectLog, Level level, String message)
    {
        LoggingEvent event = CapturingAppender.pollEvent();

        if (expectLog) {
            String actual = event == null ? null : String.format("%s %s", event.getLevel(), event.getRenderedMessage());
            String expected = String.format("%s %s", level, message);
            ThrowableInformation throwableInformation = event == null ? null : event.getThrowableInformation();

            Assert.assertEquals(actual, expected);

            if (throwableInformation != null) {
                Assert.fail(String.format("Expected no exception, but got: %s", throwableInformation.getThrowableStrRep()[0]));
            }
        }
        else {
            Assert.assertNull(event);
        }
    }

    private void assertEvent(boolean expectLog, Level level, String exception, String message)
    {
        LoggingEvent event = CapturingAppender.pollEvent();

        if (expectLog) {
            String actual = event == null ? null : String.format("%s %s", event.getLevel(), event.getRenderedMessage());
            String expected = String.format("%s %s", level, message);
            ThrowableInformation throwableInformation = event == null ? null : event.getThrowableInformation();

            Assert.assertEquals(actual, expected);
            Assert.assertNotNull(throwableInformation);
            Assert.assertEquals(throwableInformation.getThrowableStrRep()[0], exception);
        }
        else {
            Assert.assertNull(event);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setup()
    {
        CapturingAppender.reset();
    }

    @Test
    public void testNonStaticWarning()
    {
        Logger.getLogger();
        assertEvent(
            true,
            Level.WARN,
            "Logger com.mogwee.logging.TestLogger wasn't allocated in static constructor -- did you forget to make the field static? (TestLogger.java:329)"
        );
    }

    @Test
    public void testDebug() throws Exception
    {
        testMessage(Level.DEBUG);
    }

    @Test
    public void testDebugf() throws Exception
    {
        testMessagef(Level.DEBUG);
    }

    @Test
    public void testInfo() throws Exception
    {
        testMessage(Level.INFO);
    }

    @Test
    public void testInfof() throws Exception
    {
        testMessagef(Level.INFO);
    }

    @Test
    public void testInfoDebug() throws Exception
    {
        testMessageDebug(Level.INFO);
    }

    @Test
    public void testInfoDebugf() throws Exception
    {
        testMessageDebugf(Level.INFO);
    }

    @Test
    public void testWarn() throws Exception
    {
        testMessage(Level.WARN);
    }

    @Test
    public void testWarnf() throws Exception
    {
        testMessagef(Level.WARN);
    }

    @Test
    public void testWarnDebug() throws Exception
    {
        testMessageDebug(Level.WARN);
    }

    @Test
    public void testWarnDebugf() throws Exception
    {
        testMessageDebugf(Level.WARN);
    }

    @Test
    public void testError() throws Exception
    {
        testMessage(Level.ERROR);
    }

    @Test
    public void testErrorf() throws Exception
    {
        testMessagef(Level.ERROR);
    }

    @Test
    public void testErrorDebug() throws Exception
    {
        testMessageDebug(Level.ERROR);
    }

    @Test
    public void testErrorDebugf() throws Exception
    {
        testMessageDebugf(Level.ERROR);
    }

    private void testMessage(Level level) throws Exception
    {
        Exception e = new BrokenBarrierException("Uh oh!");
        boolean expectLog = true;

        for (Level currentLevel : Arrays.asList(Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR)) {
            CapturingAppender.setLogLevel(currentLevel);
            Assert.assertTrue(LOG.isEnabledFor(currentLevel));

            log(level, "Hello %s");
            assertEvent(expectLog, level, "Hello %s");
            logf(level, "Hello %s");
            assertEvent(expectLog, level, "Hello %s");

            log(level, null, "Hello %s");
            assertEvent(expectLog, level, "Hello %s");
            logf(level, null, "Hello %s");
            assertEvent(expectLog, level, "Hello %s");

            log(level, e, "Hello %s");
            assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Uh oh!", "Hello %s");
            logf(level, e, "Hello %s");
            assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Uh oh!", "Hello %s");

            log(level, new BrokenBarrierException(null), "Hello %s");
            assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException", "Hello %s");
            logf(level, new BrokenBarrierException(null), "Hello %s");
            assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException", "Hello %s");

            log(level, new BrokenBarrierException("Foo\nbar"), "Hello %s");
            assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Foo\nbar", "Hello %s");
            logf(level, new BrokenBarrierException("Foo\nbar"), "Hello %s");
            assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Foo\nbar", "Hello %s");

            log(level, null);
            assertEvent(expectLog, level, null);

            logf(level, null);
            assertEvent(expectLog, level, null);

            log(level, null, null);
            assertEvent(expectLog, level, null);

            logf(level, (Exception) null, null);
            assertEvent(expectLog, level, null);

            log(level, e, null);
            assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Uh oh!", null);

            logf(level, e, null);
            assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Uh oh!", null);

            if (level.equals(currentLevel)) {
                expectLog = false;
            }
        }
    }

    private void testMessageDebug(Level level) throws Exception
    {
        Exception e = new BrokenBarrierException("Uh oh!");
        boolean expectLog = true;

        CapturingAppender.setLogLevel(Level.DEBUG);
        logDebug(level, null, "Hello %s");
        assertEvent(expectLog, level, "Hello %s");

        logDebugf(level, null, "Hello %s");
        assertEvent(expectLog, level, "Hello %s");

        logDebug(level, e, "Hello %s");
        assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Uh oh!", "Hello %s");

        logDebugf(level, e, "Hello %s");
        assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Uh oh!", "Hello %s");

        logDebugf(level, new BrokenBarrierException(null), "Hello %s");
        assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException", "Hello %s");

        logDebugf(level, new BrokenBarrierException("Foo\nbar"), "Hello %s");
        assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Foo\nbar", "Hello %s");

        logDebug(level, null, null);
        assertEvent(expectLog, level, null);

        logDebugf(level, null, null);
        assertEvent(expectLog, level, null);

        logDebug(level, e, null);
        assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Uh oh!", null);

        logDebugf(level, e, null);
        assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Uh oh!", null);

        for (Level currentLevel : Arrays.asList(Level.INFO, Level.WARN, Level.ERROR)) {
            CapturingAppender.setLogLevel(currentLevel);
            Assert.assertTrue(LOG.isEnabledFor(currentLevel));
            logDebug(level, null, "Hello %s");
            assertEvent(expectLog, level, "Hello %s");

            logDebugf(level, null, "Hello %s");
            assertEvent(expectLog, level, "Hello %s");

            logDebug(level, e, "Hello %s");
            assertEvent(expectLog, level, "Hello %s (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!");

            logDebugf(level, e, "Hello %s");
            assertEvent(expectLog, level, "Hello %s (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!");

            logDebugf(level, new BrokenBarrierException(null), "Hello %s");
            assertEvent(expectLog, level, "Hello %s (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException");

            logDebugf(level, new BrokenBarrierException("Foo\nbar"), "Hello %s");
            assertEvent(expectLog, level, "Hello %s (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Foo");

            logDebug(level, null, null);
            assertEvent(expectLog, level, null);

            logDebugf(level, null, null);
            assertEvent(expectLog, level, null);

            logDebug(level, e, null);
            assertEvent(expectLog, level, "null (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!");

            logDebugf(level, e, null);
            assertEvent(expectLog, level, "null (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!");

            if (level.equals(currentLevel)) {
                expectLog = false;
            }
        }
    }

    private void testMessagef(Level level) throws Exception
    {
        String bogusFormat = String.format("Bogus format string: %s ", level);
        Exception e = new BrokenBarrierException("Uh oh!");
        boolean expectLog = true;

        for (Level currentLevel : Arrays.asList(Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR)) {
            CapturingAppender.setLogLevel(currentLevel);
            Assert.assertTrue(LOG.isEnabledFor(currentLevel));
            logf(level, "Hello %s", 1234);
            assertEvent(expectLog, level, "Hello 1234");

            logf(level, (Exception) null, "Hello %s", 1234);
            assertEvent(expectLog, level, "Hello 1234");

            logf(level, e, "Hello %s", 1234);
            assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Uh oh!", "Hello 1234");

            logf(level, null, 1234);
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "null [1234] (java.lang.NullPointerException)"
            );

            logf(level, "Foo %d", "bar");
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Foo %d [bar] (java.util.IllegalFormatConversionException: d != java.lang.String)"
            );

            logf(level, "Foo %s %s", "bar");
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Foo %s %s [bar] (java.util.MissingFormatArgumentException: Format specifier 's')"
            );

            logf(level, "Foo %q", "bar");
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Foo %q [bar] (java.util.UnknownFormatConversionException: Conversion = 'q')"
            );

            logf(level, "Hello %s", new ExplodingArgument());
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Hello %s [toString():java.lang.IllegalStateException: I was doomed to fail...] (java.lang.IllegalStateException: I was doomed to fail...)"
            );

            logf(level, "Hello %s", new ExplodingArgumentWithFuse());
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Hello %s [toString():???] (toString():java.lang.IllegalStateException: I was doomed to fail...)"
            );

            if (level.equals(currentLevel)) {
                expectLog = false;
            }
        }
    }

    private void testMessageDebugf(Level level) throws Exception
    {
        String bogusFormat = String.format("Bogus format string: %s ", level);
        Exception e = new BrokenBarrierException("Uh oh!");
        boolean expectLog = true;

        CapturingAppender.setLogLevel(Level.DEBUG);
        logDebugf(level, null, "Hello %s", 1234);
        assertEvent(expectLog, level, "Hello 1234");

        logDebugf(level, e, "Hello %s", 1234);
        assertEvent(expectLog, level, "java.util.concurrent.BrokenBarrierException: Uh oh!", "Hello 1234");

        logDebugf(level, e, null, 1234);
        assertEvent(
            expectLog,
            level == Level.ERROR ? Level.ERROR : Level.WARN,
            "java.util.concurrent.BrokenBarrierException: Uh oh!",
            bogusFormat + "null [1234] (java.lang.NullPointerException)"
        );

        logDebugf(level, e, "Foo %d", "bar");
        assertEvent(
            expectLog,
            level == Level.ERROR ? Level.ERROR : Level.WARN,
            "java.util.concurrent.BrokenBarrierException: Uh oh!",
            bogusFormat + "Foo %d [bar] (java.util.IllegalFormatConversionException: d != java.lang.String)"
        );

        logDebugf(level, e, "Foo %s %s", "bar");
        assertEvent(
            expectLog,
            level == Level.ERROR ? Level.ERROR : Level.WARN,
            "java.util.concurrent.BrokenBarrierException: Uh oh!",
            bogusFormat + "Foo %s %s [bar] (java.util.MissingFormatArgumentException: Format specifier 's')"
        );

        logDebugf(level, e, "Foo %q", "bar");
        assertEvent(
            expectLog,
            level == Level.ERROR ? Level.ERROR : Level.WARN,
            "java.util.concurrent.BrokenBarrierException: Uh oh!",
            bogusFormat + "Foo %q [bar] (java.util.UnknownFormatConversionException: Conversion = 'q')"
        );

        logDebugf(level, e, "Hello %s", new ExplodingArgument());
        assertEvent(
            expectLog,
            level == Level.ERROR ? Level.ERROR : Level.WARN,
            "java.util.concurrent.BrokenBarrierException: Uh oh!",
            bogusFormat + "Hello %s [toString():java.lang.IllegalStateException: I was doomed to fail...] (java.lang.IllegalStateException: I was doomed to fail...)"
        );

        logDebugf(level, e, "Hello %s", new ExplodingArgumentWithFuse());
        assertEvent(
            expectLog,
            level == Level.ERROR ? Level.ERROR : Level.WARN,
            "java.util.concurrent.BrokenBarrierException: Uh oh!",
            bogusFormat + "Hello %s [toString():???] (toString():java.lang.IllegalStateException: I was doomed to fail...)"
        );

        for (Level currentLevel : Arrays.asList(Level.INFO, Level.WARN, Level.ERROR)) {
            CapturingAppender.setLogLevel(currentLevel);
            Assert.assertTrue(LOG.isEnabledFor(currentLevel));
            logDebugf(level, null, "Hello %s", 1234);
            assertEvent(expectLog, level, "Hello 1234");

            logDebugf(level, e, "Hello %s", 1234);
            assertEvent(
                expectLog,
                level,
                "Hello 1234 (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!"
            );

            logDebugf(level, e, null, 1234);
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "null [1234] (java.lang.NullPointerException) (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!"
            );

            logDebugf(level, e, "Foo %d", "bar");
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Foo %d [bar] (java.util.IllegalFormatConversionException: d != java.lang.String) (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!"
            );

            logDebugf(level, e, "Foo %s %s", "bar");
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Foo %s %s [bar] (java.util.MissingFormatArgumentException: Format specifier 's') (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!"
            );

            logDebugf(level, e, "Foo %q", "bar");
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Foo %q [bar] (java.util.UnknownFormatConversionException: Conversion = 'q') (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!"
            );

            logDebugf(level, e, "Foo %q", (Object[]) null);
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Foo %q [null] (java.util.UnknownFormatConversionException: Conversion = 'q') (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!"
            );

            logDebugf(level, e, "Hello %s %s", "bar", new ExplodingArgument());
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Hello %s %s [bar, toString():java.lang.IllegalStateException: I was doomed to fail...] (java.lang.IllegalStateException: I was doomed to fail...) (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!"
            );

            logDebugf(level, e, "Hello %s", new ExplodingArgumentWithFuse());
            assertEvent(
                expectLog && (level == Level.ERROR || currentLevel != Level.ERROR),
                level == Level.ERROR ? Level.ERROR : Level.WARN,
                bogusFormat + "Hello %s [toString():???] (toString():java.lang.IllegalStateException: I was doomed to fail...) (Switch to DEBUG for full stack trace): java.util.concurrent.BrokenBarrierException: Uh oh!"
            );

            if (level.equals(currentLevel)) {
                expectLog = false;
            }
        }
    }
}
