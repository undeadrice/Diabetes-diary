package bruc.diary.result;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Result {

	@SerializedName("total_hits")
	private int totalHits;
	@SerializedName("max_score")
	private double maxScore;
	private List<Product> hits;

	public int getTotalHits() {
		return totalHits;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public List<Product> getHits() {
		return hits;
	}

}