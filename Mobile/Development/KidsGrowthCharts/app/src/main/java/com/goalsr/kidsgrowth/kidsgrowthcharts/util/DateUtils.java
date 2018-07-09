package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

/**
 * 
 */


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Goalsr
 *
 */
public class DateUtils {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
	
	/**
	 * Date Format Constants
	 */
	public static final String DATE_MMM_DD_YYYY_12_HOURS = "MMM dd, yyyy HH:mm:ss a";
	public static final String DATE_MMM_DD_YYYY_24_HOURS = "MMM dd, yyyy HH:mm";
	public static final String DATE_MMM_DD_YYYY = "MMM dd, yyyy";
	public static final String DATE_MM_DD_YYYY_FRONT_SLASH = "MM/dd/yyyy";
	public static final String DATE_MM_DD_YYYY_BACK_SLASH = "MM\\dd\\yyyy";
	public static final String DATE_DD_MM_YYYY_FRONT_SLASH = "dd/MM/yyyy";
	public static final String DATE_DD_MM_YYYY_BACK_SLASH = "dd\\MM\\yyyy";
	public static final String DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String DATE_YYY_MM_DD_HIPHEN = "yyyy-MM-dd";


	/**
	 * @param format
	 * @param date
	 * @return <pre>Date in string form</pre>
	 */
	public static String dateToString(String format, Date date) {

		//LOGGER.info("################ End DateUtils.dateToString()");
		String returnDate = "";

		if(format != null && date != null){
			try {
				DateFormat dateFormat = new SimpleDateFormat(format);	
				returnDate = dateFormat.format(date);
			} catch (Exception e) {
				//LOGGER.info("################ Exception @ DateUtils.dateToString(): "+e.getMessage());
			}
		}

		//LOGGER.info("################ End DateUtils.dateToString()");
		return returnDate;
	}

	/**
	 * @param format
	 * @param stringDate
	 * @return <pre>Date object</pre>
	 */
	public static Date stringToDate(String format, String stringDate) {
		//LOGGER.info("################ START DateUtils.stringToDate()");

		//LOGGER.info("################ format: "+format+" : stringDate: "+stringDate);
		Date date = null;
		try {
			if(format != null && stringDate != null){
				DateFormat df = new SimpleDateFormat(format);
				date = df.parse(stringDate);

				//LOGGER.info("################ date: "+date.toString());
			}
		} catch (Exception e) {
			//LOGGER.info("################ Exception @ nd DateUtils.stringToDate(): "+e.getMessage());
		}
		//LOGGER.info("################ End DateUtils.stringToDate()");
		return date;
	}
}
