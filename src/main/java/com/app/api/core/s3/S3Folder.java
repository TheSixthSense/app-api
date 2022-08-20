package com.app.api.core.s3;

import lombok.Getter;

@Getter
public enum S3Folder {
    /** IMAGE */
    IMAGE("image")
    ;

    private final String path;

    S3Folder(String path) {
        this.path = path;
    }

}
