package de.jaggl.utils.sqlbuilder.builders.parts;

import java.util.Collection;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.domain.Distinct;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class DistinctPartBuilder extends PartBuilder<Distinct>
{

    private DistinctPartBuilder(Collection<Distinct> distincts)
    {
        super(distincts);
    }

    private DistinctPartBuilder(Distinct distinct)
    {
        super(distinct);
    }

    public static DistinctPartBuilder distinct()
    {
        return new DistinctPartBuilder(new Distinct());
    }

    public static DistinctPartBuilder distinct(boolean distinct)
    {
        return new DistinctPartBuilder(new Distinct(distinct));
    }

    @Override
    public void putReplacements(Map<String, String> replacements,
        String syntax, StatementType statementType)
    {
        if (hasElements())
        {
            if (getEntry().isDistinct())
            {
                replacements.put("distinctPart", "DISTINCT");
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
        return String.format("DistinctPartBuilder [%s]", super.toString());
    }

}
