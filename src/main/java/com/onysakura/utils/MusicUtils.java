package com.onysakura.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onysakura.constans.FileType;
import com.onysakura.model.Music;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class MusicUtils {
    private static final Logger LOG = CustomLogger.getLogger(FileUtils.class);

    public static Music getMusicInfo(File file) {
        if (file != null) {
            Music music = new Music();
            music.setName(file.getName());
            music.setSize(file.length());
            music.setPath(file.getAbsolutePath());
            music.setMd5(FileUtils.getMD5(file));
            music.setType(getFileType(file));
            return music;
        }
        return null;
    }

    public static FileType getFileType(File file) {
        if (file != null) {
            String name = file.getName();
            int index = name.lastIndexOf('.');
            if (index > 0 && index - 1 != name.length()) {
                return FileType.getType(name.substring(index + 1));
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
        analyze163List();
    }
}
