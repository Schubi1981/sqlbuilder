package de.jaggl.utils.sqlbuilder.domain;

import static de.jaggl.utils.sqlbuilder.domain.Combination.AND;

public class Where implements SqlFragmentSource
{

    private String column;
    private Object value;
    private Combination combination;

    private WhereSource whereSource;

    public Where(String column, Object value, Combination combination)
    {
        this.column = column;
        this.value = value;
        this.combination = combination;
    }

    public Where(String column, Object value)
    {
        this(column, value, AND);
    }

    public Where(String statement)
    {
        this(statement, null, AND);
    }

    public Where(String statement, Combination combination)
    {
        this(statement, null, combination);
    }

    public Where(WhereSource whereSource, Combination combination)
    {
        this.whereSource = whereSource;
        this.combination = combination;
    }

    public Where(WhereSource whereSource)
    {
        this(whereSource, AND);
    }

    public String getColumn()
    {
        return column;
    }

    public Object getValue()
    {
        return value;
    }

    public Combination getCombination()
    {
        return combination;
    }

    public WhereSource getWhereSource()
    {
        return whereSource;
    }

    @Override
    public String toString()
    {
        return String.format(
            "Where [column=%s, value=%s, combination=%s, whereSource=%s]",
            column, value, combination, whereSource);
    }

}
