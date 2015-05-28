package de.jaggl.utils.sqlbuilder.domain;

import static de.jaggl.utils.sqlbuilder.domain.Combination.AND;

public class Having implements SqlFragmentSource
{

    private String column;
    private Object value;
    private Combination combination;

    private HavingSource havingSource;

    public Having(String column, Object value, Combination combination)
    {
        this.column = column;
        this.value = value;
        this.combination = combination;
    }

    public Having(String column, Object value)
    {
        this(column, value, AND);
    }

    public Having(String statement)
    {
        this(statement, null, AND);
    }

    public Having(String statement, Combination combination)
    {
        this(statement, null, combination);
    }

    public Having(HavingSource havingSource, Combination combination)
    {
        this.havingSource = havingSource;
        this.combination = combination;
    }

    public Having(HavingSource havingSource)
    {
        this(havingSource, AND);
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

    public HavingSource getHavingSource()
    {
        return havingSource;
    }

    @Override
    public String toString()
    {
        return String.format(
            "Having [column=%s, value=%s, combination=%s, havingSource=%s]",
            column, value, combination, havingSource);
    }

}
