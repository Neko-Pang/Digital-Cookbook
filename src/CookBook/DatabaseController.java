package CookBook;

import java.io.Serializable;
import java.sql.*;

import org.omg.CORBA.portable.ValueInputStream;

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
	private String dbUser = "root";
	private String dbPass = "xiaoxia12";

	public Connection getConn() {
		Connection conn = null;

		try {
			// load the driver
			Class.forName(driver);
			// connect to the database
			conn = DriverManager.getConnection(url, dbUser, dbPass);

			if (!conn.isClosed()) {
				System.out.println("Succeeded connecting to the Database!");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return conn;
	}

	public void insertRecipe(Recipe recipe) {

		Connection conn = this.getConn();

		try {

			Statement state = conn.createStatement();
			// to check whether the recipe has already be added to the database
			String statementCheck = "select * from recipe where Name like '" + recipe.getName() + "';";
			ResultSet resultSetOfCheck = state.executeQuery(statementCheck);
			if (!resultSetOfCheck.next()) {
				/*
				 * 1st step: to store basic information into database(not
				 * included the preparation & ingredient info)
				 */
				String statementBasic = "insert into recipe( Name , PrepTime , AccountID , CookingTime , ServingPeople , Category ) "
						+ "values(?,?,?,?,?,?)";
				PreparedStatement sql;
				int result;
				//set the information
				sql = conn.prepareStatement(statementBasic);
				sql.setString(1, recipe.getName());
				sql.setInt(2, recipe.getPreparationTime());
				sql.setInt(3, recipe.getAccountID());
				sql.setInt(4, recipe.getCookingTime());
				sql.setInt(5, recipe.getServingPpl());
				sql.setString(6, recipe.getCategary());
				// execute the statement and to check whether the sql works
				result = sql.executeUpdate();
				if (result == 1) {
					System.out.println("Insert basic information successfully");
				}else{
					System.out.println("Insert basic information failed");
				}
				
				// Because the ID in database is auto_increment,we need to get
				// the ID back from the database
				resultSetOfCheck = state.executeQuery(statementCheck);
				while (resultSetOfCheck.next()) {
					recipe.setRecipeID(resultSetOfCheck.getInt("RecipeID"));
				}

				/*
				 * 2nd step: to store the ingredient using information
				 */
				String statementIngreInfo = "insert into ingredientusinginfo( Name , RecipeID , CookingWay , Weight , Unit) "
						+ "values(?,?,?,?,?)";
				sql = conn.prepareStatement(statementIngreInfo);
				for (int i = 0; i < recipe.getIngredients().size(); i++) {
					Ingredient ingrePointer = recipe.getIngredients().get(i);
					sql.setString(1, ingrePointer.getName());
					sql.setInt(2, recipe.getRecipeID());
					sql.setString(3, ingrePointer.getCookingWay());
					sql.setDouble(4, ingrePointer.getWeight());
					sql.setString(5, ingrePointer.getUnit());
					// execute the statement and to check whether the sql works
					result += sql.executeUpdate();
					
				}
				if (result == recipe.getIngredients().size()) {
					
					System.out.println("Insert ingredient information successfully");
				
				}else{
					System.out.println("Only " + result + " rows changed.");
				}
				
				/*
				 * 3rd step: to store the preparation steps
				 */
				String statementPrepSteps = "insert into preparationstep(RecipeID , StepNo , PreparationContext) " 
						+ "values(?,?,?)";
				sql = conn.prepareStatement(statementPrepSteps);
				for (int i = 0; i < recipe.getPreparationStep().size(); i++) {
					
					String context = recipe.getPreparationStep().get(i);
					int stepNo = i+1;
					sql.setString(3, context);
					sql.setInt(2, stepNo);
					sql.setInt(1, recipe.getRecipeID());
					result += sql.executeUpdate();
				}
				
				if (result == recipe.getPreparationStep().size()) {
					
					System.out.println("Insert preparation information successfully");
				
				}else{
					System.out.println("Only " + result + " rows changed.");
				}
			

			} else {
				System.out.println("The recipe already exists");
			}

			resultSetOfCheck.close();
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
