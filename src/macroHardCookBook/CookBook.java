package macroHardCookBook;
import java.util.*;

/**
 * @author LU Qin
 *
 */
public class CookBook {
	private String cookBookName;
	private List<Recipe> recipe;
	
	public String getCookBookName() {
		return cookBookName;
	}

	public void setCookBookName(String cookBookName) {
		this.cookBookName = cookBookName;
	}

	public List<Recipe> getRecipe() {
		return recipe;
	}

	public void setRecipe(List<Recipe> recipe) {
		this.recipe = recipe;
	}

	public CookBook(String cookBookName) {
		this.cookBookName = cookBookName;
	}
	
	public void addRecipe(Recipe recipe) {
		this.recipe.add(recipe);
	}
	
	public void deleteRecipe() {
		
	}
	
}
