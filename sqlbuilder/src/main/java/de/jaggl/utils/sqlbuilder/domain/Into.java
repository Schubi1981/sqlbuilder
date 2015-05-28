package de.jaggl.utils.sqlbuilder.domain;

public class Into implements SqlFragmentSource
{

    private String table;

    public Into(String table)
    {
        this.table = table;
    }

    public String getTable()
    {
        return table;
    }

    @Override
    public String toString()
    {
        return String.format("Into [table=%s]", table);
    }

}
