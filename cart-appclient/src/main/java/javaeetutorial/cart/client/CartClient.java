package javaeetutorial.cart.client;

import java.util.List;
import java.util.Properties;

import javaeetutorial.cart.ejb.Cart;
import javaeetutorial.cart.util.BookException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CartClient {
	//@EJB
	//private static Cart cart;
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		CartClient client = new CartClient();
		Properties p = new Properties();
		p.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", "http://localhost:8080/tomee/ejb");
		p.put("openejb.embedded.remotable", "true");
		// user and pass optional
		//p.put("java.naming.security.principal", "myuser");
		//p.put("java.naming.security.credentials", "mypass");

		try {
			InitialContext ctx = new InitialContext(p);	
			Cart cart = (Cart) ctx.lookup("CartBeanRemote");
			Cart cart2 = (Cart) ctx.lookup("CartBeanRemote");
			if(cart.equals(cart2)){
				System.out.println("same instance");
			}
			client.doTest(cart);
		} catch (NamingException e) {
			System.err.println("Caught a NamingException: " + e);
		}
	}

	public void doTest(Cart cart) {
		try {
			cart.initialize("Duke d'Url", "123");
			cart.addBook("Infinite Jest");
			cart.addBook("Bel Canto");
			cart.addBook("Kafka on the Shore");
			List<String> bookList = cart.getContents();
			for(String book :bookList) {
				System.out.println("Retrieving book title from cart: " + book);
			}

			System.out.println("Removing \"Gravity's Rainbow\" from cart.");
			cart.removeBook("Gravity's Rainbow");
			System.out.println("Removing the cart.");
			cart.remove();

			System.exit(0);
		} catch (BookException ex) {
			System.err.println("Caught a BookException: " + ex.getMessage());
			System.exit(0);
		}
	}

}
