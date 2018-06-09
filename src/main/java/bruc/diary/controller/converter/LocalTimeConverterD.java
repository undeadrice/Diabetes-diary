package bruc.diary.controller.converter;

import java.time.LocalTime;

import javafx.util.StringConverter;

public class LocalTimeConverterD extends StringConverter<LocalTime> {

	@Override
	public LocalTime fromString(String str) {
		String hour = str.substring(0, 2);
		String minute = str.substring(3, 5);
		LocalTime time = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));
		return time;
	}

	@Override
	public String toString(LocalTime time) {
		String str = time.getHour() + ":" + time.getMinute();
		return str;
	}
	
}
