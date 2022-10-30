package com.peakoapp.core.exception;

import com.peakoapp.core.constant.enums.ReCode;
import com.peakoapp.core.utils.R;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * The {@code GlobalExceptionHandler} class handles all the possible exceptions thrown in any Peako
 * server.
 *
 * @version 0.1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Authentication related exception handlers

    @ExceptionHandler(value = AccountDeletedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleAccountDeletedException(AccountDeletedException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.ACCOUNT_DELETED, null);
    }

    @ExceptionHandler(value = UnauthorizedAccessException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public R<?> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.UNAUTHORIZED, null);
    }

    @ExceptionHandler(value = ForbiddenAccessException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public R<?> handleForbiddenAccessException(ForbiddenAccessException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.FORBIDDEN, null);
    }

    @ExceptionHandler(value = AccountDisabledException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public R<?> handleAccountDisabledException(AccountDisabledException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.ACCOUNT_DISABLED, null);
    }

    @ExceptionHandler(value = AccountLockedException.class)
    @ResponseStatus(value = HttpStatus.LOCKED)
    public R<?> handleAccountLockedException(AccountLockedException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.ACCOUNT_LOCKED, null);
    }

    @ExceptionHandler(value = {AccountNotExistException.class, BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleAccountNotExistAndBadCredentialsException(AuthenticationException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return R.bad();
    }

    // Registration related exceptions

    @ExceptionHandler(value = AccountExistedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleAccountExistedException(AccountExistedException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.ACCOUNT_EXISTED, null);
    }

    // Friend service related exceptions

    @ExceptionHandler(value = AlreadyFriendException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleAlreadyFriendException(AlreadyFriendException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.ALREADY_FRIENDS, null);
    }

    // Parameter validation exceptions

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return R.bad();
    }

    @ExceptionHandler(value = BadParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleBadParameterException(BadParameterException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return R.bad();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public R<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.METHOD_NOT_ALLOWED, null);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return R.bad();
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return R.bad();
    }

    // JWT token validation exceptions generally thrown from JwtTokenParser implementations

    @ExceptionHandler(value = ExpiredJwtException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleExpiredJwtException(ExpiredJwtException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.TOKEN_EXPIRED, null);
    }

    @ExceptionHandler(value = InvalidJwtTokenException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleInvalidJwtTokenException(InvalidJwtTokenException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.TOKEN_INVALID, null);
    }

    // Internal server errors

    @ExceptionHandler(value = CoreException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleCoreException(CoreException e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.ERROR, null);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleException(Exception e) {
        logger.warn("Caught at the exception handler {}: {}", e.getClass(), e.getMessage());
        return new R<>(ReCode.ERROR, null);
    }
}
