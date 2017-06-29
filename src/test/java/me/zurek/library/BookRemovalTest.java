package me.zurek.library;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import me.zurek.library.entity.Book;

public class BookRemovalTest {
	
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
		
		int numberOfBooks = testBooks.size();
		int indexToRemove = (int) (Math.random() * (numberOfBooks - 0.5));
		
		Book bookToBeRemoved = library.getBooks().get(indexToRemove);
		library.removeBook(indexToRemove);
		testBooks.remove(bookToBeRemoved);
		
		//check if book have proper ID after one's removal
		for (Book book: testBooks) {
			assertEquals(book.getID(),Integer.valueOf(library.getBooks().indexOf(book)));
		}
		
		//check if book no longer exists in the list
		assertEquals(-1,library.getBooks().indexOf(bookToBeRemoved));
		
	}
}
