package com.onysakura.model;

import com.onysakura.constants.Constants;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("MUSIC_ONLINE")
public class MusicOnline {
    private String id;
    private String art;
    private String name;
    private String onlineId;
    private Constants.HasLocalFile hasLocalFile;
//    private List<String> localFileId;


    public String getHasLocalFile() {
        return hasLocalFile.getCode();
    }

    public void setHasLocalFile(String hasLocalFile) {
        this.hasLocalFile = Constants.HasLocalFile.getEnum(hasLocalFile);
    }

    public String getOnlineName() {
        return this.art + " - " + this.name;
    }
}
