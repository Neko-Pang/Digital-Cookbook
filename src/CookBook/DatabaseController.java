package CookBook;

import java.io.Serializable;
import java.lang.Thread.State;
import java.sql.*;
import java.util.ArrayList;

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
	 * This method is to add the recipe information into database and return a
	 * boolean to show the process
	 * 
	 * @param recipe
	 * @return isSuccess / whether the method succeeds
	 */
	public Boolean insertRecipe(Recipe recipe) {

		Connection conn = this.getConn();

		// these booleans are to check whether each part has been added
		// successfully
		Boolean isSuccess = false;
		Boolean isSuccessRecipe = false;
		Boolean isSuccessIngre = false;
		Boolean isSuccessPrep = false;
		int resultRecipe = 0;
		int resultIngre = 0;
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

			// set the information
			sql = conn.prepareStatement(statementBasic);
			sql.setString(1, recipe.getName());
			sql.setInt(2, recipe.getPreparationTime());
			sql.setInt(3, recipe.getAccountID());
			sql.setInt(4, recipe.getCookingTime());
			sql.setInt(5, recipe.getServingPpl());
			sql.setString(6, recipe.getCategary());
			// execute the statement and to check whether the sql works
			resultRecipe = sql.executeUpdate();
			if (resultRecipe == 1) {
				isSuccessRecipe = true;
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

				resultIngre += sql.executeUpdate();

			}
			if (resultIngre == recipe.getIngredients().size()) {

				isSuccessIngre = true;

			}

			/*
			 * 3rd step: to store the preparation steps
			 */
			int resultPrep = 0;
			String statementPrepSteps = "insert into preparationstep(RecipeID , StepNo , PreparationContext) "
					+ "values(?,?,?)";
			sql = conn.prepareStatement(statementPrepSteps);
			for (int i = 0; i < recipe.getPreparationStep().size(); i++) {

				String context = recipe.getPreparationStep().get(i);
				int stepNo = i + 1;
				sql.setString(3, context);
				sql.setInt(2, stepNo);
				sql.setInt(1, recipe.getRecipeID());
				resultPrep += sql.executeUpdate();
			}

			// check whether preparationstep has been inserted succesfully
			if (resultPrep == recipe.getPreparationStep().size()) {

				isSuccessPrep = true;

			}

			// } else {
			// System.out.println("The recipe already exists");
			// }

			if (isSuccessIngre && isSuccessIngre && isSuccessPrep) {
				isSuccess = true;
			}

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

		return isSuccess;
	}

	/**
	 * Delete the recipe by recipeID and return a boolean to check the process
	 * 
	 * @param recipeID
	 *            / the ID of recipe
	 * @return isDeleted
	 */
	public Boolean deleteRecipe(int recipeID) {

		Boolean isDeleted = true;
		Connection conn = this.getConn();

		try {
			Statement state = conn.createStatement();

			// Need to check whether the recipe is extant in the database
			String strCheckBefore = "select * from recipe where RecipeID = '" + recipeID + "'";
			ResultSet result = state.executeQuery(strCheckBefore);
			if ( result.next() ) {
				String strDelete = "delete from recipe where RecipeID = '" + recipeID + "'";
				PreparedStatement sql = conn.prepareStatement(strDelete);
				sql.executeUpdate();
				String strDeleteIngre = "delete from ingredientusinginfo where RecipeID = '" + recipeID + "'";
				sql = conn.prepareStatement(strDeleteIngre);
				sql.executeUpdate();
				String strDeletePrep = "delete from preparationstep where RecipeID = '" + recipeID + "'";
				sql = conn.prepareStatement(strDeletePrep);
				sql.executeUpdate();

				// Check whether the recipe has been deleted

				// 1st recipe form

				String strCheckAfter = "select * from recipe where RecipeID = '" + recipeID + "'";
				result = state.executeQuery(strCheckAfter);
				if ( result.next() ) {
					isDeleted = false;
				}
				// 2nd ingredientusinginfo form
				String strCheckIngre = "select * from ingredientusinginfo where RecipeID = '" + recipeID + "'";
				result = state.executeQuery(strCheckIngre);
				if ( result.next() ) {
					isDeleted = false;
				}
				// 3rd preparation step form
				String strCheckPrep = "select * from preparationstep where RecipeID = '" + recipeID + "'";
				result = state.executeQuery(strCheckPrep);
				if ( result.next() ) {
					isDeleted = false;
				}
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
		return isDeleted;
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
	 * To get the recipe from the database by name, because the name can be
	 * duplicate so we need an arraylist to store the recipes
	 * 
	 * @param recipeName
	 * @return
	 */
	public ArrayList<Recipe> searchRecipe(String recipeName) {

		ArrayList<Recipe> goalRecipe = new ArrayList<Recipe>();
		int recipeID = 0;
		Connection conn = this.getConn();

		try {

			String statementSearchRe = "select * from recipe where Name='" + recipeName + "'";
			Statement sql = conn.createStatement();
			ResultSet searchResult = sql.executeQuery(statementSearchRe);
			while (searchResult.next()) {

				Recipe recipe = new Recipe();

				// 1st:take out the basic information
				recipeID = searchResult.getInt("RecipeID");
				recipe.setRecipeID(recipeID);
				recipe.setName(searchResult.getString("Name"));
				recipe.setAccountID(searchResult.getInt("AccountID"));
				recipe.setCategary(searchResult.getString("Category"));
				recipe.setCookingTime(searchResult.getInt("CookingTime"));
				recipe.setPreparationTime(searchResult.getInt("PrepTime"));

				recipe.setServingPpl(searchResult.getInt("ServingPeople"));
				// 2nd : take out the ingredient information
				if (recipeID != 0) {
					recipe.setIngredients(searchIngre(recipeID));
				}

				// 3rd : take out the preparation step information
				if (recipeID != 0) {
					recipe.setPreparationStep(searchPrep(recipeID));
				}

				goalRecipe.add(recipe);
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
