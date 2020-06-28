package com.onysakura;

import com.onysakura.utils.CustomLogger;
import com.onysakura.utils.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

public class StringUtilsTest {

    private static final CustomLogger.Log LOG = CustomLogger.getLogger(StringUtilsTest.class);

    @Test
    public static void main(String[] args) {
        {
            String str1 = "Andy - Just test";
            LOG.info("Str1: " + str1);
            String str2 = "Andy Jack - Just test - remix";
            LOG.info("Str2: " + str2);
            LOG.info("相似度：" + StringUtils.levenshtein(str1, str2));
        }

        {
            String str1 = "Andy - Just test";
            LOG.info("Str1: " + str1);
            String str2 = "Jack Andy - Just test - remix";
            LOG.info("Str2: " + str2);
            LOG.info("相似度：" + StringUtils.levenshtein(str1, str2));
        }
    }
}
