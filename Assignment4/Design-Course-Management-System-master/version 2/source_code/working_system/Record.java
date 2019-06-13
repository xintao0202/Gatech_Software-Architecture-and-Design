
/**
 * @author zhihuixie
 * @version 0.0.2
 * @class Record includes a constructor and a method
 * @method describe to show information of this record including instructor's id, course id etc 
 */
public class Record {
	/**
	 * @variables
	 * instructorId - an instructor's id
	 * courseId - a course id
	 * performance - student's performance of this record
	 * performanceComments -instructor's performance comments
	 */
    public int instructorId;
    public int courseId;
    public String performance;
    public String performanceComments;
    /**
     * constructor for Record class
     * @param instructorId
     * @param courseId
     * @param performance
     * @param performanceComments
     */
	public Record(int instructorId, int courseId, String performance, String performanceComments) {
		// TODO Auto-generated constructor stub
		this.instructorId = instructorId;
		this.courseId = courseId;
		this.performance = performance;
		this.performanceComments = performanceComments;
	}
    
	/**
	 * @method to describe the information of this record
	 */
	public void describe() {
		// TODO Auto-generated method stub
		try{
			System.out.println("The instructor id is " + this.instructorId);
			System.out.println("The course id is " + this.courseId);
			System.out.println("The student's performance is " + this.performance);
			System.out.println("The instructor's comments are " + this.performanceComments);
		}catch(Exception e){
			throw new Error();
		}
	}
   
	/**
	 * 
	 * @return the grade of the student for this course
	 */
	public String getPerformance() {
		// TODO Auto-generated method stub
		return this.performance;
	}

}
