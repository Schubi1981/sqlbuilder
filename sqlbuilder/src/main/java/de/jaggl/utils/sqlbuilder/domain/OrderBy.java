package de.jaggl.utils.sqlbuilder.domain;

public class OrderBy implements SqlFragmentSource
{

    private String column;
    private OrderDir orderDir;

    public OrderBy(String column, OrderDir orderDir)
    {
        this.column = column;
        this.orderDir = orderDir;
    }

    public OrderBy(String column)
    {
        this(column, null);
    }

    public String getColumn()
    {
        return column;
    }

    public OrderDir getOrderDir()
    {
        return orderDir;
    }

    @Override
    public String toString()
    {
        return String.format("OrderBy [column=%s, orderDir=%s]", column,
            orderDir);
    }

}
