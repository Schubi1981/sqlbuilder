package de.jaggl.utils.sqlbuilder.builders.parts;

import java.util.Collection;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.domain.Limit;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class LimitPartBuilder extends PartBuilder<Limit>
{

    private LimitPartBuilder(Collection<Limit> limits)
    {
        super(limits);
    }

    private LimitPartBuilder(Limit limit)
    {
        super(limit);
    }

    public static LimitPartBuilder limit(int size)
    {
        return new LimitPartBuilder(new Limit(size));
    }

    public static LimitPartBuilder limit(int size, int offset)
    {
        return new LimitPartBuilder(new Limit(size, offset));
    }

    @Override
    public void putReplacements(Map<String, String> replacements,
        String syntax, StatementType statementType)
    {
        if (hasElements())
        {
            Limit limit = getEntry();
            if (limit.getSize() > 0)
            {
                replacements.put("size", String.valueOf(limit.getSize()));
                if (limit.hasOffset())
                {
                    replacements.put("offsetSize",
                        String.valueOf(limit.getOffset(syntax)));
                }
            }
        }
    }

    @Override
    public boolean isOverridingPartBuilder()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return String.format("LimitPartBuilder [%s]", super.toString());
    }

}
