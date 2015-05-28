package de.jaggl.utils.sqlbuilder.builders.parts;

import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.embrace;
import static de.jaggl.utils.sqlbuilder.helpers.ListHelper.implodeArray;

import java.util.Collection;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.builders.SqlBuilder;
import de.jaggl.utils.sqlbuilder.domain.From;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class FromPartBuilder extends PartBuilder<From>
{

	private FromPartBuilder(Collection<From> froms)
	{
		super(froms);
	}

	private FromPartBuilder(From from)
	{
		super(from);
	}

	public static FromPartBuilder from(String... tableNames)
	{
		return new FromPartBuilder(new From(tableNames));
	}

	public static FromPartBuilder from(Collection<String> tableNames)
	{
		return new FromPartBuilder(new From(tableNames));
	}

	public static FromPartBuilder from(SqlBuilder sqlBuilder, String alias)
	{
		return new FromPartBuilder(new From(sqlBuilder, alias));
	}

	@Override
	public void putReplacements(Map<String, String> replacements,
	    String syntax, StatementType statementType)
	{
		if (hasElements())
		{
			replacements.put("table", buildTable(syntax));
		}
	}

	@Override
	public boolean isOverridingPartBuilder()
	{
		return false;
	}

	private String buildTable(String syntax)
	{
		StringBuilder builder = new StringBuilder();
		for (From from : data)
		{
			if (builder.length() > 0)
			{
				builder.append(", ");
			}
			if (from.getSqlBuilder() != null)
			{
				builder
				    .append(embrace(from.getSqlBuilder().buildSelect(syntax)))
				    .append(" AS ")
				    .append(from.getAlias());
			}
			else
			{
				builder.append(implodeArray(", ", from.getTables()));
			}
		}
		return builder.toString();
	}

	@Override
	public String toString()
	{
		return String.format("FromPartBuilder [%s]", super.toString());
	}

}
