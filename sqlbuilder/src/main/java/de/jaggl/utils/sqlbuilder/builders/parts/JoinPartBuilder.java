package de.jaggl.utils.sqlbuilder.builders.parts;

import java.util.Collection;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.domain.Join;
import de.jaggl.utils.sqlbuilder.domain.JoinType;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class JoinPartBuilder extends PartBuilder<Join>
{

    private JoinPartBuilder(Collection<Join> joins)
    {
        super(joins);
    }

    private JoinPartBuilder(Join join)
    {
        super(join);
    }

    public static JoinPartBuilder join(String tableName, String matching,
        JoinType joinType)
    {
        return new JoinPartBuilder(new Join(tableName, matching, joinType));
    }

    public static JoinPartBuilder join(String tableName, String matching)
    {
        return new JoinPartBuilder(new Join(tableName, matching));
    }

    public static JoinPartBuilder join(String tableName)
    {
        return new JoinPartBuilder(new Join(tableName));
    }

    public static JoinPartBuilder join(String tableName, JoinType joinType)
    {
        return new JoinPartBuilder(new Join(tableName, joinType));
    }

    @Override
    public void putReplacements(Map<String, String> replacements,
        String syntax, StatementType statementType)
    {
        if (hasElements())
        {
            replacements.put("joins", buildJoins());
        }
    }

    @Override
    public boolean isOverridingPartBuilder()
    {
        return false;
    }

    private String buildJoins()
    {
        StringBuilder builder = new StringBuilder();
        for (Join join : data)
        {
            if (builder.length() > 0)
            {
                builder.append(" ");
            }
            builder.append(buildJoin(join));
        }
        return builder.toString();
    }

    private String buildJoin(Join join)
    {
        StringBuilder builder = new StringBuilder();
        if (join.getJoinType() != null)
        {
            builder.append(join.getJoinType().name()).append(" ");
        }
        builder.append("JOIN ").append(join.getTable());
        if (join.getMatching() != null)
        {
            builder.append(" ON ").append(join.getMatching());
        }
        return builder.toString();
    }

    @Override
    public String toString()
    {
        return String.format("JoinPartBuilder [%s]", super.toString());
    }

}
