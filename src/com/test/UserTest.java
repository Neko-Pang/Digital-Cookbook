package com.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import CookBook.DatabaseController;
import CookBook.RegisteredUser;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

	static RegisteredUser testuser;
	static DatabaseController jdbc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		jdbc = DatabaseController.getInstance();
		testuser = new RegisteredUser("tester", "11111111");
		testuser.setAccountID(1);
		testuser.setOwnComments(jdbc.searchCommentByAccount(testuser.getAccountID()));
		testuser.setOwnRecipes(jdbc.searchRecipeByAccount(testuser.getAccountID()));

	}

	/**
	 * test toString function
	 */
	@Test
	public void AAtestToString() {

		RegisteredUser user = jdbc.searchUser(3);
		System.out.println(user);

	}

	/**
	 * test register function
	 */
	@Test
	public void ABtestRegister() {
		testuser.register();
		System.out.println(testuser.getAccountID());
	}
}