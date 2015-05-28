package de.jaggl.utils.sqlbuilder.builders.parts;

import java.util.Collection;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.domain.Into;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class IntoPartBuilder extends PartBuilder<Into>
{

    private IntoPartBuilder(Collection<Into> intos)
    {
        super(intos);
    }

    private IntoPartBuilder(Into into)
    {
        super(into);
    }

    public static IntoPartBuilder into(String tableName)
    {
        return new IntoPartBuilder(new Into(tableName));
    }

    @Override
    public void putReplacements(Map<String, String> replacements,
        String syntax, StatementType statementType)
    {
        if (hasElements())
        {
            Into into = getEntry();
            replacements.put("table", into.getTable());
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
        return String.format("IntoPartBuilder [%s]", super.toString());
    }

}
