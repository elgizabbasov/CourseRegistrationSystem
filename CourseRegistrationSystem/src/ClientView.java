import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.CharBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Class ClientView is going to represent the GUI of the application.
 * @author A. Elgiz
 * @version 1.0
 * @since April 20, 2020
 *
 */
 
public class ClientView extends JFrame {
	CourseCatalogue allCourses;
	Course aCourse;
	Registration registration;
	ClientControl cc;
	ServerControl sc;
	DBManager db;
	Student aStudent;
	
	/**
	 * Constructor ClientView is responsible for creating the GUI
	 * @param widthInPixels is the width of the display
	 * @param heightInPixels is the height of the display
	 */
	 
	public ClientView (int widthInPixels, int heightInPixels) {
		JFrame parent = new JFrame();
		JLabel title = new JLabel("An Application for Course Registration System", SwingConstants.CENTER);
		JPanel panel = new JPanel();
		JButton searchCourse = new JButton ("Search for a Course");
		JButton addCourse = new JButton ("Add a Course");
		JButton removeCourse = new JButton ("Remove a Course");
		JButton viewAll = new JButton ("View all Courses");
		JButton viewCourse = new JButton ("View your Courses");
		JButton quit = new JButton ("Quit");
		JTextArea textArea = new JTextArea(600,600);
		textArea.setEditable(false);
		cc = new ClientControl("localhost", 9090);
		setVisible(false);
		Connection conn = DBConnection.initConnection();
		ResultSet rs = null;
		Statement stmt = null;
		aStudent = null;
		String studentName = JOptionPane.showInputDialog(parent, "Please enter your name: ");
		String studentID =  JOptionPane.showInputDialog(parent, "Please enter your student ID: ");
		
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM students WHERE first_name='"+studentName+"'AND student_id='"+studentID+"'";
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				
				System.out.println("We found a matching person in the data base");
				
				setVisible(true);
				
				int studentIdNum = -1;
				try
				{
					studentIdNum = Integer.parseInt(studentID);
					System.out.println("Student initialized with ID: " + studentIdNum + " Name: " + studentName);
					aStudent = new Student(studentName,Integer.parseInt(studentID));
					registration = new Registration(aStudent);
				}catch(NumberFormatException e)
				{
					System.out.println("Number formating error caught.");
				}

			}
			else {
				JOptionPane.showMessageDialog(parent, "Student name or ID is invalid.");
				System.exit(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		panel.add(textArea);
		panel.add(searchCourse);
		panel.add(addCourse);
		panel.add(removeCourse);
		panel.add(viewAll);
		panel.add(viewCourse);			
		panel.add(quit);
			
		setSize(widthInPixels, heightInPixels);
			
		setLayout(new BorderLayout());
			
		add("North", title);
		add("South", panel);
		add("Center", textArea);
		
		/**
		 * Responsible for searchCourse button's functionality/gui
		 */
		 
		searchCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame input = new JFrame ("Input");
				String courseName = JOptionPane.showInputDialog(input, "Please enter the course name: ");
				String courseNum =  JOptionPane.showInputDialog(input, "Please enter the course number: ");
				cc.getSocketOut().println(1 + ":"  + courseName + ":" + courseNum + ":");
				try {
					
					CharBuffer theBuffer = CharBuffer.allocate(800);
					
				    int width = (cc.getSocketIn().read(theBuffer));
					
					theBuffer.limit(width);
					theBuffer.position(0);
					
					String theString = theBuffer.toString();
					textArea.setText(theString);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		/**
		 * Responsible for addCourse button's functionality/gui
		 */
		 
		addCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				JFrame input = new JFrame ("Input");
				String courseName = JOptionPane.showInputDialog(input, "Please enter the desired course name: ");
				String courseNum = JOptionPane.showInputDialog(input, "Please enter the desired course number: ");
				String secNum = JOptionPane.showInputDialog(input, "Please enter the section number (1 or 2): ");
				cc.getSocketOut().println(2 + ":" + courseName + ":" + courseNum + ":" + secNum);
				try {
					cc.getSocketObjectOut().writeObject(aStudent);
				}catch (Exception exc) {
					exc.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(new JFrame(), "Course was added to your list!");
			}
		});
		
		/**
		 * Responsible for removeCourse button's functionality/gui
		 */
		 
		removeCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				JFrame input = new JFrame ("Input");
				String courseName = JOptionPane.showInputDialog(input, "Please enter the course name you want removed: ");
				String courseNum = JOptionPane.showInputDialog(input, "Please enter the course number you want removed: ");
				cc.getSocketOut().println(3 + ":" + courseName + ":" + courseNum);
				JOptionPane.showMessageDialog(new JFrame(), "Course was removed from your list!");

			}
		});
		
		/**
		 * Responsible for viewAll button's functionality/gui
		 */
		 
		viewAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				JScrollPane scrollPane = null;
				DBManager db = new DBManager();
				textArea.setText(db.readFromDataBase().toString());
				scrollPane = new JScrollPane(textArea);
				add(scrollPane, BorderLayout.CENTER);
			}
		});
		
		/**
		 * Responsible for viewCourse button's functionality/gui
		 */
		 
		viewCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				JFrame input = new JFrame ("Input");
				try {
					
					CharBuffer theBuffer = CharBuffer.allocate(800);
					
				    int width = (cc.getSocketIn().read(theBuffer));
					
					theBuffer.limit(width);
					theBuffer.position(0);
					
					String theString = theBuffer.toString();
					textArea.setText(theString);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		/**
		 * Responsible for quit button's functionality
		 */
		 
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				setVisible(false);
				System.exit(0);
			}
		});
		
	}
	
	/**
	 * Run the client
	 * @param args
	 */
	
	public static void main(String [] args) {
		ClientView myClient = new ClientView (800, 300);
	}
}
