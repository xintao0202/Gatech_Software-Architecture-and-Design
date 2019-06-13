package edu.gatech.cs6310.project7.controller;

import edu.gatech.cs6310.project7.model.CommandResult;
import edu.gatech.cs6310.project7.model.Course;
import edu.gatech.cs6310.project7.model.Instructor;
import edu.gatech.cs6310.project7.model.Person;
import edu.gatech.cs6310.project7.model.Student;
import edu.gatech.cs6310.project7.service.UniversitySystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller to handle all requests from the Admin page.
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UniversitySystem system;

    @Autowired
    public AdminController(final UniversitySystem system) {
        this.system = system;
    }

    @RequestMapping(value = "/term", method = RequestMethod.GET)
    public ResponseEntity getCurrentTerm() {
        final Map<String, String> body = new HashMap<>();
        body.put("currentYear", system.getCurrentSemester().getYear().toString());
        body.put("currentTerm", system.getCurrentSemester().getTerm());

        return ResponseEntity.ok(body);
    }

    @RequestMapping(value = "/term/next", method = RequestMethod.POST)
    public ResponseEntity startNextTerm() {
        system.startNextTerm();
        return getCurrentTerm();
    }

    @RequestMapping(value = "/instructor/register", method = RequestMethod.POST)
    public ResponseEntity registerInstructor(@RequestBody Person instructor) {
        CommandResult result;
        try {
            result = system.createInstructor(instructor);
        } catch (Exception e) {
            log.error("Error occurred while registering instructor.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Instructor resultInstructor = (Instructor) result.getResults().get("instructor");
        result.getResults().put("location", "/instructor/" + resultInstructor.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @RequestMapping(value = "/student/register", method = RequestMethod.POST)
    public ResponseEntity registerStudent(@RequestBody Person student) {
        CommandResult result;
        try {
            result = system.createStudent(student);
        } catch (Exception e) {
            log.error("Error occurred while registering student.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Student resultStudent = (Student) result.getResults().get("student");
        result.getResults().put("location", "/student/" + resultStudent.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @RequestMapping(value = "/course/register", method = RequestMethod.POST)
    public ResponseEntity registerCourse(@RequestBody Course course) {
        CommandResult result;
        try {
            result = system.createCourse(course);
        } catch (Exception e) {
            log.error("Error occurred while registering course.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Course resultCourse = (Course) result.getResults().get("course");
        result.getResults().put("location", "/course/" + resultCourse.getCourseID());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
