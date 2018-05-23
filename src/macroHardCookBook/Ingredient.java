package macroHardCookBook;

/**
 * @author LU Qin
 *
 */
public class Ingredient {
	private String name;
	private float amount;
	private String unit;
	private String requirement;
	
	public Ingredient(String name, float amount, String unit) {
		this.name = name;
		this.amount = amount;
		this.unit = unit;
	}
	
	public Ingredient(String name, float amount, String unit, String requirement) {
		this.name = name;
		this.amount = amount;
		this.unit = unit;
		this.requirement = requirement;
	}
	
}
