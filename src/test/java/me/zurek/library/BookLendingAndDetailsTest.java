package me.zurek.library;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import me.zurek.library.entity.Book;
import me.zurek.library.entity.Person;

public class BookLendingAndDetailsTest {
	final int LALKA = 1;
	final int LALKA_LENT = 1;

	Library library = new Library();
	List<Book> testBooks = new ArrayList<Book>();

	@Before
	public void initBooks() {
		// add available books
		for (int i = 0; i < LALKA; ++i)
			library.addNewBook(new Book("Lalka", "Bolesław Prus", 1890));

		// add lent books
		for (int i = 0; i < LALKA_LENT; ++i) {
			Book b = new Book("Lalka", "Bolesław Prus", 1890);
			b.setLentTo(new Person("Michał Żurek"));
			library.addNewBook(b);
		}
	}

	@Test
	public void testSearching() {
		for (Book b : library.getBooks())
			library.seeDetails(b.getID());

		System.out.println("\nTrying to lend a book that is already lent...");
		assertFalse(library.lendABook(1, "Anna Zawadzka"));
		
		System.out.println("\nTrying to lend a book that is available...");
		assertTrue(library.lendABook(0, "Jan Kochanowski"));
		
		for (Book b : library.getBooks())
			library.seeDetails(b.getID());
	}
}
