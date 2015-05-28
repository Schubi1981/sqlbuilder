package de.jaggl.utils.sqlbuilder.builders.fragments;

import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.quoteIfNeeded;

import de.jaggl.utils.sqlbuilder.domain.HavingSource;
import de.jaggl.utils.sqlbuilder.domain.WhereSource;

public class BetweenBuilder implements WhereSource, HavingSource
{

    private String column;
    private Object min;
    private Object max;

    public BetweenBuilder(String column, Object min, Object max)
    {
        this.column = column;
        this.min = min;
        this.max = max;
    }

    @Override
    public String build()
    {
        return new StringBuilder(column)
            .append(" BETWEEN ")
            .append(quoteIfNeeded(min))
            .append(" AND ")
            .append(quoteIfNeeded(max)).toString();
    }

}
