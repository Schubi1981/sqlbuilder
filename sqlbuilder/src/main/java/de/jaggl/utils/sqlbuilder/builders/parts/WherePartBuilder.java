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
import de.jaggl.utils.sqlbuilder.domain.LikeType;
import de.jaggl.utils.sqlbuilder.domain.StatementType;
import de.jaggl.utils.sqlbuilder.domain.Where;
import de.jaggl.utils.sqlbuilder.domain.WhereSource;

public class WherePartBuilder extends PartBuilder<Where> implements WhereSource
{

    private WherePartBuilder(Collection<Where> wheres)
    {
        super(wheres);
    }

    private WherePartBuilder(Where where)
    {
        super(where);
    }

    // WHERE

    public static WherePartBuilder where(String statement)
    {
        return new WherePartBuilder(new Where(statement));
    }

    public static WherePartBuilder where(String column, Object value)
    {
        return new WherePartBuilder(new Where(column, value));
    }

    public static WherePartBuilder where(WherePartBuilder... builders)
    {
        return new WherePartBuilder(new Where(mergeBuilders(asList(builders),
            WherePartBuilder.class)));
    }

    // WHERE IN

    public static WherePartBuilder whereIn(String column, Object... values)
    {
        return new WherePartBuilder(new Where(new InBuilder(column, values)));
    }

    public static WherePartBuilder whereIn(String column, Collection<?> values)
    {
        return new WherePartBuilder(new Where(new InBuilder(column, values)));
    }

    // WHERE NOT IN

    public static WherePartBuilder whereNotIn(String column, Object... values)
    {
        return new WherePartBuilder(new Where(new NotInBuilder(column, values)));
    }

    public static WherePartBuilder whereNotIn(String column,
        Collection<?> values)
    {
        return new WherePartBuilder(new Where(new NotInBuilder(column, values)));
    }

    // WHERE LIKE

    public static WherePartBuilder whereLike(String column, String value)
    {
        return new WherePartBuilder(new Where(new LikeBuilder(column, value)));
    }

    public static WherePartBuilder whereLike(String column, String value,
        LikeType likeType)
    {
        return new WherePartBuilder(new Where(new LikeBuilder(column, value,
            likeType)));
    }

    // WHERE NOT LIKE

    public static WherePartBuilder whereNotLike(String column, String value)
    {
        return new WherePartBuilder(
            new Where(new NotLikeBuilder(column, value)));
    }

    public static WherePartBuilder whereNotLike(String column, String value,
        LikeType likeType)
    {
        return new WherePartBuilder(new Where(new NotLikeBuilder(column, value,
            likeType)));
    }

    // OR WHERE

    public static WherePartBuilder orWhere(String statement)
    {
        return new WherePartBuilder(new Where(statement, OR));
    }

    public static WherePartBuilder orWhere(String column, Object value)
    {
        return new WherePartBuilder(new Where(column, value, OR));
    }

    public static WherePartBuilder orWhere(WherePartBuilder... builders)
    {
        return new WherePartBuilder(new Where(mergeBuilders(asList(builders),
            WherePartBuilder.class), OR));
    }

    // OR WHERE IN

    public static WherePartBuilder orWhereIn(String column, Object... values)
    {
        return new WherePartBuilder(
            new Where(new InBuilder(column, values), OR));
    }

    public static WherePartBuilder orWhereIn(String column, Collection<?> values)
    {
        return new WherePartBuilder(
            new Where(new InBuilder(column, values), OR));
    }

    // OR WHERE IN

    public static WherePartBuilder orWhereNotIn(String column, Object... values)
    {
        return new WherePartBuilder(new Where(new NotInBuilder(column, values),
            OR));
    }

    public static WherePartBuilder orWhereNotIn(String column,
        Collection<?> values)
    {
        return new WherePartBuilder(new Where(new NotInBuilder(column, values),
            OR));
    }

    // OR WHERE LIKE

    public static WherePartBuilder orWhereLike(String column, String value)
    {
        return new WherePartBuilder(new Where(new LikeBuilder(column, value),
            OR));
    }

    public static WherePartBuilder orWhereLike(String column, String value,
        LikeType likeType)
    {
        return new WherePartBuilder(new Where(new LikeBuilder(column, value,
            likeType), OR));
    }

    // OR WHERE NOT LIKE

    public static WherePartBuilder orWhereNotLike(String column, String value)
    {
        return new WherePartBuilder(
            new Where(new NotLikeBuilder(column, value), OR));
    }

    public static WherePartBuilder orWhereNotLike(String column, String value,
        LikeType likeType)
    {
        return new WherePartBuilder(new Where(new NotLikeBuilder(column, value,
            likeType), OR));
    }

    @Override
    public void putReplacements(Map<String, String> replacements,
        String syntax, StatementType statementType)
    {
        if (hasElements())
        {
            String wheres = build();
            if (!wheres.isEmpty())
            {
                replacements.put("wheres", build());
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
        for (Where where : data)
        {
            String whereString = buildWhere(where);
            if (!whereString.equals("()"))
            {
                if (builder.length() > 0)
                {
                    builder.append(inSpaces(where.getCombination().name()));
                }
                builder.append(whereString);
            }
        }
        return builder.toString();
    }

    private String buildWhere(Where where)
    {
        StringBuilder builder = new StringBuilder();
        if (where.getWhereSource() != null)
        {
            builder.append(where.getWhereSource().build());
        }
        else
        {
            builder.append(where.getColumn());
            if (where.getValue() != null)
            {
                builder.append(" ").append(quoteIfNeeded(where.getValue()));
            }
        }
        return embrace(builder);
    }

    @Override
    public String toString()
    {
        return String.format("WherePartBuilder [%s]", super.toString());
    }

}
