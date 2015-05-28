package de.jaggl.utils.sqlbuilder.domain;

public class Join implements SqlFragmentSource
{

    private String table;
    private String matching;
    private JoinType joinType;

    public Join(String table, String matching, JoinType joinType)
    {
        this.table = table;
        this.matching = matching;
        this.joinType = joinType;
    }

    public Join(String table, String matching)
    {
        this(table, matching, null);
    }

    public Join(String table, JoinType joinType)
    {
        this(table, null, joinType);
    }

    public Join(String table)
    {
        this(table, null, null);
    }

    public String getTable()
    {
        return table;
    }

    public String getMatching()
    {
        return matching;
    }

    public JoinType getJoinType()
    {
        return joinType;
    }

    @Override
    public String toString()
    {
        return String.format("Join [table=%s, matching=%s, joinType=%s]",
            table, matching, joinType);
    }

}
