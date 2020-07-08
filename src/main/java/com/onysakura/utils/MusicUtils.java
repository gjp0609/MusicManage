package com.onysakura.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onysakura.constants.Constants;
import com.onysakura.constants.FileType;
import com.onysakura.model.MusicLocal;
import com.onysakura.model.MusicOnline;
import com.onysakura.repository.BaseRepository;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
            int index = name.indexOf(" - ");
            String art = name.substring(0, index);
            String songName = name.substring(index + 3).substring(0, name.substring(index + 3).lastIndexOf('.'));
            LOG.debug("music name: [" + name + "], art: [" + art + "], song: [" + songName + "]");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("art", art);
            jsonObject.put("name", songName);
            return jsonObject.toString();
        }
        return null;
    }

    public static List<MusicOnline> analyze163List(Path path) throws Exception {
        ArrayList<MusicOnline> list = new ArrayList<>();
        BufferedReader reader = Files.newBufferedReader(path);
        String line = reader.readLine();
        JSONObject jsonObject = JSON.parseObject(line);
        JSONObject playlist = jsonObject.getJSONObject("playlist");
        JSONArray tracks = playlist.getJSONArray("tracks");
        tracks.forEach(track -> {
            JSONObject infoJson = (JSONObject) track;
            String musicName = infoJson.getString("name");
            String id = infoJson.getString("id");
            JSONArray artistList = infoJson.getJSONArray("ar");
            ArrayList<String> artNameList = new ArrayList<>();
            artistList.forEach(artist -> artNameList.add(((JSONObject) artist).getString("name")));
            MusicOnline musicOnline = new MusicOnline();
            musicOnline.setName(musicName);
            musicOnline.setOnlineId(id);
            musicOnline.setArt(String.join(" ", artNameList));
            musicOnline.setHasLocalFile(Constants.HasLocalFile.NO_LOCAL_FILE.getCode());
            list.add(musicOnline);
        });
        return list;
    }

    public static void main(String[] args) throws Exception {
        Path path = Paths.get("src", "main", "resources", "playlist/jp.json");
        List<MusicOnline> onlineList = analyze163List(path);
        BaseRepository<MusicOnline> musicOnlineRepository = new BaseRepository<>(MusicOnline.class);
        for (MusicOnline musicOnline : onlineList) {
            MusicOnline query = new MusicOnline();
            query.setOnlineId(musicOnline.getOnlineId());
            List<MusicOnline> select = musicOnlineRepository.select(query);
            if (select == null || select.isEmpty()) {
                musicOnline = musicOnlineRepository.insert(musicOnline);
            }
        }
        LOG.info(onlineList);
    }
}
