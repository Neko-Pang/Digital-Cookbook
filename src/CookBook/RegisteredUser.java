package CookBook;

import java.util.ArrayList;

public class RegisteredUser {
	
	private String userName;
	
	private int accountID;
	
	private String password;
	
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

	public void setOwnComments(ArrayList<Comment> ownComments) {
		this.ownComments = ownComments;
	}

	public ArrayList<Recipe> getOwnRecipes() {
		return ownRecipes;
	}

	public void setOwnRecipes(ArrayList<Recipe> ownRecipes) {
		this.ownRecipes = ownRecipes;
	}
	
	

}
