package de.jaggl.utils.sqlbuilder.domain;

import java.util.Arrays;
import java.util.Collection;

import de.jaggl.utils.sqlbuilder.builders.SqlBuilder;

public class From implements SqlFragmentSource
{

    private String[] tables;

    private SqlBuilder sqlBuilder;
    private String alias;

    public From(String... tables)
    {
        this.tables = tables;
    }

    public From(Collection<String> tables)
    {
        this.tables = tables.toArray(new String[0]);
    }

    public From(SqlBuilder sqlBuilder, String alias)
    {
        this.sqlBuilder = sqlBuilder;
        this.alias = alias;
    }

    public String[] getTables()
    {
        return tables;
    }

    public SqlBuilder getSqlBuilder()
    {
        return sqlBuilder;
    }

    public String getAlias()
    {
        return alias;
    }

    @Override
    public String toString()
    {
        return String.format("From [tables=%s, sqlBuilder=%s, alias=%s]",
            Arrays.toString(tables), sqlBuilder, alias);
    }

}
