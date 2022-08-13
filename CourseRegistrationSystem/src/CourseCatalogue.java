import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * Class CourseCatalogue is responsible for the functionality of the classes offered
 * @author A.Elgiz
 * @version 1.0
 * @since April 20, 2020
 */
public class CourseCatalogue {
	
	private ArrayList <Course> courseList;
	public CourseCatalogue () {
		loadFromDataBase ();
	}
	
	/**
	 * This method is responsible for setting up the course list by calling a method in the database class
	 */
	 
	private void loadFromDataBase() {
		DBManager db = new DBManager();
		// TODO Auto-generated method stub
		setCourseList(db.readFromDataBase());
	}
	
	/**
	 * This method is responsible for creating a course offering
	 * @param c is the course
	 * @param secNum is the section number
	 * @param secCap is the max amount of students that can take this course in that section
	 */
	 
	public void createCourseOffering (Course c, int secNum, int secCap) {
		if (c!= null) {
			CourseOffering theOffering = new CourseOffering (secNum, secCap);
			c.addOffering(theOffering);
		}
	}
	
	/**
	 * This method is responsible for searching a course
	 * @param courseName is the name of the course
	 * @param courseNum is the number of the course
	 * @return returns the course
	 */
	 
	public Course searchCat (String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) &&
					courseNum == c.getCourseNum()) {
				return c;
			}	
		}
		displayCourseNotFoundError();
		return null;
	}
	
	private void displayCourseNotFoundError() {
		JOptionPane.showMessageDialog(new JFrame(), "Course was not found!");
	}
	public ArrayList <Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList <Course> courseList) {
		this.courseList = courseList;
	}
	@Override
	public String toString () {
		String st = "All courses in the catalogue: \n";
		for (Course c : courseList) {
			st += c; 
			st += "\n";
		}
		return st;
	}
}
