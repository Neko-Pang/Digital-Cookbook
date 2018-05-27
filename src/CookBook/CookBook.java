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
	private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	private String name;

	public void add(Recipe recipe) {

		recipes.add(recipe);

	}

	public CookBook(String name) {
		this.name = name;
	}

	public Recipe getRecipe(String name) {
		Recipe recipePointer = null;
		for (int i = 0; i < recipes.size(); i++) {
			recipePointer = this.recipes.get(i);
			if (recipePointer.getName().equals(name)) {

				return recipePointer;
			}
		}
		return recipePointer;

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
	public Recipe recalculate(Recipe originRecipe, int changedservingPpl) {

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
			newWeight = (newWeight / recalRecipe.getServingPpl()) * changedservingPpl;
			ingrePointer.setWeight(newWeight);

			// add the new ingredient
			recalRecipe.getIngredients().add(i, ingrePointer);
		}

		/**
		 * take the recipe from the database and recalculate
		 */
		// DatabaseController jdbc = new DatabaseController();
		// Connection conn = jdbc.getConn();
		// String sql = "select * from recipe where Name
		// ='"+originRecipe.getName()+"'";
		// Statement statement = conn.createStatement();
		// ResultSet originSet = statement.executeQuery(sql);
		//
		// if(originSet.next()){
		//
		// }
		recalRecipe.setServingPpl(changedservingPpl);
		return recalRecipe;
	}

	/**
	 * this method is to copy a new recipe of one existed recipe to do the
	 *       recalculation in order not to change the origin recipe(Using serializing)
	 * 
	 * @return / a copy Recipe object of the specific Recipe
	 */
	public Recipe copy(Recipe origin){

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos =null;
	    ObjectInputStream ois = null;
	    
	    try {
	    	//We need to serialize the origin object into outputstream then deserialize it to obtain a new copy object
			oos = new ObjectOutputStream(bos);
			oos.writeObject(origin);
		    ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));	    
		    return (Recipe)ois.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		
		} finally {
			//We need to close the stream after finish
			
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
	 *  this method is to copy the origin ingredient into a new ingredient object but not cite
	 * @param origin / the origin version
	 * @return copyone/ the copy version of the origin object
	 */
	 public Ingredient copy(Ingredient origin){

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos =null;
	    ObjectInputStream ois = null;
	    
	    try {
	    	//We need to serialize the origin object into outputstream then deserialize it to obtain a new copy object
			oos = new ObjectOutputStream(bos);
			oos.writeObject(origin);
		    ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));	    
		    return (Ingredient)ois.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		
		} finally {
			//We need to close the stream after finish
			
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

}
