package com.UI.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

public class BackMessageController implements Initializable{
	
	
	@FXML
	private Label loginHint;
	
	@FXML
	private Button confirm;
	
	
	public Label getLoginHint() {
		return loginHint;
	}



	public void setLoginHint(Label loginHint) {
		this.loginHint = loginHint;
	}



	public Button getConfirm() {
		return confirm;
	}



	public void setConfirm(Button confirm) {
		this.confirm = confirm;
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		if(LoginViewController.isUserExist()){
			if(LoginViewController.isPasswordCorrect()){
				loginHint.setText("Login successfully");
			}else{
				loginHint.setText("The password is not correct!");
			}
		}else{
			loginHint.setText("User not exist!");
		}
		
		confirm.setOnAction(e->confirmEvent());

	
	}
	
	public void loginEnter(javafx.scene.input.KeyEvent event){
		
		if(event.getCode() == KeyCode.ENTER){
			confirmEvent();
		}
	}
	
	public void confirmEvent(){
		
		
		LoginViewController.getSubstage().close();
		
		if(LoginViewController.isPasswordCorrect()){
		
			LoginViewController.getStage().close();
		}
	}
	
	
}
