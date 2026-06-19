package com.HMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
	private Connection connection;
	
	
	public Doctors(Connection connection) {
		this.connection=connection;
	}
	

	public void ViewDoctors() {
		String query="select * from Doctors";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			System.out.println("Doctors");
			System.out.println("+------------+------------------------+--------------------------+");
			System.out.println("| Doctor Id  | Name                   | Specialization           |");
			System.out.println("+------------+------------------------+--------------------------+");
			
			while(rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String specialization  =rs.getString("specialization");
				System.out.printf("| %-10s | %-22s | %-24s |\n",id,name,specialization );
				System.out.println("+------------+------------------------+--------------------------+");
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean getDoctorId(int id) {
		String query="select * from Doctors where id= ?";
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
