package com.UI.controller;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.UI.view.Main;

import CookBook.DatabaseController;
import CookBook.RegisteredUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class SignInViewController extends Thread implements Initializable{
	
	@FXML
	private TextField usernameText;
	@FXML
	private Label usernameCheckHint;
	@FXML
	private Label usernameCheckHint2;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField passwordText;
	@FXML
	private TextField passwordRepeat;
	@FXML
	private Label passwordCheckHint;
	@FXML
	private Label passwordCheckHint2;
	@FXML
	private Button confirmButton;
	
	
	
	private String password;
	private String repeatPassword;
	private String username;
	private boolean passwordCheckLengthPass = false;
	private boolean usernamePass = false;
	private boolean passwordPass = false;
	
	public static Stage substage = Main.subStage1;
	
	//The flag for stop checking the username and password
	private boolean stopme = true;
	
	//database controller
	DatabaseController jdbc = DatabaseController.getInstance();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		usernameText.setOnKeyReleased(e->userNameCheck());
		cancelButton.setOnAction(e->MainController.getSubStage().close());
		passwordText.setOnKeyReleased(e->passwordCheck());
		passwordRepeat.setOnKeyReleased(e->passwordRepeatCheck());	
		confirmButton.setOnAction(e->comfirm());
		this.start();
		
	}
	
	
	
	
	
	/**
	 * this method is to check whether the username is legal
	 */
	public void userNameCheck(){
		
		String username = usernameText.getText();
		int maxLength = 30;
		Pattern pattern = Pattern.compile("[0-9a-zA-Z]+");
		Matcher matcher = pattern.matcher(username);
		boolean isRight = matcher.matches();
		
		if(username.length() > maxLength){
			
			String s = username.substring(0, maxLength);
			username = s;
			usernameText.setText(username);
			usernameText.positionCaret(maxLength);
		}
		
		if(!username.equals("")){
			if(isRight){
				if(jdbc.searchUser(username) == null){
			
					usernameCheckHint.setText("Username is valid ");
					usernameCheckHint2.setText("¡Ì");
					usernameCheckHint2.setTextFill(javafx.scene.paint.Color.GREEN);
					usernamePass = true;
					
				}else{
					usernameCheckHint.setText("Username is repeated ");
					usernameCheckHint2.setText("X");
					usernameCheckHint2.setTextFill(javafx.scene.paint.Color.RED);
					usernamePass = false;
				}
			}else{
				usernameCheckHint.setText("Username is invalid ");
				usernameCheckHint2.setText("X");
				usernameCheckHint2.setTextFill(javafx.scene.paint.Color.RED);
				usernamePass = false;
			}
		}else{
			usernameCheckHint.setText(" ");
			usernameCheckHint2.setText(" ");
			usernamePass = false;
		}
		
		this.username = username;
	}
	
	
	
	/**
	 * To check if the password is legal
	 */
	public void passwordCheck(){
		
		String password = passwordText.getText();
		
		int maxLength = 16;
		int minLength = 8;
		if(password.length() > maxLength){
			
			String s = password.substring(0, maxLength);
			password = s;
			passwordText.setText(password);
			passwordText.positionCaret(maxLength);
		}
		
		
		if(password.length() < minLength){
			passwordCheckHint.setText("Password is too short ");
			passwordCheckHint2.setText("X");
			passwordCheckHint2.setTextFill(javafx.scene.paint.Color.RED);
			passwordCheckLengthPass=false;
			passwordPass = false;
		}else if( !password.equals(this.repeatPassword) ){
			passwordCheckHint.setText("Please input the same password ");
			passwordCheckHint2.setText("X");
			passwordCheckHint2.setTextFill(javafx.scene.paint.Color.RED);	
			passwordCheckLengthPass=true;
			passwordPass = false;
		}else{
			passwordCheckHint.setText("Password is valid ");
			passwordCheckHint2.setText("¡Ì");
			passwordCheckHint2.setTextFill(javafx.scene.paint.Color.GREEN);
			passwordCheckLengthPass=true;
			passwordPass = true;
		}
		
		this.password=password;

		System.out.println(this.password);
		
	}
	
	/**
	 * To check if the repeated password is the same as the password
	 */
	public void passwordRepeatCheck(){
		
		
		String passwordRepeat = this.passwordRepeat.getText();
		
		int maxLength = 16;
		if(passwordCheckLengthPass){
			if (passwordRepeat.length() > maxLength) {

				String s = passwordRepeat.substring(0, maxLength);
				passwordRepeat = s;
				this.passwordRepeat.setText(passwordRepeat);
				this.passwordRepeat.positionCaret(maxLength);
			}

			if (passwordRepeat.equals(password)) {
				passwordCheckHint.setText("Password is valid ");
				passwordCheckHint2.setText("¡Ì");
				passwordCheckHint2.setTextFill(javafx.scene.paint.Color.GREEN);
				passwordPass = true;
			} else {

				passwordCheckHint.setText("Please input the same password ");
				passwordCheckHint2.setText("X");
				passwordCheckHint2.setTextFill(javafx.scene.paint.Color.RED);
				passwordPass = false;

			}

		}
		
		this.repeatPassword = passwordRepeat;
		
		System.out.println(repeatPassword);
	}
	
	

	/**
	 * Comfirm the account registry and add the user into the database
	 */
	public void comfirm(){
		
		RegisteredUser newUser = new RegisteredUser();
		newUser.setPassword(password);
		newUser.setUserName(username);
		newUser.register();
		MainController.getSubStage().close();
		stopme = false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(stopme){
			
			if(passwordPass && usernamePass){
				
				confirmButton.setDisable(false);
				
			}else{

				confirmButton.setDisable(true);
				
			}
			
		}
		
	}
	
	
}
