package com.UI.controller;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import CookBook.DatabaseController;
import CookBook.RegisteredUser;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
	
	DatabaseController jdbc = DatabaseController.getInstance();
	private static boolean isUserExist = false;
	private static boolean isPasswordCorrect = false;
	
	
	private static Stage substage = new Stage();
	
	private static Stage stage = MainController.getSubStage();
	
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
	

	



	public static Stage getSubstage() {
		return substage;
	}


	public static void setSubstage(Stage substage) {
		LoginViewController.substage = substage;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		cancel.setOnAction( e->stage.close());
		confirmButton.setOnAction(e -> confirm());
		
		
	}
	
	public void loginEnter(javafx.scene.input.KeyEvent event){
		
		if(event.getCode() == KeyCode.ENTER){
			confirm();
		}
	}
	
	
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
				MainController.setWelcomeandProfileShow(true);
				System.out.println(MainController.isWelcomeandProfileShow());
			}else{
				isPasswordCorrect = false;
			}
			
		}else{
			isUserExist = false;
			
		}
		
		System.out.println(MainController.currentUser);
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/BackMessage.fxml"));
			Scene scene = new Scene(root,328,223);
			substage.setScene(scene);
			substage.setResizable(false);
			substage.showAndWait();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
}
