package movie_list_helpers;

public class Where {
	// Translate string to where statement
	public static String getWhere( String str, String attr )
	{
		String words[] = str.split(" ");
		String where = "";
		for( int i = 0; i < words.length; i++)
		{
			if( i == words.length - 1)
				where = where + " " + attr + " like '%" + words[i] + "%'";
			else
				where = where + " " + attr + " like '%" + words[i] + "%' and";
		}
		return where;
	}

}
