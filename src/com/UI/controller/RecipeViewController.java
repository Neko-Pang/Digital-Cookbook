package com.UI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.UI.view.Main;

import CookBook.Comment;
import CookBook.Ingredient;
import CookBook.Recipe;
import CookBook.RegisteredUser;
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
	private VBox prepStepVbox;
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
	@FXML
	private VBox commentVbox;
	@FXML
	private Label commentHint;
	@FXML
	private AnchorPane commentPane;
	
	
	public static final String RecipeResource = "/com/UI/view/RecipeView.fxml" ;
	
	private Recipe currentRecipe = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		currentRecipe = MainController.jdbc.searchRecipe(MainController.currentRecipeID);
		System.out.println(currentRecipe);
		recipeNameLabel.setText(currentRecipe.getName());
		categoryLabel.setText(currentRecipe.getCategary());
		servingPplLabel.setText(String.valueOf(currentRecipe.getServingPpl()));
		prepTimeLabel.setText(String.valueOf(currentRecipe.getPreparationTime())+" min");
		cookingTimeLabel.setText(String.valueOf(currentRecipe.getCookingTime())+" min");
		loginHyperlink.setOnAction(e->backToMainInterface());
		backBtn.setOnAction(e->backToMainInterface());
		commentConfirmBtn.setOnAction(e->giveComment());
		
		if(currentRecipe.getAccountID() == 0){
			userLabel.setText("Default Recipe");
		}else{
			userLabel.setText(MainController.currentUser.getUserName());
		}
		
		setIngredientLabel();
	
		setPrepStepLabel();
		
		if(MainController.currentUser == null){
			loginHint.setVisible(true);	
			loginHyperlink.setVisible(true);
		}else{
			commentField.setVisible(true);
			commentConfirmBtn.setVisible(true);
		}
				
		setComment(currentRecipe.getComments());
	}

	
	public void setIngredientLabel(){
		
		for (int i = 0; i < currentRecipe.getIngredients().size(); i++) {
			
			Ingredient ingredient = currentRecipe.getIngredients().get(i);
			AnchorPane pane = new AnchorPane();
			Label ingredientLabel1 = new Label(ingredient.getName());
			pane.getStyleClass().add("Border");
			Label ingredientLabel3 = new Label(ingredient.getWeight() + " " + ingredient.getUnit());
			pane.setMinSize(948, 88);
			pane.setMaxSize(948, 88);
			ingredientLabel1.setMinSize(133, 44);
			ingredientLabel1.setLayoutX(22);
			ingredientLabel1.setLayoutY(22);
			ingredientLabel1.setTextFill(Color.rgb(75, 75, 173));
			ingredientLabel1.setFont(Font.font(22));
			ingredientLabel1.setAlignment(Pos.CENTER_LEFT);

			if (!ingredient.cookingWay.equals("None")) {
				Label ingredientLabel2 = new Label(ingredient.getCookingWay());
				ingredientLabel2.setMinSize(373, 44);
				ingredientLabel2.setLayoutX(288);
				ingredientLabel2.setLayoutY(22);
				ingredientLabel2.setTextFill(Color.rgb(75, 75, 173));
				ingredientLabel2.setFont(Font.font(22));
				ingredientLabel2.setAlignment(Pos.CENTER);
				pane.getChildren().add(ingredientLabel2);
			}

			ingredientLabel3.setMinSize(133, 44);
			ingredientLabel3.setLayoutX(779);
			ingredientLabel3.setLayoutY(18);
			ingredientLabel3.setTextFill(Color.rgb(75, 75, 173));
			ingredientLabel3.setFont(Font.font(22));
			ingredientLabel3.setAlignment(Pos.CENTER_RIGHT);

			if( (i+1) == currentRecipe.getIngredients().size()){
				
				pane.getStyleClass().add("BorderRadiusDown");
				
			}
			
			pane.getChildren().addAll(ingredientLabel1, ingredientLabel3);
			ingredientVBox.getChildren().add(pane);

			
		}
		
		prepStepVbox.setLayoutY(525 + 88 * (currentRecipe.getIngredients().size() + 2) + 60);
	}
	
	
	
	
	
	/**
	 * To show the preparation steps
	 */
	public void setPrepStepLabel(){
		
		
		for (int i = 0; i < currentRecipe.getPreparationStep().size(); i++) {
			
			String prepStep = String.valueOf(i+1) + ": " + currentRecipe.getPreparationStep().get(i);
			AnchorPane prepStepPane = new AnchorPane();
			prepStepPane.getStyleClass().add("Border");
			prepStepPane.setMinSize(948, 120);
			Label prepLabel = new Label(prepStep);
			prepLabel.setMinSize(922, 100);
			prepLabel.setMaxWidth(934);
			prepLabel.setWrapText(true);
			prepLabel.setLayoutX(14);
			prepLabel.setLayoutY(10);
			prepLabel.setWrapText(true);
			prepLabel.setTextFill(Color.rgb(75, 75, 173));
			prepLabel.setFont(Font.font(22));
			prepLabel.setAlignment(Pos.CENTER_LEFT);
			
			if( (i+1) == currentRecipe.getPreparationStep().size()){
				prepStepPane.getStyleClass().add("BorderRadiusDown");
			}
			
			prepStepPane.getChildren().add(prepLabel);
			prepStepVbox.getChildren().add(prepStepPane);
			
		}
		commentVbox.setLayoutY(prepStepVbox.getLayoutY()+ 60 + 88 + 120*(currentRecipe.getPreparationStep().size()+1));
		
	}
	
	/**
	 * To show the comments
	 * @param comments
	 */
	public void setComment(ArrayList<Comment> comments){
		
		if( comments.size() == 0 ){
			
			commentHint.setVisible(true);

		}else{
			
			for (int i = 0; i < comments.size();i ++) {
				
				Comment comment = comments.get(i);
				RegisteredUser commentOwner = MainController.jdbc.searchUser(comment.getAccountID());
				Label nameLabel = new Label(commentOwner.getUserName()+":");
				nameLabel.setAlignment(Pos.CENTER_LEFT);
				nameLabel.setFont(Font.font(17));
				nameLabel.setMinSize(536, 32);
				nameLabel.setLayoutX(36);
				nameLabel.setLayoutY(28+120*i);
				nameLabel.setTextFill(Color.rgb(75, 75, 173));
				
				commentPane.getChildren().add(nameLabel);
				Label contextLabel = new Label(comment.getContext());
				contextLabel.setAlignment(Pos.CENTER_LEFT);
				contextLabel.setFont(Font.font(17));
				contextLabel.setMinSize(536, 32);
				contextLabel.setLayoutX(97);
				contextLabel.setLayoutY(60+120*i);
				contextLabel.setAlignment(Pos.CENTER);
				
				contextLabel.setTextFill(Color.rgb(75, 75, 173));
				contextLabel.getStyleClass().add("Border");
				contextLabel.getStyleClass().add("BorderRadius");
				commentPane.getChildren().add(contextLabel);
				commentPane.setMinHeight(120*(i+1));
				commentPane.setMaxHeight(120*(i+1));
				
				if( MainController.currentUser.getAccountID() == comment.getAccountID() ){
					
					Hyperlink deleteLink = new Hyperlink("delete");
					
				}
				
			}
		}
		
	}
	
	
	/**
	 * this method is to give the confirmed comment to the database
	 */
	public void giveComment(){
		
		Comment comment = new Comment();
		String context = commentField.getText();
		comment.setContext(context);
		comment.setRecipeID(currentRecipe.getRecipeID());
		comment.setAccountID(MainController.currentUser.getAccountID());
		MainController.jdbc.insertComment(comment);
		try {
			Parent root = FXMLLoader.load(getClass().getResource(RecipeResource));
			Scene scene = new Scene(root,1249,837);
			Main.primaryStage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void backToMainInterface(){
			Main.primaryStage.setScene(MainController.MainScene);
	}
}



