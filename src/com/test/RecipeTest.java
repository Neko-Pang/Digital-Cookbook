package com.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import CookBook.CookBookApp;
import CookBook.DatabaseController;
import CookBook.Recipe;
import CookBook.RegisteredUser;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecipeTest {
	
	static Recipe testRecipe;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		testRecipe = CookBookApp.createHongShaoRou();
	
	}
	
	
	/**
	 * test toString function
	 */
	@Test
	public void AAtestToString() {
		
		System.out.println(testRecipe);

		
	}
	
}
