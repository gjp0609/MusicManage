package com.onysakura.constants;

import java.util.Arrays;
import java.util.List;

public interface Constants {
    List<String> ARTIST_WHITE_LIST = Arrays.asList("A-Lin", "A-Rise");

    enum HasLocalFile {
        HAS_LOCAL_FILE("1"),
        NO_LOCAL_FILE("0");
        private final String code;

        HasLocalFile(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static HasLocalFile getEnum(String code) {
            HasLocalFile[] values = HasLocalFile.values();
            for (HasLocalFile value : values) {
                if (value.code.equals(code)) {
                    return value;
                }
            }
            return null;
        }
    }

    enum Sort {
        DESC,
        ASC;
    }
}
