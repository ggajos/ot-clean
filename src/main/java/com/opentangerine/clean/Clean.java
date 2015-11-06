/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 opentangerine.com
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
package com.opentangerine.clean;

import com.jcabi.log.Logger;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * This is initial class, should be changed to something else.
 *
 * @author Grzegorz Gajos (grzegorz.gajos@opentangerine.com)
 * @version $Id$
 */
@SuppressWarnings("PMD.DoNotUseThreads")
public final class Clean implements Runnable {

    /**
     * Cleaning mode.
     */
    private final transient Mode mode;

    /**
     * Working path.
     */
    private final transient Path path;

    /**
     * Clean application.
     *
     * @param cpath Working directory.
     * @param cargs Execution arguments.
     */
    public Clean(final Path cpath, final String... cargs) {
        this(cpath, new Mode(cargs));
    }

    /**
     * Ctor.
     *
     * @param cpath Working directory.
     * @param cmode Mode.
     */
    public Clean(final Path cpath, final Mode cmode) {
        this.mode = cmode;
        this.path = cpath;
    }

    /**
     * Entry point of application.
     *
     * @param args Application arguments.
     */
    public static void main(final String... args) {
        new Console().help();
        final Path cpath = Paths.get(System.getProperty("user.dir"));
        new Clean(cpath, args).run();
        Logger.info(Clean.class, cpath.toString());
    }

    @Override
    public void run() {
        final Delete delete = new Delete(this.mode);
        if (this.path.resolve("pom.xml").toFile().exists()) {
            delete.directory(this.path.resolve("target"));
        }
        if (this.mode.recurrence()) {
            Arrays
                .stream(this.path.toFile().listFiles(File::isDirectory))
                .forEach(it -> new Clean(it.toPath(), this.mode).run());
        }
        Logger.debug(this, "Finished.");
    }
}
