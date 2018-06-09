package com.UI.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginViewController implements Initializable {
	
	@FXML
	private Button cancel;

	private Stage stage = MainController.getSubStage();
	
	public void cancel(){
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		cancel.setOnAction( e->stage.close());
		
		
	}
	
}
