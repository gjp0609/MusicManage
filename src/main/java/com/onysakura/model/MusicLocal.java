package com.onysakura.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onysakura.constants.FileType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("MUSIC_LOCAL")
public class MusicLocal {
    private String id;
    private String name;
    private String size;
    private String path;
    private String info;
    private FileType type;

    public FileType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = FileType.getType(type);
    }

    public String getOnlineName() {
        if (this.name != null) {
            return this.name.substring(0, this.name.lastIndexOf('.'));
        } else {
            return "";
        }
    }
}
