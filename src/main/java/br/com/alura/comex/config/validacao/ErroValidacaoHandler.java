package br.com.alura.comex.config.validacao;

import br.com.alura.comex.config.validacao.dto.ErroFormDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErroValidacaoHandler {
    @Autowired
    MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroFormDto> handle(MethodArgumentNotValidException exception) {
        List<ErroFormDto> dto = new ArrayList<ErroFormDto>();
        List<FieldError> erros = exception.getBindingResult().getFieldErrors();
        erros.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroFormDto erro = new ErroFormDto(e.getField(), mensagem);
            dto.add(erro);
        });
        return dto;
    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ErroFormDto> handle(ConstraintViolationException exception) {
        List<ErroFormDto> dto = new ArrayList<ErroFormDto>();
        exception.getConstraintViolations().forEach(e -> {
            ErroFormDto erro = new ErroFormDto(e.getPropertyPath().toString() + "-bd", e.getMessage());
            dto.add(erro);
        });
        return dto;
    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public List<ErroFormDto> handle(DataIntegrityViolationException exception) {
        List<ErroFormDto> dto = new ArrayList<ErroFormDto>();
        ErroFormDto erro = new ErroFormDto(exception.getClass().getName(), exception.getMostSpecificCause().getLocalizedMessage());
        dto.add(erro);
        return dto;
    }
/**/
@ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpMessageNotWritableException.class)
    public List<ErroFormDto> handle(HttpMessageNotWritableException exception) {
        List<ErroFormDto> dto = new ArrayList<ErroFormDto>();
        ErroFormDto erro = new ErroFormDto(exception.getClass().getName(), exception.getMostSpecificCause().getLocalizedMessage());
        dto.add(erro);
        return dto;
    }/**/
}
