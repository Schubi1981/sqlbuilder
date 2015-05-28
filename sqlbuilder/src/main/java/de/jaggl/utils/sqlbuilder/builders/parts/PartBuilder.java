package de.jaggl.utils.sqlbuilder.builders.parts;

import static java.util.Arrays.asList;

import java.util.Collection;

import de.jaggl.utils.sqlbuilder.domain.ReplacementsSource;
import de.jaggl.utils.sqlbuilder.domain.SqlFragmentSource;

public abstract class PartBuilder<T extends SqlFragmentSource> implements
    ReplacementsSource
{

    protected Collection<T> data;

    public PartBuilder(Collection<T> data)
    {
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    protected T getEntry()
    {
        return (T) data.toArray()[0];
    }

    public abstract boolean isOverridingPartBuilder();

    public PartBuilder(T data)
    {
        this.data = asList(data);
    }

    public Collection<T> getData()
    {
        return data;
    }

    public boolean hasElements()
    {
        return !data.isEmpty();
    }

    @Override
    public String toString()
    {
        return String.format("data=%s", data);
    }

}
