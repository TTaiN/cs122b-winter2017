package layout_helpers;

import java.io.PrintWriter;

public class TopMenu
{
	public static void print(PrintWriter out)
	{
		out.println("<br><center><a href='./main'><img src=\"./images/logo.png\" alt=\"logo\"></a></center><br>");
		out.println("<div class='topmenu'>");
		out.println("<table class='topmenu_table'>");
		out.println("<tr>");
		out.println("<td class='topmenu_button'><a href='./main'>Main Page</a></td>");
		out.println("<td class='topmenu_button'><a href='./browse'>Browse</a></td>");
		out.println("<td class='topmenu_button'><a href='./search'>Search</a></td>");
		out.println("<td class='topmenu_button'><a href='./cart'>Cart/Checkout</a></td>");
		out.println("<td class='topmenu_button'><a href='./logout'>Logout</a></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
	}
	
	public static void likePredicatePrint(PrintWriter out)
	{
		out.println("<br><center><img src=\"../images/logo.png\" alt=\"logo\"></center><br>");
		out.println("<div class='topmenu'>");
		out.println("<table class='topmenu_table'>");
		out.println("<tr>");
		out.println("<td class='topmenu_button'><a href='../main'>Return to Main Page</a></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
	}
}
