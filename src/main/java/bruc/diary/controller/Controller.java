package bruc.diary.controller;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import bruc.diary.Window.MoveableStage;
import bruc.diary.connectivity.localdatebase.DAO;
import bruc.diary.connectivity.nutritionix.APIConnection;
import bruc.diary.connectivity.server.ServerConnection;
import bruc.diary.entry.Entry;
import bruc.diary.entry.MealEntry;
import bruc.diary.entry.MeasurementEntry;
import bruc.diary.entry.TimeOfDay;
import bruc.diary.game.Game;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalTimeStringConverter;

public class Controller implements Initializable {

	private FXMLLoader loader;
	private APIConnection connect;
	private ObservableList<MealEntry> mealList = FXCollections.observableArrayList();
	private ObservableList<MeasurementEntry> selfList = FXCollections.observableArrayList();

	private ServerConnection connection;

	private DAO dao;

	private volatile Game game;

	private Entry entry;

	@FXML
	private BorderPane root;

	@FXML
	private TableColumn<DayOfWeek, String> dayCol;

	@FXML
	private DatePicker datePicker;

	@FXML
	private TableView<MeasurementEntry> selfTable;
	@FXML
	private TableView<MealEntry> mealTable;

	@FXML
	private TableColumn<MealEntry, String> productCol;
	@FXML
	private TableColumn<MealEntry, Double> caloriesCol;
	@FXML
	private TableColumn<MealEntry, Double> sugarsCol;
	@FXML
	private TableColumn<MealEntry, Double> fatsCol;

	@FXML
	private TableColumn<MeasurementEntry, LocalTime> bloodSugarTimeCol;
	@FXML
	private TableColumn<MeasurementEntry, Integer> bloodSugarCol;
	@FXML
	private TableColumn<MeasurementEntry, TimeOfDay> bloodTimeOfDayCol;
	@FXML
	private TableColumn<MeasurementEntry, LocalTime> mealTimeCol;
	@FXML
	private TableColumn<MeasurementEntry, String> mealCol;
	@FXML
	private TableColumn<MeasurementEntry, LocalTime> pressureTimeCol;
	@FXML
	private TableColumn<MeasurementEntry, String> pressureCol;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		datePicker.setValue(LocalDate.now());
		selfTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		selfTable.setPlaceholder(new Label("Please add a row"));
		prepareCells();
	}

	public void init(APIConnection connect, DAO dao) {
		this.connect = connect;
		this.dao = dao;
		loadEntry();
	}

	private void prepareCells() {
		resetTableItems();
		prepareValueFactories();
	}

	private void resetTableItems() {
		mealTable.setItems(mealList);
		selfTable.setItems(selfList);
	}

	@FXML
	private void prepareValueFactories() {
		productCol.setCellValueFactory(new PropertyValueFactory<>("product"));
		caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
		sugarsCol.setCellValueFactory(new PropertyValueFactory<>("sugars"));
		fatsCol.setCellValueFactory(new PropertyValueFactory<>("fats"));

		bloodSugarTimeCol.setCellValueFactory(new PropertyValueFactory<>("sugarMeasurementTime"));
		bloodSugarTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));
		bloodSugarTimeCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())
				.setSugarMeasurementTime(e.getNewValue()));

		bloodSugarCol.setCellValueFactory(new PropertyValueFactory<>("bloodSugarLevel"));
		bloodSugarCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		bloodSugarCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())
				.setBloodSugarLevel(e.getNewValue()));

		bloodTimeOfDayCol.setCellValueFactory(new PropertyValueFactory<>("timeOfDay"));
		bloodTimeOfDayCol.setCellFactory(ChoiceBoxTableCell.forTableColumn(TimeOfDay.values()));

		mealTimeCol.setCellValueFactory(new PropertyValueFactory<>("mealTime"));
		mealTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));
		mealTimeCol.setOnEditCommit(
				e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setMealTime(e.getNewValue()));

		mealCol.setCellValueFactory(new PropertyValueFactory<>("meal"));

		pressureTimeCol.setCellValueFactory(new PropertyValueFactory<>("pressureTime"));
		pressureTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter()));
		pressureTimeCol.setOnEditCommit(
				e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setPressureTime(e.getNewValue()));

		pressureCol.setCellValueFactory(new PropertyValueFactory<>("pressure"));
		pressureCol.setCellFactory(TextFieldTableCell.forTableColumn());
		pressureCol.setOnEditCommit(
				e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setPressure(e.getNewValue()));
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

			// controller.init(this, editStage, connect);

			editStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void save() {
		try {
			dao.addEntry(entry);

			System.out.println("LIST SIZE: " + selfList.size());

			selfList.forEach(a -> {
				try {
					a.setEntryId(entry.getId());
					dao.addOrUpdateMeasurementEntry(a);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			mealList.forEach(a -> {
				try {
					a.setEntryId(entry.getId());
					dao.addMeal(a);
				} catch (Exception e) {
					e.printStackTrace();
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void addMeasurementEntry() {
		MeasurementEntry measurementEntry = new MeasurementEntry(null, LocalTime.MIN, 0, TimeOfDay.AFTER_BREAKFAST,
				LocalTime.MIN, "", LocalTime.MIN, "", entry.getId());
		selfList.add(measurementEntry);
	}

	@FXML
	private void removeMeasurementEntry() {
		try {
			if (selfList.get(selfTable.getSelectionModel().getSelectedIndex()).getId() != null)
				dao.removeMeasurement(selfTable.getSelectionModel().getSelectedItem());
			selfList.remove(selfTable.getSelectionModel().getSelectedItem());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectDate() {
		try {
			loadEntry();
			resetTableItems();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadEntry() {
		LocalDate date = datePicker.getValue();

		try {
			entry = dao.getEntry(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
			System.out.println(entry);
			if (entry == null) {
				entry = createNewEntry();
				selfList.setAll();
				mealList.setAll();
			} else {
				selfList.setAll(dao.getDayMeasurements(entry));
				mealList.setAll(dao.getDayMeals(entry));
			}

			resetTableItems();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Entry createNewEntry() throws Exception {
		LocalDate date = datePicker.getValue();
		Integer nextId = dao.requestNextEntryId();
		Entry entry = new Entry(nextId, date);
		return entry;
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
	private void testConnection() {

		try {
			connection = new ServerConnection();
			connection.startConnection();
		} catch (UnknownHostException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Unknow host");
			alert.setHeaderText("UnknowHost Exception");
			alert.setContentText("Unable to connect");

			alert.showAndWait();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("IOException");
			alert.setHeaderText("An IO Exception has occured");
			alert.setContentText("Make sure server is online");

			alert.showAndWait();
		}

	}

	@FXML
	private void play() {
		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					datePicker.setRotate(datePicker.getRotate() + 4);
					mealTable.setRotate(mealTable.getRotate() - 5);
				});

			}

		});
		t.start();
	}

	@FXML
	private void fade() {
		while (root.getOpacity() > 0) {
			try {
				Thread.sleep(30);
				root.setOpacity(root.getOpacity() - 0.015);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	private void launchGame() {
		Thread t = new Thread(() -> {
			play();
			fade();
			game = new Game(root);
			game.start();
		});
		t.start();
	}

	@FXML
	private void exit() {
		save();
		System.exit(0);
	}

	@FXML
	private void deleteMeal() {
		try {
			if (mealTable.getItems().get(mealTable.getSelectionModel().getSelectedIndex()) != null)
				dao.removeMeal(mealTable.getItems().get(mealTable.getSelectionModel().getSelectedIndex()));
			mealTable.getItems().remove(mealTable.getSelectionModel().getSelectedIndex());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addSelectedMeal(MealEntry meal) {
		mealList.add(meal);
	}

	public Entry getEntry() {
		return entry;
	}

}
