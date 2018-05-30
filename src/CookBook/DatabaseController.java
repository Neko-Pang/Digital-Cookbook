package CookBook;

import java.io.Serializable;
import java.lang.Thread.State;
import java.sql.*;
import java.util.ArrayList;

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

	/**
	 * This method is to add the recipe information into database
	 * 
	 * @param recipe
	 */
	public void insertRecipe(Recipe recipe) {

		Connection conn = this.getConn();

		try {

			Statement state = conn.createStatement();
			/*
			 * We decide that the recipe can using the same name now so we
			 * needn't to check
			 */

			// to check whether the recipe has already be added to the database

			// ResultSet resultSetOfCheck = state.executeQuery(statementCheck);
			// if (!resultSetOfCheck.next()) {
			/*
			 * 1st step: to store basic information into database(not included
			 * the preparation & ingredient info)
			 */
			String statementBasic = "insert into recipe( Name , PrepTime , AccountID , CookingTime , ServingPeople , Category ) "
					+ "values(?,?,?,?,?,?)";
			PreparedStatement sql;
			int result;
			// set the information
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
			} else {
				System.out.println("Insert basic information failed");
			}

			// Because the ID in database is auto_increment,we need to get
			// the ID back from the database
			String statementCheck = "select * from recipe where Name like '" + recipe.getName() + "';";
			ResultSet resultSetOfCheck = state.executeQuery(statementCheck);
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

			} else {
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
				int stepNo = i + 1;
				sql.setString(3, context);
				sql.setInt(2, stepNo);
				sql.setInt(1, recipe.getRecipeID());
				result += sql.executeUpdate();
			}

			if (result == recipe.getPreparationStep().size()) {

				System.out.println("Insert preparation information successfully");

			} else {
				System.out.println("Only " + result + " rows changed.");
			}

			// } else {
			// System.out.println("The recipe already exists");
			// }

			/*
			 * We need to add the ingredient into ingredient table when insert
			 * the recipe
			 */

			for (int i = 0; i < recipe.getIngredients().size(); i++) {
				this.insertIngre(recipe.getIngredients().get(i));
			}

			// close the connection
			resultSetOfCheck.close();
			conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * From the recipe, we need to add the ingredient into the ingredient table
	 * 
	 * @param the
	 *            ingredient
	 */
	public void insertIngre(Ingredient ingre) {

		Connection conn = this.getConn();
		int result = 0;
		try {
			// We need to check whether the ingredient exists in the database
			String strSelectIngre = "select * from ingredient where Name='" + ingre.getName() + "'";
			Statement sqlSearch = conn.createStatement();
			ResultSet searchResult = sqlSearch.executeQuery(strSelectIngre);

			// only when the ingredent is not in the database can the ingredient
			// be inserted
			if (!searchResult.next()) {

				// execute the statement
				String strInsertIngre = "insert into ingredient(Name) values(?)";
				PreparedStatement sqlInsert = conn.prepareStatement(strInsertIngre);
				sqlInsert.setString(1, ingre.getName());
				result = sqlInsert.executeUpdate();

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// close the connection
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * To get the recipe from the database by name
	 * 
	 * @param recipeName
	 * @return
	 */
	public Recipe searchRecipe(String recipeName) {

		Recipe goalRecipe = null;
		int recipeID = 0;
		Connection conn = this.getConn();

		try {
			// 1st:take out the basic information
			String statementSearchRe = "select * from recipe where Name='" + recipeName + "'";
			Statement sql = conn.createStatement();
			ResultSet searchResult = sql.executeQuery(statementSearchRe);
			while (searchResult.next()) {
				goalRecipe = new Recipe();
				goalRecipe.setName(searchResult.getString("Name"));
				goalRecipe.setAccountID(searchResult.getInt("AccountID"));
				goalRecipe.setCategary(searchResult.getString("Category"));
				goalRecipe.setCookingTime(searchResult.getInt("CookingTime"));
				goalRecipe.setPreparationTime(searchResult.getInt("PrepTime"));
				recipeID = searchResult.getInt("RecipeID");
				goalRecipe.setRecipeID(recipeID);
				goalRecipe.setServingPpl(searchResult.getInt("ServingPeople"));
			}

			// 2nd : take out the ingredient information
			if (recipeID != 0) {
				goalRecipe.setIngredients(searchIngre(recipeID));
			}

			// 3rd : take out the preparation step information
			if (recipeID != 0) {
				goalRecipe.setPreparationStep(searchPrep(recipeID));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		// We need to close the connection after the method ends
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (recipeID == 0) {
			System.out.println("The recipe does not exist");
		}

		return goalRecipe;
	}

	/**
	 * This method is to get the ingredient information from database by
	 * recipeID
	 * 
	 * @param RecipeID
	 * @return
	 */
	public ArrayList<Ingredient> searchIngre(int RecipeID) {
		ArrayList<Ingredient> goalIngre = new ArrayList<Ingredient>();

		Connection conn = this.getConn();

		try {
			String statementSearchIn = "select * from ingredientusinginfo where RecipeID ='" + RecipeID + "'";
			Statement sql = conn.createStatement();
			ResultSet resultIngredient = sql.executeQuery(statementSearchIn);
			while (resultIngredient.next()) {

				Ingredient ingredient = new Ingredient();
				ingredient.setName(resultIngredient.getString("Name"));
				ingredient.setCookingWay(resultIngredient.getString("CookingWay"));
				ingredient.setUnit(resultIngredient.getString("Unit"));
				ingredient.setWeight(resultIngredient.getDouble("Weight"));
				goalIngre.add(ingredient);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// close the connection
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (goalIngre.size() == 0) {
			System.out.println("None ingredient information was found in the database");
		}
		return goalIngre;

	}

	/**
	 * This method is to take the preparation step from database by recipe ID
	 * and StepNo ascend
	 * 
	 * @param RecipeID/
	 *            the primary key of recipe
	 * @return goalPrep/ the ArrayList of preparation step
	 */
	public ArrayList<String> searchPrep(int RecipeID) {

		ArrayList<String> goalPrep = new ArrayList<String>();

		Connection conn = this.getConn();

		try {

			// The sql statement
			String statementSearchPrep = "select * from preparationstep where RecipeID ='" + RecipeID
					+ "' order by StepNo asc";
			Statement sql = conn.createStatement();
			ResultSet resultPrep = sql.executeQuery(statementSearchPrep);
			while (resultPrep.next()) {
				String prep = resultPrep.getString("PreparationContext");
				goalPrep.add(prep);
			}

			sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (goalPrep.size() == 0) {
			System.out.println("No preparation step information was found in the databse");
		}

		// close the connection
		try {

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return goalPrep;

	}

}
