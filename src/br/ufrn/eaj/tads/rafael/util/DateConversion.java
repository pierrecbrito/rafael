package br.ufrn.eaj.tads.rafael.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConversion {
	public static Date convertToDate(LocalDate localDate) { 
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); 
	} 
	
	public static LocalDate convertToLocalDate(Date date) { 
		 if (date instanceof java.sql.Date) {
		        date = new Date(date.getTime());
		    }
		 return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
