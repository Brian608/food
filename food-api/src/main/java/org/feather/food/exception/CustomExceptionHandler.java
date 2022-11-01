package org.feather.food.exception;

import org.feather.food.common.utils.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @projectName: food
 * @package: org.feather.food.exception
 * @className: CustomExceptionHandler
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-11-01 20:48
 * @version: 1.0
 */

@RestControllerAdvice
public class CustomExceptionHandler  {

    /**
     * 上传文件捕获异常
     * @param exception
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JSONResult handleMaxUploadFile(MaxUploadSizeExceededException exception){
        return  JSONResult.errorMsg("文件过大，不能超过500KB");

    }


}
