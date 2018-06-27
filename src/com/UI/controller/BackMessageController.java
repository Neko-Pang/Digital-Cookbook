package com.UI.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.UI.view.Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BackMessageController implements Initializable{
	
	
	@FXML
	private Label loginHint;
	
	@FXML
	private Button confirm;
	
	
	public static int messageType;
	
	public static String message;
	
	public static Stage stage ;
	
	public static final String BackResourse = "/com/UI/view/BackMessage.fxml";
	
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
		
	

		loginHint.setText(message);
		
		
		confirm.setOnAction(e -> confirmEvent());
		
	}
	
	public void loginEnter(javafx.scene.input.KeyEvent event){
		
		if(event.getCode() == KeyCode.ENTER){
			confirmEvent();
		}
	}
	
	public void confirmEvent(){
		
		
		stage.close();
		
		if (messageType == 0) {
			if (LoginViewController.isPasswordCorrect()) {

				LoginViewController.getStage().close();
			}
		}
	}
	
	
}
