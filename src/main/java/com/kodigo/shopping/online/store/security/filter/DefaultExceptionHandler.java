package com.kodigo.shopping.online.store.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodigo.shopping.online.store.security.utils.ExceptionMessage;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ExpiredJwtException.class })
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        log.info("Exception --->ExpiredJwtException ");
        ExceptionMessage re = new ExceptionMessage("FORBIDDEN", "TOKEN EXPIRED", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(re);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ExceptionMessage> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.info("Exception --->AccessDeniedException ");
        ExceptionMessage re = new ExceptionMessage("ACCESS DENIED", "YOU DO NOT HAVE PERMISSIONS FOR THIS RESOURCE", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(re);
    }

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleAuthenticationException(Exception ex) {
        log.info("Exception --->AuthenticationException ");
        ExceptionMessage re = new ExceptionMessage("UNAUTHORIZED", "Authentication failed at controller advice",
                HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }

    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleException(Exception ex) {
        log.info("Exception --->Exception ");
        ExceptionMessage re = new ExceptionMessage("INTERNAL_SERVER_ERROR", "Application failed at controller advice",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(re);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.info("Exception --->handleHttpMessageNotReadable ");
        ExceptionMessage re = new ExceptionMessage("BAD_REQUEST", ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
    }


    protected ResponseEntity<Object> handleExceptionMissingServletRequestPartException(MissingServletRequestPartException ex) {
        log.info("Exception --->handleExceptionMissingServletRequestPartException ");
        ExceptionMessage re = new ExceptionMessage("BAD_REQUEST", ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
    }


    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info("Exception --->handleMethodArgumentNotValid ");
        logger.info(ex.getClass().getName());

        ObjectMapper mapper = new ObjectMapper();

        final List<String> errors = new ArrayList<>();
        List<Map<String,String>>mapErrors=new ArrayList<>();

        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            Map<String,String> err=new HashMap<>();
            err.put(error.getField(), error.getDefaultMessage());
            mapErrors.add(err);

            //mapErrors.put(error.getField(), error.getDefaultMessage());
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            //mapErrors.put(error.getObjectName(),error.getDefaultMessage());
            Map<String,String> objectError=new HashMap<>();
            objectError.put(error.getObjectName(),error.getDefaultMessage());
            mapErrors.add(objectError);
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        Map<String,Object> errorsFields=new HashMap<>();
        errorsFields.put("errorsFields", mapErrors);
        final ExceptionMessage apiError = new ExceptionMessage(
                errorsFields, "Validation attributes",
                HttpStatus.BAD_REQUEST.value());
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ NoSuchFileException.class })
    @ResponseBody
    public ResponseEntity<ExceptionMessage> handleExceptionFile(NoSuchFileException ex) {
        log.info("Exception --->NoSuchFileException ");
        ExceptionMessage re = new ExceptionMessage("Error al obtener archivo : ".concat(ex.getMessage()),
                ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
    }

    @ExceptionHandler(MultipartException.class)
    void handleMultipartException(MultipartException ex, HttpServletResponse response) throws IOException {
        log.info("Exception --->MultipartException ");
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Please select a file");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(ConstraintViolationException ex, HttpServletResponse response)
            throws IOException {
        log.info("Exception --->MultipartException ");
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
