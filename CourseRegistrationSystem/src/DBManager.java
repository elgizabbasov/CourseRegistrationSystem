import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * This class is responsible for simulating a database for our program
 * @author A.Elgiz
 * @version 1.0
 * @since April 20, 2020
 *
 */

public class DBManager {
	
	ArrayList <Course> courseList;
	ArrayList <CourseOffering> courseOffering;
	ArrayList <Student> students;
	ArrayList<String> courseNames = new ArrayList <String>();
	ArrayList<Integer> courseNumbers = new ArrayList <Integer>();
	ArrayList<Integer> secNumbers = new ArrayList <Integer>();
	ArrayList<Integer> secCaps = new ArrayList <Integer>();

	public DBManager () {
		courseList = new ArrayList<Course>();
		students = new ArrayList<Student>();
		Connection conn = DBConnection.initConnection();
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn2 = DBConnection.initConnection();
		ResultSet rs2 = null;
		Statement stmt2 = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM COURSES");
			while (rs.next()) {
				courseNames.add((String) rs.getObject(getIndex(rs, "courses", "course_name")));
				courseNumbers.add(Integer.parseInt(rs.getObject(3).toString()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt2 = conn2.createStatement();
			rs2 = stmt2.executeQuery("SELECT * FROM COURSE_OFFERINGS");
			while (rs2.next()) {
				secNumbers.add((Integer) rs2.getObject(2));
				secCaps.add(Integer.parseInt(rs2.getObject(3).toString()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	/**
	 * readFromDataBase is responsible for creating courses and courseofferings/acting as a database
	 * @return returns the courseList
	 */
	public int getIndex(ResultSet rs, String table, String column) throws SQLException
	{
	    for (int i=1; i < rs.getMetaData().getColumnCount(); i++)
	    {
	        if (rs.getMetaData().getTableName(i).equals(table) && rs.getMetaData().getColumnName(i).equals(column))
	        {
	            return i;
	        }
	    }
	    return -1;
	}
	public ArrayList<String> getCourseName() {
		return courseNames;
	}
	public ArrayList<Integer> getCourseNum() {
		return courseNumbers;
	}
	public ArrayList<Course> readFromDataBase() {
		Course course1 = new Course(courseNames.get(0), courseNumbers.get(0));
		Course course2 = new Course(courseNames.get(1), courseNumbers.get(1));
		Course course3 = new Course(courseNames.get(2), courseNumbers.get(2));
		Course course4 = new Course(courseNames.get(3), courseNumbers.get(3));
		Course course5 = new Course(courseNames.get(4), courseNumbers.get(4));
		Course course6 = new Course(courseNames.get(5), courseNumbers.get(5));

		CourseOffering courseOff1 = new CourseOffering (secNumbers.get(0), secCaps.get(0));
		CourseOffering courseOff2 = new CourseOffering (secNumbers.get(1), secCaps.get(1));
		
		course1.addOffering(courseOff1);
		course1.addOffering(courseOff2);

		
		CourseOffering courseOff4 = new CourseOffering (secNumbers.get(2), secCaps.get(2));
		CourseOffering courseOff5 = new CourseOffering (secNumbers.get(3), secCaps.get(3));

		
		course2.addOffering(courseOff4);
		course2.addOffering(courseOff5);
		
		CourseOffering courseOff6 = new CourseOffering (secNumbers.get(4), secCaps.get(4));
		CourseOffering courseOff7 = new CourseOffering (secNumbers.get(5), secCaps.get(5));
		
		course3.addOffering(courseOff6);
		course3.addOffering(courseOff7);
		
		CourseOffering courseOff8 = new CourseOffering (secNumbers.get(6), secCaps.get(6));
		CourseOffering courseOff9 = new CourseOffering (secNumbers.get(7), secCaps.get(7));
		
		course4.addOffering(courseOff8);
		course4.addOffering(courseOff9);
		
		CourseOffering courseOff10 = new CourseOffering (secNumbers.get(8), secCaps.get(8));
		CourseOffering courseOff11 = new CourseOffering (secNumbers.get(9), secCaps.get(9));
		
		course5.addOffering(courseOff10);
		course5.addOffering(courseOff11);
		
		CourseOffering courseOff12 = new CourseOffering (secNumbers.get(10), secCaps.get(10));
		CourseOffering courseOff13 = new CourseOffering (secNumbers.get(11), secCaps.get(11));
		
		course6.addOffering(courseOff12);
		course6.addOffering(courseOff13);

				
				
		courseList.add(course1);
		courseList.add(course2);
		courseList.add(course3);
		courseList.add(course4);
		courseList.add(course5);
		courseList.add(course6);
	
		return courseList;
	}
	

	
	

}
