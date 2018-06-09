package bruc.diary.entry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Entry {
	private final Integer id;
	private final LocalDate date;
	private final List<MealEntry> meals = new ArrayList<>();
	private final List<MeasurementEntry> measurements = new ArrayList<>();

	public Entry(Integer id, LocalDate date) {
		this.id = id;
		this.date = date;

	}

	public LocalDate getDate() {
		return date;
	}

	public List<MealEntry> getMeals() {
		return meals;
	}

	public List<MeasurementEntry> getMeasurements() {
		return measurements;

	}

	public Integer getId() {
		return id;
	}

}
