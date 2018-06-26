package com.UI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.security.auth.Refreshable;

import com.UI.view.Main;

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
import javafx.scene.control.TextField;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainController implements Initializable {

	@FXML
	private Button goButton;
	@FXML
	private Hyperlink randomRecipe1;
	@FXML
	private Hyperlink randomRecipe2;
	@FXML
	private Hyperlink randomRecipe3;
	@FXML
	private Hyperlink randomRecipe4;
	@FXML
	private Hyperlink randomRecipe5;
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
	@FXML
	private ImageView backgroundImageView;
	@FXML
	private TextField searchBox;

	public static DatabaseController jdbc = DatabaseController.getInstance();

	public static Scene MainScene;

	public static final String MainResourse = "/com/UI/view/MainInterface.fxml";

	private static Stage subStage = new Stage();

	public static Recipe currentRecipe = null;

	public static String currentRecipeName = null;

	public static RegisteredUser currentUser = null;

	public static String RecipeSearch = null;

	public static int backPoint = 0;
	
	public static int loginPoint = 0;
	
	public Label getWelcomeLabel() {
		return welcomeLabel;
	}

	public void setWelcomeLabel(Label welcomeLabel) {
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

		login.setOnAction(e -> showLogin(subStage));

		signIn.setOnAction(e -> showSignIn(subStage));

		randomRecipe1.setOnAction(e -> showRecipe1(randomRecipe1));
		randomRecipe2.setOnAction(e -> showRecipe1(randomRecipe2));
		randomRecipe3.setOnAction(e -> showRecipe1(randomRecipe3));
		randomRecipe4.setOnAction(e -> showRecipe1(randomRecipe4));
		randomRecipe5.setOnAction(e -> showRecipe1(randomRecipe5));
		signout.setOnAction(e -> signOut());

		profileLink.setOnAction(e -> showProfile());

		goButton.setOnAction(e -> {

			searchResult();

		});

		if(currentUser != null){
			
			showWelcomeandProfile();
		}
		
		Image backgroundImage = new Image("/Picture/background.jpg");

		backgroundImageView.setImage(backgroundImage);
	}

	public void givingRandomRecipe() {

		CookBook cB = new CookBook("test");
		ArrayList<Integer> allRecipe = jdbc.getAllRecipeID();
		Set<Integer> set = new HashSet<Integer>();
		while(set.size()<5){
			
			set.add(allRecipe.get((int)(Math.random()*allRecipe.size())));
		
		}
		Recipe recipe1,recipe2,recipe3,recipe4,recipe5;
		Object[] ints = set.toArray();
		
		recipe1 = jdbc.searchRecipe((int)ints[0]);
		randomRecipe1.setText(
				recipe1.getName() + "\n\n" + recipe1.getCategary() + "\n\nServingPeople: " + recipe1.getServingPpl());
		randomRecipe1.setUserData(recipe1.getRecipeID());
		recipe2 = jdbc.searchRecipe((int)ints[1]);
		randomRecipe2.setText(
				recipe2.getName() + "\n\n" + recipe2.getCategary() + "\n\nServingPeople: " + recipe2.getServingPpl());
		randomRecipe2.setUserData(recipe2.getRecipeID());
		recipe3 = jdbc.searchRecipe((int)ints[2]);
		randomRecipe3.setText(
				recipe3.getName() + "\n\n" + recipe3.getCategary() + "\n\nServingPeople: " + recipe3.getServingPpl());
		randomRecipe3.setUserData(recipe3.getRecipeID());
		recipe4 = jdbc.searchRecipe((int)ints[3]);
		randomRecipe4.setText(
				recipe4.getName() + "\n\n" + recipe4.getCategary() + "\n\nServingPeople: " + recipe4.getServingPpl());
		randomRecipe4.setUserData(recipe4.getRecipeID());
		recipe5 = jdbc.searchRecipe((int)ints[4]);
		randomRecipe5.setText(
				recipe5.getName() + "\n\n" + recipe5.getCategary() + "\n\nServingPeople: " + recipe5.getServingPpl());
		randomRecipe5.setUserData(recipe5.getRecipeID());
		
		
	}

	public void showLogin(Stage subStage) {

		subStage.setTitle("Login");
		loginPoint = 0 ;
		try {

			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/LoginView.fxml"));
			Scene scene = new Scene(root, 660, 402);
			subStage.setScene(scene);
			subStage.setResizable(false);
			subStage.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showSignIn(Stage substage) {

		substage.setTitle("Sign in");

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/SignInView.fxml"));
			Scene scene = new Scene(root, 592, 684);
			substage.setScene(scene);
			substage.setResizable(false);
			substage.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showWelcomeandProfile() {

		System.out.println("1");
		this.welcomeLabel.setText("Welcome, " + this.currentUser.getUserName());
		this.profileLink.setVisible(true);
		this.login.setVisible(false);
		this.signIn.setVisible(false);
		this.signout.setVisible(true);

	}

	public void signOut() {

		MainController.currentUser = null;
		this.signout.setVisible(false);
		this.login.setVisible(true);
		this.signIn.setVisible(true);
		this.welcomeLabel.setVisible(false);
		this.profileLink.setVisible(false);

	}

	public void showRecipe1(Hyperlink link) {

		try {

			currentRecipe = jdbc.searchRecipe((int) link.getUserData());
			this.backPoint = 0;
			// initialize the recipe interface
			Parent root = FXMLLoader.load(getClass().getResource(RecipeViewController.RecipeResource));
			Scene scene = new Scene(root, 1249, 837);
			scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setScene(scene);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showProfile() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/ProfileView.fxml"));
			Scene scene = new Scene(root, 1249, 837);
			scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void searchResult() {

		try {

			RecipeSearch = searchBox.getText();
			SearchResultController.currentRecipeList = MainController.jdbc.searchRecipe(MainController.RecipeSearch);
			// initialize the recipe interface
			Parent root = FXMLLoader.load(getClass().getResource(SearchResultController.RecipeResource));
			Scene scene = new Scene(root, 1249, 837);
			scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setScene(scene);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// @Override
	// public void run(){
	//
	// while(true){
	//
	// try {
	// Thread.sleep(200);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// //System.out.println(isWelcomeandProfileShow);
	//
	// if(isWelcomeandProfileShow){
	//
	//
	// /**
	// * !!!!!!!!!!!!!!!
	// * !!!!!!!!!!!!!!!
	// * !!!!!!!!!!!!!!!
	// * When we need to start a thread in fx application and
	// * if this thread is related to fx application,
	// * Platform.runLater() is the best choice
	// */
	// Platform.runLater(()->showWelcomeandProfile());
	//
	//
	//
	//
	// }else{
	//
	// }
	//
	// }
	//
	//
	// }
}
