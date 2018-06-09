package bruc.diary.entry;

import java.time.LocalTime;

public class MeasurementEntry {

	private Integer id;

	private LocalTime sugarMeasurementTime;
	private Integer bloodSugarLevel;
	private TimeOfDay timeOfDay;

	private LocalTime mealTime;
	private String meal;

	private LocalTime pressureTime;
	private String pressure;

	private Integer entryId;

	public MeasurementEntry(Integer id, LocalTime sugarMeasurementTime, Integer bloodSugarLevel, TimeOfDay timeOfDay,
			LocalTime mealTime, String meal, LocalTime pressureTime, String pressure, Integer entryId) {
		super();
		this.id = id;
		this.sugarMeasurementTime = sugarMeasurementTime;
		this.bloodSugarLevel = bloodSugarLevel;
		this.timeOfDay = timeOfDay;
		this.mealTime = mealTime;
		this.meal = meal;
		this.pressureTime = pressureTime;
		this.pressure = pressure;
		this.entryId = entryId;

	}

	public LocalTime getSugarMeasurementTime() {
		return sugarMeasurementTime;
	}

	public Integer getBloodSugarLevel() {
		return bloodSugarLevel;
	}

	public TimeOfDay getTimeOfDay() {
		return timeOfDay;
	}

	public LocalTime getMealTime() {
		return mealTime;
	}

	public String getMeal() {
		return meal;
	}

	public LocalTime getPressureTime() {
		return pressureTime;
	}

	public String getPressure() {
		return pressure;
	}

	public Integer getId() {
		return id;
	}

	public Integer getEntryId() {
		return entryId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSugarMeasurementTime(LocalTime sugarMeasurementTime) {
		this.sugarMeasurementTime = sugarMeasurementTime;
	}

	public void setBloodSugarLevel(Integer bloodSugarLevel) {
		this.bloodSugarLevel = bloodSugarLevel;
	}

	public void setTimeOfDay(TimeOfDay timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public void setMealTime(LocalTime mealTime) {
		this.mealTime = mealTime;
	}

	public void setMeal(String meal) {
		this.meal = meal;
	}

	public void setPressureTime(LocalTime pressureTime) {
		this.pressureTime = pressureTime;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

}
