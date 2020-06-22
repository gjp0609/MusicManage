package com.onysakura.utils;

import java.io.File;

public class StringUtils {

    public static void main(String[] args) {
        File file = new File("D:/Ent/Music/");
        System.err.println(file.getAbsolutePath());
        System.err.println(FileUtils.getFileList(file));
    }

}
