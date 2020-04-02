package com.github.stark4j.web.interceptor;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.stark4j.beans.Stark4jCode;
import com.github.stark4j.beans.vo.OperationResult;
import com.github.stark4j.core.exception.Stark4jException;
import com.github.stark4j.core.utils.IBeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;
import java.util.Map;

/**
 * @author Allen Created 2020/4/1
 */
public abstract class AbstractGlobalExceptionHandler {

    private final Map<Object, Object> respCode;

    public AbstractGlobalExceptionHandler() {
        respCode = IBeanUtils.getDescription(getResultCode());
    }

    public Map<Object, Object> getRespCode(){
        return this.respCode;
    }

    protected abstract <T extends Stark4jCode> Class<T> getResultCode();

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public OperationResult<Void> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getAllErrors()
                    .forEach(err -> sb.append(err.getDefaultMessage()).append(";"));
            return OperationResult.returnFail(Stark4jCode.VALID_PARAMETER_FAIL, sb.toString());
        }
        return OperationResult.returnFail(Stark4jCode.VALID_PARAMETER_FAIL, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public OperationResult<Void> invalidFormatException(InvalidFormatException e) {
        return OperationResult.returnFail(Stark4jCode.INVALID_FORMAT, "invalid format error.");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public OperationResult<Void> validationException(ValidationException e) {
        return OperationResult.returnFail(Stark4jCode.VALID_PARAMETER_FAIL, e.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public OperationResult<Void> httpRequestMethodNotSupportedException() {
        return OperationResult.returnFail(Stark4jCode.REQUEST_METHOD_NOT_SUPPORT, "Request method not support, please check your code.");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Stark4jException.class)
    public OperationResult<Void> Stark4jException(Stark4jException e) {
        String responseMessage = (String) respCode.getOrDefault(e.getCode(), "bad request.");
        String error = e.getMessage();
        return OperationResult.returnFail(e.getCode(), StringUtils.hasText(error) ? error : responseMessage);
    }

    @ExceptionHandler(Exception.class)
    public OperationResult<Void> exception(Exception e) {
        String errMsg = ExceptionUtils.getRootCauseMessage(e);
        return OperationResult.returnFail(Stark4jCode.SERVER_ERROR, StringUtils.hasText(errMsg) ? errMsg : "server error.");
    }

}
