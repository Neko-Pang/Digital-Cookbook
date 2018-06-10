package CookBook;

import java.util.ArrayList;

public class RegisteredUser {
	
	private String userName = null;
	
	private int accountID = 0;
	
	private String password = null;
	
	private ArrayList<Comment> ownComments;
	
	private ArrayList<Recipe> ownRecipes;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Comment> getOwnComments() {
		return ownComments;
	}

	public void addOwnComments(Comment ownComments) {
		this.ownComments.add(ownComments);
	}

	public ArrayList<Recipe> getOwnRecipes() {
		return ownRecipes;
	}

	public void setOwnComments(ArrayList<Comment> ownComments) {
		this.ownComments = ownComments;
	}

	public void setOwnRecipes(ArrayList<Recipe> ownRecipes) {
		this.ownRecipes = ownRecipes;
	}

	public void addOwnRecipes(Recipe ownRecipes) {
		this.ownRecipes.add(ownRecipes);
	}
	
	public RegisteredUser(String username, String password) {
		this.userName = username;
		this.password = password;
		this.accountID++;
	}
	
	public RegisteredUser() {
		
	}
	
	@Override
	public String toString() {
		String userInfo = "Username: " + this.getUserName() + "\n"
				+ "AccountID: " + this.getAccountID() + "\n"
				+ "Password: " + this.getPassword() + "\n";

		String strOwnRecipes = "Own recipes: \n";
		for (int i = 0; i < ownRecipes.size(); i++) {
			Recipe recipePointer = ownRecipes.get(i);
			strOwnRecipes = strOwnRecipes + "Recipe " + (i + 1) + ": " + recipePointer + "\n";
		}
		
		String strOwnComments = "Own comments: \n";
		for (int i = 0; i < ownComments.size(); i++) {
			Comment commentPointer = ownComments.get(i);
			strOwnComments = strOwnComments + "Comment " + (i + 1) + ": " + commentPointer + "\n";
		}
		
		return userInfo + strOwnRecipes + strOwnComments;
	}
}
