package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.comment.exception.BadRequestException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    /**
     * 404 NOT_FOUND — битые ссылки, фейковые id и т.п.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException e) {
        log.debug("404 NOT_FOUND: {}", e.getMessage(), e);
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .message(e.getMessage())
                .reason("The required object was not found.")
                // timestamp заполняется по @Builder.Default
                .build();
    }

    /**
     * 400 BAD_REQUEST — ваши BadRequestException (когда коммент есть, но не ваш),
     * плюс валидационные ошибки Spring (MethodArgumentNotValid и т.п.)
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(BadRequestException e) {
        log.debug("400 BAD_REQUEST (custom): {}", e.getMessage(), e);
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(e.getMessage())
                .reason(e.getReason())
                .errors(null)
                .build();
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ParametersException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationErrors(Exception e) {
        log.debug("400 BAD_REQUEST (validation): {}", e.getMessage(), e);
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(e.getMessage())
                .reason("Incorrect parameters")
                .build();
    }

    /**
     * 409 CONFLICT — дубли, нарушения связей в БД и т.д.
     */
    @ExceptionHandler({
            PSQLException.class,
            ConflictException.class,
            DataIntegrityViolationException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(Exception e) {
        log.debug("409 CONFLICT: {}", e.getMessage(), e);
        return ApiError.builder()
                .status(HttpStatus.CONFLICT.toString())
                .message(e.getMessage())
                .reason("Request is CONFLICT")
                .build();
    }

    /**
     * Любое другое исключение → 500 INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleOtherException(Throwable e) {
        log.warn("500 INTERNAL_SERVER_ERROR: {}", e.getMessage(), e);
        return ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message(e.getMessage())
                .reason("Request is INTERNAL_SERVER_ERROR")
                .build();
    }
}
