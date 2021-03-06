package com.UI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.UI.view.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * the controller of BassMessageView
 * @author MacroHard
 * @version 1.0
 */
public class BackMessageController implements Initializable{
	
	
	@FXML
	private Label loginHint;
	@FXML
	private Button confirm;
	@FXML
	private Button confirmForCancel;
	@FXML
	private Button cancel;
	
	//the flag of message
	public static int messageType;
	
	//the message context
	public static String message;
	
	//showing stage
	public static Stage stage ;
	
	//the load root
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

	/**
	 * the initialize the view
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	

		if(messageType == 2)
		{
			message = "Uploaded Suceessfully!";
		}
		else if(messageType == 3)
		{
			message = "Updated Suceessfully!";
		}
		if(messageType == 4)
		{
			confirm.setVisible(false);
			confirmForCancel.setVisible(true);
			cancel.setVisible(true);
			message = "Are you sure to cancel all the works?";
			confirmForCancel.setOnAction(e -> confirmCancel());
			cancel.setOnAction(e -> cancel());
		}
		loginHint.setText(message);
		
		
		confirm.setOnAction(e -> confirmEvent());
		stage.setAlwaysOnTop(true);
	}
	
	
	/**
	 * to monitor the keyboard event to make enter as confirm
	 * @param event/the keyboard event
	 */
	public void loginEnter(javafx.scene.input.KeyEvent event){
		
		if(event.getCode() == KeyCode.ENTER){
			confirmEvent();
		}
	}
	
	
	/**
	 * the interaction after clicking confirm button when login
	 */
	public final void confirmEvent(){
		
		
		stage.close();
		
		if (messageType == 0) {
			if (LoginViewController.isPasswordCorrect()) {

				LoginViewController.getStage().close();
			}
		}
		message = null;
	}
	
	/**
	 * the interaction after clicking cancel button when add and edit recipe
	 */
	public void confirmCancel()
	{
		stage.close();
		confirm.setVisible(true);
		confirmForCancel.setVisible(false);
		cancel.setVisible(false);

		try 
		{
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
	
    /**
     * the interaction after clicking cancel button when login in
     */
	public void cancel()
	{
		stage.close();
		confirm.setVisible(true);
		confirmForCancel.setVisible(false);
		cancel.setVisible(false);
		
	}
	
}
