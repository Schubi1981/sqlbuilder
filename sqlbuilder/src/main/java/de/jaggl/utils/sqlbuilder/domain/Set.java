package de.jaggl.utils.sqlbuilder.domain;

import java.util.Arrays;
import java.util.Collection;

import de.jaggl.utils.sqlbuilder.builders.SqlBuilder;

public class Set implements SqlFragmentSource
{

	private String column;
	private Object value;

	private SqlBuilder sqlBuilder;
	private String[] columns;

	public Set(String column, Object value)
	{
		this.column = column;
		this.value = value;
	}

	public Set(SqlBuilder sqlBuilder, String... columns)
	{
		this.sqlBuilder = sqlBuilder;
		this.columns = columns;
	}

	public Set(SqlBuilder sqlBuilder, Collection<String> columns)
	{
		this.sqlBuilder = sqlBuilder;
		this.columns = columns.toArray(new String[]
		{});
	}

	public String getColumn()
	{
		return column;
	}

	public Object getValue()
	{
		return value;
	}

	public SqlBuilder getSqlBuilder()
	{
		return sqlBuilder;
	}

	public String[] getColumns()
	{
		return columns;
	}

	@Override
	public String toString()
	{
		return String.format(
		    "Set [column=%s, value=%s, sqlBuilder=%s, columns=%s]", column,
		    value, sqlBuilder, Arrays.toString(columns));
	}

}
