package me.zurek.library;

import java.util.ArrayList;
import java.util.List;

import me.zurek.library.entity.Book;

public class Library {
	
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
	
}
