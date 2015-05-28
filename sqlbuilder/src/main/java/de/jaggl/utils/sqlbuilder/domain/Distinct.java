package de.jaggl.utils.sqlbuilder.domain;

public class Distinct implements SqlFragmentSource
{

    private boolean distinct;

    public Distinct(boolean distinct)
    {
        this.distinct = distinct;
    }

    public Distinct()
    {
        this(true);
    }

    public boolean isDistinct()
    {
        return distinct;
    }

    @Override
    public String toString()
    {
        return String.format("Distinct []");
    }

}
