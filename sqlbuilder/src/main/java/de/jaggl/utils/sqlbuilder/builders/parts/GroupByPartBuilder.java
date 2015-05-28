package de.jaggl.utils.sqlbuilder.builders.parts;

import static de.jaggl.utils.sqlbuilder.helpers.ListHelper.implodeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.domain.GroupBy;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class GroupByPartBuilder extends PartBuilder<GroupBy>
{

	private GroupByPartBuilder(Collection<GroupBy> groupBys)
	{
		super(groupBys);
	}

	private GroupByPartBuilder(GroupBy groupBy)
	{
		super(groupBy);
	}

	public static GroupByPartBuilder groupBy(String... fields)
	{
		return new GroupByPartBuilder(new GroupBy(fields));
	}

	public static GroupByPartBuilder groupBy(Collection<String> fields)
	{
		return new GroupByPartBuilder(new GroupBy(fields));
	}

	@Override
	public void putReplacements(Map<String, String> replacements,
	    String syntax, StatementType statementType)
	{
		if (hasElements())
		{
			replacements.put("groupByColumn", buildGroupBy());
		}
	}

	@Override
	public boolean isOverridingPartBuilder()
	{
		return false;
	}

	private String buildGroupBy()
	{
		List<String> fields = distinctFields();
		return implodeList(", ", fields);
	}

	private List<String> distinctFields()
	{
		List<String> fields = new ArrayList<String>();
		for (GroupBy groupBy : data)
		{
			for (String field : groupBy.getFields())
			{
				if (!fields.contains(field))
				{
					fields.add(field);
				}
			}
		}
		return fields;
	}

	@Override
	public String toString()
	{
		return String.format("GroupByPartBuilder [%s]", super.toString());
	}

}
