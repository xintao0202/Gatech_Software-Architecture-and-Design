package edu.gatech.cs6310.project7.exception;

/**
 *
 */
public class DatasourceException extends RuntimeException {

    public DatasourceException(final String message, final Exception e) {
        super(message, e);
    }
}
