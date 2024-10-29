package kr.co.dw.Utils;

public class DataUtils {

	public static String makeEngilshMonth(String DEAL_YMD) {

		String month = DEAL_YMD.substring(4, 6);
		int numMonth = Integer.parseInt(month);
		if (numMonth > 12) {
			numMonth = numMonth - 12;
			month = String.valueOf(numMonth);
		}
		String EnglishMonth = null;
		if (month.equals("01")) {
			EnglishMonth = "January";
		} else if (month.equals("02")) {
			EnglishMonth = "February";
		} else if (month.equals("03")) {
			EnglishMonth = "March";
		} else if (month.equals("04")) {
			EnglishMonth = "April";
		} else if (month.equals("05")) {
			EnglishMonth = "May";
		} else if (month.equals("06")) {
			EnglishMonth = "June";
		} else if (month.equals("07")) {
			EnglishMonth = "July";
		} else if (month.equals("08")) {
			EnglishMonth = "August";
		} else if (month.equals("09")) {
			EnglishMonth = "September";
		} else if (month.equals("10")) {
			EnglishMonth = "October";
		} else if (month.equals("11")) {
			EnglishMonth = "November";
		} else if (month.equals("12")) {
			EnglishMonth = "December";
		}
		return EnglishMonth;
	}

}
