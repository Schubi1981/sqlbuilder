package de.jaggl.utils.sqlbuilder.builders.parts;

import static de.jaggl.utils.sqlbuilder.helpers.ListHelper.implodeArray;

import java.util.Collection;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.domain.Select;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class SelectPartBuilder extends PartBuilder<Select>
{

	private SelectPartBuilder(Collection<Select> fields)
	{
		super(fields);
	}

	private SelectPartBuilder(Select field)
	{
		super(field);
	}

	public static SelectPartBuilder select(String... fields)
	{
		return new SelectPartBuilder(new Select(fields));
	}

	public static SelectPartBuilder select(Collection<String> fields)
	{
		return new SelectPartBuilder(new Select(fields));
	}

	@Override
	public void putReplacements(Map<String, String> replacements,
	    String syntax, StatementType statementType)
	{
		replacements.put("fields", buildFields());
	}

	@Override
	public boolean isOverridingPartBuilder()
	{
		return false;
	}

	private String buildFields()
	{
		StringBuilder builder = new StringBuilder();
		if (hasElements())
		{
			for (Select field : data)
			{
				if (builder.length() > 0)
				{
					builder.append(", ");
				}
				builder.append(buildField(field));
			}
		}
		else
		{
			builder.append("*");
		}
		return builder.toString();
	}

	public String buildField(Select field)
	{
		return field.getFields().length > 0 ? implodeArray(", ",
		    field.getFields()) : "*";
	}

	@Override
	public String toString()
	{
		return String.format("SelectPartBuilder [%s]", super.toString());
	}

}
