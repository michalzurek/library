package me.zurek.library.utils;

import org.apache.commons.lang3.StringUtils;

import me.zurek.library.entity.Book;

public class Utils {
	final static int NAME_MAX_LENGTH_SEARCH = 20;
	private static String fixLenStr(String string, int length) {
	    return String.format("%1$-"+length+ "s", StringUtils.abbreviate(string,length));
	}
	
	public static String distinctBookToListResultRow(String year, String author, String title, String available, String lent) {
		return " " + fixLenStr(year,8) + "| " + fixLenStr(author,30) + "| " + fixLenStr(title,30) + "| " + fixLenStr(available,10) + "| " + fixLenStr(lent,10);
	}
	
	public static void bookToSearchResultRowPrint(Book b) {
		String lentTo;
		if (b.getLentTo() == null || b.getLentTo().getName() == null) lentTo = StringUtils.repeat('-', NAME_MAX_LENGTH_SEARCH);
		else lentTo = b.getLentTo().getName();
		System.out.println(bookDetailsToSearchResultRow(b.getID().toString(),b.getTitle(),b.getAuthor(),b.getYear().toString(),lentTo));
	}
	
	public static String bookDetailsToSearchResultRow(String ID, String title, String author, String year, String name) {
		return " " + fixLenStr(ID,10) + "| " + fixLenStr(title,30) + "| " + fixLenStr(author,30) + "| " + fixLenStr(year,10) + "| " + fixLenStr(name,NAME_MAX_LENGTH_SEARCH);
	}
	
	
}
