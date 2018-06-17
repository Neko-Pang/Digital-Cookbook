package com.UI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.UI.view.Main;

import CookBook.Comment;
import CookBook.Ingredient;
import CookBook.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SearchResultController implements Initializable{
	
	@FXML
	private TextField searchBox;
	@FXML
	private Button goButton;
	@FXML
	private Button backBtn;
	@FXML
	private Hyperlink login;
	@FXML
	private Hyperlink signIn;
	@FXML
	private Label userLabel;
	@FXML
	private Hyperlink profileLink;
	@FXML
	private Hyperlink signout;
	@FXML
	private Button nextPage;
	@FXML
	private Button lastPage;
	@FXML
	private Hyperlink recipe1; 
	@FXML
	private Hyperlink recipe2;
	@FXML
	private Hyperlink recipe3;
	@FXML
	private Hyperlink recipe4;
	@FXML
	private Hyperlink recipe5;
	
	private static Stage subStage = new Stage();
	
	public static final String RecipeResource = "/com/UI/view/SearchResult.fxml" ;

	private ArrayList<Recipe> currentRecipeList = null;
	
	int i = 0;
	
	public int currentRecipeID = 0;
	
	public static Recipe currentRecipe;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub\
		
		
		currentRecipeList = MainController.jdbc.searchRecipe(MainController.RecipeSearch);

		goButton.setOnAction(e->searchResult());
		
		backBtn.setOnAction(e->backToMainInterface());
		
        login.setOnAction(e ->showLogin(subStage));
		
		signIn.setOnAction(e ->showSignIn(subStage));
		
		signout.setOnAction(e->signOut());
		
		nextPage.setOnAction(e->i=i+5);
		
		lastPage.setOnAction(e->{			

			if(i>4) {i=i-5;}

		});
		
		if(currentRecipeList.get(i) != null) {
			currentRecipe = currentRecipeList.get(i);
			recipe1.setText(currentRecipe.getName()+ "\n\n" + currentRecipe.getCategary()+"\n\nServingPeople: "+currentRecipe.getServingPpl());
			System.out.println(i);
		}
		if(currentRecipeList.get(i+1) != null) {
			currentRecipe = currentRecipeList.get(i+1);
			recipe2.setText(currentRecipe.getName()+ "\n\n" + currentRecipe.getCategary()+"\n\nServingPeople: "+currentRecipe.getServingPpl());
			
		}
		if(currentRecipeList.get(i+2) != null) {
			currentRecipe = currentRecipeList.get(i+2);
			recipe3.setText(currentRecipe.getName()+ "\n\n" + currentRecipe.getCategary()+"\n\nServingPeople: "+currentRecipe.getServingPpl());
			
		}
		if(currentRecipeList.get(i+3) != null) {
			currentRecipe = currentRecipeList.get(i+4);
			recipe4.setText(currentRecipe.getName()+ "\n\n" + currentRecipe.getCategary()+"\n\nServingPeople: "+currentRecipe.getServingPpl());
			
		}
		if(currentRecipeList.get(i+4) != null) {
			currentRecipe = currentRecipeList.get(i+5);
			recipe5.setText(currentRecipe.getName()+ "\n\n" + currentRecipe.getCategary()+"\n\nServingPeople: "+currentRecipe.getServingPpl());
			
		}
		
		recipe1.setOnAction(e->showRecipe());
		recipe2.setOnAction(e->{
			i=i+1;
			showRecipe();});
		recipe2.setOnAction(e->{
			i=i+2;
			showRecipe();});
		recipe2.setOnAction(e->{
			i=i+3;
			showRecipe();});
		recipe2.setOnAction(e->{
			i=i+4;
			showRecipe();});
		
	}
	
	
	public void showRecipe(){
		
		try {
			currentRecipe = currentRecipeList.get(i);
			
			
			//initialize the recipe interface
			Parent root = FXMLLoader.load(getClass().getResource(RecipeViewController.RecipeResource));
			Scene scene = new Scene(root,1249,837);
			scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setScene(scene);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
public void searchResult(){
		
		try {
			ArrayList<Recipe> goalRecipe2 = new ArrayList<Recipe>();
			goalRecipe2 = MainController.jdbc.searchRecipe(searchBox.getText());
			
			//initialize the recipe interface
			Parent root = FXMLLoader.load(getClass().getResource(SearchResultController.RecipeResource));
			Scene scene = new Scene(root,1249,837);
			scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setScene(scene);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
public void backToMainInterface(){
	Main.primaryStage.setScene(MainController.MainScene);
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

public void signOut(){
	
	MainController.currentUser = null;
	this.signout.setVisible(false);
	this.login.setVisible(true);
	this.signIn.setVisible(true);
	this.profileLink.setVisible(false);
	
}
}


