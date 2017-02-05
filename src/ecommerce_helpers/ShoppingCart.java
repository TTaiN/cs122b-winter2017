package ecommerce_helpers;

import java.util.LinkedHashMap;

import general_helpers.Movie;

import java.util.Set;

public class ShoppingCart 
{
	private LinkedHashMap<Integer, Movie> cart;

	private String number;
	private String firstName;
	private String lastName;
	private String date;
	
	public ShoppingCart()
	{
		this.cart = new LinkedHashMap<Integer, Movie>();
	}

	// Begin Setter/Getters
	public void setCustomerInformation(String number, String firstName, String lastName, String date)
	{
		this.setNumber(number);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDate(date);
	}
	
	
	public LinkedHashMap<Integer, Movie> getCart() {
		return cart;
	}

	public void setCart(LinkedHashMap<Integer, Movie> cart) {
		this.cart = cart;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	// Begin Checkers
	public boolean contains(int movie_id)
	{
		return cart.containsKey(movie_id);
	}
	
	public boolean isEmpty()
	{
		return cart.isEmpty();
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
		if (isEmpty())
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
		return cart.keySet();
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
