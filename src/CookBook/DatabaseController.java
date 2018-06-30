package CookBook;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;


/**
 * This class is to connect the database with logic layer,and it is realized in singleton pattern
 * @author MacroHard
 * @version 1.0
 */
public class DatabaseController implements Serializable {
	
	/**
	 * the connection to database
	 */
	private static Connection conn;
	
	/**
	 * the name of the program driver
	 */
	private String driver = "com.mysql.cj.jdbc.Driver";

	/**
	 * point to your database
	 */
	private String url = "jdbc:mysql://localhost:3306/cookbookdatabase?useSSL=false&serverTimezone=GMT";
	

	/**
	 * your username and the password of mysql
	 */
	private String dbUser = "root";
	private String dbPass = "xiaoxia12"
			+ "";
	
	// To realize Singleton Pattern
	private static DatabaseController instance = null;
	
	/**
	 * to make constructor private
	 */
	private DatabaseController() {
		this.conn = this.getConn();
	}
	
	/**
	 * To get the only object
	 * @return the jdbc of singleton 
	 */
	public static DatabaseController getInstance() {
		
		if(instance == null){
			instance = new DatabaseController();
		}
		
	    return instance;
	}
	
	
	/**
	 * To get the connection to the database
	 * @return connection 
	 */
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


		// these booleans are to check whether each part has been added
		// successfully
		Boolean isSuccess = false;
		Boolean isSuccessRecipe = false;
		Boolean isSuccessIngre = false;
		Boolean isSuccessPrep = false;

		try {

			/*
			 * 1st step: to store the basic information
			 */
			isSuccessRecipe = this.insertRecipeBasicInfo(recipe);
			
			// Because the ID in database is auto_increment,we need to get
			// the ID back from the database
			Statement state = conn.createStatement();
			String statementCheck = "select * from recipe where Name like '" + recipe.getName() + "' order by recipeID asc;";
			ResultSet resultSetOfCheck = state.executeQuery(statementCheck);
			while (resultSetOfCheck.next()) {
				recipe.setRecipeID(resultSetOfCheck.getInt("RecipeID"));
			}

			/*
			 * 2nd step: to store the ingredient using information
			 */
			isSuccessIngre = this.insertRecipeIngreInfo(recipe);

			
			/*
			 * 3rd step: to store the preparation steps
			 */
			isSuccessPrep = this.insertRecipePrepStep(recipe);

		

			if (isSuccessRecipe && isSuccessIngre && isSuccessPrep) {
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
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		
		return isSuccess;
	}
	
	/**
	 * This method is to store the basic recipe information into database(recipe form)
	 * @param recipe
	 * @return isSuccessRecipe/whether the information has been added to database
	 */
	public boolean insertRecipeBasicInfo(Recipe recipe){
		
		
		boolean isSuccessRecipe = false;
		int resultRecipe;
		
		
		/*
		 * 1st step: to store basic information into database(not included
		 * the preparation & ingredient info)
		 */
		String statementBasic = "insert into recipe( Name , PrepTime , AccountID , CookingTime , ServingPeople , Category ) "
				+ "values(?,?,?,?,?,?)";
		PreparedStatement sql;

		// set the information
		try {
			
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
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return isSuccessRecipe;
	}
	
	/**
	 * this method is to store the ingredient information into database (ingredientusiinginfo form )
	 * @param recipe
	 * @return isSuccessIngre/boolean
	 */
	public boolean insertRecipeIngreInfo(Recipe recipe){
		
		Boolean isSuccessIngre = false;
		int resultIngre=0;
		String statementIngreInfo = "insert into ingredientusinginfo( Name , RecipeID , CookingWay , Weight , Unit) "
				+ "values(?,?,?,?,?)";
		PreparedStatement sql;
		try {
			
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (resultIngre == recipe.getIngredients().size()) {

			isSuccessIngre = true;

		}
		
		
		return isSuccessIngre;
	}
	
	/**
	 * this method is to store the preparation step into database(PreparationStep form)
	 * @param recipe
	 * @return isSuccessPrep/boolean
	 */
	public Boolean insertRecipePrepStep(Recipe recipe){
		
		int resultPrep = 0;
		Boolean isSuccessPrep =false;
		
		String statementPrepSteps = "insert into preparationstep(RecipeID , StepNo , PreparationContext) "
				+ "values(?,?,?)";
		PreparedStatement sql;
		
		try {
			sql = conn.prepareStatement(statementPrepSteps);
			for (int i = 0; i < recipe.getPreparationStep().size(); i++) {

				String context = recipe.getPreparationStep().get(i);
				int stepNo = i + 1;
				sql.setString(3, context);
				sql.setInt(2, stepNo);
				sql.setInt(1, recipe.getRecipeID());
				resultPrep += sql.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// check whether preparationstep has been inserted succesfully
		if (resultPrep == recipe.getPreparationStep().size()) {

			isSuccessPrep = true;

		}
		
		
		return  isSuccessPrep;
	}
	
	
	
	/**
	 * To update a recipe by a new recipe
	 * @param recipeID
	 * @param newRecipe
	 */
	public void updateRecipe(int recipeID, Recipe newRecipe){
		
		
		/*
		 * 1st step: update the basic information
		 */
		this.updateRecipeBasicInfo(recipeID, newRecipe);
		
		
		/*
		 * 2nd step: update the ingredient information
		 */
		this.updateIngreInfo(recipeID, newRecipe);
		
		/*
		 * 3rd step: update the preparation step
		 */
		this.updatePrepStep(recipeID, newRecipe);
		
	}
	
	
	/**
	 * This method is to update the basic information of a recipe by recipeID(Recipe Form)
	 * @param recipeID / the ID
	 * @param newRecipe / the newest edited recipe
	 */
	public void updateRecipeBasicInfo(int recipeID,Recipe newRecipe){
		
		
		try {
			
			String updateStr = "update recipe set Name=? , PrepTime=? , AccountID = ? ,CookingTime = ? , ServingPeople = ? , Category = ? where "
					+ "RecipeID = '" + recipeID +"'";
			PreparedStatement statement = conn.prepareStatement(updateStr);
			statement.setString(1, newRecipe.getName());
			statement.setInt(2, newRecipe.getPreparationTime());
			statement.setInt(3, newRecipe.getAccountID());
			statement.setInt(4, newRecipe.getCookingTime());
			statement.setInt(5, newRecipe.getServingPpl());
			statement.setString(6, newRecipe.getCategary());
			statement.execute();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * this method is to update the new ingredient information
	 * @param recipeID
	 * @param newRecipe
	 */
	public void updateIngreInfo(int recipeID, Recipe newRecipe){
		
		this.deleteRecipeIngreInfo(recipeID);
		newRecipe.setRecipeID(recipeID);
		this.insertRecipeIngreInfo(newRecipe);
	
	}
	
	/**
	 * this method is to update the new preparation steps
	 * @param recipeID
	 * @param newRecipe
	 */
	public void updatePrepStep(int recipeID, Recipe newRecipe){
		
		this.deleteRecipePrepStep(recipeID);
		newRecipe.setRecipeID(recipeID);
		this.insertRecipePrepStep(newRecipe);
		
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
		
		try {
			Statement state = conn.createStatement();

			// Need to check whether the recipe is extant in the database
			String strCheckBefore = "select * from recipe where RecipeID = '" + recipeID + "'";
			ResultSet result = state.executeQuery(strCheckBefore);
			if ( result.next() ) {
				//1st :delete basic information
				isDeleted = isDeleted && this.deleteRecipeBasicInfo(recipeID);
				
				//2nd :delete ingredient using information
				isDeleted = isDeleted && this.deleteRecipeIngreInfo(recipeID);
				
				//3rd :delete preparation step
				isDeleted = isDeleted && this.deleteRecipePrepStep(recipeID);
			
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return isDeleted;
	}
	
	/**
	 * this method is to delete basic information of recipe from database (recipe form)
	 * @param recipeID
	 * @return
	 */
	public boolean deleteRecipeBasicInfo(int recipeID){
		
		boolean isDeleted = false;
		
		String strDelete = "delete from recipe where RecipeID = '" + recipeID + "'";
		PreparedStatement sql;
		
		try {
			sql = conn.prepareStatement(strDelete);
			sql.executeUpdate();
			isDeleted = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String strCheckAfter = "select * from recipe where RecipeID = '" + recipeID + "'";
		
		ResultSet result;
		Statement state;
		try {
			
			state = conn.createStatement();
			result = state.executeQuery(strCheckAfter);
			if ( result.next() ) {
				isDeleted = false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return isDeleted;
	}
	
	
	/**
	 * this method is to delete ingredient information from the database(ingredientusinginfo form)
	 * @param recipeID
	 * @return
	 */
	public boolean deleteRecipeIngreInfo(int recipeID){
		boolean isDeleted = false;
		
		
		//delete the information
		String strDeleteIngre = "delete from ingredientusinginfo where RecipeID = '" + recipeID + "'";
		PreparedStatement sql;
		try {
			sql = conn.prepareStatement(strDeleteIngre);
			sql.executeUpdate();
			isDeleted = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Check ingredientusinginfo form
		String strCheckIngre = "select * from ingredientusinginfo where RecipeID = '" + recipeID + "'";
		Statement state;
		try {
			
			state = conn.createStatement();
			ResultSet result = state.executeQuery(strCheckIngre);
			if ( result.next() ) {
				isDeleted = false;
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return isDeleted;
	}
	
	/**
	 * this method is to delete preparation step from database(PreparationStep form)
	 * @param recipeID
	 * @return
	 */
	public boolean deleteRecipePrepStep(int recipeID){
	
		boolean isDeleted = false;
		String strDeletePrep = "delete from preparationstep where RecipeID = '" + recipeID + "'";
		PreparedStatement sql;
		
		try {
			sql = conn.prepareStatement(strDeletePrep);
			sql.executeUpdate();
			isDeleted = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		//check whether delete is successful
		String strCheckPrep = "select * from preparationstep where RecipeID = '" + recipeID + "'";
		Statement state;
		try {
			
			state = conn.createStatement();
			ResultSet result = state.executeQuery(strCheckPrep);
			if ( result.next() ) {
				isDeleted = false;
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		return isDeleted;
	}
	
	/**
	 * From the recipe, we need to add the ingredient into the ingredient table
	 * 
	 * @param the ingredient
	 */
	public void insertIngre(Ingredient ingre) {

	
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

		

	}
	
	/**
	 * To get the recipe from the database by name, because the name can be
	 * duplicated so we need an arraylist to store the recipes
	 * 
	 * @param recipeName
	 * @return
	 */
	public ArrayList<Recipe> searchRecipe(String recipeName) {

		ArrayList<Recipe> goalRecipe = new ArrayList<Recipe>();
		int recipeID = 0;
		

		try {

			String statementSearchRe = "select * from recipe where Name like '%" + recipeName + "%'";
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
				
				if (recipeID != 0) {
					recipe.setComments(searchCommentByRecipe(recipeID));
				}

				goalRecipe.add(recipe);
			}

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
	 * This method is an overload one , to search recipe by recipeID
	 * @param recipeID
	 * @return
	 */
	public Recipe searchRecipe(int recipeID){
		
		Recipe recipe = null;

		try {
			
			String statementSearchRe = "select * from recipe where RecipeID='" + recipeID + "'";
			Statement sql = conn.createStatement();
			ResultSet searchResult = sql.executeQuery(statementSearchRe);
			while (searchResult.next()) {

				recipe = new Recipe();
				// 1st:take out the basic information
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
					recipe.setComments(searchCommentByRecipe(recipeID));
				}
			
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return recipe;
		
	}
	
	/**
	 * this method is to search recipe by accountID
	 * @param ID
	 * @return
	 */
	public ArrayList<Recipe> searchRecipeByAccount(int ID) {

		ArrayList<Recipe> goalRecipe = new ArrayList<Recipe>();
		int recipeID = 0;

		try {

			String statementSearchRe = "select * from recipe where AccountID='" + ID + "'";
			Statement sql = conn.createStatement();
			ResultSet searchResult = sql.executeQuery(statementSearchRe);
			while (searchResult.next()) {

				Recipe recipe = new Recipe();

				// 1st:take out the basic information
				recipeID = searchResult.getInt("RecipeID");
				recipe.setRecipeID(recipeID);
				recipe.setName(searchResult.getString("Name"));
				recipe.setAccountID(ID);
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
					recipe.setComments(searchCommentByAccount(ID));
				}

				goalRecipe.add(recipe);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}


		if (ID == 0) {
			System.out.println("The user does not exist");
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


//		if (goalIngre.size() == 0) {
//			System.out.println("None ingredient information was found in the database");
//		}
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

//		if (goalPrep.size() == 0) {
//			System.out.println("No preparation step information was found in the databse");
//		}

		
		return goalPrep;

	}
	
	/**
	 * store user information into database(user)
	 * @param registeredUser
	 * @return
	 */
	public boolean insertUser(RegisteredUser registeredUser) {
		int result = 0;
		boolean isSuccess = false;
		String username = registeredUser.getUserName();
		try {
			// We need to check whether the user name exists in the database
			String strSelectUser = "select * from user where Username='" + username + "'";
			Statement sqlSearch = conn.createStatement();
			ResultSet searchResult = sqlSearch.executeQuery(strSelectUser);
			// Only when the user name does not exist in the database can the User be inserted
			if (!searchResult.next()) {
				String strInsertUser = "insert into user( Username , Password)" + "values(?,?)";
				PreparedStatement sqlInsert = conn.prepareStatement(strInsertUser);
				sqlInsert.setString(1, username);
				sqlInsert.setString(2, registeredUser.getPassword());
				result = sqlInsert.executeUpdate();
	
				if (result == 1) {
					
					isSuccess = true;
					
				}
			}
			
			searchResult = sqlSearch.executeQuery(strSelectUser);
			if (searchResult.next()) {
				registeredUser.setAccountID(searchResult.getInt("AccountID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	
	
	public boolean insertComment(Comment comment) {
		int result = 0;
		boolean isSuccess = false;
		try {
			// Only when the user name does not exist in the database can the User be inserted
			
				String strInsertComment = "insert into comment( RecipeID , AccountID , Content)" + "values(?,?,?)";
				PreparedStatement sqlInsert = conn.prepareStatement(strInsertComment);
				sqlInsert.setInt(1, comment.getRecipeID());
				sqlInsert.setInt(2, comment.getAccountID());
				sqlInsert.setString(3, comment.getContext());
				result = sqlInsert.executeUpdate();
				if (result == 1) {
					isSuccess = true;
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	
	/**
	 * This method is to get the specific user by Username
	 * @param name
	 * @return
	 */
	public RegisteredUser searchUser(String name){
		
		RegisteredUser user = null;
		int userID ;
		
		try {
			String searchUser = "select * from user where Username = '"+ name +"'";
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery(searchUser);
			
			if(result.next()){
				user = new RegisteredUser();
				userID=result.getInt("AccountID");
				user.setAccountID(userID);
				user.setUserName(name);
				user.setPassword(result.getString("password"));
				
				user.setOwnRecipes(searchRecipeByAccount(userID));
				user.setOwnComments(searchCommentByAccount(userID));
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return user;
	}
	
	
	public RegisteredUser searchUser(int accountID){
		RegisteredUser registeredUser = new RegisteredUser();
		try {
			String statementSearchRe = "select * from user where AccountID='" + accountID + "'";
			Statement sql = conn.createStatement();
			ResultSet searchResult = sql.executeQuery(statementSearchRe);
			while (searchResult.next()) {
				// 1st:take out the basic information
				registeredUser.setAccountID(accountID);
				registeredUser.setUserName(searchResult.getString("Username"));
				registeredUser.setPassword(searchResult.getString("Password"));
				// 2nd : take out the ingredient information
				registeredUser.setOwnRecipes(searchRecipeByAccount(accountID));
				// 3rd : take out the preparation step information
				registeredUser.setOwnComments(searchCommentByAccount(accountID));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return registeredUser;
	}
	
	
	
	public ArrayList<Comment> searchCommentByRecipe(int recipeID) {
		ArrayList<Comment> resultComment = new ArrayList<Comment>();
		int accountID = 0;
		try {
			String statementSearchRe = "select * from comment where RecipeID ='" + recipeID + "' order by CommentNo asc";
			Statement sql = conn.createStatement();
			ResultSet searchResult = sql.executeQuery(statementSearchRe);
			while (searchResult.next()) {
				Comment comment = new Comment();
				accountID = searchResult.getInt("AccountID");
				comment.setCommentNo(searchResult.getInt("CommentNo"));
				comment.setRecipeID(recipeID);
				comment.setAccountID(accountID);
				comment.setContext(searchResult.getString("Content"));	
				resultComment.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (recipeID == 0) {
			System.out.println("The recipe does not exist.");
		}
		return resultComment;
	}
	
	public ArrayList<Comment> searchCommentByAccount(int accountID) {
		ArrayList<Comment> resultComment = new ArrayList<Comment>();
		int recipeID = 0;
		try {
			String statementSearchRe = "select * from comment where AccountID ='" + accountID + "'";
			Statement sql = conn.createStatement();
			ResultSet searchResult = sql.executeQuery(statementSearchRe);
			while (searchResult.next()) {
				Comment comment = new Comment();
				recipeID = searchResult.getInt("RecipeID");
				comment.setCommentNo(searchResult.getInt("CommentNo"));
				comment.setRecipeID(recipeID);
				comment.setAccountID(accountID);
				comment.setContext(searchResult.getString("Content"));	
				resultComment.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (accountID == 0) {
			System.out.println("The user does not exist.");
		}
		return resultComment;
	}
	
	/**
	 * Delete the comment
	 * @param recipeID
	 * @param accountID
	 * @param commentID
	 * @return
	 */
	public boolean deleteComment(int recipeID, int accountID, int commentNo){
		boolean isDeleted = false;
		String strDeleteComment = "delete from comment where RecipeID = '" + recipeID + "'"
				+ " and AccountID = '" + accountID + "'"+ " and CommentNo = '" + commentNo + "'";
		PreparedStatement sql;
		try {
			sql = conn.prepareStatement(strDeleteComment);
			sql.executeUpdate();
			isDeleted = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Check whether deletion is successful
		String strCheckComment = "select * from comment where RecipeID = '" + recipeID + "'"
				+ " and AccountID = '" + accountID + "'"+ " and CommentNo = '" + commentNo + "'";
		Statement state;
		try {
			state = conn.createStatement();
			ResultSet result = state.executeQuery(strCheckComment);
			if (result.next()) {
				isDeleted = false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return isDeleted;
	}
	
	
	public ArrayList<Integer> getAllRecipeID(){
		String str = "select * from recipe";
		ArrayList<Integer> idList = new ArrayList<Integer>();
		
		try {
			Statement sql = conn.createStatement();
			ResultSet result = sql.executeQuery(str);
			
			while(result.next()){
				
				idList.add(result.getInt("RecipeID"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return idList;
		
	}
	
}
