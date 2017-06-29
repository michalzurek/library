package me.zurek.library;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import me.zurek.library.entity.Book;
import me.zurek.library.entity.Person;

public class BooksListingTest {
	final int LALKA = 5;
	final int WESELE = 1;
	final int OGNIEM = 15;
	final int LALKA_LENT = 2;
	final int WESELE_LENT = 0;
	final int OGNIEM_LENT = 10;

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

		// add lent books
		for (int i = 0; i < LALKA_LENT; ++i) {
			Book b = new Book("Lalka", "Bolesław Prus", 1890);
			b.setLentTo(new Person("Michał Żurek"));
			library.addNewBook(b);
		}
		for (int i = 0; i < WESELE_LENT; ++i) {
			Book b = new Book("Wesele", "Stanisław Wyspiański", 1901);
			b.setLentTo(new Person("Michał Żurek"));
			library.addNewBook(b);
		}
		for (int i = 0; i < OGNIEM_LENT; ++i) {
			Book b = new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1884);
			b.setLentTo(new Person("Michał Żurek"));
			library.addNewBook(b);
		}
		
		library.listAllBooksDistinctly();
	}
	
	@Test
	public void testGrouping() {
		Map<Integer,Map<String,Map<String,Set<Book>>>> allBooks = library.prepareDistinctBooksCollection();
		
		assertEquals((long) LALKA,allBooks.get(1890).get("Bolesław Prus").get("Lalka").stream().filter(b -> b.getLentTo() == null).count());
		assertEquals((long) WESELE,allBooks.get(1901).get("Stanisław Wyspiański").get("Wesele").stream().filter(b -> b.getLentTo() == null).count());
		assertEquals((long) OGNIEM,allBooks.get(1884).get("Henryk Sienkiewicz").get("Ogniem i mieczem").stream().filter(b -> b.getLentTo() == null).count());
		assertEquals((long) LALKA_LENT,allBooks.get(1890).get("Bolesław Prus").get("Lalka").stream().filter(b -> b.getLentTo() != null).count());
		assertEquals((long) WESELE_LENT,allBooks.get(1901).get("Stanisław Wyspiański").get("Wesele").stream().filter(b -> b.getLentTo() != null).count());
		assertEquals((long) OGNIEM_LENT,allBooks.get(1884).get("Henryk Sienkiewicz").get("Ogniem i mieczem").stream().filter(b -> b.getLentTo() != null).count());
	}
}
