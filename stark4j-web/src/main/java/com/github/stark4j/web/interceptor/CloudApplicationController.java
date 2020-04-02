package com.github.stark4j.web.interceptor;

import com.github.stark4j.beans.dto.ApplicationInfo;
import com.github.stark4j.beans.vo.OperationResult;
import com.github.stark4j.core.support.application.ApplicationManager;
import com.github.stark4j.core.utils.IBeanUtils;
import com.github.stark4j.web.constant.RequestHeadConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Allen Created 2020/4/1
 */
@RestController
@AllArgsConstructor
@RequestMapping("/public")
@Api(tags = "Application")
public class CloudApplicationController {

    private final AbstractGlobalExceptionHandler exceptionHandler;

    private final ApplicationManager applicationManager;

    @GetMapping("/head_definition")
    @ApiOperation("获取通用的RequestHead中的定义")
    public OperationResult<Map<Object, Object>> headDefinition() {
        return OperationResult.returnOk(IBeanUtils.getDescription(RequestHeadConstant.class));
    }

    @GetMapping("/response_code")
    @ApiOperation("获取异常状态码信息")
    public OperationResult<Map<Object, Object>> responseCode() {
        return OperationResult.returnOk(exceptionHandler.getRespCode());
    }

    @GetMapping("/hello")
    @ApiOperation("获取系统相关信息")
    public OperationResult<ApplicationInfo> hello() {
        return OperationResult.returnOk(ApplicationInfo.create(applicationManager.getApplicationName(), applicationManager.getActiveProfile()));
    }

}
