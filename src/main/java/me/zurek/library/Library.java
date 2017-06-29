package me.zurek.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import me.zurek.library.entity.Book;
import me.zurek.library.entity.Person;
import me.zurek.library.utils.Utils;

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
	
	
	Map<Integer,Map<String,Map<String,Set<Book>>>> prepareDistinctBooksCollection() {
		Map<Integer,Map<String,Map<String,Set<Book>>>> allBooks = books.stream()
			.collect(Collectors.groupingBy((Book::getYear), 
							Collectors.groupingBy((Book::getAuthor),
									Collectors.groupingBy((Book::getTitle), 
											Collectors.toSet()))));
		
		return allBooks;
	}
	
	/**
	 * Prints to the standard output all books from the library distinctly
	 */
	public void listAllBooksDistinctly() {
		Map<Integer,Map<String,Map<String,Set<Book>>>> allBooks = prepareDistinctBooksCollection();
		
		String header = Utils.distinctBookToListResultRow("Year","Author","Title","Available","Lent");
		System.out.println(header);
		System.out.println(StringUtils.repeat('-', header.length()));
		
		for (Integer year : allBooks.keySet()) {
			for (String author : allBooks.get(year).keySet()) {
				for (String title : allBooks.get(year).get(author).keySet()) {
					int copies = allBooks.get(year).get(author).get(title).size();
					int lent = 0;
					for (Book b : allBooks.get(year).get(author).get(title)) {
						if (b.getLentTo() != null) ++lent;
					}
					System.out.println(Utils.distinctBookToListResultRow(year.toString(),author,title,Integer.toString(copies-lent),Integer.toString(lent)));
				}
			}
		}
	}
	
	
	
	
	/**
	 * Searches for the books and prints them to the standard output.
	 * You can search by all or only selected attributes. Pass null to the others.
	 * 
	 * @param title Title to search
	 * @param author Author to search
	 * @param year Year to search
	 * @return List of matching books
	 */
	public List<Book> searchBooks(String title, String author, Integer year) {
		Stream<Book> foundBooks = books.stream();
		
		if (year != null) {
			foundBooks = foundBooks.filter(b -> b.getYear().equals(year));
		}
		if (author != null) {
			foundBooks = foundBooks.filter(b -> b.getAuthor().equals(author));
		}
		if (title != null) {
			foundBooks = foundBooks.filter(b -> b.getTitle().equals(title));
		}
		
		
		String header = Utils.bookDetailsToSearchResultRow("ID","Title","Author","Year","Lent to");
		System.out.println();
		System.out.println(header);
		System.out.println(StringUtils.repeat('-', header.length()));
		return foundBooks.map(b -> {Utils.bookToSearchResultRowPrint(b); return b;}).collect(Collectors.toList());
	}
	
	/**
	 * Lending a book by ID for specified person.
	 * 
	 * @param id ID of book to lend
	 * @param name Name of the borrower
	 * @return If succeeded
	 */
	public boolean lendABook(int id, String name) {
		if (books.get(id).getLentTo() != null) {
			System.out.println("Book is already lent");
			return false;
		}
		books.get(id).setLentTo(new Person(name));
		return true;
	}
	
	/**
	 * Printing book's details (ID, title, author, year, if is available or who borrowed) to the standard output.
	 * 
	 * @param id ID of the book to see details
	 */
	public void seeDetails(int id) {
		Book b = books.get(id);
		String header = Utils.bookDetailsToSearchResultRow("ID","Title","Author","Year","Lent to / --- avail.");
		System.out.println();
		System.out.println(header);
		System.out.println(StringUtils.repeat('-', header.length()));
		Utils.bookToSearchResultRowPrint(b);
	}
}
