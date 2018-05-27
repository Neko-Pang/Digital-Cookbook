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
	    	
	    	
	   
	    	try {
	    		
	    		Statement state = conn.createStatement();
	    		//to check whether the recipe has already be added to the database
		    	String statementCheck = "select * from recipe where Name like '" + recipe.getName() + "';";
				ResultSet resultSetOfCheck = state.executeQuery(statementCheck);
				if(!resultSetOfCheck.next()){
					/*
		    		 * 1st step: to store basic information into database(not included the preparation & ingredient info)
		    		 */
					String statementBasic = "insert into recipe(Name,PrepTime,AccountID,CookingTime,ServingPeople,Category) "
		    				+ "values(?,?,?,?,?,?)";
		    		PreparedStatement sql;
		    		int result;
		    		try {
		    			sql = conn.prepareStatement(statementBasic);
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
		    				System.out.println("Insert basic information success");
		    			}
		    		
					
		    		} catch (SQLException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		    		/*
			    	 * 2nd step: to store the ingredient using information
			    	 */
//		    		String statementIngreInfo = "insert into "
		    		
		    	
		    	
		    	}else{
		    		System.out.println("The recipe already exists");
		    	}
				
				
				//Because the ID in database is auto_increment,we need to get the ID back from the database
				resultSetOfCheck = state.executeQuery(statementCheck);
				while(resultSetOfCheck.next()){
					recipe.setRecipeID(resultSetOfCheck.getInt("RecipeID"));
				}
				resultSetOfCheck.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	
	    	
	    	
	    }


}


	    
