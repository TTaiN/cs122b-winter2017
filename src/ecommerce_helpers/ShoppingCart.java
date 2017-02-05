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
		if (exists())
		{
			return cart.containsKey(movie_id);
		}
		else return false;
	}
	
	public boolean isEmpty() // lets me handle null cases better
	{
		if (exists())
		{
			return cart.isEmpty();
		}
		else return true;
	}
	
	// Begin Useful Functions
	
	public int getTotalQuantity()
	{
		int totalQuantity = 0;
		
		if (!isEmpty())
		{
			for (Integer movie_id : cart.keySet())
			{
				totalQuantity += cart.get(movie_id).getQuantity();
			}
		}
		
		return totalQuantity;
	}
	
	public double getTotalPrice()
	{
		double totalPrice = 0.00;
		
		if (!isEmpty())
		{
			for (Integer movie_id : cart.keySet())
			{
				Movie movie = cart.get(movie_id);
				totalPrice += (movie.getPrice() * movie.getQuantity());
			}
		}
		
		return totalPrice;
	}
	
	public int size()
	{
		if (!exists() || isEmpty())
		{
			return 0;
		}
		else return cart.size();
	}
	public boolean addToCart(Movie movie, int quantity) // lets me handle null cases better
	{
		if (contains(movie.getId()))
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
		if (contains(movie_id))
		{
			cart.remove(movie_id);
			return true;
		}
		else return false;
	}
	
	
	public Movie getMovie(int movie_id) // lets me handle null cases better
	{
		if (contains(movie_id))
		{
			return cart.get(movie_id);
		}
		else return null;
	}
	
	public Set<Integer> getMovies()
	{
		if (exists())
		{
			return cart.keySet();
		}
		else return null;
	}
	
	public int updateQuantity(int movie_id, int quantity) // lets me handle null cases better
	{
		if (contains(movie_id))
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
		if (contains(movie_id))
		{
			return getMovie(movie_id).getQuantity();
		}
		else return 0;
	}
}
