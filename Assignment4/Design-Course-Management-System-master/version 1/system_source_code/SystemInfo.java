/**
 * @packages for SystemInfo class
 */
import java.io.*;
import java.util.*;

/**
 * @author zhihuixie
 * @version 0.0.1
 * @class SystemInfo includes methods to compute basic information of the system
 * @method readFile reads the file that store in the same directory as SystemInfo class
 * @method addStudentsData constructs new Student object adds this object to students list
 * @method addInstructorData constructs new Instructor object adds this object to instructors list
 * @method addCourseData constructs new Course object adds this object to courses list
 * @method addRecordData constructs new Record object adds this object to records list
 * @method testCase test computed output
 * @method main print out system information including number of students, number of records etc.
 */
public class SystemInfo {
	/**
	 * @variables
	 * students - array list to store Student object
	 * instructors - array list to store Instruct object
	 * courses - array list to store Course object
	 * academicRecords - array list to store AcademicRecord object
	 */
	public static List<Student> students = new ArrayList<Student>();
	public static List<Instructor> instructors = new ArrayList<Instructor>();
	public static List<Course> courses = new ArrayList<Course>();
	public static List<AcademicRecord> academicRecords = new ArrayList<AcademicRecord>();
	/**
	 * @method to read data from file
	 * @param file
	 * @return a list of string array
	 */
	public static List<String[]> readFile(String file){
		List<String[]> texts = new ArrayList<String[]>();
		try{
			File newFile = new File(file);
			FileReader fReader = new FileReader(newFile);
			BufferedReader bReader = new BufferedReader(fReader);
			String line = bReader.readLine();
			while (line != null){
				texts.add(line.split(","));
				line = bReader.readLine();
			}
			bReader.close();
		}catch (IOException ex){
			ex.printStackTrace();
		}
		return texts;
	}
	
	/**
	 * @method to parse students' data and add Student object to students list array
	 */
	public static void addStudentData(){
		List<String[]> studentData = readFile("students.csv");
		for (String[] text:studentData){
			Student student = new Student (true, Integer.parseInt(text[0]), text[1], text[2], text[3]);
			students.add(student);
		}
		
	}
	
	/**
	 * @method to parse instructors' data and add Instructor object to instructors list array
	 */
	public static void addInstructorData(){
		List<String[]> instructorData = readFile("instructors.csv");
		for (String[] text:instructorData){
			Instructor instructor = new Instructor (Integer.parseInt(text[0]), text[1], text[2], text[3]);
			instructors.add(instructor);
		}
	}
	
	
	/**
	 * @method to parse courses' data and add Course object to courses list array
	 */
	public static void addCourseData(){
		List<String[]> courseData = readFile("courses.csv");
		for (String[] text:courseData){
			List<String> semesters = new ArrayList<String>();
			for (int i = 2; i < text.length; i++){
				semesters.add(text[i]);
			}
			Course course = new Course ("None", Integer.parseInt(text[0]), text[1], "None", semesters);
			courses.add(course);
		}
	}
	
	
	/**
	 * @method to parse records' data and add AcademicRecord object to academicRecords list array
	 */
	public static void addRecordData(){
		List<String[]> recordData = readFile("records.csv");
		for (String[] text:recordData){
			AcademicRecord record = new AcademicRecord (Integer.parseInt(text[0]));
			record.enterNewAcademicRecord(Integer.parseInt(text[2]), Integer.parseInt(text[1]), text[4], text[3]);
			academicRecords.add(record);
		}
		
	}
	
	/**
	 * @method to test the results
	 */
	public static void testCase(String fileName, List<Integer> output){
		List<String[]> texts = readFile(fileName);
		List<Integer> results = new ArrayList<Integer>();
		for (int i = 1; i < texts.size(); i++){
			results.add(Integer.parseInt(texts.get(i)[0]));
		}
		if (results.equals(output)){
			System.out.println("Test SUCCESS for " + fileName);
		}
		else {
			System.out.println("Test FAILED for " + fileName);
		}
	}

	/**
	 * @method main to print out information as listed in the method
	 * @param args
	 */
	public static void main(String[] args){
		int countStudent = 0;
		int countInstructor = 0;
		int countCourse = 0;
		int countFallSemester = 0;
		int countSpringSemester = 0;
		int countSummerSemester = 0;
		List<Integer> coursesTakenByStudents = new ArrayList<Integer>();
		List<Integer> output =new ArrayList<Integer>();
		// (1) the total number of records in the records.csv file
		addRecordData();
		System.out.println(academicRecords.size());
		output.add(academicRecords.size());
		// (2) the total number of records in the students.csv file
		addStudentData();
		System.out.println(students.size());
		output.add(students.size());
		// (3) the number of students who haven’t taken any classes
		for (Student student: students){
			if (student.takeCourses(academicRecords) == 0) {
				countStudent ++;
			}
		}
		System.out.println(countStudent);
		output.add(countStudent);
		// (4) the total number of records in the instructors.csv file
		addInstructorData();
		System.out.println(instructors.size());
		output.add(instructors.size());
		// (5) the number of instructors who haven’t taught any classes
		for (Instructor instructor: instructors){
			instructor.getTeachCourses(academicRecords);
			if (instructor.teachCourses.isEmpty()) {
				countInstructor ++;
			}
		}
		System.out.println(countInstructor);
		output.add(countInstructor);
		// (6) the total number of records in the courses.csv file
		addCourseData();
		System.out.println(courses.size());
		output.add(courses.size());
		// (7) the number of courses that haven’t been taken by any students
		for (AcademicRecord aRecord: academicRecords){
			for (Record record: aRecord.records){
				coursesTakenByStudents.add(record.courseId);
			}
		}
		for (Course course:courses){
			if (!coursesTakenByStudents.contains(course.courseId)){
				countCourse ++;
			}
		}
		System.out.println(countCourse);
		output.add(countCourse);
		// (8) the total number of courses being offered during the Fall semester
		for (Course course:courses){
			if (course.semesters.contains("Fall")){
				countFallSemester ++;
			}
		}
		System.out.println(countFallSemester);
		output.add(countFallSemester);
		// (9) the total number of courses being offered during the Spring semester
		for (Course course:courses){
			if (course.semesters.contains("Spring")){
				countSpringSemester ++;
			}
		}
		System.out.println(countSpringSemester);
		output.add(countSpringSemester);
		// (10) the total number of courses being offered during the Summer semester
		for (Course course:courses){
			if (course.semesters.contains("Summer")){
				countSummerSemester ++;
			}
		}
		System.out.println(countSummerSemester);
		output.add(countSummerSemester);
		// Automatically test the output, uncomment the following two lines to run test cases
		// String fileName = "test_case_0_digest.txt";
		// testCase(fileName, output);
	}

}
