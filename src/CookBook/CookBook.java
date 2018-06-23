package CookBook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CookBook {
	
	
	public static DatabaseController jDatabaseController = DatabaseController.getInstance();
	private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	private String name;

	/**
	 * This method will add the parameter recipe into the ArrayList of recipes
	 * and database
	 * 
	 * @param recipe
	 */
	public void add(Recipe recipe) {

		
		jDatabaseController.insertRecipe(recipe);
		recipes.add(recipe);

	}
	
	/**
	 * To update the recipe by a newly edited version
	 * @param recipeID
	 * @param newRecipe
	 */
	public Recipe updateRecipe(Recipe originRecipe, Recipe newRecipe){
		
		newRecipe.setRecipeID(originRecipe.getRecipeID());
		jDatabaseController.updateRecipe(originRecipe.getRecipeID(), newRecipe);
		originRecipe = this.getRecipe(originRecipe.getRecipeID());
		return originRecipe;
	}

	public CookBook(String name) {
		this.name = name;
	}

	/**
	 * This method is to search the recipe by name and get the wanted recipe
	 * 
	 * @param name
	 * @return
	 */
	public ArrayList<Recipe> getRecipe(String name) {
		
		ArrayList<Recipe> goalRecipes = new ArrayList<Recipe>();

		//search the recipes directly from database
		goalRecipes = jDatabaseController.searchRecipe(name);

		return goalRecipes;

	}
	
	/**
	 * This method is overloaded to get the recipe by ID from database
	 * @param recipeID
	 * @return
	 */
	public Recipe getRecipe(int recipeID){
		
		Recipe goalRecipe = new Recipe();
		goalRecipe = jDatabaseController.searchRecipe(recipeID);
		
		return goalRecipe;
	}
	
	
	public ArrayList<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(ArrayList<Recipe> recipes) {
		this.recipes = recipes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method is to recalculate the amount of each ingredient based on the
	 * changed serving ppl
	 * 
	 * @param originRecipe/the
	 *            orginal version of the recipe
	 * @param changedservingPpl/the
	 *            reseted number of the recipe
	 * @return the new recipe(but this recipe will not be stored into the
	 *         database)
	 */
	public Recipe recalculate(Recipe originRecipe, int changedServingPpl) {

		Recipe recalRecipe;

		recalRecipe = this.copy(originRecipe);

		/**
		 * take the recipe in the working memory part and recalculate(do nothing
		 * with the database so the recipe need to be create in the main
		 * program)
		 */
		for (int i = 0; i < recalRecipe.getIngredients().size(); i++) {
			// this ingredient serves as the changed ingredient
			Ingredient ingrePointer = new Ingredient();

			// take the orginal ingredient out and remove it from the new recipe
			// ingredient list
			ingrePointer = this.copy(recalRecipe.getIngredients().get(i));
			recalRecipe.getIngredients().remove(i);

			// calculate the new weight
			double newWeight = ingrePointer.getWeight();
			newWeight = (newWeight / recalRecipe.getServingPpl()) * changedServingPpl;
			ingrePointer.setWeight(newWeight);

			// add the new ingredient
			recalRecipe.getIngredients().add(i, ingrePointer);
		}

		recalRecipe.setServingPpl(changedServingPpl);
		return recalRecipe;
	}

	

	/**
	 * this method is to copy a new recipe of one existed recipe to do the
	 * recalculation in order not to change the origin recipe(Using serializing)
	 * 
	 * @param origin/
	 *            the origin recipe
	 * @return (Recipe) / a copy Recipe object of the specific Recipe
	 */
	public Recipe copy(Recipe origin) {

		Recipe copyVersion;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;

		try {
			// We need to serialize the origin object into outputstream then
			// deserialize it to obtain a new copy object
			oos = new ObjectOutputStream(bos);
			oos.writeObject(origin);
			ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			copyVersion = (Recipe) ois.readObject();
			return copyVersion;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		} finally {
			// We need to close the stream after finish

			try {
				bos.close();
				oos.close();
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * this method is to copy the origin ingredient into a new ingredient object
	 * but not cite
	 * 
	 * @param origin
	 *            / the origin version
	 * @return copyone/ the copy version of the origin object
	 */
	public Ingredient copy(Ingredient origin) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;

		try {
			// We need to serialize the origin object into outputstream then
			// deserialize it to obtain a new copy object
			oos = new ObjectOutputStream(bos);
			oos.writeObject(origin);
			ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			return (Ingredient) ois.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		} finally {
			// We need to close the stream after finish

			try {
				bos.close();
				oos.close();
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	
	/**
	 * To delete the recipe from the cookbook and database
	 * @param recipe
	 * @return isDeleted
	 */
	public Boolean deleteRecipe(Recipe recipe){
		
		Boolean isDeleted = jDatabaseController.deleteRecipe(recipe.getRecipeID());
		for(int i = 0; i < recipes.size();i++){
			Recipe testRecipe = recipes.get(i);
			if(testRecipe.getRecipeID()==recipe.getRecipeID()){
				this.recipes.remove(testRecipe);
			}
		}
		return isDeleted;
	}

}
