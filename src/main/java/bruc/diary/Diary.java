package bruc.diary;

import bruc.diary.Window.MoveableStage;
import bruc.diary.connectivity.localdatebase.DAO;
import bruc.diary.connectivity.localdatebase.SqliteDAO;
import bruc.diary.connectivity.nutritionix.APIConnection;
import bruc.diary.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Diary extends Application {

	/*
	 * Nutritionix API credentials
	 */
	public static final String APPLICATION_ID = "b384ccf8";
	public static final String APPLICATION_KEY = "cc98da509a1cfa10cf5a66c0e7def3c5";
	////

	private FXMLLoader loader;

	private static DAO dao;
	private static APIConnection connect;

	public static void main(String[] args) throws Exception {
		
		connect = new APIConnection();
		dao = new SqliteDAO();
		launch(args);
		
		
	}

	@Override
	public void start(Stage stage) throws Exception {

		loader = new FXMLLoader(getClass().getResource("controller/mainView.fxml"));
		Parent root = loader.load();
		Controller controller = loader.getController();
		controller.init(connect, dao);

		Scene scene = new MoveableStage(root,stage);


		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();

	}

}
