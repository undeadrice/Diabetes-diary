package bruc.diary.controller;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

import bruc.diary.connectivity.APIConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller implements Initializable {

	private FXMLLoader loader;
	private APIConnection connect;
	private ObservableList<Map<String, Object>> list = FXCollections.observableArrayList();

	
	@FXML
	private TableColumn<DayOfWeek, String> dayCol;
	
	@FXML
	private DatePicker datePicker;

	@FXML
	private TableView<Map<String, Object>> selfTable;
	@FXML
	private TableView<Map<String, Object>> mealTable;

	@FXML
	private TableColumn<Map<String, Object>, Object> productCol;
	@FXML
	private TableColumn<Map<String, Object>, Object> caloriesCol;
	@FXML
	private TableColumn<Map<String, Object>, Object> sugarsCol;
	@FXML
	private TableColumn<Map<String, Object>, Object> fatsCol;

	@FXML
	private Button addEntryBtn;
	@FXML
	private Button editEntryBtn;
	@FXML
	private Button deleteEntryBtn;

	@FXML
	private Button addMealBtn;
	@FXML
	private Button removeMealBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<DayOfWeek> days = FXCollections.observableArrayList(DayOfWeek.values());
		
		mealTable.setItems(list);
		productCol.setCellValueFactory(new MapValueFactory("item_name"));
		caloriesCol.setCellValueFactory(new MapValueFactory("nf_calories"));
		sugarsCol.setCellValueFactory(new MapValueFactory("nf_sugars"));
		fatsCol.setCellValueFactory(new MapValueFactory("nf_total_fat"));

	}
	
	@FXML
	private void editEntry() {
		Stage editStage = new Stage();
		loader = new FXMLLoader(getClass().getResource("editWindow.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			editStage.setScene(scene);
			editStage.initModality(Modality.APPLICATION_MODAL);

			EditController controller = loader.getController();
		//	controller.init(this, editStage, connect);

			editStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
 
	public void init(APIConnection connect) {
		this.connect = connect;
	}
	
	public void selectDate() {
		LocalDate date = datePicker.getValue();
		DayOfWeek dow = date.getDayOfWeek();
		System.out.println(dow.getValue());
		
	}

	@FXML
	private void addMeal() {
		Stage searchStage = new Stage();
		loader = new FXMLLoader(getClass().getResource("searchScene.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			searchStage.setScene(scene);
			searchStage.initModality(Modality.APPLICATION_MODAL);

			SearchController controller = loader.getController();
			controller.init(this, searchStage, connect);

			searchStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void deleteMeal() {
		mealTable.getItems().remove(mealTable.getSelectionModel().getSelectedIndex());
	}

	public void addSelectedMeal(Map<String, Object> fields) {
		list.add(fields);
	}

}
