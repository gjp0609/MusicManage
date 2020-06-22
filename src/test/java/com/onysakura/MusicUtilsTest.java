package com.onysakura;

import com.onysakura.utils.CustomLogger;
import com.onysakura.utils.FileUtils;
import com.onysakura.utils.MusicUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class MusicUtilsTest {
    Logger logger = CustomLogger.getLogger(MusicUtilsTest.class);

    @Test
    public void getFileInfo() {
        File file = new File("D:/Ent/Music/");
        List<File> fileList = FileUtils.getFileList(file);
        for (int i = 0; i < 20; i++) {
            logger.info(MusicUtils.getMusicInfo(fileList.get(i)).toString());
        }
    }
}
