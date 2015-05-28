package de.jaggl.utils.sqlbuilder.builders.fragments;

import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.quoteIfNeeded;

import java.util.Collection;

import de.jaggl.utils.sqlbuilder.domain.HavingSource;
import de.jaggl.utils.sqlbuilder.domain.WhereSource;

public class InBuilder implements WhereSource, HavingSource
{

    private String column;
    private Object[] values;

    public InBuilder(String column, Object... values)
    {
        this.column = column;
        this.values = values;
    }

    public InBuilder(String column, Collection<?> values)
    {
        this.column = column;
        this.values = values.toArray();
    }

    @Override
    public String build()
    {
        StringBuilder builder = new StringBuilder(column).append(" IN (");
        boolean first = true;
        for (Object value : values)
        {
            if (!first)
            {
                builder.append(", ");
            }
            builder.append(quoteIfNeeded(value));
            first = false;
        }
        return builder.append(")").toString();
    }

}
