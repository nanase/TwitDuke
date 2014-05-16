/*
 * The MIT License
 *
 * Copyright 2014 noko
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
package net.nokok.twitduke.core.impl.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import net.nokok.twitduke.core.api.log.ErrorLogger;
import net.nokok.twitduke.core.api.log.LogPath;
import net.nokok.twitduke.core.type.ErrorMessageReceivable;

public class ErrorLogExporter implements ErrorLogger, ErrorMessageReceivable {

    private final File logFile = new File(LogPath.LOG_PATH);

    public ErrorLogExporter() {
        if ( !logFile.exists() ) {
            createNewLogFile(logFile);
        }
    }

    @Override
    public void error(Throwable e) {
        StackTraceElement[] elements = e.getStackTrace();
        appendLine(LocalDateTime.now() + newLine());
        Stream.of(elements)
                .filter(p -> !p.isNativeMethod())
                .forEach(elm -> appendLine(elm.toString() + newLine()));
        appendLine(newLine());
    }

    @Override
    public void onError(String errorMessage) {
        appendLine(errorMessage + newLine());
    }

    private void appendLine(String line) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.append(line);
        } catch (IOException ignored) {

        }
    }

    private String newLine() {
        return System.lineSeparator();
    }

    private void createNewLogFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException ex) {
            throw new InternalError("ログファイルを作成出来ません", ex);
        }
    }
}
