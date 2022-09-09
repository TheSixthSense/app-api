package com.app.api.common.util.file;

import com.app.api.core.exception.BizException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUtil {

    public static boolean isPermissionFileExt(String fileName) {

        final String[] PERMISSION_FILE_EXT_ARR = {"JPG", "JPEG"};

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

    public static void checkPermissionImageExt(List<MultipartFile> multipartFileList) {
        for (MultipartFile multipartFile : multipartFileList) {
            boolean permissionFileExt = FileUtil.isPermissionFileExt(multipartFile.getOriginalFilename());
            System.out.println(multipartFile.getOriginalFilename());
            System.out.println(permissionFileExt);
            if (!permissionFileExt) throw BizException.withUserMessageKey("exception.common.file.extension.not.allow").build();
        }
    }
}
