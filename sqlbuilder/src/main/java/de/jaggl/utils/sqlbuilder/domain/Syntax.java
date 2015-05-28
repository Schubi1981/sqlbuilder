package de.jaggl.utils.sqlbuilder.domain;

import static de.jaggl.utils.sqlbuilder.domain.StatementType.DELETE;
import static de.jaggl.utils.sqlbuilder.domain.StatementType.INSERT;
import static de.jaggl.utils.sqlbuilder.domain.StatementType.SELECT;
import static de.jaggl.utils.sqlbuilder.domain.StatementType.UPDATE;

import java.util.HashMap;
import java.util.Map;

public class Syntax
{

	public static final String DEFAULT = "DEFAULT";
	public static final String MYSQL = "MYSQL";
	public static final String SYBASE = "SYBASE";

	private static Syntax defaultSyntax;

	private static Map<String, Syntax> registeredSyntaxen = new HashMap<String, Syntax>();

	private Map<StatementType, String> statementSyntaxen = new HashMap<StatementType, String>();

	static
	{
		defaultSyntax = new Syntax();

		defaultSyntax.putSyntax(SELECT,
		    "SELECT {distinct=[{distinctPart} ]}{fields}"
		        + "{from=[ FROM {table}]}"
		        + "{join=[ {joins}]}"
		        + "{where=[ WHERE {wheres}]}"
		        + "{groupBy=[ GROUP BY {groupByColumn}]}"
		        + "{having=[ HAVING {havings}]}"
		        + "{orderBy=[ ORDER BY {orderByColumn}]}"
		        + "{limit=[ LIMIT {offset=[{offsetSize}, ]}{size}]}");

		defaultSyntax.putSyntax(INSERT,
		    "INSERT INTO {table} ({fields}) VALUES ({values})");

		defaultSyntax.putSyntax(UPDATE, "UPDATE {table} SET {values}{where=[ WHERE {wheres}]}");

		defaultSyntax.putSyntax(DELETE, "DELETE FROM {table}"
		    + "{join=[ {joins}]}"
		    + "{where=[ WHERE {wheres}]}");

		Syntax sybaseSyntax = new Syntax();

		sybaseSyntax
		    .putSyntax(
		        SELECT,
		        "SELECT {distinct=[{distinctPart} ]}{limit=[TOP {size}{offset=[ START AT {offsetSize}]} ]}{fields}"
		            + "{from=[ FROM {table}]}"
		            + "{join=[ {joins}]}"
		            + "{where=[ WHERE {wheres}]}"
		            + "{groupBy=[ GROUP BY {groupByColumn}]}"
		            + "{having=[ HAVING {havings}]}"
		            + "{orderBy=[ ORDER BY {orderByColumn}]}");

		registerSyntax(SYBASE, sybaseSyntax);
	}

	public static String findSyntax(String syntax, StatementType statementType)
	{
		String foundSyntax = null;
		Syntax registeredSyntax = registeredSyntaxen.get(syntax);
		if (registeredSyntax != null)
		{
			foundSyntax = registeredSyntax.getSyntax(statementType);
		}
		if (foundSyntax == null)
		{
			foundSyntax = defaultSyntax.getSyntax(statementType);
		}
		return foundSyntax;
	}

	public static void registerSyntax(String databaseType, Syntax syntax)
	{
		registeredSyntaxen.put(databaseType, syntax);
	}

	public void putSyntax(StatementType statementType, String syntax)
	{
		statementSyntaxen.put(statementType, syntax);
	}

	public String getSyntax(StatementType statementType)
	{
		return statementSyntaxen.get(statementType);
	}

	@Override
	public String toString()
	{
		return String
		    .format("Syntax [statementSyntaxen=%s]", statementSyntaxen);
	}

}
