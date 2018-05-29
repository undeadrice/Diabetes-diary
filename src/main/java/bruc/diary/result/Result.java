package bruc.diary.result;

import java.util.List;

public class Result {

	private int total_hits;
	private double max_score;
	private List<Product> hits;

	public int getTotal_hits() {
		return total_hits;
	}

	public double getMax_score() {
		return max_score;
	}

	public List<Product> getHits() {
		return hits;
	}

}