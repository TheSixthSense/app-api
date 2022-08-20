package com.app.api.s3.controller;

import com.app.api.common.util.file.FileUtil;
import com.app.api.core.exception.BizException;
import com.app.api.core.response.RestResponse;
import com.app.api.core.s3.NaverS3Uploader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public RestResponse<String> uploadImage(@RequestPart(value = "file") MultipartFile multipartFile) throws Exception {
        // 파일형식 체크
        boolean permissionFileExt = FileUtil.isPermissionFileExt(multipartFile.getOriginalFilename());
        if (!permissionFileExt) throw BizException.withUserMessageKey("exception.common.file.extension.not.allow").build();

        String uploadUrl = naverS3Uploader.upload(multipartFile);
        return RestResponse
                .withData(uploadUrl)
                .withUserMessageKey("success.s3.upload.image")
                .build();
    }
}
