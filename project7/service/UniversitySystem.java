package edu.gatech.cs6310.project7.service;

import edu.gatech.cs6310.project7.dao.UniversityDataSource;
import edu.gatech.cs6310.project7.exception.UniversitySystemException;
import edu.gatech.cs6310.project7.model.AcademicRecord;
import edu.gatech.cs6310.project7.model.CommandResult;
import edu.gatech.cs6310.project7.model.Course;
import edu.gatech.cs6310.project7.model.CourseOffering;
import edu.gatech.cs6310.project7.model.CourseRequest;
import edu.gatech.cs6310.project7.model.Instructor;
import edu.gatech.cs6310.project7.model.Person;
import edu.gatech.cs6310.project7.model.Semester;
import edu.gatech.cs6310.project7.model.Student;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by student on 7/18/17.
 */
@Service
public class UniversitySystem {

    @Getter
    private Semester currentSemester;
    private UniversityDataSource dataSource;

    @Autowired
    public UniversitySystem(final Semester currentSemester,
                            final UniversityDataSource dataSource) {
        this.currentSemester = currentSemester;
        this.dataSource = dataSource;
    }

    /**
     * Perform actions to start the next term.
     */
    public void startNextTerm() {
        currentSemester.startNextTerm();
    }

    public  ArrayList<String[]> readCsvFile(String fileName) {
        ArrayList<String[]> fileContent = new ArrayList<>();
        try {
            File f = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                fileContent.add(line.split(","));
                line = br.readLine(); // read a new line
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public  void  parseTermInfo() {
        String termCSVFile = "terms.csv";
        ArrayList<String[]> termInfoList = readCsvFile(termCSVFile);
        // 2. parse terms info
        for (String[] termInfo : termInfoList) {
            String termCourseID = termInfo[0];
            String term = termInfo[1];

            final Course course = dataSource.getCourseById(termCourseID);
            course.getTermsOffered().add(term);

            dataSource.updateCourse(course);
        }
    }

    public  void parseStudentInfo() {
        String studentCSVFile = "students.csv";
        ArrayList<String[]> studentInfoList = readCsvFile(studentCSVFile);
        for (String[] studentInfo : studentInfoList) {
            String studentID = studentInfo[0];
            String name = studentInfo[1];
            String address = studentInfo[2];
            String phoneNumber = studentInfo[3];

            Student newStudent = Student.builder()
                    .id(studentID)
                    .name(name)
                    .address(address)
                    .phoneNumber(phoneNumber)
                    .build();
            dataSource.createStudent(newStudent);
        }

    }

    public  void parseInstructorInfo(){
        String instructorCSVFile = "instructors.csv";
        ArrayList<String[]> instructorInfoList = readCsvFile(instructorCSVFile);
        for (String[] instructor_Info : instructorInfoList) {
            String instructorID = instructor_Info[0];
            String name = instructor_Info[1];
            String address = instructor_Info[2];
            String phoneNumber = instructor_Info[3];

            Instructor newInstructor = Instructor.builder()
                    .id(instructorID)
                    .name(name)
                    .address(address)
                    .phoneNumber(phoneNumber)
                    .build();
            dataSource.createInstructor(newInstructor);
        }
    }

    public  void parse_Course_Info() {
        String course_CSV_File = "courses.csv";

        ArrayList<String[]> courseInfoList = readCsvFile(course_CSV_File);

        //  1. parse basic course info
        for (String[] course_Info : courseInfoList) {
            String courseID = course_Info[0];
            String courseName = course_Info[1];

            Course newCourse = Course.builder()
                    .courseID(courseID)
                    .courseName(courseName)
                    .build();
            dataSource.createCourse(newCourse);
        }

    }

    public void parseEligibleCourseInfo() {
        String eligibleCSVFile = "eligible.csv";
        ArrayList<String[]> eligibleInfoList = readCsvFile(eligibleCSVFile);

        for (String[] eligible_Info : eligibleInfoList) {
            String instructorId = eligible_Info[0];
            String courseId = eligible_Info[1];

            Instructor instructor = dataSource.getInstructorById(instructorId);
            Course course = dataSource.getCourseById(courseId);

            if (instructor == null) {
                throw new UniversitySystemException("Instructor not found while adding eligible course: " + instructorId);
            }

            if (course == null) {
                throw new UniversitySystemException("Course not found while adding eligible course: " + courseId);
            }
            instructor.getEligibleCourses().add(course);
            dataSource.updateInstructor(instructor);
        }
    }

    public void parsePrereqsCourseInfo(){
        String prereqCSVFile = "prereqs.csv";
        ArrayList<String[]> prereqInfoList = readCsvFile(prereqCSVFile);
        for (String[] prereqInfo : prereqInfoList) {
            String prereqCourseID = prereqInfo[0];
            String mainCourseID = prereqInfo[1];

            final Course course = dataSource.getCourseById(mainCourseID);
            final Course prereq = dataSource.getCourseById(prereqCourseID);

            if (course == null) {
                throw new UniversitySystemException("Course not found while adding prerequisite: " + mainCourseID);
            }

            if (prereq == null) {
                throw new UniversitySystemException("Prerequisite not found while adding prerequisite: " + prereqCourseID);
            }

            course.getPrereqsCourses().add(prereq);
            dataSource.updateCourse(course);
        }
    }

    public CommandResult goToNextSemester() {
        CommandResult commandResult = CommandResult.builder().build();
        currentSemester.startNextTerm();
        commandResult.setStatus(true);
        String message="Next term start: "+currentSemester.getNextSemester().getTerm()+" "+currentSemester.getNextSemester().getYear().toString();
        commandResult.setMessage(message);
        return commandResult;
    }

    public CommandResult hireInstructor(String instructorID){
        Instructor instructor = dataSource.getInstructorById(instructorID);
        CommandResult commandResult = CommandResult.builder().build();
        String message = "";
        // 1. valid whether the instructor is hired
        if(instructor.isActive()){
            commandResult.setStatus(false);
            message = "# ERROR: instructor is already hired";
            commandResult.setMessage(message);
        }
        else {
            instructor.setActive(true);
            message="Instructor with ID " +instructorID +" is successfully hired";
            commandResult.setMessage(message);
            commandResult.setStatus(true);
        }
        return commandResult;
    }

    public CommandResult addStudent(Student student){
        Student student1 = dataSource.getStudentById(student.getId());
        CommandResult commandResult = CommandResult.builder().build();
        String message = "";
        // 1. valid whether the student exists
        if(student1!=null){
            commandResult.setStatus(false);
            message = "# ERROR: Student already exists";
            commandResult.setMessage(message);
        }
        else {
            dataSource.createStudent(student);
            message="Student with ID " +student.getId() +" is successfully added";
            commandResult.setMessage(message);
            commandResult.setStatus(true);
        }
        return commandResult;
    }

    public CommandResult addInstructor(Instructor instructor){
        Instructor instructor1 = dataSource.getInstructorById(instructor.getId());
        CommandResult commandResult = CommandResult.builder().build();
        String message = "";
        // 1. valid whether the student exists
        if(instructor1!=null){
            commandResult.setStatus(false);
            message = "# ERROR: Instructor already exists";
            commandResult.setMessage(message);
        }
        else {
            dataSource.createInstructor(instructor);
            message="Instructor with ID " +instructor.getId() +" is successfully added";
            commandResult.setMessage(message);
            commandResult.setStatus(true);
        }
        return commandResult;
    }

    public CommandResult addCourse(Course course){
        Course course1 = dataSource.getCourseById(course.getCourseID());
        CommandResult commandResult = CommandResult.builder().build();
        String message = "";
        // 1. valid whether the student exists
        if(course1!=null){
            commandResult.setStatus(false);
            message = "# ERROR: Course already exists";
            commandResult.setMessage(message);
        }
        else {
            dataSource.createCourse(course);
            message="Course with ID " +course.getCourseID() +" is successfully added";
            commandResult.setMessage(message);
            commandResult.setStatus(true);
        }
        return commandResult;
    }
    public CommandResult getStudentDetail(String id) {
        CommandResult commandResult = CommandResult.builder().build();
        String message = "";
        Student student = dataSource.getStudentById(id);
        if(student==null){
            commandResult.setStatus(false);
            message = "# ERROR: Student does not exist";
            commandResult.setMessage(message);
        }

        else {
            List<Object> resultList = new ArrayList<>();
            List<AcademicRecord> records = dataSource.getStudentRecords(id);
            Map<String, String> courseGrade = dataSource.getStudentCoursewithGrade(id);
            resultList.add(student);
            resultList.add(records);
            resultList.add(courseGrade);
            commandResult.setStatus(true);
            commandResult.setResults((Collections.singletonMap(id, resultList)));
        }
        return commandResult;
    }



    public CommandResult requestCourse(String studentId, String courseId){
        CommandResult commandResult = CommandResult.builder().build();
        String message = "";
        CourseRequest request = CourseRequest.builder()
                .year(currentSemester.getYear())
                .term(currentSemester.getTerm())
                .studentID(studentId)
                .courseID(courseId)
                .build();
        dataSource.createCourseRequest(request);

        message="request has been sent";
        commandResult.setMessage(message);
        commandResult.setStatus(true);

        return commandResult;
    }

    public CommandResult getCourseWaitList(String id) {
        CommandResult commandResult = CommandResult.builder().build();
        String message = "";
        List<String> courseWaitList = dataSource.getCourseWaitList(id);
        if(courseWaitList.size()==0){
            commandResult.setStatus(false);
            message = "# ERROR: Course does not have waitlist";
            commandResult.setMessage(message);
        }

        else {
            commandResult.setStatus(true);
            commandResult.setResults((Collections.singletonMap(id, courseWaitList)));
        }
        return commandResult;
    }
    public CommandResult getAcademicRecord(String recordId) {
        CommandResult commandResult = CommandResult.builder().build();
        String message = "";
        AcademicRecord record = dataSource.getAcademicRecord(recordId);
        if(record==null){
            commandResult.setStatus(false);
            message = "# ERROR: Record does not exist";
            commandResult.setMessage(message);
        }

        else {
            commandResult.setStatus(true);
            commandResult.setResults((Collections.singletonMap(recordId, record)));
        }
        return commandResult;
    }


    public CommandResult getInstructor(String id) {
        CommandResult commandResult = CommandResult.builder().build();
        String message = "";
        Instructor instructor = dataSource.getInstructorById(id);
        if(instructor==null){
            commandResult.setStatus(false);
            message = "# ERROR: Instructor does not exist";
            commandResult.setMessage(message);
        }

        else {
            commandResult.setStatus(true);
            commandResult.setResults((Collections.singletonMap(id, instructor)));
        }
        return commandResult;
    }

    public CommandResult assignGrade(String recordID, String instructorID, String courseID,  String studentID, String grade, String comment){

        CommandResult commandResult = CommandResult.builder().build();

        AcademicRecord academicRecord = AcademicRecord.builder()
                .id(recordID)
                .studentID(studentID)
                .courseID(courseID)
                .studentID(studentID)
                .grade(grade)
                .year(currentSemester.getYear())
                .term(currentSemester.getTerm())
                .comment(comment)
                .build();


        dataSource.createAcademicRecord(academicRecord);
        commandResult.setStatus(true);
        return commandResult;
    }
    public CommandResult validateTeachCourseRequest(String instructorID, String courseID){

        CommandResult commandResult = CommandResult.builder().build();

        CourseOffering courseOffering = CourseOffering.builder()
                .year(currentSemester.getYear())
                .term(currentSemester.getTerm())
                .instructorID(instructorID)
                .courseID(courseID)
                .availableSeats(3)
                .build();

        Instructor instructor = dataSource.getInstructorById(instructorID);
        List<String> eligibleCourses = dataSource.getEligibleCourses(instructorID);
        String errorMessage = "";

        // 1. valid whether the instructor is hired
        if(!instructor.isActive()){
            commandResult.setStatus(false);
            errorMessage = "# ERROR: instructor is not working";
            commandResult.setMessage(errorMessage);
            return commandResult;

        }

        // 2. valid whether the instructor is eligible to teach the course
        else if(!eligibleCourses.contains(courseID)){
            commandResult.setStatus(false);
            errorMessage = "# ERROR: instructor is not eligible to teach this course";
            commandResult.setMessage(errorMessage);
            return commandResult;

        }

        // 3. valid whether the instructor is already teaching a different course
        else if(instructor.getCourseCurrentTeaching() != null){
            commandResult.setStatus(false);
            errorMessage = "# ERROR: instructor is already teaching a different course";
            commandResult.setMessage(errorMessage);
            return commandResult;

        }

        // 4. request successfull
        else{
            commandResult.setStatus(true);
            dataSource.createCourseOffering(courseOffering);
            return commandResult;
        }

    }


    /**
     * The validateCourseRequest() function is used to validate course enrollment request by student
     * @param studentID
     * @param courseID
     * @return
     */
    public CommandResult validateCourseRequest(String studentID, String courseID){
        int status = 0;
        CommandResult commandResult = CommandResult.builder().build();

        CourseRequest request = CourseRequest.builder()
                .year(currentSemester.getYear())
                .term(currentSemester.getTerm())
                .studentID(studentID)
                .courseID(courseID)
                .status(status)
                .build();

        // 1. validate whether the course has been passed or not before, or already enrolled or not
        List<AcademicRecord> studentRecords = dataSource.getStudentRecords(studentID);
        Map<String, String> studentCoursewithGrade = dataSource.getStudentCoursewithGrade(studentID);
        List<String> prereqsCourses = dataSource.getPrereqsCourses(courseID);
        String errorMessage = "";

        for(AcademicRecord studentRecord : studentRecords){
            if(studentRecord.getCourseID().equals(courseID)){
                String grade = studentRecord.getGrade();
                 // (1.1). check if the student has already passed the course
                if (grade.equals("A") || grade.equals("B") || grade .equals("C")){
                    status = -100;
                    request.setStatus(status);
                    dataSource.createCourseRequest(request);
                    commandResult.setStatus(false);
                    errorMessage = "# not enrolled: course already passed before";
                    commandResult.setMessage(errorMessage);
                    return commandResult;
                }

                // (1.2) check if the student has already enrolled or not
                else if (grade.equals("_")){
                    status = -200;
                    request.setStatus(status);
                    dataSource.createCourseRequest(request);
                    commandResult.setStatus(false);
                    errorMessage = "# student already enrolled in course";
                    commandResult.setMessage(errorMessage);
                    return commandResult;
                }

            }
        }


        // 2. may need to check for the terms.csv, for termsOffered of the course, not clear yet


        // 3. validate the course prerequisites
        for(String prereqsCourse : prereqsCourses){
            // (3.1). student has not taken the prereqsCourses yet
            if(!studentCoursewithGrade.containsKey(prereqsCourse)){
                status = -300;
                request.setStatus(status);
                dataSource.createCourseRequest(request);
                commandResult.setStatus(false);
                errorMessage = "# not enrolled: missing prerequisites";
                commandResult.setMessage(errorMessage);
                return commandResult;
            }

            // (3.2). student has not passed the preresCourses yet
            else {
                String prereqsCourseGrade = studentCoursewithGrade.get(prereqsCourse);
                if(!prereqsCourseGrade.equals("A") && !prereqsCourseGrade.equals("B") && !prereqsCourseGrade.equals("C")){
                    status = -300;
                    request.setStatus(status);
                    dataSource.createCourseRequest(request);
                    commandResult.setStatus(false);
                    errorMessage = "# not enrolled: missing prerequisites";
                    commandResult.setMessage(errorMessage);
                    return commandResult;
                }
            }

        }


        // 4. validate whether the course has been signed up by an instructor / current offering
        List<String> currentCourseOfferingList = dataSource.getCurrentCourseOfferingList();
        if(!currentCourseOfferingList.contains(courseID)){
            status = -400;
            request.setStatus(status);
            dataSource.createCourseRequest(request);
            commandResult.setStatus(false);
            errorMessage = "# not enrolled: no available seats";
            commandResult.setMessage(errorMessage);
            return commandResult;
        }


        // 5. validate available seats, as discussed by piazza @167, same output as before
        // https://piazza.com/class/j2pi7gp4xe35ql?cid=167
        // need to consider the case that there are multiple instructors teach the same course in a semester
        CourseOffering courseOffering = dataSource.getCurrentCourseOffering(courseID);
        int availableSeats = courseOffering.getAvailableSeats();
        if(availableSeats <= 0){
            status = -500;
            request.setStatus(status);
            dataSource.createCourseRequest(request);
            commandResult.setStatus(false);
            errorMessage = "# not enrolled: no available seats";
            commandResult.setMessage(errorMessage);
            return commandResult;
        }
        // course is not full, can be enrolled
        else{
            //(5.1). update availableSeats
            availableSeats -= 1;
            courseOffering.setAvailableSeats(availableSeats);
            // (5.2). create and pass the successfull CourseRequest to the data source
            // here use the related courseoffering instructorID as status, for later reference
            status = Integer.valueOf(courseOffering.getInstructorID());
            request.setStatus(status);
            dataSource.createCourseRequest(request);
            commandResult.setStatus(true);
            return commandResult;
        }


    }

    public CommandResult createInstructor(final Person instructor) {
        //TODO call dao to save Instructor info

        Instructor result = Instructor.builder()
                .id("123")
                .name("John Smith")
                .address("123 Main St, Beaverton, OR, 97005")
                .phoneNumber("555-123-4567")
                .isActive(false)
                .build();

        return CommandResult.builder()
                .status(true)
                .results(Collections.singletonMap("instructor", result))
                .build();
    }

    public CommandResult getInstructorById(final String id) {
        //TODO call dao to get instructor by id
        Instructor result = Instructor.builder()
                .id("123")
                .name("John Smith")
                .address("123 Main St, Beaverton, OR, 97005")
                .phoneNumber("555-123-4567")
                .isActive(false)
                .build();

        return CommandResult.builder()
                .status(true)
                .results(Collections.singletonMap("instructor", result))
                .build();
    }
    public CommandResult createStudent(final Person student) {
        //TODO call dao to save student info
        Student result = Student.builder()
                .id("123")
                .name("John Smith")
                .address("123 Main St, Beaverton, OR, 97005")
                .phoneNumber("555-123-4567")
                .build();

        return CommandResult.builder()
                .status(true)
                .results(Collections.singletonMap("student", result))
                .build();
    }

    public CommandResult getStudentById(final String id) {
        //TODO call dao to get student info
        Student result = Student.builder()
                .id("123")
                .name("John Smith")
                .address("123 Main St, Beaverton, OR, 97005")
                .phoneNumber("555-123-4567")
                .build();

        return CommandResult.builder()
                .status(true)
                .results(Collections.singletonMap("student", result))
                .build();
    }

    public CommandResult createCourse(final Course course) {
        //TODO call dao to save course info
        Course result = Course.builder()
                .courseID("CS6310")
                .courseName("Software Architecture and Design")
                .build();
        return CommandResult.builder()
                .results(Collections.singletonMap("course", result))
                .build();
    }

    public CommandResult getCourseById(final String id) {
        //TODO call dao to get course info
        Course result = Course.builder()
                .courseID("CS6310")
                .courseName("Software Architecture and Design")
                .build();
        return CommandResult.builder()
                .results(Collections.singletonMap("course", result))
                .build();
    }
}
