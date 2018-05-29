package bruc.diary.connectivity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import bruc.diary.Diary;
import bruc.diary.result.Product;
import bruc.diary.result.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class APIConnection {

	private Gson gson = new Gson();
	private URLConnect connect = new URLConnect(Diary.APPLICATION_ID, Diary.APPLICATION_KEY);
	private InputStreamReader inr;

	public APIConnection() {

	}
	/**
	 * connects to Nutritionix database and parses the received JSON
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public Result getResult(String query) throws IOException {
		inr = new InputStreamReader(connect.query(query));
		Result result = gson.fromJson(inr, Result.class);
		return result;
	}

	/**
	 * connects to Nutritionix database and parses the received JSON. Returns fields of all the hits in form of a List
	 * @param query
	 * @return
	 * @throws IOException
	 */
	public ObservableList<Map<String, Object>> getFieldsAsObservableList(String query) throws IOException {
		Result result = getResult(query);
		List<Map<String, Object>> list = new ArrayList<>();

		for (Product p : result.getHits()) {
			list.add(p.getFields());
		}

		ObservableList<Map<String, Object>> observableList = FXCollections.observableArrayList(list);
		return observableList;
	}

}
