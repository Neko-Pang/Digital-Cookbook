package CookBook;
import java.io.Serializable;
import java.sql.*;

public class DatabaseController implements Serializable {
		
		/**
		 * the name of the program driver
		 */
		private String driver = "com.mysql.jdbc.Driver"; 
	    
		/**
		 * point to your database
		 */
		private String url = "jdbc:mysql://localhost:3306/cookbookdatabase?useSSL=false";
		
		/**
		 * your username and the password of mysql
		 */
		private String dbUser="root";  
	    private String dbPass="xiaoxia12";
	    
	    public Connection getConn() {
	    	Connection conn = null;
	    	
	    	try { 
	    		// load the driver
	    		Class.forName(driver);
	    		// connect to the database
	    		conn = DriverManager.getConnection(url, dbUser, dbPass);
	    		
	    		//we need to close the autocommit here or the event will not be committed later
	    		conn.setAutoCommit(false);
	    		
	    		if(!conn.isClosed()) 
	                 {System.out.println("Succeeded connecting to the Database!");}
	    		
	    	}catch(Exception e){
	    		
	    		e.printStackTrace(); 
	    	
	    	}
	    
	        return conn;
	    }
	    
	    public void insertRecipe(Recipe recipe){
	    	
	    	Connection conn = this.getConn();
	    	 String statement="insert into recipe(Name,PrepTime,AccountID,CookingTime,ServingPeople,Category) "
	    	 		+ "values(?,?,?,?,?,?)";
	    	 PreparedStatement sql;
	    	 int result;
			try {
				sql = conn.prepareStatement(statement);
				sql.setString(1,recipe.getName());
				sql.setInt(2, recipe.getPreparationTime());
				sql.setInt(3, recipe.getAccountID());
				sql.setInt(4, recipe.getCookingTime());
				sql.setInt(5, recipe.getServingPpl());
				sql.setString(6, recipe.getCategary());
				//to check whether it works
				result = sql.executeUpdate();
				conn.commit();
				
				
				if( result == 1){
		    		System.out.println("Insert success");
		    	}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 
	    	
	    	
	    }


}


	    
