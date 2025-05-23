package org.openmrs.eip.dbsync.utils;

import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class DateUtils {
	
	private DateUtils() {
	}
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static String dateToString(final LocalDateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		return dateTime.format(FORMATTER);
	}
	
	public static LocalDateTime stringToDate(final String dateAsString) {
		if (dateAsString == null) {
			return null;
		}
		return LocalDateTime.parse(dateAsString, FORMATTER);
	}
	
	/**
	 * Serializes the specified {@link LocalDateTime} instance to a string
	 *
	 * @param dateTime date to serialize
	 * @return string
	 */
	public static String serialize(LocalDateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		
		return dateTime.atZone(ZoneId.systemDefault()).format(ISO_OFFSET_DATE_TIME);
	}
	
	/**
	 * Parses the specified date string to {@link LocalDateTime}
	 * 
	 * @param date date string to be parsed
	 * @return {@link LocalDateTime} instance
	 */
	public static LocalDateTime parse(String date) {
		if (date == null) {
			return null;
		}
		
		return ZonedDateTime.parse(date, ISO_OFFSET_DATE_TIME).withZoneSameInstant(systemDefault()).toLocalDateTime();
	}
	
	/**
	 * Checks the dates in the first collection argument to those in the second to determine the
	 * collection containing the latest date, null being considered to be the earliest value.
	 *
	 * @param dates1 Collection of dates
	 * @param dates2 Collection of dates
	 * @return returns true ONLY if dates1 contains the latest non null date instance otherwise false
	 */
	public static boolean containsLatestDate(Collection<LocalDateTime> dates1, Collection<LocalDateTime> dates2) {
		//The algorithm is:
		//1: For each collection remove nulls first
		//2: Sort the dates and get the last item because that would be the latest in the collection
		//3: Compare the 2 latest dates from each collection to determine the latest between them
		List<LocalDateTime> sorted1 = dates1.stream().filter(d -> d != null).sorted().collect(Collectors.toList());
		List<LocalDateTime> sorted2 = dates2.stream().filter(d -> d != null).sorted().collect(Collectors.toList());
		LocalDateTime latestDateFromColl1 = sorted1.isEmpty() ? null : sorted1.get(sorted1.size() - 1);
		LocalDateTime latestDateFromColl2 = sorted2.isEmpty() ? null : sorted2.get(sorted2.size() - 1);
		if (latestDateFromColl1 == null) {
			return false;
		}
		
		return latestDateFromColl2 == null || latestDateFromColl1.isAfter(latestDateFromColl2);
	}
	
}
