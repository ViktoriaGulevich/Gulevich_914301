package by.victoria.controller;

import by.victoria.exception.DuplicateException;
import by.victoria.exception.IncorrectDataException;
import by.victoria.exception.NoAccessException;
import by.victoria.exception.NotFoundException;
import by.victoria.model.dto.ResponseExceptionDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({DuplicateException.class, IncorrectDataException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ResponseExceptionDto validateDataException(RuntimeException e) {
        return new ResponseExceptionDto(e.getMessage());
    }

    @ExceptionHandler({DateTimeParseException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ResponseExceptionDto validateDateTimeException(DateTimeParseException e) {
        return new ResponseExceptionDto("Введите корректную дату");
    }

    @ExceptionHandler({NoAccessException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseExceptionDto validateAccessException(RuntimeException e) {
        return new ResponseExceptionDto(e.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseExceptionDto validateSearchException(RuntimeException e) {
        return new ResponseExceptionDto(e.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseExceptionDto validateAuthException(Exception e) {
        return new ResponseExceptionDto("Неверный логин или пароль");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ResponseExceptionDto validateValidationException(MethodArgumentNotValidException e) {
        return new ResponseExceptionDto(e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("<br>")));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseExceptionDto validateException(Exception e) {
        return new ResponseExceptionDto(e.getMessage());
    }
}
