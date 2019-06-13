import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Instructions {

	
	private static Instructor getInstructor(String ID, CsvReader csv) {
		
		Instructor instructor=null;
		for (Instructor ins:csv.instructors){
			if(ins.UUID.equals(ID))
				{
				instructor=ins;
				break;
				}
		}
			return instructor;
	}
	
private static Student getStudent(String ID, CsvReader csv) {
		
		Student student=null;
		for (Student s:csv.students){
			if(s.UUID.equals(ID))
				{
				student=s;
				break;
				}
		}
			return student;
	}
	
private static Course getCourse(String ID, CsvReader csv) {
	int i=0;	
	Course course=null;
		for (Course c:csv.courses){
			if(c.courseID.equals(ID))
				{ 
				course=c;
				break;
				}
		}
			//System.out.println(i);
			return course;
	}



	public static void main(String[] args) {
		
		
			CsvReader readCsv=new CsvReader();
			readCsv.addInstructorData();
			readCsv.addEligible();
			readCsv.addCourseData();
			readCsv.addStudentData();
			readCsv.addPrereq();
			readCsv.addTerms();
			
		/**
		 *  @method main to read actions and print output
		 */
		Term term_init=new Term("Fall",2017);
		Term term_current=term_init;
		
			String Output="";
			String csvFile = "actions.csv";
	        String line = "";
	        String cvsSplitBy = ",";
	
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	
	            while ((line = br.readLine()) != null) {
	
	                // use comma as separator
	                String[] actions = line.split(cvsSplitBy);
	
	                if(actions[0].equals("start_sim"))
	                {
	                 term_init=new Term("Fall",Integer.valueOf(actions[1])); 
	                 term_current=term_init;
	                 Output=Output+line+'\n'+"# begin "+term_init.getSemester()+"_"+Integer.toString(term_init.getYear())+ " term";
	                }
	                
	                if(actions[0].equals("next_term"))
	                {
	                
	                 Term term=term_current.next();
	                 term_current=term;
	                 Output=Output+'\n'+line+'\n'+"# begin "+term.getSemester()+"_"+Integer.toString(term.getYear())+ " term";
	                }
	                
	                if(actions[0].equals("stop_sim"))
	                {
	                 Output=Output+'\n'+line+'\n'+"# end "+term_current.getSemester()+"_"+Integer.toString(term_current.getYear())+ " term";
	                }
	                
	                if(actions[0].equals("hire"))
	                {
	                 term_current.hire(actions[1]);  
	                 Output=Output+'\n'+line+'\n'+"# instructor "+actions[1]+" now hired";
	                }
	                
	                if(actions[0].equals("take_leave"))
	                {       
	                term_current.leave(actions[1]);  
	                //System.out.println(term_current.HiredInstructor);
	                 Output=Output+'\n'+line+'\n'+"# instructor "+actions[1]+" now on leave";
	                }
	                
	                if(actions[0].equals("teach_course"))
	                {       
	                	Instructor instructor=getInstructor(actions[1], readCsv);
	                	Course course=getCourse(actions[2],readCsv);
	                	Session session=new Session(term_current, course, instructor); 
	                	
	                	
	                 if(!term_current.HiredInstructor.contains(actions[1]))
	                	 Output=Output+'\n'+line+'\n'+"# ERROR: instructor is not working";    
	                 else if (!instructor.eligibleCourses.contains(actions[2]))
	                	 Output=Output+'\n'+line+'\n'+"# ERROR: instructor is not eligible to teach this course";
	                 else if (session!=null) 
	                	 	if(!session.course.equals(course)) 
	                	 		Output=Output+'\n'+line+'\n'+"# ERROR: instructor is already teaching a different course";
	                 else
	                 	{	                	 
	                	 term_current.addSession(session);
	                	 session.availableSeats+=3;
	                	 Output=Output+'\n'+line+'\n'+"# instructor "+actions[1]+" is assigned to course "+actions[2];
	                 	}
	                 }
	                
	                if(actions[0].equals("instructor_report")){
	                	Instructor instructor=getInstructor(actions[1], readCsv);
	                	Session session=term_current.getSession(instructor);
	                	 Output=Output+'\n'+line+'\n'+"# instructor, "+instructor.Name+'\n'+"# "+session.course.courseID+", "+session.course.courseTitle;
	                }
	                
	                if(actions[0].equals("request_course")){
	            		Student student=getStudent(actions[1],readCsv);
	            		Course course=getCourse(actions[2],readCsv);
	            		Session session=term_current.getSession(course);
	            		
	            		//System.out.println(course.prereqCourses);
	            		//System.out.println(readCsv.courses.get(1).prereqCourses);
	            		CourseRequest course_request=new CourseRequest(student,course,term_current);
	            		
	            		if(student.passCourse(course)==true)
	            			Output=Output+'\n'+line+'\n'+"# not enrolled: course already passed before";	
	            		else if(session!=null)
	            				if(session.enrolledStudent.contains(course.courseID))
	            					Output=Output+'\n'+line+'\n'+"# student already enrolled in course";
	            		else if(course.isOffered()==false)
	            			Output=Output+'\n'+line+'\n'+"# not enrolled: course not being offered this term";	
	            		else if(course_request.passPrereq(student, course)==false)
	            			Output=Output+'\n'+line+'\n'+"# not enrolled: missing prerequisites";
	            		else if(session.availableSeats<1)
	            			Output=Output+'\n'+line+'\n'+"# not enrolled: no available seats";
	            		else {
	            			session.enrolledStudent.add(student);
	            			Grade grade=new Grade(student,course, "_", session.instructor,"",term_current);
	            			student.addGrade(grade);
	            			session.availableSeats-=1;
	            			Output=Output+'\n'+line+'\n'+"# enrolled"; 
	            		}
	            		
	            	}
	                
	                if(actions[0].equals("assign_grade")){
	            		Student student=getStudent(actions[1],readCsv);
	            		Course course=getCourse(actions[2],readCsv);
	            		Instructor instructor=getInstructor(actions[4], readCsv);
	            		String comments="";
	            		if (actions.length==6)
	            			comments=actions[5];
	            		Grade grade=new Grade(student,course, actions[3],instructor,comments,term_current);
	            		student.addGrade(grade);
	            		Output=Output+'\n'+line+'\n'+"# grade recorded"; 
	            	}
	                
	                if(actions[0].equals("student_report")){
	                	Student student=getStudent(actions[1],readCsv);
	                	String records="";
	                	for (Grade g:student.academicRecord)
	                		if(g.comments!="")
	                			records=records+'\n'+g.course.courseID+", "+g.grade+", "+g.term.getSemester()+"_"+g.term.getYear()+", "+g.instructor.UUID+", "+g.comments;
	                		else
	                			records=records+'\n'+g.course.courseID+", "+g.grade+", "+g.term.getSemester()+"_"+g.term.getYear()+", "+g.instructor.UUID;
	                	Output=Output+'\n'+line+'\n'+"# student, "+student.Name+'\n'+student.UUID+": "+student.Name+", "+student.Address+", "+student.PhoneNumber
	                			+records;
	                }
	            
	            }
	            	
            
                try(  PrintWriter out = new PrintWriter( "result.txt" )  ){
                    out.println( Output );
                }
                
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	
	    }

	

	
	
}	
