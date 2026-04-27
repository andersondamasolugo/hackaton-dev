package com.segurosbolivar.rvt.infrastructure.config;

import com.segurosbolivar.rvt.domain.exception.BeneficiarioNotFoundException;
import com.segurosbolivar.rvt.domain.exception.ClienteNotFoundException;
import com.segurosbolivar.rvt.domain.exception.CredencialesInvalidasException;
import com.segurosbolivar.rvt.domain.exception.FiltroRequeridoException;
import com.segurosbolivar.rvt.domain.exception.ParametroNotFoundException;
import com.segurosbolivar.rvt.domain.exception.PolizaNoActivableException;
import com.segurosbolivar.rvt.domain.exception.PolizaNotFoundException;
import com.segurosbolivar.rvt.domain.exception.PorcentajeExcedidoException;
import com.segurosbolivar.rvt.domain.exception.RvtDomainException;
import com.segurosbolivar.rvt.domain.exception.ValorFueraDeRangoException;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.ErrorResponse;
import com.segurosbolivar.rvt.infrastructure.adapter.in.rest.dto.FieldError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Manejador global de excepciones que mapea excepciones de dominio a respuestas HTTP
 * con formato estándar {@link ErrorResponse}. Incluye correlationId para trazabilidad.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Credenciales inválidas → 401 Unauthorized.
     */
    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<ErrorResponse> handleCredencialesInvalidas(CredencialesInvalidasException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getUserMessage());
    }

    /**
     * Recursos no encontrados → 404 Not Found.
     */
    @ExceptionHandler({
        PolizaNotFoundException.class,
        BeneficiarioNotFoundException.class,
        ClienteNotFoundException.class,
        ParametroNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RvtDomainException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getUserMessage());
    }

    /**
     * Conflicto de estado → 409 Conflict.
     */
    @ExceptionHandler(PolizaNoActivableException.class)
    public ResponseEntity<ErrorResponse> handleConflict(PolizaNoActivableException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getUserMessage());
    }

    /**
     * Errores de validación de dominio → 400 Bad Request.
     */
    @ExceptionHandler({
        PorcentajeExcedidoException.class,
        ValorFueraDeRangoException.class,
        FiltroRequeridoException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(RvtDomainException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getUserMessage());
    }

    /**
     * Errores de validación Jakarta (@Valid) → 400 Bad Request con detalle por campo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new FieldError(fe.getField(), fe.getDefaultMessage()))
                .toList();

        String correlationId = UUID.randomUUID().toString();
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Error de validación",
                fieldErrors,
                correlationId
        );

        log.warn("Validation error [correlationId={}]: {} field errors",
                correlationId, fieldErrors.size());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Excepción genérica → 500 Internal Server Error con mensaje genérico (sin stack trace).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        String correlationId = UUID.randomUUID().toString();

        log.error("Unhandled exception [correlationId={}]: {}", correlationId, ex.getMessage(), ex);

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Error interno del servidor",
                null,
                correlationId
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Construye una respuesta de error estándar sin detalles de campo.
     *
     * @param status  código HTTP
     * @param message mensaje descriptivo orientado al usuario
     * @return ResponseEntity con ErrorResponse
     */
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
        String correlationId = UUID.randomUUID().toString();

        log.warn("Domain exception [correlationId={}, status={}]: {}",
                correlationId, status.value(), message);

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                null,
                correlationId
        );

        return ResponseEntity.status(status).body(response);
    }
}
