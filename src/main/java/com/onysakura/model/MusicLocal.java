package com.onysakura.model;

import com.onysakura.constants.FileType;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicLocal that = (MusicLocal) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }
}
