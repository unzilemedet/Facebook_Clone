package com.muhammet.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

/**
 * Bu sınıf uygulama içinde oluşacak tüm istisnaların yakalanması için kullanılacaktır.
 * burada bu sınıfın bizim belirlediğimiz özelleştirilmiş istsinaları yakalayacağız.
 * bunun dışında ek kullandığımız kütüphanelerin istisnalarını da ayrıca belirleyip
 * yakalayacğız.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private ErrorMessage createErrorMessage(ErrorType errorType,Exception exception){
        System.out.println("Bu kısımda hata mesajlarnın loglama işlemlerini yapmalıyız.");
        System.out.println("Hata mesajı: "+ exception.toString());
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

    @ExceptionHandler(UserException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handlerJava7MonoException(UserException exception){
        System.out.println("Java7MonoException hatası...: "+ exception.toString());
        return new ResponseEntity(createErrorMessage(exception.getErrorType(),exception),exception.getErrorType().getHttpStatus());
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public ResponseEntity<String> handleArithmeticException(ArithmeticException exception){
        /**
         * Burada oluşan istisna ile ilgili eğer log tutulacak ise bu işlemler yapılır.
         */
        System.out.println("Aritmetik hatası...: "+ exception.toString());
        return ResponseEntity.ok("Sıfıra bölme işlem hatası");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        ErrorType errorType = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMissingPathVariableException(MissingPathVariableException exception){
        ErrorType errorType = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseEntity<String> handleException(Exception exception){
//        return ResponseEntity.badRequest().body("Beklenmeyen bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.");
//    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleException(Exception exception){
        ErrorType errorType = ErrorType.ERROR;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }


    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatException(
            InvalidFormatException exception) {
        ErrorType errorType = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatException(
            AccessDeniedException exception) {
        ErrorType errorType = ErrorType.ERROR_ACCESS_DENIED;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MethodArgumentTypeMismatchException exception) {

        ErrorType errorType = ErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        ErrorType errorType = ErrorType.BAD_REQUEST;
        List<String> fields = new ArrayList<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
        ErrorMessage errorMessage = createErrorMessage(errorType, exception);
        errorMessage.setFields(fields);
        return new ResponseEntity<>(errorMessage, errorType.getHttpStatus());
    }


}
