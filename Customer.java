import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Customer {
	public static void main(String[] args){
		createTable();
		createCustomer("Kang", "123456789", "Male", "19", "He has been slain....");
		ArrayList<String> list = getCustomers();
		for(String item: list){
			System.out.println(item);
		}
		createCustomer("Anonymous", "123411145", "Female", "22", "Important Customer");
		list = getCustomers();
		for(String item: list){
			System.out.println(item);
		}
	}
	
	public static ArrayList<String> getCustomers(){
		try{
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("Select name, phone, gender FROM customer");
			ResultSet results = statement.executeQuery();
			ArrayList<String> list = new ArrayList<String>();
			while(results.next()){
				list.add("Name : " + results.getString("name") +
						" Phone : " +results.getString("phone") +
						" Gender :" +results.getString("gender"));
			}
			System.out.println("The data has been fetched");
			return list;
		} catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	
	public static void createCustomer(String name, String phone, String gender, String age, String note ){
		try{
			Connection con = getConnection();
			PreparedStatement insert = con.prepareStatement(""
					+ "INSERT INTO customer"
					+ "(name, phone, gender, age, note) "
					+ "VALUE "
					+ "('"+name+"','"+phone+"','"+gender+"','"+age+"','"+note+"')");
			insert.executeUpdate();
			System.out.println("The data has been saved!");
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static void createTable(){
		try{
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS " // 여기 EXISTS 뒤에 스페이스 하나 무적권 넣어줘야 합니다.
					+ "customer(id int NOT NULL AUTO_INCREMENT,"
					+ "name varChar(255),"
					+ "phone varChar(255),"
					+ "gender varChar(255),"
					+ "age varChar(255),"
					+ "note varChar(255),"
					+ "PRIMARY KEY(id))");
			create.execute();
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally{
			System.out.println("Table successfully created");
		}
	}
	
	
	
	public static Connection getConnection() {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://sql5.freemysqlhosting.net:3306/sql5452557";
			String user = "sql5452557";
			String pass = "ZFLbQedUhC";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,user,pass);
			System.out.println("The Connection is Success");
			return con;
		} catch (Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}

}
