package CookBook;

import java.util.ArrayList;


import java.io.Serializable;
import java.sql.*;


/**
 * the Recipe model class
 * @author MacroHard
 * @version 1.0
 */
public class Recipe implements Serializable {

	private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
	private int recipeID;
	private int servingPpl;
	private int accountID = 0;
	private String name;
	private String categary;
	private ArrayList<String> preparationStep = new ArrayList<String>();
	private int preparationTime;
	private int cookingTime;
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	
	public Recipe(String name, String categary, int servingPpl) {

		this.name = name;
		this.categary = categary;
		this.servingPpl = servingPpl;

	}

	public Recipe() {

	}

	/*
	 * getters and setters
	 */
	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}

	public void setServingPpl(int servingPpl) {
		this.servingPpl = servingPpl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategary(String categary) {
		this.categary = categary;
	}

	public void setPreparationStep(ArrayList<String> preparationStep) {
		this.preparationStep = preparationStep;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public int getRecipeID() {
		return recipeID;
	}

	public int getServingPpl() {
		return servingPpl;
	}

	public String getName() {
		return name;
	}

	public String getCategary() {
		return categary;
	}

	public ArrayList<String> getPreparationStep() {
		return preparationStep;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public int getCookingTime() {
		return cookingTime;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	
	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public void addPreparationStep(String preparation) {
		this.preparationStep.add(preparation);
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

	
	/**
	 * Override the toString method to give the Recipe information
	 */
	@Override
	public String toString() {
		// To give the information of Name and Category
		String strNameandCategory = "Name: " + this.getName() + "\n" + "Category: " + this.getCategary() + "\n";

		// This string is to give all steps of preparation
		String strPreparationStep = "Preparation Steps: \n";
		for (int i = 0; i < preparationStep.size(); i++) {
			String str = preparationStep.get(i);
			strPreparationStep = strPreparationStep + "Step " + (i + 1) + ": " + str + "\n";
		}

		// To give the ID of the Recipe
		String strRecipeID = "Recipe ID: " + this.recipeID + "\n";

		// To give all the information of ingredients
		String strIngredients = "Ingredients: \n";
		for (int i = 0; i < ingredients.size(); i++) {

			Ingredient ingrePointer = ingredients.get(i);
			// Using the override toString() method to change Ingredient into
			// String
			strIngredients = strIngredients + ingrePointer;

		}

		// To give the preparation time of the recipe
		String strPreparationTime = "Preparation Time: " + this.preparationTime + " min" + "\n";

		// To give the cooking time of the recipe
		String strCookingTime = "Cooking Time: " + this.preparationTime + "\n";

		// To give the serving people of the recipe
		String strServingPeople = "Serving People:" + this.servingPpl + "\n";
		
		String strComments = "Comments: \n";
		for (int i = 0; i < comments.size(); i++) {
			String string  = comments.get(i).getContext()+"\n";
			strComments = strComments + string;
		}
		// Return all the recipe information
		return strNameandCategory + strRecipeID + strIngredients + strPreparationStep + strPreparationTime
				+ strCookingTime + strServingPeople + strComments;
	}
}
