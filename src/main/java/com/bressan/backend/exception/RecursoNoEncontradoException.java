package com.bressan.backend.exception;

/**
 * Se utiliza cuando un identificador solicitado no corresponde a un recurso existente.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
