package com.app.api.common.util.file;

import org.springframework.util.StringUtils;

public class FileUtil {

    public static boolean isPermissionFileExt(String fileName) throws Exception {

        final String[] PERMISSION_FILE_EXT_ARR = {"JPG"};

        if( !StringUtils.hasText(fileName) ) {
            return false;
        }

        String[] fileNameArr = fileName.split("\\.");

        if( fileNameArr.length == 0 ) {
            return false;
        }

        String ext = fileNameArr[fileNameArr.length - 1].toUpperCase();

        boolean isPermissionFileExt = false;

        for (String s : PERMISSION_FILE_EXT_ARR) {
            if (s.equals(ext)) {
                isPermissionFileExt = true;
                break;
            }
        }

        return isPermissionFileExt;

    }
}
