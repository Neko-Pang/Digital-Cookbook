package com.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import CookBook.Ingredient;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IngredientTest {

	static Ingredient testIngre;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		testIngre = new Ingredient("pork belly", 0.5, "kg", "cut into 2cm pieces");
	}

	@Test
	public void AAtestToString() {
		System.out.println(testIngre);
	}

}
