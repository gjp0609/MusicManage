package com.onysakura.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onysakura.constans.FileType;
import com.onysakura.model.MusicLocal;
import com.onysakura.repository.MusicRepository;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MusicUtils {
    private static final CustomLogger.Log LOG = CustomLogger.getLogger(MusicUtils.class);

    public static MusicLocal getMusicInfo(File file) {
        if (file != null) {
            MusicLocal musicLocal = new MusicLocal();
            musicLocal.setName(file.getName());
            musicLocal.setSize(String.valueOf(file.length()));
            musicLocal.setPath(file.getAbsolutePath());
//            musicLocal.setMd5(FileUtils.getMD5(file));
            musicLocal.setType(getFileType(file).toString());
            LOG.debug("get music info: " + musicLocal);
            return musicLocal;
        }
        return null;
    }

    public static FileType getFileType(File file) {
        if (file != null) {
            String name = file.getName();
            int index = name.lastIndexOf('.');
            if (index > 0 && index - 1 != name.length()) {
                FileType fileType = FileType.getType(name.substring(index + 1));
                if (fileType != null) {
                    return fileType;
                }
            }
        }
        return FileType.OTHERS;
    }

    public static void analyze163List() throws Exception {
        Path path = Paths.get("src", "main", "resources", "jp.json");
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();
        JSONObject jsonObject = JSON.parseObject(line);
        JSONObject playlist = jsonObject.getJSONObject("playlist");
        JSONArray tracks = playlist.getJSONArray("tracks");
        tracks.forEach(track -> {
            String musicName = ((JSONObject) track).getString("name");
            JSONArray artistList = ((JSONObject) track).getJSONArray("ar");
            artistList.forEach(artist -> {
                String artistName = ((JSONObject) artist).getString("name");
            });
        });
    }

    public static void main(String[] args) throws Exception {
        MusicRepository musicRepository = MusicRepository.getInstance();
        List<MusicLocal> list = musicRepository.selectAll();
        LOG.info(JSON.toJSONString(list));
        MusicLocal local = new MusicLocal();
        local.setName("3");
        list = musicRepository.select(local);
        LOG.info(JSON.toJSONString(list));
        int delete = musicRepository.delete("726811370237984768");
        LOG.info(delete);
    }
}
