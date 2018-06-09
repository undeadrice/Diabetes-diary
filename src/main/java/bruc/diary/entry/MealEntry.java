package bruc.diary.entry;

import java.util.Map;

import bruc.diary.result.Product;

public class MealEntry {

	private Integer id;
	private String product;
	private Double calories;
	private Double sugars;
	private Double fats;
	private Integer entryId;

	public MealEntry(Integer id, String product, Double calories, Double sugars, Double fats, Integer entryId) {
		super();
		this.product = product;
		this.calories = calories;
		this.sugars = sugars;
		this.fats = fats;
		this.id = id;
		this.entryId = entryId;
	}

	public MealEntry(Product p) {
		Map<String, Object> fields = p.getFields();

		this.product = (String) fields.get("item_name");
		this.calories = (Double) fields.get("nf_calories");
		this.sugars = (Double) fields.get("nf_sugars");
		this.fats = (Double) fields.get("nf_total_fat");

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Double getCalories() {
		return calories;
	}

	public void setCalories(Double calories) {
		this.calories = calories;
	}

	public Double getSugars() {
		return sugars;
	}

	public void setSugars(Double sugars) {
		this.sugars = sugars;
	}

	public Double getFats() {
		return fats;
	}

	public void setFats(Double fats) {
		this.fats = fats;
	}

}
