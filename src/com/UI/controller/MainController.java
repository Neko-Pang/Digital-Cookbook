package com.UI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.UI.view.Main;
import com.sun.glass.ui.TouchInputSupport;

import CookBook.CookBook;
import CookBook.DatabaseController;
import CookBook.Recipe;
import CookBook.RegisteredUser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.Shadow;
import javafx.stage.Stage;

public class MainController extends Thread implements Initializable{

	@FXML
	private Button goButton;
	@FXML
	private Hyperlink randomRecipe1;
	@FXML
	private Hyperlink login;
	@FXML
	private Hyperlink signIn;
	@FXML
	private Label welcomeLabel;
	@FXML
	private Hyperlink profileLink;
	@FXML
	private Hyperlink signout;
	
	public static DatabaseController jdbc = DatabaseController.getInstance();
	
	private static boolean isWelcomeandProfileShow = false;
	
	private static Stage subStage = new Stage();
	
	public static int currentRecipeID = 0;

	public static RegisteredUser currentUser = null;
	
	public static boolean isWelcomeandProfileShow() {
		return isWelcomeandProfileShow;
	}
	
	
	public static void setWelcomeandProfileShow(boolean isWelcomeandProfileShow) {
		MainController.isWelcomeandProfileShow = isWelcomeandProfileShow;
	}


	public  Label getWelcomeLabel() {
		return welcomeLabel;
	}

	public  void setWelcomeLabel(Label welcomeLabel) {
		this.welcomeLabel = welcomeLabel;
	}

	public Hyperlink getProfileLink() {
		return profileLink;
	}

	public void setProfileLink(Hyperlink profileLink) {
		this.profileLink = profileLink;
	}
	
	public static Stage getSubStage() {
		return subStage;
	}

	public static void setSubStage(Stage subStage) {
		MainController.subStage = subStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		this.givingRandomRecipe();
		
		login.setOnAction(e ->showLogin(subStage));
		
		signIn.setOnAction(e ->showSignIn(subStage));
		
		randomRecipe1.setOnAction(e->showRecipe1());
		
		signout.setOnAction(e->signOut());
		
		if(currentUser != null){
		
			showWelcomeandProfile();
		}
		
		this.start();
	}
	
	public void givingRandomRecipe(){
		
		CookBook cB = new CookBook("test");
		Recipe recipe = cB.getRecipe(1);
		randomRecipe1.setText(recipe.getName()+ "\n\n" + recipe.getCategary()+"\n\nServingPeople: "+recipe.getServingPpl());
		randomRecipe1.setUserData(recipe.getRecipeID());
		
	}
	
	public void showLogin(Stage subStage){
		
		subStage.setTitle("Login");
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/LoginView.fxml"));
			Scene scene = new Scene(root,660,402);
			subStage.setScene(scene);
			subStage.setResizable(false);
			subStage.showAndWait();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void showSignIn(Stage substage){
		
		substage.setTitle("Sign in");
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/SignInView.fxml"));
			Scene scene = new Scene(root,592,684);
			substage.setScene(scene);
			substage.setResizable(false);
			substage.showAndWait();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void showWelcomeandProfile(){
		
		this.welcomeLabel.setText("Welcome, "+ this.currentUser.getUserName());
		this.profileLink.setVisible(true);
		this.login.setVisible(false);
		this.signIn.setVisible(false);
		this.signout.setVisible(true);
		
		
		
	}
	
	public void signOut(){
		
		MainController.currentUser = null;
		this.signout.setVisible(false);
		this.login.setVisible(true);
		this.signIn.setVisible(true);
		this.welcomeLabel.setVisible(false);
		this.profileLink.setVisible(false);
		isWelcomeandProfileShow = false;
		
	}
	
	
	public void showRecipe1(){
		
		try {
			
			currentRecipeID = (int)randomRecipe1.getUserData();
			
			//initialize the recipe interface
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/RecipeView.fxml"));
			Scene scene = new Scene(root,1249,837);
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setScene(scene);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@Override
	public void run(){
		
		while(true){
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(isWelcomeandProfileShow);
			
			if(isWelcomeandProfileShow){
				
				
				/**
				 * !!!!!!!!!!!!!!!
				 * !!!!!!!!!!!!!!!
				 * !!!!!!!!!!!!!!!
				 * When we need to start a thread in fx application and
				 * if this thread is related to fx application, 
				 * Platform.runLater() is the best choice
				 */
				Platform.runLater(()->showWelcomeandProfile());
				
				
				
				
			}else{

			}
			
		}
		
		
	}
}
