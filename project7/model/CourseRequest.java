package edu.gatech.cs6310.project7.model;

import lombok.Builder;
import lombok.Data;

/**
 *
 */
@Data
@Builder
public class CourseRequest {
    private int year;
    private String term;
    private String studentID;
    private String courseID;
    private int status; // status such as "course not find" can be represented as "CNF"
}
