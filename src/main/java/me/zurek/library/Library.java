package me.zurek.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import me.zurek.library.entity.Book;
import me.zurek.library.entity.Person;

public class Library {
	
	final int HEADER_INTERVAL = 20; //print header while listing all books every HEADER_INTERVAL records
	
	private final List<Book> books = new ArrayList<Book>();
	
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * Adds a new book to the library
	 * 
	 * @param newBook Book to be added
	 * @return New book's ID
	 */
	public long addNewBook(Book newBook) {
		books.add(newBook);
		Integer newID = books.indexOf(newBook);
		newBook.setID(newID); //expensive, but enables fast access by index, when someome would knows book's index after searching
		return newID;
	}
	
	/**
	 * Removes the book from library
	 * 
	 * @param id ID of book to be removed
	 * @return If succeded at removal
	 */
	public boolean removeBook(Integer id) {
		try {
		if (books.get(id).getID().equals(id)) {
			if (books.get(id).getLentTo() != null) {
				System.out.println("Book is already lent");
				return false;
			}
			books.remove(id.intValue());
			renumberIndices(id);
			return true;
		} else {
			System.out.println("Problem with removing - ID inside the object doesn't equal ID in ArrayList (internal library error)");
			return false;
		}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Problem with removing - ID out of range. Please pass ID in range 0 - " + (books.size() - 1));
			e.printStackTrace();
			return false;
		}
	}
	
	
	//It was assumed that remove operation is very rare, so it is important to have fast access to books by ID, thanks to ArrayList, and tolerate this expensive operation while books removal
	private void renumberIndices(Integer startID) {
		int booksSize = books.size();
		for (int i = startID; i < booksSize; ++i) {
			books.get(i).setID(i);
		}
	}
	
	
	Map<Integer,Map<String,Map<String,Set<Book>>>> prepareDistinctBooksList() {
		Map<Integer,Map<String,Map<String,Set<Book>>>> allBooks = books.stream()
			.collect(Collectors.groupingBy((Book::getYear), 
							Collectors.groupingBy((Book::getAuthor),
									Collectors.groupingBy((Book::getTitle), 
											Collectors.toSet()))));
		
		return allBooks;
	}
	
	public void listAllBooksDistinctly() {
		Map<Integer,Map<String,Map<String,Set<Book>>>> allBooks = prepareDistinctBooksList();
		
		String header = formatRecordToPrint("Year","Author","Title","Available","Lent");
		int lineLength = header.length();
		System.out.println(header);
		System.out.println(StringUtils.repeat('-', lineLength));
		int headerCounter = 1;
		
		for (Integer year : allBooks.keySet()) {
			for (String author : allBooks.get(year).keySet()) {
				for (String title : allBooks.get(year).get(author).keySet()) {
					int copies = allBooks.get(year).get(author).get(title).size();
					int lent = 0;
					for (Book b : allBooks.get(year).get(author).get(title)) {
						if (b.getLentTo() != null) ++lent;
					}
					System.out.println(formatRecordToPrint(year.toString(),author,title,Integer.toString(copies-lent),Integer.toString(lent)));
					
					printHeaderIfNeeded(headerCounter, header, lineLength);
				}
			}
		}
	}
	
	private String fixLenStr(String string, int length) {
	    return String.format("%1$-"+length+ "s", StringUtils.abbreviate(string,length));
	}
	
	private String formatRecordToPrint(String year, String author, String title, String available, String lent) {
		return " " + fixLenStr(year,8) + "| " + fixLenStr(author,30) + "| " + fixLenStr(title,30) + "| " + fixLenStr(available,10) + "| " + fixLenStr(lent,10);
	}
	
	private void printHeaderIfNeeded(int headerCounter, String header, int lineLength) {
		++headerCounter;
		if (headerCounter == HEADER_INTERVAL) {
			System.out.println();
			System.out.println(header);
			System.out.println(StringUtils.repeat('-', lineLength));
			headerCounter = 1;
		}
	}
	

}
