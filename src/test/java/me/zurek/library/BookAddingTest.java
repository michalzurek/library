package me.zurek.library;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import me.zurek.library.entity.Book;

public class BookAddingTest {
	
	Library library = new Library();
	List<Book> testBooks = new ArrayList<Book>();

	
	@Before
	public void initBooks() {
		testBooks.add(new Book("Lalka", "Bolesław Prus", 1890));
		testBooks.add(new Book("Wesele", "Stanisław Wyspiański", 1901));
		testBooks.add(new Book("Ogniem i mieczem", "Henryk Sienkiewicz", 1884));
		testBooks.add(new Book("Quo vadis", "Henryk Sienkiewicz", 1896));
		testBooks.add(new Book("Zemsta", "Aleksander Fredro", 1838));
		testBooks.add(new Book("Nad Niemnem", "Aleksander Fredro", 1838));
	}
	
	@Test
	public void testBooksRemoving() {
		for (Book book: testBooks) {
			library.addNewBook(book);
		}
		
		//check if IDs are proper
		for (Book book: testBooks) {
			assertEquals(book.getID(),Integer.valueOf(library.getBooks().indexOf(book)));
		}
		
		//check if books are in library and have proper indices
		for (Book book: testBooks) {
			assertSame(book,library.getBooks().get(book.getID()));
		}
	}
}
