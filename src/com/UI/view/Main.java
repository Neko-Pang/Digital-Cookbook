package com.UI.view;

import java.io.IOException;

import com.UI.controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static Stage primaryStage = new Stage();
	
	public static final String cssResource = "/com/UI/view/com.UI.view.css";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage){
		// TODO Auto-generated method stub
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainInterface.fxml"));
			//set the scene into the stage and show
			Scene scene = new Scene(root,1249,837);
			scene.getStylesheets().add(getClass().getResource(cssResource).toExternalForm());
			MainController.MainScene = scene;
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
