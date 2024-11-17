package kr.co.dw.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {

	public static String makeDealYearMonth(int num) {
		LocalDate now = LocalDate.now();
		String yearMonth = now.minusMonths(num).format(DateTimeFormatter.ofPattern("yyyyMM"));
		return yearMonth;
	}
	public static List<String> makeDealYearMonthList(int num) {
	    List<String> dealYearMonthList = new ArrayList<>();
	    
	    for(int i = 0; i <= num; i++) {
	        String yearMonth = makeDealYearMonth(i);
	        dealYearMonthList.add(yearMonth);
	    }
	    
	    return dealYearMonthList;
	}
	
}
