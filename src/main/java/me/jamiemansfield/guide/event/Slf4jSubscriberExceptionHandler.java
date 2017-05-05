/*
 * This file is part of Guide, licensed under the MIT License (MIT).
 *
 * Copyright (c) Jamie Mansfield <https://www.jamierocks.uk/>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.jamiemansfield.guide.event;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.slf4j.Logger;

import java.lang.reflect.Method;

/**
 * An implementation of {@link SubscriberExceptionHandler} for use with Slf4J.
 */
public class Slf4jSubscriberExceptionHandler implements SubscriberExceptionHandler {

    private final Logger logger;

    public Slf4jSubscriberExceptionHandler(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        this.logger.error(message(context), exception);
    }

    /**
     * Produces the message to use as an exception is logged.
     *
     * @param context The exception context
     * @return The message to log
     */
    private static String message(final SubscriberExceptionContext context) {
        final Method method = context.getSubscriberMethod();
        return "Exception thrown by subscriber method "
                + method.getName() + '(' + method.getParameterTypes()[0].getName() + ')'
                + " on subscriber " + context.getSubscriber()
                + " when dispatching event: " + context.getEvent();
    }

}
