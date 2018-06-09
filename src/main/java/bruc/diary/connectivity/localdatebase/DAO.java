package bruc.diary.connectivity.localdatebase;

import java.util.List;

import bruc.diary.entry.Entry;
import bruc.diary.entry.MealEntry;
import bruc.diary.entry.MeasurementEntry;

public interface DAO {
	/// meal entries

	public List<MealEntry> getDayMeals(Entry entry) throws Exception;

	public void removeMeal(MealEntry entry) throws Exception;

	public void addMeal(MealEntry entry) throws Exception;

	/// measurement entries
	public List<MeasurementEntry> getDayMeasurements(Entry entry) throws Exception;

	public void removeMeasurement(MeasurementEntry entry) throws Exception;

	public void addMeasurement(MeasurementEntry entry) throws Exception;
	
	public void addOrUpdateMeasurementEntry(MeasurementEntry entry) throws Exception;

	/// entries

	public List<Entry> getAllEntries() throws Exception;

	public Entry getEntry(Integer year, Integer month, Integer day) throws Exception;

	public void addEntry(Entry entry) throws Exception;

	public void addOrUpdateEntry(Entry entry) throws Exception;

	public Integer requestNextEntryId() throws Exception;

}
