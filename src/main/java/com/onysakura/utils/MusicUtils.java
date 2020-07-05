package com.onysakura.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onysakura.constants.FileType;
import com.onysakura.model.MusicLocal;

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
            musicLocal.setInfo(getMusicInfo(file.getName()));
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

    public static String getMusicInfo(String name) {
        if (!StringUtils.isBlank(name) && name.contains("-")) {
//            boolean inWhiteList = false;
            String art = "";
            String songName = "";
//            for (String artist : Constants.ARTIST_WHITE_LIST) {
//                if (name.toLowerCase().startsWith(artist.toLowerCase())) {
//                    art = name.substring(0, artist.length());
//                    inWhiteList = true;
//                    songName = name.substring(artist.length() + 3).substring(0, name.substring(artist.length() + 3).lastIndexOf('.'));
//                    break;
//                }
//            }
//            if (!inWhiteList) {
                int index = name.indexOf(" - ");
                art = name.substring(0, index);
                songName = name.substring(index + 3).substring(0, name.substring(index + 3).lastIndexOf('.'));
//            }
            LOG.debug("music name: [" + name + "], art: [" + art + "], song: [" + songName + "]");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("art", art);
            jsonObject.put("name", songName);
            return jsonObject.toString();
        }
        return null;
    }

    public static void analyze163List() throws Exception {
        Path path = Paths.get("src", "main", "resources", "playlist/jp.json");
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
        List<File> fileList = FileUtils.getFileList(new File("/Files/Music"));
        for (File file : fileList) {
            System.err.println(getMusicInfo(getMusicInfo(file).getName()));
        }
    }
}
