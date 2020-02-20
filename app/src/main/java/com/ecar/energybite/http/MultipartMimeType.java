package com.ecar.energybite.http;

public enum MultipartMimeType {

    FILE(1, "file"),

    TEXT(2, "text"),

    ;

    private int id;
    private String type;

    MultipartMimeType(int id, String type){
        this.id = id;
        this.type = type;
    }

}
