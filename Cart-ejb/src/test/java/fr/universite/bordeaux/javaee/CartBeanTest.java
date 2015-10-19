package fr.universite.bordeaux.javaee;

import javaeetutorial.cart.ejb.Cart;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import junit.framework.TestCase;

public class CartBeanTest extends TestCase{
	public void testCart() throws Exception{
		 final Context context = EJBContainer.createEJBContainer().getContext();
	     Cart cart = (Cart) context.lookup("java:global/Cart-ejb/CartBean");
	     cart.initialize("toto");
	     cart.addBook("book1");
	     assertEquals(cart.getContents().size(), 1);
	}
	
}
