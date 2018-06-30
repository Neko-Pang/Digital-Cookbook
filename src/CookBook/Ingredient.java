package CookBook;

import java.io.Serializable;


/**
 * the ingredient model class
 * @author MacroHard
 * @version 1.0
 */
public class Ingredient implements Serializable {

	public String name;
	public double weight;
	public String unit = "g";
	public String cookingWay = null;

	public Ingredient(String name, double weight, String unit, String cookingWay) {
		this.name = name;
		this.weight = weight;
		this.unit = unit;
		this.cookingWay = cookingWay;
	}

	public Ingredient(String name, double weight, String unit) {
		this.name = name;
		this.weight = weight;
		this.unit = unit;
		this.cookingWay = "None";
	}

	public Ingredient() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCookingWay() {
		return cookingWay;
	}

	public void setCookingWay(String cookingWay) {
		this.cookingWay = cookingWay;
	}

	
	/**
	 * Override the toString method to give the ingredient information
	 */
	@Override
	public String toString() {
		// this string is collection of all ingredient
		String totalInformation;
		
		totalInformation = this.name + ": " + this.weight + " " + this.unit;
		if(!this.cookingWay.equals("None")){
			totalInformation = totalInformation + "     Cooking way: " + this.cookingWay + "\n";
		}else{
			totalInformation = totalInformation + "\n";
		}
		

		return totalInformation;
	}
}
