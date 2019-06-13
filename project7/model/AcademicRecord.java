package edu.gatech.cs6310.project7.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by student on 7/18/17.
 */

@Data
@Builder
public class AcademicRecord {
    private String id;
    private String studentID;
    private String courseID;
    private String instructorID;

    private int year;
    private String term;
    private String grade;
    private String comment;

}
