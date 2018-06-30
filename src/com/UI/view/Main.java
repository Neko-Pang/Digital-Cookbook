package com.UI.view;

import java.io.IOException;

import com.UI.controller.BackMessageController;
import com.UI.controller.MainController;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The entry of the software
 * @author MacroHard
 * @version 1.0
 *
 */
public class Main extends Application{
	
	public static Stage primaryStage = new Stage();
	public static Stage subStage1 = new Stage();
	public static Stage subStage2 = new Stage();
	public static Stage subStage3 = new Stage();
	public static final String cssResource = "/com/UI/view/com.UI.view.css";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage){
		// TODO Auto-generated method stub
		
		try {
			subStage1.initModality(Modality.WINDOW_MODAL);
			subStage1.initOwner(primaryStage);
			subStage2.initModality(Modality.WINDOW_MODAL);
			subStage2.initOwner(subStage1);
			subStage3.initModality(Modality.WINDOW_MODAL);
			subStage3.initOwner(primaryStage);
			Parent root = FXMLLoader.load(getClass().getResource("MainInterface.fxml"));
			//set the scene into the stage and show
			Scene scene = new Scene(root,1249,837);
			scene.getStylesheets().add(getClass().getResource(cssResource).toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
