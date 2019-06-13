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
	public static List<Prereqs> prereqs = new ArrayList<>();
	private static List<Assignment> assignments  = new ArrayList<>();
	private static List<Request> requests = new ArrayList<>();
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
	 * 
	 */
	public static void addPrereqsData(){
		List<String[]> prereqData = readFile("prereqs.csv");
		Map<Integer, List<Integer>> preerqCourses = new HashMap<>();
		int preCourseId;
		int courseId;
		List<Integer> preCourseIds = new ArrayList<>();
		for (String[] text:prereqData){
			preCourseId = Integer.parseInt(text[0]);
			courseId = Integer.parseInt(text[1]);
			if (!preerqCourses.containsKey(courseId)){
				preCourseIds = new ArrayList<>();
				preCourseIds.add(preCourseId);
				preerqCourses.put(courseId, preCourseIds);
			}
			else{
				preCourseIds = preerqCourses.get(courseId);
				preCourseIds.add(preCourseId);
				preerqCourses.put(courseId, preCourseIds);
			}

		}
		
		for (Integer couId: preerqCourses.keySet()){
			Prereqs prereq = new Prereqs (couId);
			prereq.addPreps(preerqCourses.get(couId));
			prereqs.add(prereq);
		}
	}
	
	/**
	 *@method to parse assignments' data and add Request object to requests list array 
	 */
	public static void addAssignmentData(){
		List<String[]> assignmentData = readFile("assignments.csv");
		for (String[] text:assignmentData){
			Assignment assignment = new Assignment (Integer.parseInt(text[0]), 
					     Integer.parseInt(text[1]), Integer.parseInt(text[2]));
			assignments.add(assignment);
		}
	}
	
	/**
	 * @method to parse requests' data and add Request object to requests list array
	 */
	public static void addRequestData(){
		List<String[]> requestData = readFile("requests.csv");
		for (String[] text:requestData){
			Request request = new Request (Integer.parseInt(text[0]), Integer.parseInt(text[1]));
			requests.add(request);
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
		// comment lines for assignment 3
		// int countStudent = 0;
		// int countInstructor = 0;
		// int countCourse = 0;
		// int countFallSemester = 0;
		// int countSpringSemester = 0;
		// int countSummerSemester = 0;
		// List<Integer> coursesTakenByStudents = new ArrayList<Integer>();
		// List<Integer> output =new ArrayList<Integer>();
		// A3 -(1) the total number of records in the records.csv file
		addRecordData();
		// System.out.println(academicRecords.size());
		// output.add(academicRecords.size());
		// A3 - (2) the total number of records in the students.csv file
		addStudentData();
		// System.out.println(students.size());
		// output.add(students.size());
		// A3 - (3) the number of students who haven’t taken any classes
		// for (Student student: students){
		//	if (student.takeCourses(academicRecords) == 0) {
		//		countStudent ++;
		//	}
		//}
		//System.out.println(countStudent);
		//output.add(countStudent);
		// A3 - (4) the total number of records in the instructors.csv file
		addInstructorData();
		// System.out.println(instructors.size());
		// output.add(instructors.size());
		// A3 - (5) the number of instructors who haven’t taught any classes
		// for (Instructor instructor: instructors){
			//instructor.getTeachCourses(academicRecords);
			//if (instructor.teachCourses.isEmpty()) {
			//	countInstructor ++;
			//}
		//}
		//System.out.println(countInstructor);
		//output.add(countInstructor);
		//A3- (6) the total number of records in the courses.csv file
		addCourseData();
		//System.out.println(courses.size());
		//output.add(courses.size());
		// (7) the number of courses that haven’t been taken by any students
		//for (AcademicRecord aRecord: academicRecords){
			//for (Record record: aRecord.records){
				//coursesTakenByStudents.add(record.courseId);
		//	}
		//}
		//for (Course course:courses){
			//if (!coursesTakenByStudents.contains(course.courseId)){
				//countCourse ++;
			//}
		//}
		//System.out.println(countCourse);
		//output.add(countCourse);
		// A3 -(8) the total number of courses being offered during the Fall semester
		// for (Course course:courses){
			// if (course.semesters.contains("Fall")){
				// countFallSemester ++;
			// }
		//}
		//System.out.println(countFallSemester);
		//output.add(countFallSemester);
		// A3 -(9) the total number of courses being offered during the Spring semester
		//for (Course course:courses){
			//if (course.semesters.contains("Spring")){
				//countSpringSemester ++;
			//}
		//}
		//System.out.println(countSpringSemester);
		//output.add(countSpringSemester);
		// (10) the total number of courses being offered during the Summer semester
		//for (Course course:courses){
			//if (course.semesters.contains("Summer")){
				//countSummerSemester ++;
			//}
		//}
		//System.out.println(countSummerSemester);
		//output.add(countSummerSemester);
		// Automatically test the output, uncomment the following two lines to run test cases
		// String fileName = "test_case_0_digest.txt";
		// testCase(fileName, output);
		
		// add prereqs data
		addPrereqsData();
		
		// add assignments data
		addAssignmentData();
		
		// add total course seats to the course
		for (Course course: courses){
			course.getTotalSeats(assignments);
		}
		
		// add requests data
		addRequestData();

		// A6 - (1) the total number of records in the requests.csv file (don’t count blank lines, etc.)
		System.out.println(requests.size());
		
		// A6 - (2) the number of valid (granted) requests
		int countValidRequest = 0;
		int countMissRequest1 = 0;
		int countMissRequest2 = 0;
		int countMissRequest3 = 0;
		for (Request request: requests){
			int requestResponse = request.checkQualify(courses, students, academicRecords, prereqs);
			// valid requests
			if (requestResponse> -1){
				countValidRequest ++;
			}
			// missing prereqs
			if (requestResponse == -1){
				countMissRequest1 ++;
			}
			// not meet retaking criteria
			if (requestResponse == -2){
				countMissRequest2 ++;
			}
			// no seats available
			if (requestResponse == -3){
				countMissRequest3 ++;
			}
		}
		System.out.println(countValidRequest);
		
		// A6 - (3) the number of requests that were denied because of one or more missing prerequisites
		System.out.println(countMissRequest1);
		
		// A6 - (4) the number of requests that were denied the course was already taken
		System.out.println(countMissRequest2);
		
		// A6 - (5) the number of requests that were denied because of a lack of available seats
		System.out.println(countMissRequest3);
		
		// A6 - handle different inputs
		String input = "";
		while(true){
			System.out.printf("$main: ");
			Scanner scanner  = new Scanner(System.in);
			input = scanner.nextLine();
			
			// instruction to typing in command, not used for this assignment
			/*	
			System.out.println("Please input one of the following commands: ");
			System.out.println("display_requests");
			System.out.println("display_seats");
			System.out.println("display_records");
			System.out.println("check_request");
			System.out.println("add_record");
			System.out.println("add_seats");
			System.out.println("quit");	
			 */
			
			// quit command - quit program
			if (input.equals("quit")){
				System.out.println("stopping the command loop");
				break;
			}
			else{
				// display_requests - in Request class
				if (input.equals("display_requests")){
					for (Request request: requests){
						request.displayRequests();
					}
				}
				// display_seats - in Course class
				if (input.equals("display_seats")){
					for (Course course: courses){
						course.showCourseInfo();
					}
				}
				// display_records - in AcademicRecord class
				if (input.equals("display_records")){
					for (AcademicRecord aRecord: academicRecords){
						aRecord.showInfo();
					}
				}
				
				// check_request - in Request class
				int studentId;
				int courseId;
				int checkResult;
				String[] text;
				if (input.contains("check_request")){
					try{
						input = input.replaceAll("\\s+","");
						text = input.split(",");
						studentId = Integer.parseInt(text[1]);
						courseId = Integer.parseInt(text[2]);
						Request newRequest = new Request(studentId, courseId);
						requests.add(newRequest);
						checkResult = newRequest.checkQualify(courses, students, 
								                                  academicRecords, prereqs);
						if (checkResult == 0){
							System.out.println("request is valid");
						}
						if (checkResult == -1){
							System.out.println("student is missing one or more prerequisites");
						}
						if (checkResult == -2){
							System.out.println("student has already taken the course with a grade of C or higher");
						}
						if (checkResult == -3){
							System.out.println("no remaining seats available for the course at this time");
						}
					}
					catch (Exception e){
						System.out.println("Invalid request, please input valid student ID and course ID!");
						throw new Error();
					}
				}
					
				// add_record - in AcademicRecord class
				int instructorId;
				String comments;
				String grade;
				if (input.contains("add_record")){
						try{
							text = input.split(",");
							studentId = Integer.parseInt(text[1].replaceAll("\\s+",""));
							courseId = Integer.parseInt(text[2].replaceAll("\\s+",""));
							instructorId = Integer.parseInt(text[3].replaceAll("\\s+",""));
							comments = text[4];
							grade = text[5];
							AcademicRecord newRecord = new AcademicRecord(studentId);
							newRecord.enterNewAcademicRecord(instructorId, courseId, grade, comments);
							academicRecords.add(newRecord);
						}
						catch (Exception e){
							System.out.println("Invalid input, please input valid record information!");
							throw new Error();
						}
				}
					
				// add_seats - in Course class
				int addedSeats;
				if (input.contains("add_seats")){
						try{
							input = input.replaceAll("\\s+","");
							text = input.split(",");
							courseId = Integer.parseInt(text[1]);
							addedSeats = Integer.parseInt(text[2]);
							for (Course course: courses){
								if (course.courseId == courseId){
									course.courseSeats += addedSeats;
								}
							}
						}
						catch (Exception e){
							System.out.println("Invalid input, please input valid addtional seats information!");
							throw new Error();
						}
				}
				
			}
		}
	}

}
