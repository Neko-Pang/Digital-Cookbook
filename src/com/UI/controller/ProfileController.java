package com.UI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.LineUnavailableException;

import com.UI.view.Main;

import CookBook.Comment;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ProfileController implements Initializable{

	@FXML
	private Label profileLabel;
	@FXML
	private Label ownRecipes;
	@FXML
	private AnchorPane profilePane;
	@FXML
	private Label ownComment;
	@FXML
	private Button backBtn;
	@FXML
	private ImageView backgroundView;
	
	int Y;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		profileLabel.setText(MainController.currentUser.getUserName()+"' s    Profile");
		ownRecipes.setText(MainController.currentUser.getUserName()+"' s recipes:");
		showOwnRecipe();
		ownComment.setText(MainController.currentUser.getUserName()+"' s comments:");
		if(MainController.currentUser.getOwnRecipes().size()!=0){
			ownComment.setLayoutY(236+59*MainController.currentUser.getOwnRecipes().size()+50);
			Y = 236+59*MainController.currentUser.getOwnRecipes().size()+109;
		}else{
			ownComment.setLayoutY(236+109);
			Y  = 236+59+109 ;
		}
		showOwnComment();
		
		Image backgroundImage = new Image("/Picture/delicious.jpg");

		backgroundView.setImage(backgroundImage);
		
		backBtn.setOnAction(e->backToMain());
		
		
	}
	
	
	public void showOwnRecipe(){
		
		if(MainController.currentUser.getOwnRecipes().size() == 0){
		
			Label addHint = new Label("You haven't got any recipes , create it now!");
			
			addHint.setTextFill(Color.rgb(75, 75, 173));
			addHint.setFont(Font.font(22));
			addHint.setAlignment(Pos.CENTER);
			addHint.setMinSize(706, 49);
			addHint.setLayoutX(266);
			addHint.setLayoutY(236);
			profilePane.getChildren().add(addHint);
			
		}else{
			
			for (int i = 0; i < MainController.currentUser.getOwnRecipes().size(); i++) {
				Recipe recipe = MainController.currentUser.getOwnRecipes().get(i);
			
				Hyperlink recipeLink = new Hyperlink(recipe.getName());
				recipeLink.setTextFill(Color.rgb(75, 75, 173));
				recipeLink.setFont(Font.font(22));
				recipeLink.setAlignment(Pos.CENTER_LEFT);
				recipeLink.setMinSize(606, 49);
				recipeLink.setLayoutX(266);
				recipeLink.setLayoutY(236+ 59*i);
				recipeLink.setUserData(recipe.getRecipeID());
				recipeLink.setOnAction(e->showRecipe(recipe.getRecipeID()));
				recipeLink.getStyleClass().add("Hyperlink:Hover");
				
				Hyperlink editRecipeLink  = new Hyperlink("edit");
				
				editRecipeLink.setFont(Font.font(22));
				editRecipeLink.setAlignment(Pos.CENTER_LEFT);
				editRecipeLink.setMinSize(121, 49);
				editRecipeLink.setLayoutX(922);
				editRecipeLink.setLayoutY(236+ 59*i);
				editRecipeLink.getStyleClass().add("Hyperlink:Hover");
				
				
				Hyperlink deleteRecipeLink = new Hyperlink("delete");
				deleteRecipeLink.setTextFill(Color.rgb(255, 0, 0));
				deleteRecipeLink.setFont(Font.font(22));
				deleteRecipeLink.setAlignment(Pos.CENTER_LEFT);
				deleteRecipeLink.setMinSize(121, 49);
				deleteRecipeLink.setLayoutX(1022);
				deleteRecipeLink.setLayoutY(236+ 59*i);
				deleteRecipeLink.getStyleClass().add("Hyperlink:Hover");
				deleteRecipeLink.setOnAction(e->deleteOwnRecipe(recipe));
				profilePane.getChildren().addAll(recipeLink,editRecipeLink,deleteRecipeLink);
				
				
			}
			
		}
		
	}
	
	
	public void showRecipe(int recipeID){
		
		MainController.currentRecipe = MainController.jdbc.searchRecipe(recipeID);
		MainController.backPoint = 2;
		try {
			Parent root = FXMLLoader.load(getClass().getResource(RecipeViewController.RecipeResource));
			Scene scene = new Scene(root,1249,837);
			scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void deleteOwnRecipe(Recipe recipe){
		
		MainController.jdbc.deleteRecipe(recipe.getRecipeID());
		MainController.currentUser = MainController.jdbc.searchUser(MainController.currentUser.getAccountID());
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/ProfileView.fxml"));
			Scene scene = new Scene(root,1249,837);
			scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
			Main.primaryStage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	public void showOwnComment(){
		
		if(MainController.currentUser.getOwnComments().size() == 0){
			
			Label addHint = new Label("You haven't got any comments , create it now!");
			
			addHint.setTextFill(Color.rgb(75, 75, 173));
			addHint.setFont(Font.font(22));
			addHint.setAlignment(Pos.CENTER);
			addHint.setMinSize(706, 49);
			addHint.setLayoutX(266);
			addHint.setLayoutY(Y);
			profilePane.getChildren().add(addHint);
			
		}else{
			
			for (int i = 0; i < MainController.currentUser.getOwnComments().size(); i++) {
				Comment comment = MainController.currentUser.getOwnComments().get(i);
				Hyperlink recipeName = new Hyperlink(MainController.jdbc.searchRecipe(comment.getRecipeID()).getName()+":");
				
				recipeName.setTextFill(Color.rgb(75, 75, 173));
				recipeName.setFont(Font.font(22));
				recipeName.setAlignment(Pos.CENTER_LEFT);
				recipeName.setMinSize(100, 100);
				recipeName.setMaxWidth(200);
				recipeName.setWrapText(true);
				recipeName.setLayoutX(107);
				recipeName.setLayoutY(Y+110*i);
				recipeName.setOnAction(e->showRecipe(comment.getRecipeID()));
				
				Label commentLink = new Label(comment.getContext());
				commentLink.setTextFill(Color.rgb(75, 75, 173));
				commentLink.setFont(Font.font(17));
				commentLink.setAlignment(Pos.CENTER);
				commentLink.setMinSize(656, 90);
				commentLink.setMaxWidth(656);
				commentLink.setWrapText(true);
				commentLink.setLayoutX(306);
				commentLink.setLayoutY(Y+ 110*i);
				commentLink.getStyleClass().add("Border");
				commentLink.getStyleClass().add("BorderRadius");
				commentLink.setWrapText(true);
				
				Hyperlink deleteCommentLink = new Hyperlink("delete");
				deleteCommentLink.setTextFill(Color.rgb(255, 0, 0));
				deleteCommentLink.setFont(Font.font(22));
				deleteCommentLink.setAlignment(Pos.CENTER_LEFT);
				deleteCommentLink.setMinSize(121, 100);
				deleteCommentLink.setLayoutX(1022);
				deleteCommentLink.setLayoutY(Y+110*i);
				deleteCommentLink.getStyleClass().add("Hyperlink:Hover");
				deleteCommentLink.setOnAction(e->deleteComment(comment));
				profilePane.getChildren().addAll(recipeName,commentLink,deleteCommentLink);
			}
		
		
		}
		
	}
	
	
	public void backToMain(){
		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(MainController.MainResourse));
			Parent root = loader.load();
			Scene refresh = new Scene(root, 1249, 837);
			MainController.MainScene = refresh;
			Main.primaryStage.setScene(refresh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void deleteComment(Comment comment){
		
		MainController.jdbc.deleteComment(comment.getRecipeID(), comment.getAccountID(), comment.getCommentNo());
		MainController.currentUser = MainController.jdbc.searchUser(MainController.currentUser.getAccountID());
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/ProfileView.fxml"));
			Scene scene = new Scene(root,1249,837);
			scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
