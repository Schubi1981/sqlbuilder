package de.jaggl.utils.sqlbuilder.domain;

import java.util.Arrays;
import java.util.Collection;

public class GroupBy implements SqlFragmentSource
{

    private String[] fields;

    public GroupBy(String... fields)
    {
        this.fields = fields;
    }

    public GroupBy(Collection<String> fields)
    {
        this.fields = fields.toArray(new String[0]);
    }

    public String[] getFields()
    {
        return fields;
    }

    @Override
    public String toString()
    {
        return String.format("GroupBy [fields=%s]", Arrays.toString(fields));
    }

}
