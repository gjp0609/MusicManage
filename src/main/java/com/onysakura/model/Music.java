package com.onysakura.model;

import com.onysakura.constans.FileType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Music {
    private String name;
    private Long size;
    private String path;
    private String md5;
    private FileType type;
}
