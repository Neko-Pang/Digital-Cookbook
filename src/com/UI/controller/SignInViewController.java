package com.UI.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CookBook.DatabaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SignInViewController implements Initializable{
	
	@FXML
	private TextField usernameText;
	@FXML
	private Label usernameCheckHint;
	@FXML
	private Button cancelButton;
	
	DatabaseController jdbc = DatabaseController.getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		usernameText.setOnKeyReleased(e->userNameCheck());
		cancelButton.setOnAction(e->MainController.getSubStage().close());
	}
	
	
	public void userNameCheck(){
		
		String username = usernameText.getText();
		Pattern pattern = Pattern.compile("[0-9a-zA-Z]+");
		Matcher matcher = pattern.matcher(username);
		boolean isRight = matcher.matches();
		
		if(!username.equals("")){
			if(isRight){
				if(jdbc.searchUser(username) == null){
			
					usernameCheckHint.setText("Username is valid ¡Ì");
			
				}else{
					usernameCheckHint.setText("Username is repeated X");
				}
			}else{
				usernameCheckHint.setText("Username is invalid X");
			}
		}else{
			usernameCheckHint.setText(" ");
		}
		
		
	}
	
	
}
