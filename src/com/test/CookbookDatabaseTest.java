package com.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import CookBook.Comment;
import CookBook.CookBookApp;
import CookBook.DatabaseController;
import CookBook.Recipe;
import CookBook.RegisteredUser;
import org.junit.BeforeClass;
import org.junit.runners.MethodSorters;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)


public class CookbookDatabaseTest{

	static DatabaseController jdbc;
	static Recipe testRecipe;
	static Recipe testRecipe2;
	static CookBookApp cb ;
	static RegisteredUser user;
	
	@BeforeClass
	public static void setUp()throws Exception{
		
		jdbc = DatabaseController.getInstance();
		cb = new CookBookApp();
		testRecipe = cb.createGongBaoJiding();
		testRecipe2 = cb.createHongShaoRou();
		user = new RegisteredUser("testuser133", "12345678");
	}
	
	/**
	 * to test whether the controller get the connection to the database
	 */
	@Test
	public void AAtestGetConn() {
		
		assertNotNull(jdbc.getConn());
	}
	
	/**
	 * to test whether the database controller could add the recipe into the database
	 */
	@Test
	public void ABtestInsertRecipe(){
		
		assertTrue(jdbc.insertRecipe(testRecipe));
		assertTrue(jdbc.insertRecipe(testRecipe2));
		
	}
	
	
	/**
	 * to test the search function
	 */
	@Test
	public void ACtestSearchRecipe(){
		
		assertNotNull(jdbc.searchRecipe(testRecipe.getRecipeID()));
		assertTrue(jdbc.searchRecipe("Gong Bao JiDing").size()!= 0);
		assertTrue(jdbc.searchIngre(testRecipe.getRecipeID()).size() != 0);
		assertTrue(jdbc.searchPrep(testRecipe.getRecipeID()).size() != 0);
		
	}
	
	
	/**
	 * to test the update function
	 */
	@Test
	public void ADtestUpdateRecipe(){
		
		jdbc.updateRecipeBasicInfo(testRecipe.getRecipeID(), cb.createHongShaoRou());
		jdbc.updateIngreInfo(testRecipe.getRecipeID(), cb.createHongShaoRou());
		jdbc.updatePrepStep(testRecipe.getRecipeID(), cb.createHongShaoRou());
		testRecipe = jdbc.searchRecipe(testRecipe.getRecipeID());
		System.out.println(testRecipe);
		
		jdbc.updateRecipe(testRecipe.getRecipeID(), cb.createGongBaoJiding());
		testRecipe = jdbc.searchRecipe(testRecipe.getRecipeID());
		System.out.println(testRecipe);
		
	}

	/**
	 * to test the delete recipe function
	 */
	@Test
	public void AEtestDeleteRecipe(){
		
		assertTrue(jdbc.deleteRecipeBasicInfo(testRecipe.getRecipeID()));
		assertTrue(jdbc.deleteRecipeIngreInfo(testRecipe.getRecipeID()));
		assertTrue(jdbc.deleteRecipePrepStep(testRecipe.getRecipeID()));
		assertNull(jdbc.searchRecipe(testRecipe.getRecipeID()));
		assertTrue(jdbc.deleteRecipe(testRecipe2.getRecipeID()));
		assertNull(jdbc.searchRecipe(testRecipe2.getRecipeID()));
		
	}
	
	
	
	/**
	 * to test registeration function
	 */
	@Test
	public void AFtestInsertUser(){
		
		assertTrue(jdbc.insertUser(user));
		
		System.out.println(user.getAccountID());
	}
	
	
	/**
	 * to test the search user function
	 */
	@Test
	public void AGtestSearchUser(){
		
		assertNotNull(jdbc.searchUser(user.getAccountID()));
		assertNotNull(jdbc.searchUser(user.getUserName()));
		
	}
	
	
	/**
	 * to test the add comment function
	 */
	@Test
	public void AHtestInsertComment(){
		
		Comment newComment = new Comment();
		newComment.setAccountID(5);
		newComment.setRecipeID(2);
		newComment.setContext("test");
		assertTrue(jdbc.insertComment(newComment));
		
	}
	
	/**
	 * to test search comments function
	 */
	@Test
	public void AItestSearchComment(){
		
		assertTrue(jdbc.searchCommentByAccount(5).size() != 0);
		assertTrue(jdbc.searchCommentByRecipe(2).size() != 0);
	
	}
	
	/**
	 * to test delete comments function
	 */
	@Test
	public void AJtestDeleteComment(){
		
		Comment comment=jdbc.searchCommentByAccount(5).get(0);
		jdbc.deleteComment(comment.getRecipeID(), comment.getAccountID(), comment.getCommentNo());
		assertTrue(jdbc.searchCommentByAccount(5).size() == 0);
	
	}
	
	
	
	
	
	
	
	
	
}
