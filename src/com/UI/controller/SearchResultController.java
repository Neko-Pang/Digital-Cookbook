package com.UI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.UI.view.Main;

import CookBook.Comment;
import CookBook.DatabaseController;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SearchResultController implements Initializable
{

	@FXML
	private TextField searchBox;
	@FXML
	private Button goButton;
	@FXML
	private Button backBtn;
	@FXML
	private Hyperlink login;
	@FXML
	private Hyperlink signIn;
	@FXML
	private Label userLabel;
	@FXML
	private Hyperlink profileLink;
	@FXML
	private Hyperlink signout;
	@FXML
	private Button nextPage;
	@FXML
	private Button lastPage;
	@FXML
	private Hyperlink recipe1;
	@FXML
	private Hyperlink recipe2;
	@FXML
	private Hyperlink recipe3;
	@FXML
	private Hyperlink recipe4;
	@FXML
	private Hyperlink recipe5;
	@FXML
	private HBox hbox1;
	@FXML
	private HBox hbox2;
	@FXML
	private HBox hbox3;
	@FXML
	private HBox hbox4;
	@FXML
	private HBox hbox5;
	@FXML
	private Text page;

	public static Scene ResultScene;

//	private static Stage subStage = new Stage();

	public static final String RecipeResource = "/com/UI/view/SearchResult.fxml";

	public static ArrayList<Recipe> currentRecipeList = null;

	static int i = 0;

	int j = 0;

	int m = 0;

	int n = 0;

	String p = null;

	public int currentRecipeID = 0;

	public static Recipe currentRecipe;

	public static ArrayList<Recipe> goalRecipe1 = new ArrayList<Recipe>();

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO Auto-generated method stub\

		goButton.setOnAction(e -> searchResult());

		backBtn.setOnAction(e -> backToMainInterface());

		login.setOnAction(e -> showLogin(MainController.getSubStage()));

		signIn.setOnAction(e -> showSignIn(MainController.getSubStage()));

		signout.setOnAction(e -> signOut());

		nextPage.setOnAction(e ->
		{

			i = i + 5;
			n = n + 1;
			if (currentRecipeList.size() > i)
			{
				if (currentRecipeList.size() > (i))
				{
					currentRecipe = currentRecipeList.get(i);
					recipe1.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary()
							+ "\n\nServingPeople: " + currentRecipe.getServingPpl());
					hbox1.setVisible(true);
				}
				if (currentRecipeList.size() > (i + 1))
				{
					currentRecipe = currentRecipeList.get(i + 1);
					recipe2.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary()
							+ "\n\nServingPeople: " + currentRecipe.getServingPpl());
					hbox2.setVisible(true);

				}
				if (currentRecipeList.size() > (i + 2))
				{
					currentRecipe = currentRecipeList.get(i + 2);
					recipe3.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary()
							+ "\n\nServingPeople: " + currentRecipe.getServingPpl());
					hbox3.setVisible(true);

				}
				if (currentRecipeList.size() > (i + 3))
				{
					currentRecipe = currentRecipeList.get(i + 3);
					recipe4.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary()
							+ "\n\nServingPeople: " + currentRecipe.getServingPpl());
					hbox4.setVisible(true);

				}
				if (currentRecipeList.size() > (i + 4))
				{
					currentRecipe = currentRecipeList.get(i + 4);
					recipe5.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary()
							+ "\n\nServingPeople: " + currentRecipe.getServingPpl());
					hbox5.setVisible(true);

				}
				if (currentRecipeList.size() <= (i + 1))
				{
					hbox2.setVisible(false);
					hbox3.setVisible(false);
					hbox4.setVisible(false);
					hbox5.setVisible(false);
				}
				if (currentRecipeList.size() <= (i + 2))
				{
					hbox3.setVisible(false);
					hbox4.setVisible(false);
					hbox5.setVisible(false);
				}
				if (currentRecipeList.size() <= (i + 3))
				{
					hbox4.setVisible(false);
					hbox5.setVisible(false);
				}
				if (currentRecipeList.size() <= (i + 4))
				{
					hbox5.setVisible(false);
				}
			}
			if (currentRecipeList.size() < i)
			{
				i = i - 5;
				n = n - 1;
			}
			p = String.valueOf(n + "/" + m);
			page.setText(p);
		});

		lastPage.setOnAction(e ->
		{
			if (i >= 5)
			{
				i = i - 5;
				n = n - 1;
				if (currentRecipeList.size() > (i))
				{
					currentRecipe = currentRecipeList.get(i);
					recipe1.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary()
							+ "\n\nServingPeople: " + currentRecipe.getServingPpl());
					hbox1.setVisible(true);
				}
				if (currentRecipeList.size() > (i + 1))
				{
					currentRecipe = currentRecipeList.get(i + 1);
					recipe2.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary()
							+ "\n\nServingPeople: " + currentRecipe.getServingPpl());
					hbox2.setVisible(true);

				}
				if (currentRecipeList.size() > (i + 2))
				{
					currentRecipe = currentRecipeList.get(i + 2);
					recipe3.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary()
							+ "\n\nServingPeople: " + currentRecipe.getServingPpl());
					hbox3.setVisible(true);

				}
				if (currentRecipeList.size() > (i + 3))
				{
					currentRecipe = currentRecipeList.get(i + 3);
					recipe4.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary()
							+ "\n\nServingPeople: " + currentRecipe.getServingPpl());
					hbox4.setVisible(true);

				}
				if (currentRecipeList.size() > (i + 4))
				{
					currentRecipe = currentRecipeList.get(i + 4);
					recipe5.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary()
							+ "\n\nServingPeople: " + currentRecipe.getServingPpl());
					hbox5.setVisible(true);

				}
				if (currentRecipeList.size() <= (i + 1))
				{
					hbox2.setVisible(false);
					hbox3.setVisible(false);
					hbox4.setVisible(false);
					hbox5.setVisible(false);
				}
				if (currentRecipeList.size() <= (i + 2))
				{
					hbox3.setVisible(false);
					hbox4.setVisible(false);
					hbox5.setVisible(false);
				}
				if (currentRecipeList.size() <= (i + 3))
				{
					hbox4.setVisible(false);
					hbox5.setVisible(false);
				}
				if (currentRecipeList.size() <= (i + 4))
				{
					hbox5.setVisible(false);
				}
			}
			p = String.valueOf(n + "/" + m);
			page.setText(p);
		});
		i = 0;
		if (currentRecipeList.size() > (i))
		{
			currentRecipe = currentRecipeList.get(i);
			recipe1.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary() + "\n\nServingPeople: "
					+ currentRecipe.getServingPpl());
			hbox1.setVisible(true);
		}
		if (currentRecipeList.size() > (i + 1))
		{
			currentRecipe = currentRecipeList.get(i + 1);
			recipe2.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary() + "\n\nServingPeople: "
					+ currentRecipe.getServingPpl());
			hbox2.setVisible(true);

		}
		if (currentRecipeList.size() > (i + 2))
		{
			currentRecipe = currentRecipeList.get(i + 2);
			recipe3.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary() + "\n\nServingPeople: "
					+ currentRecipe.getServingPpl());
			hbox3.setVisible(true);

		}
		if (currentRecipeList.size() > (i + 3))
		{
			currentRecipe = currentRecipeList.get(i + 3);
			recipe4.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary() + "\n\nServingPeople: "
					+ currentRecipe.getServingPpl());
			hbox4.setVisible(true);

		}
		if (currentRecipeList.size() > (i + 4))
		{
			currentRecipe = currentRecipeList.get(i + 4);
			recipe5.setText(currentRecipe.getName() + "\n\n" + currentRecipe.getCategary() + "\n\nServingPeople: "
					+ currentRecipe.getServingPpl());
			hbox5.setVisible(true);

		}
		if (currentRecipeList.size() <= (i))
		{
			hbox1.setVisible(false);
			hbox2.setVisible(false);
			hbox3.setVisible(false);
			hbox4.setVisible(false);
			hbox5.setVisible(false);
		}
		if (currentRecipeList.size() <= (i + 1))
		{
			hbox2.setVisible(false);
			hbox3.setVisible(false);
			hbox4.setVisible(false);
			hbox5.setVisible(false);
		}
		if (currentRecipeList.size() <= (i + 2))
		{
			hbox3.setVisible(false);
			hbox4.setVisible(false);
			hbox5.setVisible(false);
		}
		if (currentRecipeList.size() <= (i + 3))
		{
			hbox4.setVisible(false);
			hbox5.setVisible(false);
		}
		if (currentRecipeList.size() <= (i + 4))
		{
			hbox5.setVisible(false);
		}
		System.out.println(currentRecipeList.size());
		recipe1.setOnAction(e -> showRecipe(i));
		recipe2.setOnAction(e -> showRecipe(i + 1));
		recipe3.setOnAction(e -> showRecipe(i + 2));
		recipe4.setOnAction(e -> showRecipe(i + 3));
		recipe5.setOnAction(e -> showRecipe(i + 4));

		m = currentRecipeList.size() / 5;
		if (currentRecipeList.size() % 5 != 0)
		{
			m = m + 1;
		}
		n = 1;
		if (m == 0)
		{
			n = 0;
		}
		p = String.valueOf(n + "/" + m);
		page.setText(p);

		if (MainController.currentUser != null)
		{

			login.setVisible(false);
			signIn.setVisible(false);
			signout.setVisible(true);

		}

	}

	public void showRecipe(int i)
	{

		try
		{
			currentRecipe = currentRecipeList.get(i);
			MainController.backPoint = 1;
			// initialize the recipe interface
			Parent root = FXMLLoader.load(getClass().getResource(RecipeViewController.RecipeResource));
			Scene scene = new Scene(root, 1249, 837);
			scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setScene(scene);

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void searchResult()
	{

		try
		{
			ArrayList<Recipe> goalRecipe2 = new ArrayList<Recipe>();
			goalRecipe2 = MainController.jdbc.searchRecipe(this.searchBox.getText());
			currentRecipeList = goalRecipe2;
			// initialize the recipe interface
			Parent root = FXMLLoader.load(getClass().getResource(SearchResultController.RecipeResource));
			Scene scene = new Scene(root, 1249, 837);
			scene.getStylesheets().add(getClass().getResource(Main.cssResource).toExternalForm());
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setScene(scene);

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	public void backToMainInterfaceOrSearchReuslt()
//	{
//		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainController.MainResourse));
//		Parent root;
//		try {
//			root = loader.load();
//			Scene refresh = new Scene(root, 1249, 837);
//			
//			Main.primaryStage.setScene(refresh);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//	}

	public void showLogin(Stage subStage)
	{

		subStage.setTitle("Login");
		MainController.loginPoint = 1;
		try
		{

			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/LoginView.fxml"));
			Scene scene = new Scene(root, 660, 402);
			subStage.setScene(scene);
			subStage.setResizable(false);
			subStage.showAndWait();

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showSignIn(Stage substage)
	{

		substage.setTitle("Sign in");

		try
		{
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/SignInView.fxml"));
			Scene scene = new Scene(root, 592, 684);
			substage.setScene(scene);
			substage.setResizable(false);
			substage.showAndWait();

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void signOut()
	{

		MainController.currentUser = null;
		this.signout.setVisible(false);
		this.login.setVisible(true);
		this.signIn.setVisible(true);
		this.profileLink.setVisible(false);

	}

	public void backToMainInterface()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainController.MainResourse));
		Parent root;
		try {
			root = loader.load();
			Scene refresh = new Scene(root, 1249, 837);
			
			Main.primaryStage.setScene(refresh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}