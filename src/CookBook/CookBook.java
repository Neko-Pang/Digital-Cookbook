package CookBook;

import java.util.ArrayList;

public class CookBook {
	private ArrayList<Recipe> recipes =new ArrayList<Recipe>();
	private String name;
	
	public void add(Recipe recipe){
		
		recipes.add(recipe);
	
	}
	
	public CookBook(String name){
		this.name = name;
	}

	public Recipe getRecipe(String name) {
		Recipe recipePointer=null;
		for(int i =0 ; i < recipes.size(); i++){
			recipePointer = this.recipes.get(i);
			if( recipePointer.getName().equals(name) ){
				
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
	
}
