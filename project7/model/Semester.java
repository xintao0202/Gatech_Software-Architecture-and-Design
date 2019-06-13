package edu.gatech.cs6310.project7.model;

import edu.gatech.cs6310.project7.exception.UniversitySystemException;
import lombok.Data;

/**
 * Represents a semester. Needed to keep track of the current semester across multiple user sessions.
 */
@Data
public class Semester {
    private Integer year;
    private String term;

    public Semester(Integer year, String term){
        this.year=year;
        this.term=term;
    }

    public void startNextTerm() {
        switch(term) {
            case "Fall":
                term = "Winter";
                break;
            case "Winter":
                term = "Spring";
                break;
            case "Spring":
                term = "Summer";
                break;
            case "Summer":
                term = "Fall";
                year++;
                break;
            default:
                throw new UniversitySystemException("Unknown term: " + term);
        }
    }

    public Semester getNextSemester(){
        String newTerm="";
        int newYear=year;
        switch(term) {
            case "Fall":
                newTerm = "Winter";
                break;
            case "Winter":
                newTerm = "Spring";
                break;
            case "Spring":
                newTerm = "Summer";
                break;
            case "Summer":
                newTerm = "Fall";
                newYear++;
                break;
            default:
                throw new UniversitySystemException("Unknown term: " + newTerm);
        }
        Semester sememter=new Semester(newYear,newTerm);
        return sememter;


    }

}
