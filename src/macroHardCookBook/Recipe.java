package macroHardCookBook;
import java.util.*;

/**
 * @author LU Qin
 *
 */
public class Recipe {
	private String recipeName;
	private String category;
	private List<String> preparationStep;
	private List<Ingredient> recipeIngredient;
	private List<Comment> recipeComment;
	private int cookingTime;
	private int preparationTime;
	private int servePeople;
	
	
	
	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getPreparationStep() {
		return preparationStep;
	}

	public void setPreparationStep(List<String> preparationStep) {
		this.preparationStep = preparationStep;
	}

	public List<Ingredient> getRecipeIngredient() {
		return recipeIngredient;
	}

	public void setRecipeIngredient(List<Ingredient> recipeIngredient) {
		this.recipeIngredient = recipeIngredient;
	}

	public List<Comment> getRecipeComment() {
		return recipeComment;
	}

	public void setRecipeComment(List<Comment> recipeComment) {
		this.recipeComment = recipeComment;
	}

	public int getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

	public int getServePeople() {
		return servePeople;
	}

	public void setServePeople(int servePeople) {
		this.servePeople = servePeople;
	}
	
	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}
	
	public Recipe(String recipeName, String category, int servePeople) {
		this.recipeName = recipeName;
		this.category = category;
		this.servePeople = servePeople;
	}
	
	public void addIngredient(Ingredient ingredient) {
		this.recipeIngredient.add(ingredient);
	}
	
	public void addPreparationStep(String preparationStep) {
		this.preparationStep.add(preparationStep);
	}
	
	public void addComment(Comment comment) {
		this.recipeComment.add(comment);
	}
	
}
