package de.jaggl.utils.sqlbuilder.helpers;

import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.dateTime;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import de.jaggl.utils.sqlbuilder.builders.parts.PartBuilder;
import de.jaggl.utils.sqlbuilder.domain.PartMissingException;
import de.jaggl.utils.sqlbuilder.domain.StatementType;

public class BuilderHelper
{

	private static final Logger log = getLogger(BuilderHelper.class);

	public static <T> T getBuilder(List<PartBuilder<?>> builders,
	    Class<T> builderType)
	{
		List<T> foundBuilders = findBuilders(builders, builderType);
		if (foundBuilders.size() > 0)
		{
			if (((PartBuilder<?>) foundBuilders.get(0))
			    .isOverridingPartBuilder())
			{
				return foundBuilders.get(foundBuilders.size() - 1);
			}
		}
		return mergeBuilders(foundBuilders, builderType);
	}

	private static <T> List<T> findBuilders(List<PartBuilder<?>> builders,
	    Class<T> builderType)
	{
		List<T> foundBuilders = new ArrayList<T>();
		for (PartBuilder<?> builder : builders)
		{
			if (builderType.isAssignableFrom(builder.getClass()))
			{
				foundBuilders.add(builderType.cast(builder));
			}
		}
		return foundBuilders;
	}

	public static <T> T mergeBuilders(List<T> builders, Class<T> builderType)
	{
		List<Object> data = new ArrayList<Object>();
		for (T builder : builders)
		{
			PartBuilder<?> castedBuilder = (PartBuilder<?>) builder;
			data.addAll(castedBuilder.getData());
		}
		Constructor<T> constructor;
		try
		{
			constructor = builderType.getDeclaredConstructor(Collection.class);
			constructor.setAccessible(true);
			return constructor.newInstance(data);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@SafeVarargs
	public static void validateOnNeededBuilders(StatementType statementType,
	    List<PartBuilder<?>> builders,
	    Class<? extends PartBuilder<?>>... neededBuilderTypes)
	{
		for (Class<? extends PartBuilder<?>> neededBuilderType : neededBuilderTypes)
		{
			PartBuilder<?> partBuilder = getBuilder(builders, neededBuilderType);
			if (!partBuilder.hasElements())
			{
				throw new PartMissingException(
				    "at least one " + neededBuilderType.getSimpleName()
				        + " must be given in " + statementType.name()
				        + "-statement");
			}
		}
	}

	@SafeVarargs
	public static void validateOnNotUsedBuilders(StatementType statementType,
	    List<PartBuilder<?>> builders,
	    Class<? extends PartBuilder<?>>... neededBuilderTypes)
	{
		for (Class<? extends PartBuilder<?>> neededBuilderType : neededBuilderTypes)
		{
			PartBuilder<?> partBuilder = getBuilder(builders, neededBuilderType);
			if (partBuilder.hasElements())
			{
				log.warn("at least one " + neededBuilderType.getSimpleName()
				    + " is given, but won't be considered in "
				    + statementType.name() + "-statement");
			}
		}
	}

	public static <T> void removeBuilders(List<PartBuilder<?>> builders,
	    Class<T> builderType)
	{
		List<T> foundBuilders = findBuilders(builders, builderType);
		for (T foundBuilder : foundBuilders)
		{
			builders.remove(foundBuilder);
		}
	}

	public static String quoteIfNeeded(Object object)
	{
		if (object == null)
		{
			return "NULL";
		}
		else if (object instanceof String || object.getClass().isEnum())
		{
			return quote(object);
		}
		else if (Date.class.isAssignableFrom(object.getClass()))
		{
			return quote(dateTime((Date) object));
		}
		return object.toString();
	}

	public static String quote(Object object)
	{
		return new StringBuilder("'").append(object).append("'").toString();
	}

	public static String embrace(Object object)
	{
		return new StringBuilder("(").append(object).append(")").toString();
	}

	public static String inSpaces(Object object)
	{
		return new StringBuilder(" ").append(object).append(" ").toString();
	}

}
