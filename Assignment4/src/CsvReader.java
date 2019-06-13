import java.io.*;
import java.util.*;


public class CsvReader {
	public static List<Instructor> instructors = new ArrayList<Instructor>();
	public static List<Course> courses = new ArrayList<Course>();
	public static List<Student> students = new ArrayList<Student>();


	public static List<String[]> readCsv(String f){
		List<String[]> data = new ArrayList<String[]>();
		try{
			File nf = new File(f);
			FileReader fr = new FileReader(nf);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null){
				data.add(line.split(","));
				line = br.readLine();
			}
			br.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		return data;
	}
	
	
	
	public static void addInstructorData(){
		List<String[]> instructorInfo = readCsv("instructors.csv");
		for (String[] data:instructorInfo){
			Instructor instructor = new Instructor (data[0], data[1], data[2], data[3]);
			instructors.add(instructor);
		}
	}
	
	public static void addCourseData(){
		List<String[]> coursesInfo = readCsv("courses.csv");
		for (String[] data:coursesInfo){
			Course course = new Course (data[0], data[1]);
			courses.add(course);
		}
	}
	
	public static void addStudentData(){
		List<String[]> studentInfo = readCsv("students.csv");
		for (String[] data:studentInfo){
			Student student = new Student(data[0], data[1], data[2], data[3]);
			students.add(student);
		}
	}
	
	public static void addEligible(){
		List<String[]> eligibleInfo = readCsv("eligible.csv");
		for (String[] data:eligibleInfo){
			for (Instructor ins:instructors){
				if(ins.UUID.equals(data[0])){
				ins.addCourse(data[1]);	
				}
			}
		}
	}
	
	public static void addPrereq(){
		List<String[]> prereqInfo = readCsv("prereqs.csv");
		for (String[] data:prereqInfo){
			for (Course c:courses){
				if(c.courseID.equals(data[1])){
					
					for (Course course:courses)
						if(course.courseID.equals(data[0]))
							{
							c.addPrereq(course);
							}
				}
			}
		}
	}
	
	
	public static void addTerms(){
		List<String[]> termsInfo = readCsv("terms.csv");
		for (String[] data:termsInfo){
			for (Course c:courses){
				if(c.courseID.equals(data[0])){
					c.offerTerm.add(data[1]);
				}
			}
		}
	}

} 