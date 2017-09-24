package com.snapgroup.Models;

/**
 * Created by snapgroup2 on 12/09/17.
 */

public class MemberFIle {
    public String id,fileName,mimeType,path,size,fileType;

    public MemberFIle(String id, String fileName, String mimeType, String path, String size,String fileType) {
        this.id = id;
        this.fileType=fileType;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.path = path;
        this.size = size;
    }
}
