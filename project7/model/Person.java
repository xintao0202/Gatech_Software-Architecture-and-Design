package edu.gatech.cs6310.project7.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Base class for a person
 */
@Data
@AllArgsConstructor
public class Person {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private List<String> roles;
}
