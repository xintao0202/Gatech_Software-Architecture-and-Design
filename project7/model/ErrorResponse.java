package edu.gatech.cs6310.project7.model;

import lombok.Builder;
import lombok.Data;

/**
 * HTTP Error Response object.
 */
@Data
@Builder
public class ErrorResponse {
    private String message;
    private Exception e;
}
