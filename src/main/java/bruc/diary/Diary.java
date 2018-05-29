package bruc.diary;

import bruc.diary.connectivity.APIConnection;
import bruc.diary.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Diary extends Application {

	/*
	 * Nutritionix API credentials
	 */
	public static final String APPLICATION_ID = "b384ccf8";
	public static final String APPLICATION_KEY = "cc98da509a1cfa10cf5a66c0e7def3c5";
	////

	private FXMLLoader loader;

	private static APIConnection connect;

	public static void main(String[] args) {
		connect = new APIConnection();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		loader = new FXMLLoader(getClass().getResource("controller/mainWindow2.fxml"));
		Parent root = loader.load();
		Controller controller = loader.getController();
		controller.init(connect);

		Scene scene = new Scene(root);

		stage.setTitle("Dziennik cukrzyka - wersja Pro - Twoja licencja wygasa za 3 dni");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}

}
