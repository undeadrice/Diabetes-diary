package bruc.diary.controller;

import java.io.IOException;
import java.util.Map;

import com.jfoenix.controls.JFXSpinner;

import bruc.diary.connectivity.APIConnection;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
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
	private ObservableList<Map<String, Object>> list;

	@FXML
	private JFXSpinner spinner;
	@FXML
	private TextField productFld;
	@FXML
	private Button searchBtn;
	@FXML
	private Button addBtn;
	@FXML
	private TableView<Map<String, Object>> productTable;
	@FXML
	private TableColumn<Map<String, Object>, Object> productCol;
	@FXML
	private TableColumn<Map<String, Object>, Object> caloriesCol;
	@FXML
	private TableColumn<Map<String, Object>, Object> sugarsCol;
	@FXML
	private TableColumn<Map<String, Object>, Object> fatsCol;

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
			list = connect.getFieldsAsObservableList(query);
			productTable.setItems(list);
			productCol.setCellValueFactory(new MapValueFactory("item_name"));
			caloriesCol.setCellValueFactory(new MapValueFactory("nf_calories"));
			sugarsCol.setCellValueFactory(new MapValueFactory("nf_sugars"));
			fatsCol.setCellValueFactory(new MapValueFactory("nf_total_fat"));
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
