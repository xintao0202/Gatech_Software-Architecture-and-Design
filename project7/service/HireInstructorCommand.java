package edu.gatech.cs6310.project7.service;

import org.springframework.stereotype.Service;


/**
 * Hire Instructor command.
 */
@Service
public class HireInstructorCommand {

//    private final InstructorDao instructorDao;
//
//    @Autowired
//    public HireInstructorCommand(final InstructorDao instructorDao) {
//        this.instructorDao = instructorDao;
//    }
//
//    /**
//     * Sets an Instructor to active.
//     * @param parameters Parameters should include the following tokens:
//     *  1. Instructor ID
//     */
//    @Override
//    public CommandResult execute(String... parameters) {
//        final String id = parameters[0];
//        final CommandResult result = new CommandResult();
//
//        final Optional<Instructor> instructor = instructorDao.getInstructorById(id);
//
//        if (!instructor.isPresent()) {
//            result.setStatus(false);
//            result.setErrorMessage("Unable to retrieve instructor " + id);
//        }
//
//        try {
//            instructor.ifPresent(i -> {
//                instructorDao.setInstructorActive(i.getId(), true);
//                result.setStatus(true);
//            });
//        } catch (Exception e) {
//            result.setStatus(false);
//            result.setErrorMessage("Exception occurred while updating instructor. " + e.getMessage());
//        }
//
//
//        return result;
//    }
}
