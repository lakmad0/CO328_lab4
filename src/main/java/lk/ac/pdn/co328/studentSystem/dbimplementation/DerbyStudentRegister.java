/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ac.pdn.co328.studentSystem.dbimplementation;

import java.sql.*;
import java.util.ArrayList;
import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;

/**
 *
 * @author himesh
 */
public class  DerbyStudentRegister extends StudentRegister {

    Connection connection = null;
    public DerbyStudentRegister() throws SQLException
    {
        String dbURL1 = "jdbc:derby:codejava/studentDB;create=true";
        connection = DriverManager.getConnection(dbURL1);
        if (connection != null)
        {
            String SQL_CreateTable = "CREATE TABLE Students(id INT , firstname VARCHAR(24), lastname VARCHAR(24), PRIMARY KEY (id))";
            System.out.println ( "Creating table addresses..." );
            try
            {
                Statement stmnt = connection.createStatement();
                stmnt.execute( SQL_CreateTable );
                stmnt.close();
                System.out.println("Table created");
            } catch ( SQLException e )
            {
                System.out.println(e);
            }
            System.out.println("Connected to database");
        }
        else
        {
            throw new SQLException("Connection Failed");
        }
    }

    @Override
    public void addStudent(Student st) throws Exception {
        if (connection != null) {

            String SQL_AddStudent = "INSERT INTO Students VALUES (" + st.getId() + ",'" + st.getFirstName() + "','"+ st.getLastName()+"')";
            System.out.println ( "Adding the student..." + SQL_AddStudent);

            Statement stmnt = connection.createStatement();
            stmnt.execute(SQL_AddStudent );
            stmnt.close();
            System.out.println("Student Added");


        }
        else
        {
            throw new Exception("Database Connection Error");
        }
    }

    @Override
    public void removeStudent(int regNo) throws Exception{
        if (connection != null)
        {
            String SQL_RemoveStudent = "DELETE FROM Students WHERE id = " + regNo + "";
            System.out.println ( "Remove the student..." + SQL_RemoveStudent);

            Statement stmnt = connection.createStatement();
            stmnt.executeUpdate(SQL_RemoveStudent);
            stmnt.close();
            System.out.println("Student Removed");
        }
        else
        {
            throw new Exception("Database Connection Error");
        }    }

    @Override
    public Student findStudent(int regNo) throws Exception {
        if (connection != null)
        {
            String SQL_FindStudent = "SELECT * FROM Students WHERE id = " + regNo ;
            System.out.println ( "Find the student..." + SQL_FindStudent);

            Statement stmnt = connection.createStatement();
            ResultSet results =  stmnt.executeQuery(SQL_FindStudent);
            Student student = null;
            while(results.next()) {
                student = new Student(results.getInt("id"), results.getString("firstname"), results.getString("lastname"));
            }
            stmnt.close();
            return student;
        }
        else
        {
            throw new Exception("Database Connection Error");
        }
    }

    @Override
    public void reset() throws Exception {
        if (connection != null)
        {
            String SQL_Reset = "DROP TABLE Students";
            System.out.println ( "drop the table..." + SQL_Reset);

            Statement stmnt = connection.createStatement();
            stmnt.execute(SQL_Reset);
            stmnt.close();
            System.out.println("Table Droped");
        }
        else
        {
            throw new Exception("Database Connection Error");
        }
    }

    @Override
    public ArrayList<Student> findStudentsByName(String name) throws Exception {
        ArrayList<Student> students = new ArrayList<Student>();

        if (connection != null)
        {
            String SQL_FindStudent = "SELECT * FROM Students WHERE firstname = '" + name + "' OR lastname = '"+name+"'";
            System.out.println ( "Find the student..." + SQL_FindStudent);

            Statement stmnt = connection.createStatement();
            ResultSet results =  stmnt.executeQuery(SQL_FindStudent);

            while(results.next()) {
                students.add(new Student(results.getInt("id"),results.getString("firstname"),results.getString("lastname")));
            }
            stmnt.close();
            return students;
        }
        else
        {
            throw new Exception("Database Connection Error");
        }
    }

    @Override
    public ArrayList<Integer> getAllRegistrationNumbers() throws Exception{
        ArrayList<Integer> redNumbers = new ArrayList<Integer>();

        if (connection != null)
        {
            String SQL_FindStudent = "SELECT id FROM Students";
            System.out.println ( "Find the student..." + SQL_FindStudent);

            Statement stmnt = connection.createStatement();
            ResultSet response =  stmnt.executeQuery(SQL_FindStudent);

            while(response.next())
            {
                redNumbers.add(response.getInt("id"));
            }

            stmnt.close();
            return redNumbers;
        }
        else
        {
            throw new Exception("Database Connection Error");
        }
    }

}
