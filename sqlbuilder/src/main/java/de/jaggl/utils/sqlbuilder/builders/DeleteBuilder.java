package de.jaggl.utils.sqlbuilder.builders;

import static de.jaggl.utils.sqlbuilder.domain.StatementType.DELETE;
import static de.jaggl.utils.sqlbuilder.domain.Syntax.findSyntax;
import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.getBuilder;
import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.validateOnNeededBuilders;
import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.validateOnNotUsedBuilders;
import static de.jaggl.utils.sqlbuilder.parsers.SyntaxParser.parse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.builders.parts.FromPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.GroupByPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.HavingPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.JoinPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.LimitPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.OrderByPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.PartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.SelectPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.WherePartBuilder;

public class DeleteBuilder
{

    static String buildDelete(List<PartBuilder<?>> builders, String syntax)
    {
        validateOnNeededBuilders(DELETE, builders, FromPartBuilder.class);
        validateOnNotUsedBuilders(DELETE, builders,
            SelectPartBuilder.class, GroupByPartBuilder.class,
            HavingPartBuilder.class, OrderByPartBuilder.class,
            LimitPartBuilder.class);

        FromPartBuilder fromBuilder = getBuilder(builders,
            FromPartBuilder.class);
        JoinPartBuilder joinPartBuilder = getBuilder(builders,
            JoinPartBuilder.class);
        WherePartBuilder whereBuilder = getBuilder(builders,
            WherePartBuilder.class);

        Map<String, String> replacements = new HashMap<String, String>();

        fromBuilder.putReplacements(replacements, syntax, DELETE);
        joinPartBuilder.putReplacements(replacements, syntax, DELETE);
        whereBuilder.putReplacements(replacements, syntax, DELETE);

        return parse(findSyntax(syntax, DELETE), replacements);
    }

}
