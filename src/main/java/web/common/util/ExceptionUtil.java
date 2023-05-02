package web.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    public static String getStackTraceWithCustomInfoToStr(String description, Throwable e) {
        return String.format("description: %s, class name: %s, stacktrace: %s", description, e.getClass().getName(), getStackTraceToStr(e));
    }

    public static String getStackTraceWithCustomInfoToStr(Throwable e) {
        return String.format("class name: %s, stacktrace: %s", e.getClass().getName(), getStackTraceToStr(e));
    }

    public static String getStackTraceToStr(Throwable e) {
        StringWriter stringWriter = null;
        PrintWriter printWriter = null;
        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);

            e.printStackTrace(printWriter);

            printWriter.flush();
            stringWriter.flush();
        } finally {
            if (null != stringWriter) {
                try {
                    stringWriter.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if (null != printWriter) {
                printWriter.close();
            }
        }
        return stringWriter.toString();
    }

}
