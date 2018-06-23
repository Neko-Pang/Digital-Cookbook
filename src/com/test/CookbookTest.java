package com.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import CookBook.CookBook;
import CookBook.CookBookApp;
import CookBook.Recipe;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class CookbookTest {
	
	static CookBook cb;
	static Recipe testRecipe;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cb = new CookBook("test");
		testRecipe = CookBookApp.createGongBaoJiding();
		
	}
	
	/**
	 * to test the recalculation function
	 */
	@Test
	public void AAtestRecalculate() {
		
		Recipe recalRecipe = cb.recalculate(testRecipe, 10);
		System.out.println(recalRecipe);
		
	}
	
	/**
	 *  test add recipe function
	 */
	@Test
	public void ABtestAddRecipe(){
		
		cb.add(testRecipe);
		assertTrue(cb.getRecipes().size() != 0);
		
	}
	
	/**
	 * test the update function
	 */
	@Test
	public void ACtestUpdateRecipe(){

		Recipe newRecipe = CookBookApp.createHongShaoRou();
		testRecipe=cb.updateRecipe(testRecipe, newRecipe);
		System.out.println(testRecipe);
	}
	
	
	/**
	 * test delete recipe function
	 */
	@Test
	public void ADtestDeleteRecipe(){
		
		
		assertTrue(cb.deleteRecipe(testRecipe));
		
	}
	
	
	
}
