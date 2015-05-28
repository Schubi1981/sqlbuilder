package de.jaggl.utils.sqlbuilder.domain;

public class Unquoted
{

    private String unquoted;

    private Unquoted(String unquoted)
    {
        this.unquoted = unquoted;
    }

    private Unquoted()
    {
    }

    public static Unquoted unquoted(String unquoted)
    {
        return new Unquoted(unquoted);
    }

    @Override
    public String toString()
    {
        return unquoted;
    }

}
