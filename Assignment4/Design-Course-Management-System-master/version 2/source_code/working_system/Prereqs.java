import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zhihuixie
 * @version 0.0.1
 * @class Prereqs includes a constructor and 2 methods
 * @method addPreps - add prereqs courses to the course
 * @method getPreps - get the prereqs for the course 
 */

/**
 * @author zhihuixie
 *
 */
public class Prereqs {
	
	private List<Integer>preCourseIds = new ArrayList<>();
	int courseId;
    
	/**
	 * constructor
	 * @param courseId
	 */
	public Prereqs(int courseId) {
		this.courseId = courseId;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @method add prereqs courses to the course
	 * @param prepCourseIds
	 */
	public void addPreps(List<Integer> prepCourseIds){
		this.preCourseIds = prepCourseIds;
	}
   
	/**
	 * @method get the prereqs for the course
	 * @return
	 */
	public List<Integer> getPreps() {
		// TODO Auto-generated method stub
		return this.preCourseIds;
	}

}
