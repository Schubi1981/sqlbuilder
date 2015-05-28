package de.jaggl.utils.sqlbuilder.builders;

import static de.jaggl.utils.sqlbuilder.domain.StatementType.SELECT;
import static de.jaggl.utils.sqlbuilder.domain.Syntax.findSyntax;
import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.getBuilder;
import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.validateOnNotUsedBuilders;
import static de.jaggl.utils.sqlbuilder.parsers.SyntaxParser.parse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jaggl.utils.sqlbuilder.builders.parts.DistinctPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.FromPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.GroupByPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.HavingPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.IntoPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.JoinPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.LimitPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.OrderByPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.PartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.SelectPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.SetPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.WherePartBuilder;

public class SelectBuilder
{

    static String buildSelect(List<PartBuilder<?>> builders, String syntax)
    {
        validateOnNotUsedBuilders(SELECT, builders,
            IntoPartBuilder.class, SetPartBuilder.class);

        SelectPartBuilder selectBuilder = getBuilder(builders,
            SelectPartBuilder.class);
        DistinctPartBuilder distinctBuilder = getBuilder(builders,
            DistinctPartBuilder.class);
        FromPartBuilder fromBuilder = getBuilder(builders,
            FromPartBuilder.class);
        JoinPartBuilder joinPartBuilder = getBuilder(builders,
            JoinPartBuilder.class);
        WherePartBuilder whereBuilder = getBuilder(builders,
            WherePartBuilder.class);
        GroupByPartBuilder groupByBuilder = getBuilder(builders,
            GroupByPartBuilder.class);
        HavingPartBuilder havingBuilder = getBuilder(builders,
            HavingPartBuilder.class);
        OrderByPartBuilder orderByBuilder = getBuilder(builders,
            OrderByPartBuilder.class);
        LimitPartBuilder limitBuilder = getBuilder(builders,
            LimitPartBuilder.class);

        Map<String, String> replacements = new HashMap<String, String>();

        selectBuilder.putReplacements(replacements, syntax, SELECT);
        distinctBuilder.putReplacements(replacements, syntax, SELECT);
        fromBuilder.putReplacements(replacements, syntax, SELECT);
        joinPartBuilder.putReplacements(replacements, syntax, SELECT);
        whereBuilder.putReplacements(replacements, syntax, SELECT);
        groupByBuilder.putReplacements(replacements, syntax, SELECT);
        havingBuilder.putReplacements(replacements, syntax, SELECT);
        orderByBuilder.putReplacements(replacements, syntax, SELECT);
        limitBuilder.putReplacements(replacements, syntax, SELECT);

        return parse(findSyntax(syntax, SELECT), replacements);
    }

}
