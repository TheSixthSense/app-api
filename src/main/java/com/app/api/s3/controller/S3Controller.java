package com.app.api.s3.controller;

import com.app.api.common.util.file.FileUtil;
import com.app.api.core.response.RestResponse;
import com.app.api.core.s3.NaverS3Uploader;
import com.app.api.core.s3.S3Folder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "S3")
@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final NaverS3Uploader naverS3Uploader;

    @ApiOperation(value = "이미지 업로드")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "이미지 업로드 성공"),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "이미지 업로드 실패")
    })
    @PostMapping("/image")
    public RestResponse<List<String>> uploadImage(@RequestPart(value = "file") List<MultipartFile> multipartFileList) {
        // 파일형식 체크
        FileUtil.checkPermissionImageExt(multipartFileList);

        List<String> uploadUrlList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            uploadUrlList.add(naverS3Uploader.upload(multipartFile, S3Folder.IMAGE));
        }

        return RestResponse
                .withData(uploadUrlList)
                .withUserMessageKey("success.s3.upload.image")
                .build();
    }
}
