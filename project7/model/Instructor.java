package edu.gatech.cs6310.project7.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for an Instructor.
 */
@Data
public class Instructor extends Person {
    private boolean isActive;
    private String courseCurrentTeaching;
    private List<Course> eligibleCourses = new ArrayList<>();

    @Builder
    private Instructor(final String id,
                       final String name,
                       final String address,
                       final String phoneNumber,
                       final boolean isActive) {
        super(id, name, address, phoneNumber, Collections.singletonList("INSTRUCTOR"));
        this.isActive = isActive;
    }
}



