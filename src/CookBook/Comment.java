package CookBook;

public class Comment {
	
	private int commentID;
	private int accountID;
	private int recipeID;
	private String context;
	
	public int getCommentID() {
		return commentID;
	}
	
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	
	public int getAccountID() {
		return accountID;
	}
	
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	
	public int getRecipeID() {
		return recipeID;
	}
	
	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}
	
	public String getContext() {
		return context;
	}
	
	public void setContext(String context) {
		this.context = context;
	}
	
	@Override
	public String toString() {
		String str = "CommentID: " + this.getCommentID() + "\n"
				+ "AccountID: " + this.getAccountID() + "\n"
				+ "RecipeID: " + this.getRecipeID() + "\n"
				+ this.getContext() + "\n";
		
		return str;
	}
}
