import java.util.*;
import java.sql.*;
public class Voting
{
	static String url = "jdbc:postgresql://localhost:5432/StudentsD2T2", name = "postgres", password = "hello", sql;
    static Connection con;
    static PreparedStatement st;
    static ResultSet result;
    static Statement statement;
	static Scanner sc;
	static int vote() throws Exception
	{
		String aadhar,regex;
		System.out.println("ENTER YOUR AADHAR NUMBER:");
		aadhar=sc.nextLine();
		regex ="[0-9]{12}"; 
		if(aadhar.matches(regex))
		{
			statement=con.createStatement();
			sql="SELECT * FROM voting WHERE aadhar="+aadhar;
			result=statement.executeQuery(sql);
			int i=0;
			while(result.next())
			{
				i++;
			}
			if(i==0)
			{
				int choice;
				System.out.println("1.PARTY 1");
				System.out.println("2.PARTY 2");
				System.out.println("3.PARTY 3");
				System.out.println("ENTER YOUR CHOICE:");
				choice=sc.nextInt();
				sc.nextLine();
				if(choice==1)
				{
					statement.executeUpdate("INSERT INTO voting values("+aadhar+",1,0,0)");  
				}
				else if(choice==2)
				{
					statement.executeUpdate("INSERT INTO voting values("+aadhar+",0,1,0)"); 
				}
				else if(choice==3)
				{
					statement.executeUpdate("INSERT INTO voting values("+aadhar+",0,0,1)");   
				}
				System.out.println("VOTED SUCCESSFULLY!!!");
				
			}
			else
			{
				System.out.println("YOU HAVE ALREADY VOTED");
			}
		}
		else 
		{
			System.out.println("INVALID AADHAR NUMBER");
			return 0;
		}
		
		return 0;
		
	}
	static void display() throws Exception
	{
		statement=con.createStatement();
		sql="SELECT * FROM voting";
		result=statement.executeQuery("SELECT * FROM voting"); 
		int party1=0,party2=0,party3=0;
		while(result.next())
		{
			party1=party1+result.getInt(2);
			party2=party2+result.getInt(3);
			party3=party3+result.getInt(4);
		}
		if((party1>party2)&& (party1>party3))
		{
			System.out.println("PARTY 1 WITH "+party1+" VOTES WINS");
		}
		else if((party2>party3)&&(party2>party1))
		{
			System.out.println("PARTY 2 WITH "+party2+" VOTES WINS");
		}
		else if((party3>party2)&&(party3>party1))
		{
			System.out.println("PARTY 3 WITH "+party3+" VOTES WINS");
		}
		else
		{
			System.out.println("NO WINNER");
		}
	}
	public static void main(String args[])
	{
		try{
			
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection(url, name, password);
		
		System.out.printf("%50s\n","E-VOTING SYSTEM");
		int choice;
		sc=new Scanner(System.in);
		do
		{
			System.out.println("1.VOTE");
			System.out.println("2.VIEW RESULTS");
			System.out.println("3.EXIT");
			System.out.println("ENTER YOUR CHOICE:");
			choice=sc.nextInt();
			sc.nextLine();
			if(choice==1)
			{
				vote();
			}
			else if(choice==2)
			{
				display();
			}
			else if(choice==3)
			{
				con.close();;
			}
			else
			{
				System.out.println("Enter one of the given options:");
			}
			
		}while(choice!=3);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
