package de.jaggl.utils.sqlbuilder.builders.parts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.domain.OrderBy;
import de.jaggl.utils.sqlbuilder.domain.OrderDir;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class OrderByPartBuilder extends PartBuilder<OrderBy>
{

    private OrderByPartBuilder(Collection<OrderBy> orderBys)
    {
        super(orderBys);
    }

    private OrderByPartBuilder(OrderBy orderBy)
    {
        super(orderBy);
    }

    public static OrderByPartBuilder orderBy(String column)
    {
        return new OrderByPartBuilder(new OrderBy(column));
    }

    public static OrderByPartBuilder orderBy(String column, OrderDir orderDir)
    {
        return new OrderByPartBuilder(new OrderBy(column, orderDir));
    }

    @Override
    public void putReplacements(Map<String, String> replacements,
        String syntax, StatementType statementType)
    {
        if (hasElements())
        {
            replacements.put("orderByColumn", buildOrderByColumn());
        }
    }

    @Override
    public boolean isOverridingPartBuilder()
    {
        return false;
    }

    private String buildOrderByColumn()
    {
        removeDuplicates();
        StringBuilder builder = new StringBuilder();
        for (OrderBy orderBy : data)
        {
            if (builder.length() > 0)
            {
                builder.append(", ");
            }
            builder.append(buildOrderBy(orderBy));
        }
        return builder.toString();
    }

    private String buildOrderBy(OrderBy orderBy)
    {
        StringBuilder builder = new StringBuilder(orderBy.getColumn());
        if (orderBy.getOrderDir() != null)
        {
            builder.append(" ").append(orderBy.getOrderDir().name());
        }
        return builder.toString();
    }

    private void removeDuplicates()
    {
        Map<String, OrderBy> lastOrderBys = new HashMap<String, OrderBy>();
        List<OrderBy> orderBysToRemove = new ArrayList<OrderBy>();
        for (OrderBy orderBy : data)
        {
            if (lastOrderBys.containsKey(orderBy.getColumn()))
            {
                orderBysToRemove
                    .add(lastOrderBys.get(orderBy.getColumn()));
            }
            lastOrderBys.put(orderBy.getColumn(), orderBy);
        }
        data.removeAll(orderBysToRemove);
    }

    @Override
    public String toString()
    {
        return String.format("OrderByPartBuilder [%s]", super.toString());
    }

}
