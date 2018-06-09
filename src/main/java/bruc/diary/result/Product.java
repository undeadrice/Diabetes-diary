package bruc.diary.result;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

public class Product {

	@SerializedName("_id")
	private String id;
	private HashMap<String, Object> fields;

	public String getId() {
		return id;
	}

	public HashMap<String, Object> getFields() {
		return fields;
	}

}