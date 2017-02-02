package ecommerce_helpers;

import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;
import util.Movie;
import java.util.Set;

public class ShoppingCart 
{
	LinkedHashMap<Integer, Movie> cart;
	
	public ShoppingCart (HttpSession session)
	{
		this.cart = (LinkedHashMap<Integer, Movie>) session.getAttribute("cart");
	}

	// Begin Setter/Getters 
	public LinkedHashMap<Integer, Movie> getCart() 
	{
		return cart;
	}

	public void setCart(LinkedHashMap<Integer, Movie> cart) 
	{
		this.cart = cart;
	}
	
	public boolean exists() // cleaner null case
	{
		return cart != null;
	}
	
	// Begin Checkers
	public boolean contains(int movie_id) // again, cleaner null case
	{
		if (cart != null)
		{
			return cart.containsKey(movie_id);
		}
		else return false;
	}
	
	public boolean isEmpty() // lets me handle null cases better
	{
		if (cart != null)
		{
			return cart.isEmpty();
		}
		else return false;
	}
	
	// Begin Useful Functions
	public Movie getMovie(int movie_id) // lets me handle null cases better
	{
		if (exists() && contains(movie_id))
		{
			return cart.get(movie_id);
		}
		else return null;
	}
	
	public Set<Integer> getMovies()
	{
		return cart.keySet();
	}
	
	public int getQuantity(int movie_id) // lets me handle null cases better
	{
		if (exists() && contains(movie_id))
		{
			return getMovie(movie_id).getQuantity();
		}
		else return 0;
	}
}
