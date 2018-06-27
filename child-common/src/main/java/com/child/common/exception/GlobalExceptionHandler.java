package com.child.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory
            .getLogger(GlobalExceptionHandler.class);


    /*@ExceptionHandler(value=Exception.class)
    public Object allExceptionHandler(HttpServletRequest request,HttpServletResponse response,
                                        Exception exception) throws Exception {
        if (exception instanceof BusinessException) {
            return processException(response, (BusinessException) exception);
        }else{
            return processException(response, new BusinessException(ErrorCode.INTERNAL_ERROR, exception));
        }
    }*/

    private ModelAndView processException(HttpServletResponse response, BusinessException te) {
        ErrorCode errorCode = te.getErrorCode();
        String message = errorCode.toJson();
        logger.warn("Business Exception: " + message);

        response.setStatus(errorCode.getHttpStatusCode());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(message);
            writer.flush();
        } catch (Exception handlerException) {
            logger.warn("Handling of [" + te.getClass().getName()
                    + "] resulted in Exception", handlerException);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return new ModelAndView();
    }

}
