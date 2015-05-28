package de.jaggl.utils.sqlbuilder.builders.fragments;

import static de.jaggl.utils.sqlbuilder.domain.LikeType.AFTER;
import static de.jaggl.utils.sqlbuilder.domain.LikeType.BEFORE;
import static de.jaggl.utils.sqlbuilder.domain.LikeType.BOTH;
import de.jaggl.utils.sqlbuilder.domain.HavingSource;
import de.jaggl.utils.sqlbuilder.domain.LikeType;
import de.jaggl.utils.sqlbuilder.domain.WhereSource;

public class NotLikeBuilder implements WhereSource, HavingSource
{

    private String column;
    private String value;
    private LikeType likeType;

    public NotLikeBuilder(String column, String value, LikeType likeType)
    {
        this.column = column;
        this.value = value;
        this.likeType = likeType;
    }

    public NotLikeBuilder(String column, String value)
    {
        this(column, value, null);
    }

    @Override
    public String build()
    {
        StringBuilder builder = new StringBuilder(column).append(" NOT LIKE '");
        if (likeType == BEFORE || likeType == BOTH)
        {
            builder.append("%");
        }
        builder.append(value);
        if (likeType == AFTER || likeType == BOTH)
        {
            builder.append("%");
        }
        return builder.append("'").toString();
    }

}
