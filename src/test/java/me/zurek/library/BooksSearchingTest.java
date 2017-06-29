package me.zurek.library;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import me.zurek.library.entity.Book;
import me.zurek.library.entity.Person;

public class BooksSearchingTest {
	private final int LALKA = 3;
	private final int WESELE = 1;
	private final int OGNIEM = 2;
	private final int PUSTYNI = 4;
	private final int WESELE2 = 5;
	private final int WIELKA = 2;
	private final int WIELKA2 = 3;
	private final int LALKA_LENT = 2;
	private final int WESELE_LENT = 0;
	private final int OGNIEM_LENT = 1;
	private final int PUSTYNI_LENT = 1;
	private final int WESELE2_LENT = 3;
	private final int WIELKA_LENT = 1;
	private final int WIELKA2_LENT = 2;

	Library library = new Library();
	List<Book> testBooks = new ArrayList<Book>();

	@Before
	public void initBooks() {
		// add available books
		for (int i = 0; i < LALKA; ++i)
			library.addNewBook(new Book("Lalka", "Bolesław Prus", 1890));
		for (int i = 0; i < WESELE; ++i)
			library.addNewBook(new Book("Wesele", "Stanisław Wyspiański", 1901));
		for (int i = 0; i < OGNIEM; ++i)
			library.addNewBook(new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1884));
		for (int i = 0; i < PUSTYNI; ++i)
			library.addNewBook(new Book("W pustyni i w puszczy", "Henryk Sienkiewicz", 1911));
		for (int i = 0; i < WESELE2; ++i)
			library.addNewBook(new Book("Wesele", "Andrzej Kowalski", 2015));
		for (int i = 0; i < WIELKA; ++i)
			library.addNewBook(new Book("Wielka Powieść", "Hanna Kołecka", 1884));
		for (int i = 0; i < WIELKA2; ++i)
			library.addNewBook(new Book("Wielka Powieść", "Hanna Kołecka", 1955));

		// add lent books
		for (int i = 0; i < LALKA_LENT; ++i) {
			Book b = new Book("Lalka", "Bolesław Prus", 1890);
			b.setLentTo(new Person("Michał Żurek"));
			library.addNewBook(b);
		}
		for (int i = 0; i < WESELE_LENT; ++i) {
			Book b = new Book("Wesele", "Stanisław Wyspiański", 1901);
			b.setLentTo(new Person("Andrzej Kotowski"));
			library.addNewBook(b);
		}
		for (int i = 0; i < OGNIEM_LENT; ++i) {
			Book b = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1884);
			b.setLentTo(new Person("Halina Bielecki"));
			library.addNewBook(b);
		}
		for (int i = 0; i < PUSTYNI_LENT; ++i) {
			Book b = new Book("W pustyni i w puszczy", "Henryk Sienkiewicz", 1911);
			b.setLentTo(new Person("Halina Szyszka"));
			library.addNewBook(b);
		}
		for (int i = 0; i < WESELE2_LENT; ++i) {
			Book b = new Book("Wesele", "Andrzej Kowalski", 2015);
			b.setLentTo(new Person("Jan Wieczorek"));
			library.addNewBook(b);
		}
		for (int i = 0; i < WIELKA_LENT; ++i) {
			Book b = new Book("Wielka Powieść", "Hanna Kołecka", 1884);
			b.setLentTo(new Person("Anna Jantar"));
			library.addNewBook(b);
		}
		for (int i = 0; i < WIELKA2_LENT; ++i) {
			Book b = new Book("Wielka Powieść", "Hanna Kołecka", 1955);
			b.setLentTo(new Person("Janusz Malinowski"));
			library.addNewBook(b);
		}
	}
	
	@Test
	public void testSearching() {
		List<Book> foundBooks;
		
		foundBooks = library.searchBooks(null, null, null);
		assertEquals(LALKA + WESELE + OGNIEM + PUSTYNI + WESELE2 + WIELKA + WIELKA2 + LALKA_LENT + WESELE_LENT + OGNIEM_LENT + PUSTYNI_LENT + WESELE2_LENT + WIELKA_LENT + WIELKA2_LENT, foundBooks.size());
		
		foundBooks = library.searchBooks("Wesele", null, null);
		assertEquals(WESELE + WESELE2 + WESELE_LENT + WESELE2_LENT,foundBooks.size());
		foundBooks.stream().forEach(b -> assertTrue(b.getTitle().equals("Wesele")));
		
		foundBooks = library.searchBooks("Lalka", null, null);
		assertEquals(LALKA + LALKA_LENT,foundBooks.size());
		foundBooks.stream().forEach(b -> assertTrue(b.getTitle().equals("Lalka")));
		
		foundBooks = library.searchBooks(null, "Henryk Sienkiewicz", null);
		assertEquals(OGNIEM + PUSTYNI + OGNIEM_LENT + PUSTYNI_LENT,foundBooks.size());
		foundBooks.stream().forEach(b -> assertTrue(b.getAuthor().equals("Henryk Sienkiewicz")));
		
		foundBooks = library.searchBooks(null, null, 1884);
		assertEquals(OGNIEM + WIELKA + OGNIEM_LENT + WIELKA_LENT,foundBooks.size());
		foundBooks.stream().forEach(b -> assertTrue(b.getYear().equals(1884)));
		
		foundBooks = library.searchBooks("Wielka Powieść", "Hanna Kołecka", null);
		assertEquals(WIELKA + WIELKA2 + WIELKA_LENT + WIELKA2_LENT,foundBooks.size());
		foundBooks.stream().forEach(b -> assertTrue(b.getTitle().equals("Wielka Powieść") && b.getAuthor().equals("Hanna Kołecka")));
		
		foundBooks = library.searchBooks("Wielka Powieść", "Hanna Kołecka", 1884);
		assertEquals(WIELKA + WIELKA_LENT,foundBooks.size());
		foundBooks.stream().forEach(b -> assertTrue(b.getTitle().equals("Wielka Powieść") && b.getAuthor().equals("Hanna Kołecka") && b.getYear().equals(1884)));
	}
}
