package bruc.diary.connectivity.localdatebase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import bruc.diary.Diary;
import bruc.diary.entry.Entry;
import bruc.diary.entry.MealEntry;
import bruc.diary.entry.MeasurementEntry;
import bruc.diary.entry.TimeOfDay;

public class SqliteDAO implements DAO {

	public static final String DRIVER = "jdbc:sqlite:";

	private Connection connection;

	public SqliteDAO() throws SQLException {
		connection = DriverManager.getConnection(DRIVER + "Diary.db");

	}

	@Override
	public List<MealEntry> getDayMeals(Entry entry) throws Exception {
		ArrayList<MealEntry> list = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM meal_entry where entry_id = ?")) {
			ps.setInt(1, entry.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new MealEntry(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5),
						rs.getInt(6)));
			}
		}
		return list;

	}

	@Override
	public void removeMeal(MealEntry entry) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement("delete from meal_entry where id = ?")) {
			ps.setInt(1, entry.getId());
			ps.execute();
		} catch (Exception e) {

		}

	}

	@Override
	public void addMeal(MealEntry entry) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO meal_entry(id,product,calories,sugars,fats,entry_id)" + "select ?,?,?,?,?,? WHERE NOT EXISTS(select id from meal_entry where id = ?)",
				Statement.RETURN_GENERATED_KEYS)) {

			ps.setObject(1, entry.getId());
			ps.setString(2, entry.getProduct());
			ps.setDouble(3, entry.getCalories());
			ps.setDouble(4, entry.getSugars());
			ps.setDouble(5, entry.getFats());
			ps.setInt(6, entry.getEntryId());
			ps.setObject(7, entry.getId());
			ps.execute();

			System.out.println(entry.getId() == null);
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next() && entry.getId() == null) {
				entry.setId(rs.getInt(1));
			}

		}

	}

	// Measurements

	@Override
	public List<MeasurementEntry> getDayMeasurements(Entry entry) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement("select * from measurement_entry where entry_id = ?")) {
			List<MeasurementEntry> list = new ArrayList<>();
			ps.setInt(1, entry.getId());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				LocalTime sugarTime = LocalTime.of(rs.getInt(2), rs.getInt(3));
				LocalTime mealTime = LocalTime.of(rs.getInt(6), rs.getInt(7));
				LocalTime pressureTime = LocalTime.of(rs.getInt(9), rs.getInt(10));
				MeasurementEntry mentry = new MeasurementEntry(rs.getInt(1), sugarTime, rs.getInt(4),
						TimeOfDay.values()[rs.getInt(5)], mealTime, rs.getString(8), pressureTime, rs.getString(11),
						rs.getInt(12));

				list.add(mentry);
				System.out.println(mentry.getId());
			}

			return list;
		}

	}

	@Override
	public void removeMeasurement(MeasurementEntry entry) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement("delete from measurement_entry where id = ?")) {
			ps.setInt(1, entry.getId());
			ps.execute();
		}
	}

	@Override
	public void addMeasurement(MeasurementEntry entry) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO measurement_entry(id,blood_sugar_hour,blood_sugar_minute,blood_sugar_level,"
						+ "time_of_day,meal_hour,meal_minute,meal,pressure_hour,pressure_minute,pressure,entry_id)"
						+ "select ?,?,?,?,?,?,?,?,?,?,?,?"
						+ "WHERE (select changes() = 0) AND NOT EXISTS(SELECT ID FROM measurement_entry WHERE ID = ?)",
				Statement.RETURN_GENERATED_KEYS)) {

			ps.setObject(1, entry.getId());
			ps.setInt(2, entry.getSugarMeasurementTime().getHour());
			ps.setInt(3, entry.getSugarMeasurementTime().getMinute());
			ps.setInt(4, entry.getBloodSugarLevel());
			ps.setInt(5, entry.getTimeOfDay().ordinal());
			ps.setInt(6, entry.getMealTime().getHour());
			ps.setInt(7, entry.getMealTime().getMinute());
			ps.setString(8, entry.getMeal());
			ps.setInt(9, entry.getPressureTime().getHour());
			ps.setInt(10, entry.getPressureTime().getMinute());
			ps.setString(11, entry.getPressure());
			ps.setInt(12, entry.getEntryId());
			ps.setObject(13, entry.getId());

			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				entry.setId(rs.getInt(1));
			}

		}

	}

	@Override
	public List<Entry> getAllEntries() throws Exception {
		ArrayList<Entry> list = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM entry")) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LocalDate date = LocalDate.of(rs.getInt(2), rs.getInt(3), rs.getInt(4));
				Entry entry = new Entry(rs.getInt(1), date);
				list.add(entry);
			}
		}

		return list;
	}

	@Override
	public Entry getEntry(Integer year, Integer month, Integer day) throws Exception {
		try (PreparedStatement ps = connection
				.prepareStatement("SELECT * FROM entry WHERE year = ? AND month = ? AND day = ?")) {
			ps.setInt(1, year);
			ps.setInt(2, month);
			ps.setInt(3, day);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				LocalDate date = LocalDate.of(rs.getInt(2), rs.getInt(3), rs.getInt(4));
				Entry entry = new Entry(rs.getInt(1), date);
				return entry;
			}

		}

		return null;
	}

	@Override
	public void addEntry(Entry entry) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement("INSERT INTO entry(id,year,month,day) select ?,?,?,?"
				+ "WHERE NOT EXISTS(SELECT id FROM entry where id = ?)")) {

			ps.setInt(1, entry.getId());
			ps.setInt(2, entry.getDate().getYear());
			ps.setInt(3, entry.getDate().getMonth().getValue());
			ps.setInt(4, entry.getDate().getDayOfMonth());
			ps.setInt(5, entry.getId());
			ps.execute();
		}

	}

	@Override
	public Integer requestNextEntryId() throws Exception {
		try (PreparedStatement ps = connection
				.prepareStatement("SELECT * FROM sqlite_sequence WHERE name = \"entry\"")) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(2) + 1;
			}
			return null;
		}

	}

	@Override
	public void addOrUpdateEntry(Entry entry) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void addOrUpdateMeasurementEntry(MeasurementEntry entry) throws Exception {

		try (PreparedStatement ps = connection
				.prepareStatement("UPDATE measurement_entry set blood_sugar_hour = ? , blood_sugar_minute = ? , "
						+ "blood_sugar_level = ? , time_of_day = ? , meal_hour = ? , meal_minute = ? , meal = ? , pressure_hour = ? , pressure_minute = ?"
						+ ", pressure = ? , entry_id = ? WHERE id = ?;")) {

			// update
			ps.setInt(1, entry.getSugarMeasurementTime().getHour());
			ps.setInt(2, entry.getSugarMeasurementTime().getMinute());
			ps.setInt(3, entry.getBloodSugarLevel());
			ps.setInt(4, entry.getTimeOfDay().ordinal());
			ps.setInt(5, entry.getMealTime().getHour());
			ps.setInt(6, entry.getMealTime().getMinute());
			ps.setString(7, entry.getMeal());
			ps.setInt(8, entry.getPressureTime().getHour());
			ps.setInt(9, entry.getPressureTime().getMinute());
			ps.setString(10, entry.getPressure());
			ps.setInt(11, entry.getEntryId());
			// update where
			ps.setObject(12, entry.getId());

			System.out.println("updating mes id: " + entry.getId());
			System.out.println("rows affected: " + ps.executeUpdate());

		}
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO measurement_entry(id,blood_sugar_hour,blood_sugar_minute,blood_sugar_level,"
						+ "time_of_day,meal_hour,meal_minute,meal,pressure_hour,pressure_minute,pressure,entry_id)"
						+ "SELECT ?,?,?,?,?,?,?,?,?,?,?,?"
						+ "WHERE (SELECT changes() = 0) AND NOT EXISTS(SELECT ID FROM measurement_entry WHERE ID = ?)",
				Statement.RETURN_GENERATED_KEYS)) {

			// insert
			ps.setObject(1, entry.getId());
			ps.setInt(2, entry.getSugarMeasurementTime().getHour());
			ps.setInt(3, entry.getSugarMeasurementTime().getMinute());
			ps.setInt(4, entry.getBloodSugarLevel());
			ps.setInt(5, entry.getTimeOfDay().ordinal());
			ps.setInt(6, entry.getMealTime().getHour());
			ps.setInt(7, entry.getMealTime().getMinute());
			ps.setString(8, entry.getMeal());
			ps.setInt(9, entry.getPressureTime().getHour());
			ps.setInt(10, entry.getPressureTime().getMinute());
			ps.setString(11, entry.getPressure());
			ps.setInt(12, entry.getEntryId());
			// select where
			ps.setObject(13, entry.getId());
			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				if (entry.getId() == null)
					entry.setId(rs.getInt(1));
			}

		}

	}

}
