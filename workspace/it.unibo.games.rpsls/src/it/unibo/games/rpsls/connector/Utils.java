package it.unibo.games.rpsls.connector;

public class Utils {
	
	public static String createSimpleSPARQLQuerySelectWhere(String subject, String predicate, String object){
		
		String select = "SELECT ";
		String where = "WHERE { " ;
		
		if (subject == null){
			select += "?subject ";
			where += "?subject ";
		}else
			where += "<" + subject + "> ";
		if (predicate == null){
			select += "?predicate ";
			where += "?predicate ";
		}else
			where += "<" + predicate + "> ";
		if (object == null){
			select += "?object ";
			where += "?object ";
		}else
			where += "<" + object + "> ";
		where += "}";
		return select+where;
	}
	
	public static String removePrefix(String uri){
		if (uri.contains("#"))
			return uri.split("#")[1];
		return uri;
	}
	
}
