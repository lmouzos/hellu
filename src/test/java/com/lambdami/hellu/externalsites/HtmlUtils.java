package com.lambdami.hellu.externalsites;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class HtmlUtils {

    public static String getContent(String filePath) throws IOException {
        InputStream in = HtmlUtils.class.getResourceAsStream(filePath);
        if (in == null) {
            return "";
        }
        return IOUtils.toString(in, StandardCharsets.UTF_8.name());
    }
}
