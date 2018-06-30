package com.UI.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.UI.view.Main;

import CookBook.DatabaseController;
import CookBook.RegisteredUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginViewController implements Initializable {
	
	@FXML
	private Button cancel;
	@FXML
	private Button confirmButton;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	
	//database controller
	DatabaseController jdbc = DatabaseController.getInstance();
	
	//boolean for account confirmation
	private static boolean isUserExist = false;
	private static boolean isPasswordCorrect = false;
	
	//the using stage
	private static Stage stage = Main.subStage1;
	
	public static Stage getStage() {
		return stage;
	}

	
	public static boolean isUserExist() {
		return isUserExist;
	}


	public static void setUserExist(boolean isUserExist) {
		LoginViewController.isUserExist = isUserExist;
	}


	public static boolean isPasswordCorrect() {
		return isPasswordCorrect;
	}


	public static void setPasswordCorrect(boolean isPasswordCorrect) {
		LoginViewController.isPasswordCorrect = isPasswordCorrect;
	}
	



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		

		cancel.setOnAction( e->stage.close());
		confirmButton.setOnAction(e -> confirm());
		
		
	}
	
	/**
	 * Make enter key works
	 * @param event/the keyboard event
	 */
	public void loginEnter(javafx.scene.input.KeyEvent event){
		
		if(event.getCode() == KeyCode.ENTER){
			confirm();
		}
	}
	
	/**
	 * the event for clicking confirm button in LoginView
	 */
	public void confirm(){
		
		
		String username = this.username.getText();
		String password = this.password.getText();
		
		RegisteredUser user = jdbc.searchUser(username);
		System.out.println(user);
		
		if(user != null){
			isUserExist =true;
			if(user.getPassword().equals(password)){
				
				MainController.currentUser = user;
				isPasswordCorrect = true;
				
				try {
					if (MainController.loginPoint == 0) {
						
						FXMLLoader loader = new FXMLLoader(getClass().getResource(MainController.MainResourse));
						Parent root = loader.load();
						Scene refresh = new Scene(root, 1249, 837);
						Main.primaryStage.setScene(refresh);
						
					}else if(MainController.loginPoint == 1){
						
						Parent root = FXMLLoader.load(getClass().getResource(SearchResultController.RecipeResource));
						Scene scene = new Scene(root, 1249, 837);
						scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
						Main.primaryStage.setResizable(false);
						Main.primaryStage.setScene(scene);
						
					}else if(MainController.loginPoint == 2){
						
						Parent root = FXMLLoader.load(getClass().getResource(RecipeViewController.RecipeResource));
						Scene scene = new Scene(root, 1249, 837);
						scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
						Main.primaryStage.setResizable(false);
						Main.primaryStage.setScene(scene);
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}else{
				isPasswordCorrect = false;
			}
			
		}else{
			isUserExist = false;
			
		}
		
		if (LoginViewController.isUserExist()) {
			if (LoginViewController.isPasswordCorrect()) {
				BackMessageController.message = "Login successfully";
			} else {
				BackMessageController.message = "The password is not correct!";
			}
		} else {
			BackMessageController.message = "User not exist!";
		}

		
		try {
			
			
			BackMessageController.messageType = 0;
			BackMessageController.stage = Main.subStage2;
			Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
			Scene scene = new Scene(root,328,223);
			Main.subStage2.setScene(scene);
			Main.subStage2.setResizable(false);
			Main.subStage2.showAndWait();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
}
