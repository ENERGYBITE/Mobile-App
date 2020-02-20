package com.ecar.energybite.http;

import java.io.File;

public class MultipartNameValuePair {

    private MultipartMimeType mimeType;
    private String name;
    private String value;
    private File file;

    public MultipartMimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MultipartMimeType mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
