package testBank;
import java.io.*;
import java.sql.*;
import java.util.*;


public class bankk {
	
//	static  con=null;
	
	public static void transfer() throws ClassNotFoundException, SQLException
	{
		Scanner sc=new Scanner(System.in);
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","Pass@123");
		
		String send1=sc.next();
		
		String rece1=sc.next();
		
		
		int amtt=sc.nextInt();
		
		
		int x=0,x1=0;
		
		PreparedStatement ps = con.prepareStatement("select * from registert where accNum=?");

		ps.setString(1, send1);
		ResultSet rs=ps.executeQuery();
		
		
		while(rs.next())
		x=rs.getInt(5);

		//System.out.println(x);

		PreparedStatement ps2 = con.prepareStatement("select * from registert where accNum=?");
		ps2.setString(1, rece1);
		
		ResultSet rs1=ps2.executeQuery();
		
		while(rs1.next())
		x1=rs1.getInt(5);
		
		
		if(x>=amtt) //"update bankAcc set money=? where name=name1"
		{
			x=x-amtt;
			PreparedStatement ps1 = con.prepareStatement("update registert set bal=? where accNum=?");
			ps1.setInt(1, x);
			ps1.setString(2, send1);
			ps1.execute();
	
			PreparedStatement ps3 = con.prepareStatement("update registert set bal=? where accNum=?");
			ps3.setInt(1, x1+amtt );
			ps3.setString(2, rece1);
			ps3.execute();
			
			System.out.println("Updated");

		}
		//Scanner sc = new Scanner(System.in);
		else
		{
			System.out.println("not enough balance");
		}

	}
	
	public static void display(String uname) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","Pass@123");
		String sql="select * from registert where username=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, uname);
		ResultSet rs = ps.executeQuery();
		System.out.println("username\tphone\taccount\tbalance");
        
        while (rs.next()) {
           System.out.println(rs.getString("username")+"\t"+rs.getInt("phone")+"\t"+rs.getString("accNum")+"\t"+rs.getInt("bal"));
        
        }
        
	}
	
	public static void createAcc(String uname, String pass) throws ClassNotFoundException, SQLException
	{
		Scanner sc=new Scanner(System.in);
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","Pass@123");
		System.out.println("enter acc num");
		String accNum=sc.next();
		System.out.println("enter balnace");
		String bal=sc.next();
		PreparedStatement ptmt1=con.prepareStatement("UPDATE registert SET accNum= ?, bal= ? WHERE username=?");
		
		
//		PreparedStatement ptmt2=con.prepareStatement("update registert accNum=? and bal=? where username=?");
		
		
		ptmt1.setString(1,accNum);
		ptmt1.setString(2, bal);
		ptmt1.setString(3, uname);
		
		ptmt1.executeUpdate();
		
		System.out.println("acc created!");
		System.out.println("-----------------------------------------------");
		
		
		
		
		
	}
	
	public static void login(){
		Scanner sc=new Scanner(System.in);
		System.out.println("enter username");
		String uname=sc.next();
		System.out.println("enter password");
		String pass=sc.next();
		int found=0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","Pass@123");
			Statement stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from registerT");
			
			while(rs.next())
			{
//				System.out.println("wekhrgfkuf");
				if(rs.getString(1).equals(uname) && rs.getString(2).equals(pass))
				{
					System.out.println("logged in successfully");
					System.out.println("-----------------------------------");
//					System.out.println("wekhrgfkuf");
					found=1;
					break;
				}
			}
			
			if(found==0)
			{
				System.out.println("-----------------------------------");
				System.out.println("Please register");
				register();
				
			}
			
			int ch2=0;
			
			
			do{
				System.out.println("--------------------------------");
				System.out.println("1.create a bank account");
				System.out.println("2.Display details");
				System.out.println("3.transfer money");
				System.out.println("4.Exit");
				System.out.println("--------------------------------");
				System.out.println("Enter your Choice: ");
				ch2=sc.nextInt();
				
				if(ch2==1)
				{
					createAcc(uname,pass);
				}
				if(ch2==2)
				{
					display(uname);
				}
				if(ch2==3)
				{
					transfer();
				}
				
			
			}while(ch2!=4);
			


			
			
		}
		catch(Exception e) {
			
		}
		
		

	}
	
	public static void register() throws SQLException, ClassNotFoundException {
		Scanner sc=new Scanner(System.in);
		System.out.println("enter username");
		String username=sc.next();
		System.out.println("enter password");
		String password=sc.next();
		System.out.println("enter phone number");
		String phone=sc.next();
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","Pass@123");
		PreparedStatement ptmt=con.prepareStatement("insert into registerT values(?,?,?)");
		
		ptmt.setString(1, username);
		ptmt.setString(2, password);
		ptmt.setString(3, phone);
		
		ptmt.execute();
		
		System.out.println("registered successfully!");
	}
	
	
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		Scanner sc=new Scanner(System.in);
		System.out.println("1)Login\t2)Register");
		int ch1=sc.nextInt();
		int found=0;
		
		if(ch1==1)
		{
			login();		
		}
		if(ch1==2) {
			register();
			login();
		}
		
		
	}

}
