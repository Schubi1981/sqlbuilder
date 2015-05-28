package de.jaggl.utils.sqlbuilder.builders.parts;

import static de.jaggl.utils.sqlbuilder.domain.StatementType.INSERT;
import static de.jaggl.utils.sqlbuilder.domain.StatementType.UPDATE;
import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.quoteIfNeeded;
import static de.jaggl.utils.sqlbuilder.helpers.ListHelper.implodeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.builders.SqlBuilder;
import de.jaggl.utils.sqlbuilder.domain.Set;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class SetPartBuilder extends PartBuilder<Set>
{

	private SetPartBuilder(Collection<Set> sets)
	{
		super(sets);
	}

	private SetPartBuilder(Set set)
	{
		super(set);
	}

	public static SetPartBuilder set(String column, Object value)
	{
		return new SetPartBuilder(new Set(column, value));
	}

	public static SetPartBuilder set(SqlBuilder sqlBuilder, String... columns)
	{
		return new SetPartBuilder(new Set(sqlBuilder, columns));
	}

	public static SetPartBuilder set(SqlBuilder sqlBuilder,
	    Collection<String> columns)
	{
		return new SetPartBuilder(new Set(sqlBuilder, columns));
	}

	@Override
	public void putReplacements(Map<String, String> replacements,
	    String syntax, StatementType statementType)
	{
		if (statementType == INSERT)
		{
			if (isSqlBuilderSet())
			{
				removeNonSqlBuilders();
				replacements.put("fields", buildSqlBuilderColumns());
				replacements.put("values", buildSqlBuilderValues(syntax));
			}
			else
			{
				removeSqlBuilders();
				removeDuplicates();
				replacements.put("fields", buildColumns());
				replacements.put("values", buildValues());
			}
		}
		else if (statementType == UPDATE)
		{
			removeSqlBuilders();
			removeDuplicates();
			replacements.put("values", buildForUpdate());
		}
	}

	@Override
	public boolean isOverridingPartBuilder()
	{
		return false;
	}

	private boolean isSqlBuilderSet()
	{
		List<Set> setList = new ArrayList<Set>(data);
		return setList.get(setList.size() - 1).getSqlBuilder() != null;
	}

	private void removeSqlBuilders()
	{
		List<Set> setsToRemove = new ArrayList<Set>();
		for (Set set : data)
		{
			if (set.getSqlBuilder() != null)
			{
				setsToRemove.add(set);
			}
		}
		data.removeAll(setsToRemove);
	}

	private void removeNonSqlBuilders()
	{
		List<Set> setsToRemove = new ArrayList<Set>();
		for (Set set : data)
		{
			if (set.getSqlBuilder() == null)
			{
				setsToRemove.add(set);
			}
		}
		data.removeAll(setsToRemove);
	}

	private void removeDuplicates()
	{
		Map<String, Set> lastSets = new HashMap<String, Set>();
		List<Set> setsToRemove = new ArrayList<Set>();
		for (Set set : data)
		{
			if (lastSets.containsKey(set.getColumn()))
			{
				setsToRemove.add(lastSets.get(set.getColumn()));
			}
			lastSets.put(set.getColumn(), set);
		}
		data.removeAll(setsToRemove);
	}

	private String buildSqlBuilderColumns()
	{
		Set set = new ArrayList<Set>(data).get(0);
		return implodeArray(", ", set.getColumns());
	}

	private String buildSqlBuilderValues(String syntax)
	{
		Set set = new ArrayList<Set>(data).get(0);
		return set.getSqlBuilder().buildSelect(syntax);
	}

	private String buildColumns()
	{
		StringBuilder builder = new StringBuilder();
		for (Set set : data)
		{
			if (builder.length() > 0)
			{
				builder.append(", ");
			}
			builder.append(set.getColumn());
		}
		return builder.toString();
	}

	private String buildValues()
	{
		StringBuilder builder = new StringBuilder();
		for (Set set : data)
		{
			if (builder.length() > 0)
			{
				builder.append(", ");
			}
			builder.append(quoteIfNeeded(set.getValue()));
		}
		return builder.toString();
	}

	private String buildForUpdate()
	{
		StringBuilder builder = new StringBuilder();
		for (Set set : data)
		{
			if (builder.length() > 0)
			{
				builder.append(", ");
			}
			builder.append(set.getColumn()).append(" = ");
			builder.append(quoteIfNeeded(set.getValue()));
		}
		return builder.toString();
	}

	@Override
	public String toString()
	{
		return String.format("SetPartBuilder [%s]", super.toString());
	}

}
