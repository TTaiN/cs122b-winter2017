package ecommerce_helpers;

import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;

import general_helpers.Movie;

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
	public boolean addToCart(Movie movie, int quantity) // lets me handle null cases better
	{
		if (!exists() || contains(movie.getId()))
		{
			return false;
		}
		else
		{
			movie.setQuantity(quantity);
			cart.put(movie.getId(), movie);
			return true;
		}
	}
	
	public boolean removeMovie(int movie_id) // lets me handle null cases better
	{
		if (exists() && contains(movie_id))
		{
			cart.remove(movie_id);
			return true;
		}
		else return false;
	}
	
	
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
	
	public int updateQuantity(int movie_id, int quantity) // lets me handle null cases better
	{
		if (exists() && contains(movie_id))
		{
			Movie movie = getMovie(movie_id);
			Integer previousQuantity = movie.getQuantity();
			movie.setQuantity(quantity);
			return previousQuantity;
		}
		else return 0;
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
