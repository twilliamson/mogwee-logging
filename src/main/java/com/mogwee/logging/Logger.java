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

import org.apache.log4j.Level;

public class Logger
{
    private static final Logger LOG = new Logger(org.apache.log4j.Logger.getLogger(Logger.class.getName()));

    private final org.apache.log4j.Logger log4j;

    public static Logger getLogger()
    {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

        for (int i = 2; i < stacktrace.length; ++i) {
            StackTraceElement element = stacktrace[i];
            String name = element.getClassName();

            if (!name.equals(Logger.class.getName())) {
                if (!"<clinit>".equals(element.getMethodName())) {
                    LOG.warnf(
                        "Logger %s wasn't allocated in static constructor -- did you forget to make the field static? (%s:%s)",
                        name,
                        element.getFileName(),
                        element.getLineNumber()
                    );
                }

                return new Logger(org.apache.log4j.Logger.getLogger(name));
            }
        }

        throw new IllegalStateException();
    }

    private Logger(org.apache.log4j.Logger log4j)
    {
        this.log4j = log4j;
    }

    public final void debugf(Throwable cause, String message, Object... args)
    {
        logf(Level.DEBUG, cause, message, args);
    }

    public final void debugf(String message, Object... args)
    {
        logf(Level.DEBUG, null, message, args);
    }

    /**
     * You meant to call {@link #debug(Throwable, String)}.
     *
     * @param cause   exception to print stack trace of
     * @param message message to log
     */
    @Deprecated
    public final void debugf(Throwable cause, String message)
    {
        log4j.debug(message, cause);
    }

    /**
     * You meant to call {@link #debug(String)}.
     *
     * @param message message to log
     */
    @Deprecated
    public final void debugf(String message)
    {
        log4j.debug(message);
    }

    public final void debug(Throwable cause, String message)
    {
        log4j.debug(message, cause);
    }

    public final void debug(String message)
    {
        log4j.debug(message);
    }

    public final void infof(Throwable cause, String message, Object... args)
    {
        logf(Level.INFO, cause, message, args);
    }

    public final void infof(String message, Object... args)
    {
        logf(Level.INFO, null, message, args);
    }

    public final void info(Throwable cause, String message)
    {
        log4j.info(message, cause);
    }

    public final void info(String message)
    {
        log4j.info(message);
    }

    /**
     * You meant to call {@link #info(Throwable, String)}.
     *
     * @param cause   exception to print stack trace of
     * @param message message to log
     */
    @Deprecated
    public final void infof(Throwable cause, String message)
    {
        log4j.info(message, cause);
    }

    /**
     * You meant to call {@link #info(String)}.
     *
     * @param message message to log
     */
    @Deprecated
    public final void infof(String message)
    {
        log4j.info(message);
    }

    public final void warnf(Throwable cause, String message, Object... args)
    {
        logf(Level.WARN, cause, message, args);
    }

    public final void warnf(String message, Object... args)
    {
        logf(Level.WARN, null, message, args);
    }

    public final void warn(Throwable cause, String message)
    {
        log4j.warn(message, cause);
    }

    public final void warn(String message)
    {
        log4j.warn(message);
    }

    /**
     * You meant to call {@link #warn(Throwable, String)}.
     *
     * @param cause   exception to print stack trace of
     * @param message message to log
     */
    @Deprecated
    public final void warnf(Throwable cause, String message)
    {
        log4j.warn(message, cause);
    }

    /**
     * You meant to call {@link #warn(String)}.
     *
     * @param message message to log
     */
    @Deprecated
    public final void warnf(String message)
    {
        log4j.warn(message);
    }

    public final void errorf(Throwable cause, String message, Object... args)
    {
        logf(Level.ERROR, cause, message, args);
    }

    public final void errorf(String message, Object... args)
    {
        logf(Level.ERROR, null, message, args);
    }

    public final void error(Throwable cause, String message)
    {
        log4j.error(message, cause);
    }

    public final void error(String message)
    {
        log4j.error(message);
    }

    /**
     * You meant to call {@link #error(Throwable, String)}.
     *
     * @param cause   exception to print stack trace of
     * @param message message to log
     */
    @Deprecated
    public final void errorf(Throwable cause, String message)
    {
        log4j.error(message, cause);
    }

    /**
     * You meant to call {@link #error(String)}.
     *
     * @param message message to log
     */
    @Deprecated
    public final void errorf(String message)
    {
        log4j.error(message);
    }

    public final void infoDebugf(final Throwable cause, final String message, final Object... args)
    {
        logDebugf(Level.INFO, cause, message, args);
    }

    public final void infoDebug(final Throwable cause, final String message)
    {
        logDebug(Level.INFO, cause, message);
    }

    /**
     * You meant to call {@link #infoDebug(Throwable, String)}.
     *
     * @param cause   exception to print stack trace of if DEBUG level is enabled
     * @param message message to log
     */
    @Deprecated
    public final void infoDebugf(Throwable cause, String message)
    {
        infoDebug(cause, message);
    }

    public final void warnDebugf(final Throwable cause, final String message, final Object... args)
    {
        logDebugf(Level.WARN, cause, message, args);
    }

    public final void warnDebug(final Throwable cause, final String message)
    {
        logDebug(Level.WARN, cause, message);
    }

    /**
     * You meant to call {@link #warnDebug(Throwable, String)}.
     *
     * @param cause   exception to print stack trace of if DEBUG level is enabled
     * @param message message to log
     */
    @Deprecated
    public final void warnDebugf(Throwable cause, String message)
    {
        warnDebug(cause, message);
    }

    public final void errorDebugf(final Throwable cause, final String message, final Object... args)
    {
        logDebugf(Level.ERROR, cause, message, args);
    }

    public final void errorDebug(final Throwable cause, final String message)
    {
        logDebug(Level.ERROR, cause, message);
    }

    /**
     * You meant to call {@link #errorDebug(Throwable, String)}.
     *
     * @param cause   exception to print stack trace of if DEBUG level is enabled
     * @param message message to log
     */
    @Deprecated
    public final void errorDebugf(Throwable cause, String message)
    {
        errorDebug(cause, message);
    }

    public boolean isEnabledFor(Level level)
    {
        return log4j.isEnabledFor(level);
    }

    private void logf(final Level level, final Throwable cause, final String message, final Object... args)
    {
        if (log4j.isEnabledFor(level)) {
            log4j.log(level, String.format(message, args), cause);
        }
    }

    private void logDebug(final Level level, final Throwable cause, final String message)
    {
        if (cause == null || log4j.isDebugEnabled()) {
            log4j.log(level, message, cause);
        }
        else if (log4j.isEnabledFor(level)) {
            String causeMessage = cause.getMessage();

            if (causeMessage == null) {
                causeMessage = cause.getClass().getName();
            }
            else {
                int index = causeMessage.indexOf("\n");

                if (index != -1) {
                    causeMessage = causeMessage.substring(0, index);
                }

                causeMessage = cause.getClass().getName() + ": " + causeMessage;
            }

            log4j.log(level, message + " (Switch to DEBUG for full stack trace): " + causeMessage);
        }
    }

    private void logDebugf(final Level level, final Throwable cause, final String message, final Object... args)
    {
        if (log4j.isEnabledFor(level)) {
            logDebug(level, cause, String.format(message, args));
        }
    }
}
