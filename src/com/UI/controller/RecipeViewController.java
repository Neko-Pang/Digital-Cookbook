package com.UI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.UI.view.Main;

import CookBook.Ingredient;
import CookBook.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class RecipeViewController implements Initializable{

	@FXML
	private Label recipeNameLabel;
	@FXML
	private Label categoryLabel;
	@FXML
	private Label servingPplLabel;
	@FXML
	private Label prepTimeLabel;
	@FXML
	private Label cookingTimeLabel;
	@FXML
	private Label userLabel;
	@FXML
	private AnchorPane ingredientPane;
	@FXML
	private AnchorPane prepStepPane;
	@FXML
	private TextArea commentField;
	@FXML
	private Button commentConfirmBtn;
	@FXML
	private Label loginHint;
	@FXML
	private Hyperlink loginHyperlink;
	@FXML
	private Button backBtn;
	@FXML
	private VBox ingredientVBox;
	
	
	private Recipe currentRecipe = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		currentRecipe = MainController.jdbc.searchRecipe(MainController.currentRecipeID);
		recipeNameLabel.setText(currentRecipe.getName());
		categoryLabel.setText(currentRecipe.getCategary());
		servingPplLabel.setText(String.valueOf(currentRecipe.getServingPpl()));
		prepTimeLabel.setText(String.valueOf(currentRecipe.getPreparationTime())+" min");
		cookingTimeLabel.setText(String.valueOf(currentRecipe.getCookingTime())+" min");
		
		if(currentRecipe.getAccountID() == 0){
			userLabel.setText("Default Recipe");
		}else{
			userLabel.setText(MainController.currentUser.getUserName());
		}
		
		for(int i = 0; i < currentRecipe.getIngredients().size();i++){
			Ingredient ingredient = currentRecipe.getIngredients().get(i);
			setIngredientLabel(ingredient,i);
			ingredientPane.setMinHeight(23+63*(i+1)+10);
		}
		
		
		for (int i = 0; i < currentRecipe.getPreparationStep().size(); i++) {
			String prepStep = String.valueOf(i+1) + ": " + currentRecipe.getPreparationStep().get(i);
			setPrepStepLabel(prepStep, i);
			prepStepPane.setMinHeight(23+80*(i+1)+30);
		}
		
		if(MainController.currentUser == null){
			loginHint.setVisible(true);	
			loginHyperlink.setVisible(true);
		}else{
			commentField.setVisible(true);
			commentConfirmBtn.setVisible(true);
		}
		
		backBtn.setOnAction(e->backToMainInterface());
		
	}

	
	public void setIngredientLabel(Ingredient ingredient,int n){
		
		AnchorPane pane = new AnchorPane();
		Label ingredientLabel1 = new Label(ingredient.getName());
		
		Label ingredientLabel3 = new Label(ingredient.getWeight()+ " " +ingredient.getUnit());
		pane.setMinSize(948, 88);
		pane.setMaxSize(948, 88);
		ingredientLabel1.setMinSize(133,44);
		ingredientLabel1.setLayoutX(22);
		ingredientLabel1.setLayoutY(22);
		ingredientLabel1.setTextFill(Color.rgb(75, 75, 173));
		ingredientLabel1.setFont(Font.font(22));
		ingredientLabel1.setAlignment(Pos.CENTER_LEFT);
		
		if(ingredient.cookingWay.equals("None")){
			Label ingredientLabel2 = new Label(ingredient.getCookingWay());
			ingredientLabel2.setMinSize(373,44);
			ingredientLabel2.setLayoutX(288);
			ingredientLabel2.setLayoutY(22);
			ingredientLabel2.setTextFill(Color.rgb(75, 75, 173));
			ingredientLabel2.setFont(Font.font(22));
			ingredientLabel2.setAlignment(Pos.CENTER);
			pane.getChildren().add(ingredientLabel2);
		}
		
		ingredientLabel3.setMinSize(133,44);
		ingredientLabel3.setLayoutX(779);
		ingredientLabel3.setLayoutY(18);
		ingredientLabel3.setTextFill(Color.rgb(75, 75, 173));
		ingredientLabel3.setFont(Font.font(22));
		ingredientLabel3.setAlignment(Pos.CENTER_RIGHT);
		
		pane.getChildren().addAll(ingredientLabel1,ingredientLabel3);
		ingredientVBox.getChildren().add(pane);
	
	}
	
	public void setPrepStepLabel(String str, int n){
		
		Label prepLabel = new Label(str);
		prepLabel.setMinSize(934, 80);
		prepLabel.setMaxWidth(934);
		prepLabel.setWrapText(true);
		prepLabel.setLayoutX(7);
		prepLabel.setLayoutY(23+80*n);
		prepLabel.setTextFill(Color.rgb(75, 75, 173));
		prepLabel.setFont(Font.font(22));
		prepLabel.setAlignment(Pos.CENTER_LEFT);
		prepStepPane.getChildren().add(prepLabel);
		
	}
	
	public void backToMainInterface(){
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/Maininterface.fxml"));
			Scene scene = new Scene(root,1249,837);
			Main.primaryStage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



