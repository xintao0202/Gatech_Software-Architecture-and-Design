package edu.gatech.cs6310.project7.controller;

import edu.gatech.cs6310.project7.model.CommandResult;
import edu.gatech.cs6310.project7.service.UniversitySystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by student on 7/18/17.
 */
@Slf4j
@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final UniversitySystem system;

    @Autowired
    public InstructorController(final UniversitySystem system) {
        this.system = system;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getInstructorById(@PathVariable("id") final String id) {
        try {
            CommandResult result = system.getInstructorById(id);
            if (!result.getStatus()) {
                if (result.getMessage().equals("Instructor not found.")) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                } else {
                    log.error("An error occurred while getting Instructor {}", id);
                    log.error(result.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
            return ResponseEntity.ok(result.getResults().get("instructor"));
        } catch (Exception e) {
            log.error("An error occurred while getting Instructor {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
