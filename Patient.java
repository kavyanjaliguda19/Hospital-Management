package com.HMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	
	private Scanner scanner;
	
	public Patient(Connection connection,Scanner scanner) {
		this.connection=connection;
		this.scanner=scanner;
	}
	
	public  void AddPatient() {
		System.out.println("Enter patient name: ");
		String name=scanner.next();
		System.out.println("Enter patient Age: ");
		int age=scanner.nextInt();
		System.out.println("Enter patient Gender: ");
		String gender=scanner.next();
		
		try {
			String Query="insert into Patients(name,age,gender) Values(?,?,?)";
			PreparedStatement ps=connection.prepareStatement(Query);
			ps.setString(1,name);
			ps.setInt(2, age);
			ps.setString(3, gender);
			int affectedRows=ps.executeUpdate();
			if(affectedRows>0) {
				System.out.println("patient added Successfully");
			}
			else
			{
				System.out.println("failed to add patient!");
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void ViewPatients() {
		String query="select * from patients";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			System.out.println("Patients");
			System.out.println("+------------+------------------------+----------+----------------+");
			System.out.println("| Patient Id | Name                   | Age      | Gender         |");
			System.out.println("+------------+------------------------+----------+----------------+");
			
			while(rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				int age=rs.getInt("age");
				String gender=rs.getString("gender");
				System.out.printf("| %-10s | %-22s | %-8s | %-14s |\n",id,name,age,gender);
				System.out.println("+------------+------------------------+----------+----------------+");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean getpatientId(int id) {
		String query="select * from patients where id= ?";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1,id);
			ResultSet rs=ps.executeQuery();
			return rs.next();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
            
}
