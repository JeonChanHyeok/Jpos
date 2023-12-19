package com.example.jpos_server.config;

import com.example.jpos_server.config.security.DuplicateStoreLoginIdException;
import com.example.jpos_server.domain.response.ErrorResponse;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalException {

    @ExceptionHandler(DuplicateStoreLoginIdException.class)
    public JSONObject duplicateStoreLoginIdException(){
        return ErrorResponse.JsonErrorResponse(400, "중복된 아이디 입니다.");
    }


    
}
