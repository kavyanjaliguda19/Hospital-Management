package com.HMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HosiptalManagementSystem {
	
	private static String url="jdbc:mysql://localhost:3306/Hospital";
	private static String username="root";
	private static String password="8210";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Scanner scanner=new Scanner(System.in);
		try {
			Connection connection=DriverManager.getConnection(url,username,password);
			Patient patient=new Patient(connection,scanner);
			Doctors doctor=new Doctors(connection);
			
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patient");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter Your Choice: ");
				int choice=scanner.nextInt();
				
				switch(choice){
					case 1:
							//add patient
						patient.AddPatient();
						System.out.println();
						break;
						
					case 2:
							//view patient
						patient.ViewPatients();
						System.out.println();
						break;
						
					case 3:
							//view doctors
						doctor.ViewDoctors();
						System.out.println();
						break;
						
					case 4:
							//book Appointment
						BookAppointment(patient,doctor,connection,scanner);
						System.out.println();
						break;
						
						
						
					case 5:
							//exit
						System.out.println("Thank You!! For Using Hospital Management System");
						return;
					default:
					    System.out.println("Invalid Choice");
					    break;
				}
				
				
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	public static void BookAppointment( Patient patient,Doctors doctor,Connection connection,Scanner scanner) {
		System.out.print("Enter patient ID: ");
		int patientId=scanner.nextInt();
		System.out.print("Enter Doctor ID: ");
		int doctorId=scanner.nextInt();
		
		System.out.print("Enter Appointment Date(YYYY-MM-DD): ");
		String appointmentDate=scanner.next();
		
		if(patient.getpatientId(patientId)&& doctor.getDoctorId(doctorId)) {
			if(checkDoctorAvailabilty(doctorId,appointmentDate,connection)) {
				String query="Insert into Appointments(patient_id,doctor_id,appointment_date) Values(?,?,?)";
				try {
					PreparedStatement ps=connection.prepareStatement(query);
					ps.setInt(1,patientId);
					ps.setInt(2, doctorId);
					ps.setString(3, appointmentDate);
					int affectedRows=ps.executeUpdate();
					if(affectedRows>0) {
						System.out.println("Appointment Booked");
					}
					else
					{
						System.out.println("Failed to Book Appointment");
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
			}
			else
			{
				System.out.println("Doctor not Available on this date!!");
			}
		}else
		{
			System.out.println("Either doctor or patient doesn't exists!!!");
		}
		
	}
	public static boolean checkDoctorAvailabilty(int doctorId,String appointmentDate,Connection connection) {
		String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1, doctorId);
			ps.setString(2, appointmentDate);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				int count=rs.getInt(1);
				if(count==0) {
					return true;
				}
				else
				{
					return false;
				}
			}
			

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}

}
