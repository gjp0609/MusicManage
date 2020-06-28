package com.onysakura;

import com.onysakura.utils.CustomLogger;
import com.onysakura.utils.FileUtils;
import com.onysakura.utils.MusicUtils;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class MusicUtilsTest {
    private static final CustomLogger.Log LOG = CustomLogger.getLogger(MusicUtilsTest.class);

    @Test
    public void getFileInfo() {
        File file = new File("D:/Ent/Music/");
        List<File> fileList = FileUtils.getFileList(file);
        for (int i = 0; i < 20; i++) {
            LOG.info(MusicUtils.getMusicInfo(fileList.get(i)));
        }
    }
}
