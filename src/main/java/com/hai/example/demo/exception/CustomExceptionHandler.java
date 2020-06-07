package com.hai.example.demo.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
//https://www.roytuts.com/spring-conditionalonexpression-example/
@ConditionalOnExpression(value = "${app.debug}")//@ConditionalOnExpression(value = "${module.enabled} or ${module.submodule.enabled}")
public class CustomExceptionHandler {
    @ExceptionHandler()
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        details.add(ex.getStackTrace().toString());
        details.add(ex.getSuppressed().toString());

        HashMap<Object, Object> body = new HashMap<>();
        body.put("result", details);
        body.put("code", 2);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleRecordExceptions(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        HashMap<Object, Object> body = new HashMap<>();
        body.put("result", details);
        body.put("code", 2);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ArithmeticException.class)
    public final ResponseEntity<Object> handleMathExceptions(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        details.add(ex.getMessage());

        HashMap<Object, Object> body = new HashMap<>();
        body.put("result", details);
        body.put("code", 2);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}