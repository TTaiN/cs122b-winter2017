package util;

import java.io.PrintWriter;


public class TopMenu
{
	public static void print(PrintWriter out)
	{
		out.println("<div class='topmenu'>");
		//out.println("<span>Menu:</span>");
		out.println("<table class='topmenu_table'>");
		//out.println("<caption class='topmenu_caption'>Fabflix Menu</caption>");
		out.println("<tr>");
		out.println("<td class='topmenu_button'><a href='./main'>Main Page</a></td>");
		out.println("<td class='topmenu_button'><a href='./browse'>Browse</a></td>");
		out.println("<td class='topmenu_button'><a href='./search'>Search</a></td>");
		out.println("<td class='topmenu_button'><a href='./cart'>Cart/Checkout</a></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
	}
}
