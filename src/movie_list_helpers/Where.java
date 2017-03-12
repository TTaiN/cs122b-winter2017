package movie_list_helpers;

public class Where {
	// Translate string to where statement
	public static String getWhereEdth( String str, String attr )
	{
		Double distance = Math.floor(str.length() / 3);
		
		if(attr.equals("s.name") && (str.trim().equals("") || str == null))
		{
			return "s.id > 0";
		}
		else if(attr.equals("title") && (str.trim().equals("") || str == null))
		{
			return "id > 0";
		}
		if(str.trim().equals("") || str == null )
			return "";
		else if( attr.equals("s.name") || attr.equals("title"))
		{
			String words[] = str.split(" ");
			String where = "(";
			for( int i = 0; i < words.length; i++)
			{
				if( i == words.length - 1)
					where = where + " " + attr + " like '%" + words[i] + "%'";
				else
					where = where + " " + attr + " like '%" + words[i] + "%' and";
			}
			where = where + ") OR (edth(" + attr + ", '" + str + "', " + distance.intValue() + "))";
			return where;
		}
		else if(attr.equals("director"))
		{
			String words[] = str.split(" ");
			String where = " and (";
			for( int i = 0; i < words.length; i++)
			{
				if( i == words.length - 1)
					where = where + " " + attr + " like '%" + words[i] + "%'";
				else
					where = where + " " + attr + " like '%" + words[i] + "%' and";
			}
			where = where + ") OR (edth(" + attr + ", '" + str + "', " + distance.intValue() + "))";
			return where;
		}else
		{
			String words[] = str.split(" ");
			String where = " and (";
			for( int i = 0; i < words.length; i++)
			{
				if( i == words.length - 1)
					where = where + " " + attr + " like '%" + words[i] + "%'";
				else
					where = where + " " + attr + " like '%" + words[i] + "%' and";
			}
			where = where + ")";
			return where;
		}
	}
	
	// Translate string to where statement
//	public static String getWhere( String str)
//	{
//		String words[] = str.split(" ");
//		String where = "";
//		for( int i = 0; i < words.length; i++)
//		{
//			if( i == words.length - 1)
//				where = where + "%" + words[i] + "%";
//			else
//				where = where + "%" + words[i] + "%' and '";
//		}
//		return where;
//	}
	
	public static String addPercent( String str)
	{

		return "%" + str + "%";
	}

}
