/**
 * 
 */
package com.UI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.UI.view.Main;
import com.UI.controller.*;

import CookBook.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;

/**
 * @author KaiwenZhou
 *
 */
public class AddEditRecipeController implements Initializable
{
	@FXML
	private TextField recipeTitleTextField;
	@FXML
	private TextField categoryTextField;
	@FXML
	private TextField servingPplTextField;
	@FXML
	private TextField prepTimeTextField;
	@FXML
	private TextField cookingTimeTextField;
	@FXML
	private TextField ingreNameTextField;
	@FXML
	private TextField ingreAmountTextField;
	@FXML
	private TextField ingreUnitTextField;
	@FXML
	private TextField ingreReqTextField;
	@FXML
	private TextField prepStepTextField;
	@FXML
	private Label userLabel;
	@FXML
	private Label addEditLabel;
	@FXML
	private Button confirmButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button ingreAddButton;
	@FXML
	private Button prepStepAddButton;
	@FXML
	private VBox ingredientVBox;
	@FXML
	private VBox ingreRootVBox;
	@FXML
	private VBox prepStepVBox;
	@FXML
	private VBox prepRootVBox;
	@FXML
	private VBox buttonVBox;

	private final int maxLength = 1024;
	private static Recipe currentRecipe = new Recipe();
	private static boolean setEdit = false;
	private ArrayList<Ingredient> addingIngredient = new ArrayList<Ingredient>();
	private ArrayList<String> addingPrepStep = new ArrayList<String>();
	public static final String AddEditRecipeResource = "/com/UI/view/AddEditRecipe.fxml";

	public static void setCurrentRecipe(int recipeID)
	{
		currentRecipe = CookBook.copy(DatabaseController.getInstance().searchRecipe(recipeID));
	}

	public static void setEdit()
	{
		setEdit = true;
	}

	public static void setAdd()
	{
		setEdit = false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO Auto-generated method stub
		if (!setEdit)
		{
			ingreAddButton.setOnAction(e -> addIngre());
			prepStepAddButton.setOnAction(e -> addPrepStep());

			addEditLabel.setText("Add Recipe");
			addEditLabel.setFont(Font.font(22));
			addEditLabel.setLayoutX(569);
			addEditLabel.setLayoutY(36);
			userLabel.setText(MainController.currentUser.getUserName());
			addEditLabel.setFont(Font.font(22));

			confirmButton.setOnAction(e -> uploadRecipe());
			cancelButton.setOnAction(e -> cancel());
		} else
		{
			ingreAddButton.setOnAction(e -> addIngre());
			prepStepAddButton.setOnAction(e -> addPrepStep());

			addEditLabel.setText("Edit Recipe");
			addEditLabel.setFont(Font.font(22));
			addEditLabel.setLayoutX(569);
			addEditLabel.setLayoutY(36);

			// currentRecipe = CookBook.copy(MainController.currentRecipe);
			if (currentRecipe != null)
			{
				recipeTitleTextField.setText(currentRecipe.getName());
				categoryTextField.setText(currentRecipe.getCategary());
				servingPplTextField.setText(Integer.toString(currentRecipe.getServingPpl()));
				prepTimeTextField.setText(Integer.toString(currentRecipe.getPreparationTime()));
				cookingTimeTextField.setText(Integer.toString(currentRecipe.getCookingTime()));
				userLabel.setText(
						DatabaseController.getInstance().searchUser(currentRecipe.getAccountID()).getUserName());
				addingIngredient = currentRecipe.getIngredients();
				addingPrepStep = currentRecipe.getPreparationStep();
				loadAddingIngre();
				loadAddingPrep();
				confirmButton.setOnAction(e -> updateRecipe());
				cancelButton.setOnAction(e -> cancel());
			}
		}
	}

	public void addIngre()
	{
		Pattern pattern = Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,2}$");
		Matcher matcher = pattern.matcher(ingreAmountTextField.getText());
		// System.out.println("#" + ingreNameTextField.getText() + "#");
		// System.out.println("#" + ingreAmountTextField.getText() + "#");
		// System.out.println("#" + ingreUnitTextField.getText() + "#");
		if (ingreNameTextField.getText().trim().equals("") || ingreAmountTextField.getText().trim().equals("")
				|| ingreUnitTextField.getText().trim().equals(""))
		{
			try
			{
				BackMessageController.message = "Name, Amount, Unit should not be empty";
				BackMessageController.messageType = 4;
				BackMessageController.stage = Main.subStage2;
				Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
				Scene scene = new Scene(root, 328, 223);
				Main.subStage2.setScene(scene);
				Main.subStage2.setResizable(false);
				Main.subStage2.show();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (ingreNameTextField.getText().length() > maxLength
				|| ingreAmountTextField.getText().length() > maxLength
				|| ingreUnitTextField.getText().length() > maxLength
				|| ingreReqTextField.getText().length() > maxLength)
		{

			try
			{
				BackMessageController.message = "Any text should not longer than 1024!";
				BackMessageController.messageType = 4;
				BackMessageController.stage = Main.subStage2;
				Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
				Scene scene = new Scene(root, 328, 223);
				Main.subStage2.setScene(scene);
				Main.subStage2.setResizable(false);
				Main.subStage2.show();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (!matcher.matches())
		{
			try
			{
				BackMessageController.message = "the text of amount is illegal gor Number!";
				BackMessageController.messageType = 4;
				BackMessageController.stage = Main.subStage2;
				Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
				Scene scene = new Scene(root, 328, 223);
				Main.subStage2.setScene(scene);
				Main.subStage2.setResizable(false);
				Main.subStage2.show();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
		{
			Ingredient addingIngre = new Ingredient(ingreNameTextField.getText(),
					Double.parseDouble(ingreAmountTextField.getText()), ingreUnitTextField.getText(),
					ingreReqTextField.getText());
			addingIngredient.add(addingIngre);

			// AnchorPane ingrePane = new AnchorPane();
			// ingrePane.setId("ingreAP" + Integer.toString(addingIngredient.size()));

			loadAddingIngre();
			ingreNameTextField.clear();
			ingreAmountTextField.clear();
			ingreUnitTextField.clear();
			ingreReqTextField.clear();
		}
	}

	public void deleteIngre(String ingrePaneNo)
	{

		String regEx = "[^0-9]";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(ingrePaneNo);
		int ingreRowNo = Integer.parseInt(matcher.replaceAll(""));
		addingIngredient.remove(ingreRowNo - 1);
		loadAddingIngre();
	}

	public void editIngre(String ingrePaneNo, AnchorPane parentNode)
	{
		// System.out.println("the parent node id is: " + parentNode.getId());
		String regEx = "[^0-9]";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(ingrePaneNo);
		int ingreRowNo = Integer.parseInt(matcher.replaceAll(""));

		TextField ingreTextField1 = new TextField(addingIngredient.get(ingreRowNo - 1).getName());
		ingreTextField1.setMinSize(200, 56);
		ingreTextField1.setMaxSize(200, 56);
		ingreTextField1.setLayoutX(40);
		ingreTextField1.setLayoutY(24);
		ingreTextField1.setFont(Font.font(12));
		ingreTextField1.setAlignment(Pos.CENTER_LEFT);

		TextField ingreTextField2 = new TextField(Double.toString(addingIngredient.get(ingreRowNo - 1).getWeight()));
		ingreTextField2.setMinSize(54, 56);
		ingreTextField2.setMaxSize(54, 56);
		ingreTextField2.setLayoutX(312);
		ingreTextField2.setLayoutY(24);
		ingreTextField2.setFont(Font.font(12));
		ingreTextField2.setAlignment(Pos.CENTER_LEFT);

		TextField ingreTextField3 = new TextField(addingIngredient.get(ingreRowNo - 1).getUnit());
		ingreTextField3.setMinSize(71, 56);
		ingreTextField3.setMaxSize(71, 56);
		ingreTextField3.setLayoutX(366);
		ingreTextField3.setLayoutY(24);
		ingreTextField3.setFont(Font.font(12));
		ingreTextField3.setAlignment(Pos.CENTER_LEFT);

		TextField ingreTextField4 = new TextField(addingIngredient.get(ingreRowNo - 1).getCookingWay());
		ingreTextField4.setMinSize(378, 56);
		ingreTextField4.setMaxSize(378, 56);
		ingreTextField4.setLayoutX(489);
		ingreTextField4.setLayoutY(24);
		ingreTextField4.setFont(Font.font(12));
		ingreTextField4.setAlignment(Pos.CENTER_LEFT);

		Button ingreEditConfirmButton = new Button("confirm");
		ingreEditConfirmButton.setMinSize(10, 6);
		ingreEditConfirmButton.setLayoutX(958);
		ingreEditConfirmButton.setLayoutY(25);
		ingreEditConfirmButton.setAlignment(Pos.BOTTOM_RIGHT);

		ingreEditConfirmButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				Pattern pattern = Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,2}$");
				Matcher matcher = pattern.matcher(ingreTextField2.getText());
				if (ingreTextField1.getText().trim().equals("") || ingreTextField2.getText().trim().equals("")
						|| ingreTextField3.getText().trim().equals(""))
				{
					try
					{
						BackMessageController.message = "Name, Amount, Unit should not be empty";
						BackMessageController.messageType = 4;
						BackMessageController.stage = Main.subStage2;
						Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
						Scene scene = new Scene(root, 328, 223);
						Main.subStage2.setScene(scene);
						Main.subStage2.setResizable(false);
						Main.subStage2.show();

					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (ingreTextField1.getText().length() > maxLength
						|| ingreTextField2.getText().length() > maxLength
						|| ingreTextField3.getText().length() > maxLength
						|| ingreTextField4.getText().length() > maxLength)
				{

					try
					{
						BackMessageController.message = "Any text should not longer than 1024!";
						BackMessageController.messageType = 4;
						BackMessageController.stage = Main.subStage2;
						Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
						Scene scene = new Scene(root, 328, 223);
						Main.subStage2.setScene(scene);
						Main.subStage2.setResizable(false);
						Main.subStage2.show();

					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (!matcher.matches())
				{
					try
					{
						BackMessageController.message = "the text of amount is illegal gor Number!";
						BackMessageController.messageType = 4;
						BackMessageController.stage = Main.subStage2;
						Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
						Scene scene = new Scene(root, 328, 223);
						Main.subStage2.setScene(scene);
						Main.subStage2.setResizable(false);
						Main.subStage2.show();

					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else
				{
					addingIngredient.get(ingreRowNo - 1).setName(ingreTextField1.getText());
					addingIngredient.get(ingreRowNo - 1).setWeight(Double.parseDouble(ingreTextField2.getText()));
					addingIngredient.get(ingreRowNo - 1).setUnit(ingreTextField3.getText());
					addingIngredient.get(ingreRowNo - 1).setCookingWay(ingreTextField4.getText());
					loadAddingIngre();
				}
			}
		});
		parentNode.getChildren().clear();
		parentNode.getChildren().addAll(ingreTextField1, ingreTextField2, ingreTextField3, ingreTextField4,
				ingreEditConfirmButton);

	}

	public void loadAddingIngre()
	{
		int i = 0;
		ingredientVBox.getChildren().clear();
		for (; i < addingIngredient.size(); i++)
		{
			AnchorPane ingrePane = new AnchorPane();
			ingrePane.setId("ingreAP" + Integer.toString(i + 1));
			Label ingredientLabel1 = new Label(addingIngredient.get((i)).getName());
			ingrePane.getStyleClass().add("Border");
			Label ingredientLabel3 = new Label(
					addingIngredient.get((i)).getWeight() + " " + addingIngredient.get((i)).getUnit());
			Button ingreDeleteButton = new Button("delete");
			Button ingreEditButton = new Button("edit");
			ingrePane.setMinSize(948, 88);
			ingrePane.setMaxSize(948, 88);
			ingredientLabel1.setMinSize(133, 44);
			ingredientLabel1.setLayoutX(22);
			ingredientLabel1.setLayoutY(22);
			ingredientLabel1.setTextFill(Color.rgb(75, 75, 173));
			ingredientLabel1.setFont(Font.font(22));
			ingredientLabel1.setAlignment(Pos.CENTER_LEFT);

			Label ingredientLabel2 = new Label(addingIngredient.get((i)).getCookingWay());
			ingredientLabel2.setMinSize(373, 44);
			ingredientLabel2.setLayoutX(288);
			ingredientLabel2.setLayoutY(22);
			ingredientLabel2.setTextFill(Color.rgb(75, 75, 173));
			ingredientLabel2.setFont(Font.font(22));
			ingredientLabel2.setAlignment(Pos.CENTER);

			ingredientLabel3.setMinSize(133, 44);
			ingredientLabel3.setLayoutX(700);
			ingredientLabel3.setLayoutY(18);
			ingredientLabel3.setTextFill(Color.rgb(75, 75, 173));
			ingredientLabel3.setFont(Font.font(22));
			ingredientLabel3.setAlignment(Pos.CENTER_RIGHT);

			ingreDeleteButton.setMinSize(10, 6);
			ingreDeleteButton.setLayoutX(958);
			ingreDeleteButton.setLayoutY(40);

			ingreDeleteButton.setAlignment(Pos.BOTTOM_RIGHT);

			ingreEditButton.setMinSize(10, 6);
			ingreEditButton.setLayoutX(958);
			ingreEditButton.setLayoutY(10);
			ingreEditButton.setAlignment(Pos.BOTTOM_RIGHT);
			if (i == 0)
			{
				Button downButton = new Button("down");
				downButton.setMinSize(10, 6);
				downButton.setLayoutX(-48);
				downButton.setLayoutY(40);
				downButton.setAlignment(Pos.BOTTOM_RIGHT);
				ingrePane.getChildren().addAll(ingredientLabel1, ingredientLabel2, ingredientLabel3, ingreDeleteButton,
						ingreEditButton, downButton);
			}
			else if (i == (addingPrepStep.size()-1))
			{
			
				Button upButton = new Button("up");
				upButton.setMinSize(10, 6);
				upButton.setLayoutX(-34);
				upButton.setLayoutY(10);
				upButton.setAlignment(Pos.BOTTOM_RIGHT);
				ingrePane.getChildren().addAll(ingredientLabel1, ingredientLabel2, ingredientLabel3, ingreDeleteButton,
						ingreEditButton, upButton);
			}
			else
			{
				Button upButton = new Button("up");
				upButton.setMinSize(10, 6);
				upButton.setLayoutX(-34);
				upButton.setLayoutY(10);
				upButton.setAlignment(Pos.BOTTOM_RIGHT);
				Button downButton = new Button("down");
				downButton.setMinSize(10, 6);
				downButton.setLayoutX(-48);
				downButton.setLayoutY(40);
				downButton.setAlignment(Pos.BOTTOM_RIGHT);
				ingrePane.getChildren().addAll(ingredientLabel1, ingredientLabel2, ingredientLabel3, ingreDeleteButton,
						ingreEditButton, upButton, downButton);
			}

			ingreDeleteButton.setOnAction(e -> deleteIngre(ingrePane.getId()));
			ingreEditButton.setOnAction(e -> editIngre(ingrePane.getId(), ingrePane));

			
			ingredientVBox.getChildren().add(ingrePane);
			prepRootVBox.setLayoutY(600 + 88 * (addingIngredient.size() + 2) + 60);
			buttonVBox.setLayoutY(1200 + 88 * (addingIngredient.size() + 2) + 60);
			if (i == (addingIngredient.size() - 1))
			{
				ingrePane.getStyleClass().add("BorderRadiusDown");
			}
		}

	}

	public void addPrepStep()
	{
		if (prepStepTextField.getText().trim().equals(""))
		{
			try
			{
				BackMessageController.message = "Preparation steps should not be empty!";
				BackMessageController.messageType = 4;
				BackMessageController.stage = Main.subStage2;
				Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
				Scene scene = new Scene(root, 328, 223);
				Main.subStage2.setScene(scene);
				Main.subStage2.setResizable(false);
				Main.subStage2.show();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (prepStepTextField.getText().length() > maxLength)
		{

			try
			{
				BackMessageController.message = "Any text should not longer than 1024!";
				BackMessageController.messageType = 4;
				BackMessageController.stage = Main.subStage2;
				Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
				Scene scene = new Scene(root, 328, 223);
				Main.subStage2.setScene(scene);
				Main.subStage2.setResizable(false);
				Main.subStage2.show();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else
		{
			String addingPrep = new String(prepStepTextField.getText());
			addingPrepStep.add(addingPrep);
			System.out.println(addingPrep);
			loadAddingPrep();
			prepStepTextField.clear();
		}
	}

	public void deletePrep(String prepPaneNo)
	{
		String regEx = "[^0-9]";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(prepPaneNo);
		int prepRowNo = Integer.parseInt(matcher.replaceAll(""));
		addingPrepStep.remove(prepRowNo - 1);
		loadAddingPrep();
	}

	public void editPrep(String prepPaneNo, AnchorPane parentNode)
	{
		String regEx = "[^0-9]";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(prepPaneNo);
		int prepRowNo = Integer.parseInt(matcher.replaceAll(""));
		TextField prepTextField1 = new TextField(addingPrepStep.get(prepRowNo - 1));
		prepTextField1.setMinSize(922, 100);
		prepTextField1.setMaxSize(922, 100);
		prepTextField1.setLayoutX(14);
		prepTextField1.setLayoutY(10);
		prepTextField1.setFont(Font.font(12));
		prepTextField1.setAlignment(Pos.CENTER_LEFT);

		Button prepEditConfirmButton = new Button("confirm");
		prepEditConfirmButton.setMinSize(10, 6);
		prepEditConfirmButton.setLayoutX(958);
		prepEditConfirmButton.setLayoutY(25);
		prepEditConfirmButton.setAlignment(Pos.BOTTOM_RIGHT);

		prepEditConfirmButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				if (prepTextField1.getText().trim().equals(""))
				{
					try
					{
						BackMessageController.message = "Preparation steps should not be empty!";
						BackMessageController.messageType = 4;
						BackMessageController.stage = Main.subStage2;
						Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
						Scene scene = new Scene(root, 328, 223);
						Main.subStage2.setScene(scene);
						Main.subStage2.setResizable(false);
						Main.subStage2.show();

					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (prepTextField1.getText().length() > maxLength)
				{

					try
					{
						BackMessageController.message = "Any text should not longer than 1024!";
						BackMessageController.messageType = 4;
						BackMessageController.stage = Main.subStage2;
						Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
						Scene scene = new Scene(root, 328, 223);
						Main.subStage2.setScene(scene);
						Main.subStage2.setResizable(false);
						Main.subStage2.show();

					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				addingPrepStep.set(prepRowNo - 1, prepTextField1.getText());
				loadAddingPrep();
			}
		});
		parentNode.getChildren().clear();
		parentNode.getChildren().addAll(prepTextField1, prepEditConfirmButton);
	}

	public void loadAddingPrep()
	{
		int i = 0;
		prepStepVBox.getChildren().clear();
		for (; i < addingPrepStep.size(); i++)
		{
			AnchorPane prepStepPane = new AnchorPane();
			prepStepPane.setId("prepAP" + Integer.toString(i + 1));
			prepStepPane.getStyleClass().add("Border");
			prepStepPane.setMinSize(948, 88);
			prepStepPane.setMaxSize(948, 88);
			Label prepLabel = new Label(Integer.toString(i + 1) + ": " + addingPrepStep.get(i));
			prepLabel.setMinSize(922, 100);
			prepLabel.setMaxWidth(934);
			prepLabel.setWrapText(true);
			prepLabel.setLayoutX(14);
			prepLabel.setLayoutY(10);
			prepLabel.setWrapText(true);
			prepLabel.setTextFill(Color.rgb(75, 75, 173));
			prepLabel.setFont(Font.font(22));
			prepLabel.setAlignment(Pos.CENTER_LEFT);
			Button prepDeleteButton = new Button("delete");
			prepDeleteButton.setMinSize(10, 6);
			prepDeleteButton.setLayoutX(958);
			prepDeleteButton.setLayoutY(40);
			prepDeleteButton.setAlignment(Pos.BOTTOM_RIGHT);
			Button prepEditButton = new Button("edit");
			prepEditButton.setMinSize(10, 6);
			prepEditButton.setLayoutX(958);
			prepEditButton.setLayoutY(10);
			prepEditButton.setAlignment(Pos.BOTTOM_RIGHT);
			if (i == 0)
			{
				Button downButton = new Button("down");
				downButton.setMinSize(10, 6);
				downButton.setLayoutX(-48);
				downButton.setLayoutY(40);
				downButton.setAlignment(Pos.BOTTOM_RIGHT);
				prepStepPane.getChildren().addAll(prepLabel, prepDeleteButton, prepEditButton, downButton);
			}
			else if (i == (addingPrepStep.size()-1))
			{
			
				Button upButton = new Button("up");
				upButton.setMinSize(10, 6);
				upButton.setLayoutX(-34);
				upButton.setLayoutY(10);
				upButton.setAlignment(Pos.BOTTOM_RIGHT);
				prepStepPane.getChildren().addAll(prepLabel, prepDeleteButton, prepEditButton, upButton);
			}
			else
			{
				Button upButton = new Button("up");
				upButton.setMinSize(10, 6);
				upButton.setLayoutX(-34);
				upButton.setLayoutY(10);
				upButton.setAlignment(Pos.BOTTOM_RIGHT);
				prepStepPane.getChildren().addAll(prepLabel, prepDeleteButton, prepEditButton, upButton);
				Button downButton = new Button("down");
				downButton.setMinSize(10, 6);
				downButton.setLayoutX(-48);
				downButton.setLayoutY(40);
				downButton.setAlignment(Pos.BOTTOM_RIGHT);
				prepStepPane.getChildren().addAll(downButton);
			}
			
			
			
			prepStepVBox.getChildren().add(prepStepPane);
			buttonVBox.setLayoutY(prepRootVBox.getLayoutY() + 60 + 88 + 120 * (addingPrepStep.size() + 1));
			if (i == (addingPrepStep.size() - 1))
			{
				prepStepPane.getStyleClass().add("BorderRadiusDown");
			}
		}

	}

	public void uploadRecipe()
	{

		if (checkBasicInfo())
		{
			currentRecipe.setName(recipeTitleTextField.getText());
			currentRecipe.setCategary(categoryTextField.getText());
			currentRecipe.setServingPpl(Integer.parseInt(servingPplTextField.getText()));
			currentRecipe.setPreparationTime((int) Double.parseDouble(prepTimeTextField.getText()));
			currentRecipe.setCookingTime((int) Double.parseDouble(cookingTimeTextField.getText()));
			currentRecipe.setAccountID(MainController.currentUser.getAccountID());
			currentRecipe.setIngredients(addingIngredient);
			currentRecipe.setPreparationStep(addingPrepStep);
			CookBook cb = new CookBook(currentRecipe.getCategary());
			cb.add(currentRecipe);
			try
			{
				BackMessageController.messageType = 1;
				BackMessageController.stage = Main.subStage2;
				Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
				Scene scene = new Scene(root, 328, 223);
				Main.subStage2.setScene(scene);
				Main.subStage2.setResizable(false);
				Main.subStage2.show();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("Uploaded successfully");
			clearUp();
			backToProfile();
		}
	}

	public void updateRecipe()
	{
		if (checkBasicInfo())
		{
			CookBook cb = new CookBook(currentRecipe.getCategary());
			cb.updateRecipe(MainController.currentRecipe, currentRecipe);
			try
			{
				BackMessageController.messageType = 2;
				BackMessageController.stage = Main.subStage2;
				Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
				Scene scene = new Scene(root, 328, 223);
				Main.subStage2.setScene(scene);
				Main.subStage2.setResizable(false);
				Main.subStage2.show();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("Updated successfully");
			clearUp();
			backToProfile();

		}
	}

	public void backToProfile()
	{
		try
		{
			Parent root = FXMLLoader.load(getClass().getResource("/com/UI/view/ProfileView.fxml"));
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

	public void cancel()
	{

		try
		{
			BackMessageController.messageType = 3;
			BackMessageController.stage = Main.subStage2;
			Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
			Scene scene = new Scene(root, 328, 223);
			Main.subStage2.setScene(scene);
			Main.subStage2.setResizable(false);
			Main.subStage2.show();

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkBasicInfo()
	{
		Pattern pattern1 = Pattern.compile("^[0-9]+$");
		Matcher matcher1 = pattern1.matcher(servingPplTextField.getText());
		Pattern pattern2 = Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,2}$");
		Matcher matcher2 = pattern2.matcher(prepTimeTextField.getText());
		Matcher matcher3 = pattern2.matcher(cookingTimeTextField.getText());
		if (recipeTitleTextField.getText().trim().equals("") || categoryTextField.getText().trim().equals("")
				|| servingPplTextField.getText().trim().equals("") || prepTimeTextField.getText().trim().equals("")
				|| cookingTimeTextField.getText().trim().equals(""))
		{
			try
			{
				BackMessageController.message = "Basic information should not be empty";
				BackMessageController.messageType = 4;
				BackMessageController.stage = Main.subStage2;
				Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
				Scene scene = new Scene(root, 328, 223);
				Main.subStage2.setScene(scene);
				Main.subStage2.setResizable(false);
				Main.subStage2.show();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		} else if (!matcher1.matches())
		{
			try
			{
				BackMessageController.message = "Serving people number must be integer";
				BackMessageController.messageType = 4;
				BackMessageController.stage = Main.subStage2;
				Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
				Scene scene = new Scene(root, 328, 223);
				Main.subStage2.setScene(scene);
				Main.subStage2.setResizable(false);
				Main.subStage2.show();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		} else if (!matcher2.matches() || !matcher3.matches())
		{
			try
			{
				BackMessageController.message = "Illegal numbers for Preparation time or Cooking time";
				BackMessageController.messageType = 4;
				BackMessageController.stage = Main.subStage2;
				Parent root = FXMLLoader.load(getClass().getResource(BackMessageController.BackResourse));
				Scene scene = new Scene(root, 328, 223);
				Main.subStage2.setScene(scene);
				Main.subStage2.setResizable(false);
				Main.subStage2.show();

			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		} else
		{
			return true;
		}

	}

	public void clearUp()
	{
		recipeTitleTextField.clear();
		categoryTextField.clear();
		servingPplTextField.clear();
		prepTimeTextField.clear();
		cookingTimeTextField.clear();
	}
}