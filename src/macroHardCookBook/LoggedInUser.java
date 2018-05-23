package macroHardCookBook;
import java.util.*;
/**
 * @author LU Qin
 *
 */
public class LoggedInUser {
	private Account userAccount;
	private List<Recipe> ownRecipes;
	
	public Account getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(Account userAccount) {
		this.userAccount = userAccount;
	}
	public List<Recipe> getOwnRecipes() {
		return ownRecipes;
	}
	public void setOwnRecipes(List<Recipe> ownRecipes) {
		this.ownRecipes = ownRecipes;
	}
	
}
