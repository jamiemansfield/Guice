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

package me.jamiemansfield.guide;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Represents common operating systems.
 */
public enum OperatingSystem implements Predicate<String> {

    MACOS("mac"),
    LINUX("nix", "nux"),
    WINDOWS("win"),
    UNKNOWN;

    private final String[] partials;

    OperatingSystem(String... partials) {
        this.partials = partials;
    }

    @Override
    public boolean test(String s) {
        return Arrays.stream(this.partials)
                .anyMatch(s::contains);
    }

    /**
     * Gets the operating system currently running on the user's system.
     *
     * @return The current {@link OperatingSystem}.
     */
    public static OperatingSystem getOs() {
        final String osName = System.getProperty("os.name").toLowerCase();

        return Arrays.stream(values())
                .filter(os -> os.test(osName))
                .findFirst()
                .orElse(OperatingSystem.UNKNOWN);
    }

}
