package com.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import CookBook.Comment;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class CommentTest {

	
	static Comment testcomment;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		//create a new comment
		testcomment = new Comment();
		testcomment.setAccountID(1);
		testcomment.setCommentNo(2);
		testcomment.setRecipeID(1);
		testcomment.setContext("for test only");
		
	}


	@Test
	public void AAtestToString() {
		System.out.println(testcomment);
	}

}
