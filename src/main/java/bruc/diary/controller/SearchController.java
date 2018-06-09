package bruc.diary.controller;

import java.io.IOException;
import java.util.Map;

import com.jfoenix.controls.JFXSpinner;

import bruc.diary.connectivity.nutritionix.APIConnection;
import bruc.diary.entry.MealEntry;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchController {

	/**
	 * main stage controller
	 */
	private Controller controller;
	/**
	 * stage of this controller
	 */
	private Stage stage;

	private APIConnection connect;
	private ObservableList<MealEntry> list;

	@FXML
	private JFXSpinner spinner;
	@FXML
	private TextField productFld;
	@FXML
	private Button searchBtn;
	@FXML
	private Button addBtn;
	@FXML
	private TableView<MealEntry> productTable;
	@FXML
	private TableColumn<MealEntry, String> productCol;
	@FXML
	private TableColumn<MealEntry, Double> caloriesCol;
	@FXML
	private TableColumn<MealEntry, Double> sugarsCol;
	@FXML
	private TableColumn<MealEntry, Double> fatsCol;

	public void init(Controller controller, Stage stage, APIConnection connect) {
		this.controller = controller;
		this.stage = stage;
		this.connect = connect;
	}

	@FXML
	private void search() {
		String query = productFld.getText();
		spinner.setVisible(true);
		try {
			list = connect.getMealEntries(query);
			productTable.setItems(list);
			productCol.setCellValueFactory(new PropertyValueFactory("product"));
			caloriesCol.setCellValueFactory(new PropertyValueFactory("calories"));
			sugarsCol.setCellValueFactory(new PropertyValueFactory("sugars"));
			fatsCol.setCellValueFactory(new PropertyValueFactory("fats"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void addSelectedProduct() {
	
		controller.addSelectedMeal(productTable.getSelectionModel().getSelectedItem());
		
		this.stage.close();

	}

}
