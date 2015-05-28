package de.jaggl.utils.sqlbuilder.domain;

public class Limit implements SqlFragmentSource
{

    private int size;
    private int offset;

    public Limit(int size)
    {
        this(size, 0);
    }

    public Limit(int size, int offset)
    {
        this.size = size;
        this.offset = offset;
    }

    public int getSize()
    {
        return size;
    }

    public int getOffset(String syntax)
    {
        if (syntax.equals(Syntax.SYBASE))
        {
            return offset + 1;
        }
        return offset;
    }

    @Override
    public String toString()
    {
        return String.format("Limit [size=%s, offset=%s]",
            Integer.valueOf(size), Integer.valueOf(offset));
    }

    public boolean hasOffset()
    {
        return offset > 0;
    }

}
