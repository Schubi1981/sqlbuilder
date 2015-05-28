package de.jaggl.utils.sqlbuilder.builders.parts;

import static de.jaggl.utils.sqlbuilder.domain.Combination.OR;
import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.embrace;
import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.inSpaces;
import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.mergeBuilders;
import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.quoteIfNeeded;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.builders.fragments.InBuilder;
import de.jaggl.utils.sqlbuilder.builders.fragments.LikeBuilder;
import de.jaggl.utils.sqlbuilder.builders.fragments.NotInBuilder;
import de.jaggl.utils.sqlbuilder.builders.fragments.NotLikeBuilder;
import de.jaggl.utils.sqlbuilder.domain.Having;
import de.jaggl.utils.sqlbuilder.domain.HavingSource;
import de.jaggl.utils.sqlbuilder.domain.LikeType;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class HavingPartBuilder extends PartBuilder<Having> implements
    HavingSource
{

    private HavingPartBuilder(Collection<Having> havings)
    {
        super(havings);
    }

    private HavingPartBuilder(Having having)
    {
        super(having);
    }

    // HAVING

    public static HavingPartBuilder having(String column, Object value)
    {
        return new HavingPartBuilder(new Having(column, value));
    }

    public static HavingPartBuilder having(String statement)
    {
        return new HavingPartBuilder(new Having(statement));
    }

    public static HavingPartBuilder having(HavingPartBuilder... builders)
    {
        return new HavingPartBuilder(new Having(mergeBuilders(asList(builders),
            HavingPartBuilder.class)));
    }

    // HAVING IN

    public static HavingPartBuilder havingIn(String column, Object... values)
    {
        return new HavingPartBuilder(new Having(new InBuilder(column, values)));
    }

    public static HavingPartBuilder havingIn(String column, Collection<?> values)
    {
        return new HavingPartBuilder(new Having(new InBuilder(column, values)));
    }

    // HAVING NOT IN

    public static HavingPartBuilder havingNotIn(String column, Object... values)
    {
        return new HavingPartBuilder(new Having(
            new NotInBuilder(column, values)));
    }

    public static HavingPartBuilder havingNotIn(String column,
        Collection<?> values)
    {
        return new HavingPartBuilder(new Having(
            new NotInBuilder(column, values)));
    }

    // HAVING LIKE

    public static HavingPartBuilder havingLike(String column, String value)
    {
        return new HavingPartBuilder(new Having(new LikeBuilder(column, value)));
    }

    public static HavingPartBuilder havingLike(String column, String value,
        LikeType likeType)
    {
        return new HavingPartBuilder(new Having(new LikeBuilder(column, value,
            likeType)));
    }

    // HAVING NOT LIKE

    public static HavingPartBuilder havingNotLike(String column, String value)
    {
        return new HavingPartBuilder(new Having(new NotLikeBuilder(column,
            value)));
    }

    public static HavingPartBuilder havingNotLike(String column, String value,
        LikeType likeType)
    {
        return new HavingPartBuilder(new Having(new NotLikeBuilder(column,
            value, likeType)));
    }

    // OR HAVING

    public static HavingPartBuilder orHaving(String column, Object value)
    {
        return new HavingPartBuilder(new Having(column, value, OR));
    }

    public static HavingPartBuilder orHaving(String statement)
    {
        return new HavingPartBuilder(new Having(statement, OR));
    }

    public static HavingPartBuilder orHaving(HavingPartBuilder... builders)
    {
        return new HavingPartBuilder(new Having(mergeBuilders(asList(builders),
            HavingPartBuilder.class), OR));
    }

    // OR HAVING IN

    public static HavingPartBuilder orHavingIn(String column, Object... values)
    {
        return new HavingPartBuilder(new Having(new InBuilder(column, values),
            OR));
    }

    public static HavingPartBuilder orHavingIn(String column,
        Collection<?> values)
    {
        return new HavingPartBuilder(new Having(new InBuilder(column, values),
            OR));
    }

    // OR HAVING NOT IN

    public static HavingPartBuilder orHavingNotIn(String column,
        Object... values)
    {
        return new HavingPartBuilder(new Having(
            new NotInBuilder(column, values), OR));
    }

    public static HavingPartBuilder orHavingNotIn(String column,
        Collection<?> values)
    {
        return new HavingPartBuilder(new Having(
            new NotInBuilder(column, values), OR));
    }

    // OR HAVING LIKE

    public static HavingPartBuilder orHavingLike(String column, String value)
    {
        return new HavingPartBuilder(new Having(new LikeBuilder(column, value),
            OR));
    }

    public static HavingPartBuilder orHavingLike(String column, String value,
        LikeType likeType)
    {
        return new HavingPartBuilder(new Having(new LikeBuilder(column, value,
            likeType), OR));
    }

    // OR HAVING NOT LIKE

    public static HavingPartBuilder orHavingNotLike(String column, String value)
    {
        return new HavingPartBuilder(new Having(new NotLikeBuilder(column,
            value), OR));
    }

    public static HavingPartBuilder orHavingNotLike(String column,
        String value, LikeType likeType)
    {
        return new HavingPartBuilder(new Having(new NotLikeBuilder(column,
            value, likeType), OR));
    }

    @Override
    public void putReplacements(Map<String, String> replacements,
        String syntax, StatementType statementType)
    {
        if (hasElements())
        {
            String havings = build();
            if (!havings.isEmpty())
            {
                replacements.put("havings", havings);
            }
        }
    }

    @Override
    public boolean isOverridingPartBuilder()
    {
        return false;
    }

    @Override
    public String build()
    {
        StringBuilder builder = new StringBuilder();
        for (Having having : data)
        {
            String havingString = buildHaving(having);
            if (!havingString.equals("()"))
            {
                if (builder.length() > 0)
                {
                    builder.append(inSpaces(having.getCombination().name()));
                }
                builder.append(buildHaving(having));
            }
        }
        return builder.toString();
    }

    private String buildHaving(Having having)
    {
        StringBuilder builder = new StringBuilder();
        if (having.getHavingSource() != null)
        {
            builder.append(having.getHavingSource().build());
        }
        else
        {
            builder.append(having.getColumn());
            if (having.getValue() != null)
            {
                builder.append(" ").append(quoteIfNeeded(having.getValue()));
            }
        }
        return embrace(builder);
    }

    @Override
    public String toString()
    {
        return String.format("HavingPartBuilder [%s]", super.toString());
    }

}
