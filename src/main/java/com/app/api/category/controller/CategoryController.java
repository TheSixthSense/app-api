package com.app.api.category.controller;

import com.app.api.category.dto.CategoryListDto;
import com.app.api.category.entity.Category;
import com.app.api.category.service.CategoryService;
import com.app.api.core.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "카테고리")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "List", response = RestResponse.class, message = "카테고리 조회 성공"),
            @ApiResponse(code = 400, responseContainer = "List", response = RestResponse.class, message = "카테고리 조회 실패")
    })
    @PostMapping("/category/list")
    public RestResponse<List<CategoryListDto>> getCategoryList() {
        List<CategoryListDto> categoryList = categoryService.getCategoryList();
        return RestResponse
                .withData(categoryList)
                .withUserMessageKey("success.category.list.found")
                .build();
    }
}
