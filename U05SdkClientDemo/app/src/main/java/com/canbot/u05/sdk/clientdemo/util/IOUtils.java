package com.canbot.u05.sdk.clientdemo.util;

import java.io.Closeable;
import java.io.IOException;


public class IOUtils {

        public static void close(Closeable... closeables) {
                if (closeables != null) {
                        for (Closeable closeable : closeables) {
                                if (closeable != null) {
                                        try {
                                                closeable.close();
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }
                }
        }
}
