package CookBook;

import java.io.Serializable;

public class Ingredient implements Serializable {

	public String name;
	public double weight;
	public String unit = "g";
	public String cookingWay = null;
	public Ingredient(String name,double weight, String unit, String cookingWay){
		this.name = name;
		this.weight = weight;
		this.unit = unit;
		this.cookingWay = cookingWay;
	}
	
	public Ingredient(String name,double weight, String unit){
		this.name = name;
		this.weight = weight;
		this.unit = unit;
	}
	
	public void addToDatabase(){
		
	}
}
