package com.UI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import CookBook.CookBook;
import CookBook.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class MainController implements Initializable{

	@FXML
	private Button goButton;
	@FXML
	private Hyperlink randomRecipe1;
	@FXML
	private Hyperlink login;
	@FXML
	private Hyperlink signIn;
	
	
	private static Stage subStage = new Stage();
	

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
		
	}
	
	public void givingRandomRecipe(){
		CookBook cB = new CookBook("test");
		Recipe recipe = cB.getRecipe(1);
		randomRecipe1.setText(recipe.getName()+ "\n\n" + recipe.getCategary()+"\n\nServingPeople: "+recipe.getServingPpl());
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
	
}
