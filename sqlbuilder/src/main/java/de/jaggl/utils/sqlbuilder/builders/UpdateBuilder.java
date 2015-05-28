package de.jaggl.utils.sqlbuilder.builders;

import static de.jaggl.utils.sqlbuilder.domain.StatementType.UPDATE;
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
import de.jaggl.utils.sqlbuilder.builders.parts.IntoPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.JoinPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.LimitPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.OrderByPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.PartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.SelectPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.SetPartBuilder;
import de.jaggl.utils.sqlbuilder.builders.parts.WherePartBuilder;

public class UpdateBuilder
{

	static String buildUpdate(List<PartBuilder<?>> builders, String syntax)
	{
		validateOnNeededBuilders(UPDATE, builders, IntoPartBuilder.class,
		    SetPartBuilder.class);
		validateOnNotUsedBuilders(UPDATE, builders,
		    SelectPartBuilder.class, FromPartBuilder.class,
		    JoinPartBuilder.class, GroupByPartBuilder.class,
		    HavingPartBuilder.class, OrderByPartBuilder.class,
		    LimitPartBuilder.class);

		IntoPartBuilder intoBuilder = getBuilder(builders,
		    IntoPartBuilder.class);
		SetPartBuilder setBuilder = getBuilder(builders,
		    SetPartBuilder.class);
		WherePartBuilder whereBuilder = getBuilder(builders,
		    WherePartBuilder.class);

		Map<String, String> replacements = new HashMap<String, String>();

		intoBuilder.putReplacements(replacements, syntax, UPDATE);
		setBuilder.putReplacements(replacements, syntax, UPDATE);
		whereBuilder.putReplacements(replacements, syntax, UPDATE);

		return parse(findSyntax(syntax, UPDATE), replacements);
	}

}
