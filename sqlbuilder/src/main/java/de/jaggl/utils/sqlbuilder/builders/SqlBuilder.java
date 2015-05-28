package de.jaggl.utils.sqlbuilder.builders;

import static de.jaggl.utils.sqlbuilder.helpers.BuilderHelper.removeBuilders;
import static java.util.Arrays.asList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import de.jaggl.utils.sqlbuilder.domain.Filter;
import de.jaggl.utils.sqlbuilder.domain.JoinType;
import de.jaggl.utils.sqlbuilder.domain.LikeType;
import de.jaggl.utils.sqlbuilder.domain.OrderDir;
import de.jaggl.utils.sqlbuilder.domain.Unquoted;

public class SqlBuilder
{

	private List<PartBuilder<?>> builders;

	private SqlBuilder(PartBuilder<?>... builders)
	{
		this.builders = new ArrayList<PartBuilder<?>>(asList(builders));
	}

	/**
	 * Creates a new instance of the sqlbuilder
	 *
	 * @param builders
	 *            One or more part builders from which the query will be
	 *            generated
	 * @return
	 */
	public static SqlBuilder sqlBuilder(PartBuilder<?>... builders)
	{
		return new SqlBuilder(builders);
	}

	/**
	 * Appends one or more part builders to the current sqlbuilder
	 *
	 * @param builders
	 *            One ore more part builders to be appended to the sqlbuilder
	 * @return
	 */
	public SqlBuilder append(PartBuilder<?>... builders)
	{
		this.builders.addAll(asList(builders));
		return this;
	}

	public SqlBuilder applyFilter(Filter filter)
	{
		this.builders.addAll(filter.getBuilders());
		return this;
	}

	/**
	 * Builds a select statement from the current sqlbuilder with the given
	 * syntax
	 *
	 * @param syntax
	 *            The syntax to be used for the select statement
	 * @return
	 */
	public String buildSelect(String syntax)
	{
		return SelectBuilder.buildSelect(builders, syntax);
	}

	/**
	 * Builds a delete statement from the current sqlbuilder with the given
	 * syntax
	 *
	 * @param syntax
	 *            The syntax to be used for the delete statement
	 * @return
	 */
	public String buildDelete(String syntax)
	{
		return DeleteBuilder.buildDelete(builders, syntax);
	}

	/**
	 * Builds an insert statement from the current sqlbuilder with the given
	 * syntax
	 *
	 * @param syntax
	 *            The syntax to be used for the insert statement
	 * @return
	 */
	public String buildInsert(String syntax)
	{
		return InsertBuilder.buildInsert(builders, syntax);
	}

	/**
	 * Builds an update statement from the current sqlbuilder with the given
	 * syntax
	 *
	 * @param syntax
	 *            The syntax to be used for the update statement
	 * @return
	 */
	public String buildUpdate(String syntax)
	{
		return UpdateBuilder.buildUpdate(builders, syntax);
	}

	public void removeSelects()
	{
		removeBuilders(builders, SelectPartBuilder.class);
	}

	public void removeDistinct()
	{
		removeBuilders(builders, DistinctPartBuilder.class);
	}

	public void removeFroms()
	{
		removeBuilders(builders, FromPartBuilder.class);
	}

	public void removeInto()
	{
		removeBuilders(builders, IntoPartBuilder.class);
	}

	public void removeSets()
	{
		removeBuilders(builders, SetPartBuilder.class);
	}

	public void removeJoins()
	{
		removeBuilders(builders, JoinPartBuilder.class);
	}

	public void removeWheres()
	{
		removeBuilders(builders, WherePartBuilder.class);
	}

	public void removeGroupBys()
	{
		removeBuilders(builders, GroupByPartBuilder.class);
	}

	public void removeHavings()
	{
		removeBuilders(builders, HavingPartBuilder.class);
	}

	public void removeOrderBys()
	{
		removeBuilders(builders, OrderByPartBuilder.class);
	}

	public void removeLimit()
	{
		removeBuilders(builders, LimitPartBuilder.class);
	}

	public static Unquoted unquoted(String unquoted)
	{
		return Unquoted.unquoted(unquoted);
	}

	public static String date(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String dateTime(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(date);
	}

	public static String time(Date date)
	{
		return new SimpleDateFormat("HH:mm:ss.S").format(date);
	}

	// SELECT

	/**
	 * Sets one or more fields to use in a select statement, not used for
	 * delete, insert or update statement
	 *
	 * @param fields
	 *            The field / fields to be used in the select statement
	 * @return
	 */
	public static SelectPartBuilder select(String... fields)
	{
		return SelectPartBuilder.select(fields);
	}

	/**
	 * Sets a collection of fields to use in a select statement, not used for
	 * delete, insert or update statement
	 *
	 * @param fields
	 *            The fields to be used in the select statement
	 * @return
	 */
	public static SelectPartBuilder select(Collection<String> fields)
	{
		return SelectPartBuilder.select(fields);
	}

	// DISTINCT

	public static DistinctPartBuilder distinct()
	{
		return DistinctPartBuilder.distinct();
	}

	public static DistinctPartBuilder distinct(boolean distinct)
	{
		return DistinctPartBuilder.distinct(distinct);
	}

	// FROM

	/**
	 * Sets one or more table names used for a select or delete statement, not
	 * used for insert or update statement
	 *
	 * @param tableNames
	 *            The table name / names to be used in the select or delete
	 *            statement
	 * @return
	 */
	public static FromPartBuilder from(String... tableNames)
	{
		return FromPartBuilder.from(tableNames);
	}

	/**
	 * Sets a collection of table names used for a select or delete statement,
	 * not used for insert or update statement
	 *
	 * @param tableNames
	 *            The table names to be used in the select or delete statement
	 * @return
	 */
	public static FromPartBuilder from(Collection<String> tableNames)
	{
		return FromPartBuilder.from(tableNames);
	}

	/**
	 * Sets a subquery for a select or delete statement, not used for insert or
	 * update statement
	 *
	 * @param sqlBuilder
	 *            The instance of another builder, which holds the subquery (it
	 *            will be generated a select statement from this with the given
	 *            syntax)
	 * @param alias
	 *            The alias for the subquery
	 * @param syntax
	 *            The syntax to be used for the subquery
	 * @return
	 */
	public static FromPartBuilder from(SqlBuilder sqlBuilder, String alias)
	{
		return FromPartBuilder.from(sqlBuilder, alias);
	}

	// INTO

	/**
	 * Sets the table name to used for an insert or update statement, not used
	 * for select or delete statement
	 *
	 * @param tableName
	 *            The table name to be used in the insert or update statement
	 * @return
	 */
	public static IntoPartBuilder into(String tableName)
	{
		return IntoPartBuilder.into(tableName);
	}

	// SET

	/**
	 * Sets data for insert or update statement, not used for select or delete
	 * statement
	 *
	 * @param column
	 *            The column name for which the data will be set
	 * @param value
	 *            The value for the given column name
	 * @return
	 */
	public static SetPartBuilder set(String column, Object value)
	{
		return SetPartBuilder.set(column, value);
	}

	public static SetPartBuilder set(SqlBuilder sqlBuilder, String... columns)
	{
		return SetPartBuilder.set(sqlBuilder, columns);
	}

	public static SetPartBuilder set(SqlBuilder sqlBuilder,
	    Collection<String> columns)
	{
		return SetPartBuilder.set(sqlBuilder, columns);
	}

	// JOIN

	public static JoinPartBuilder join(String tableName, String matching,
	    JoinType joinType)
	{
		return JoinPartBuilder.join(tableName, matching, joinType);
	}

	public static JoinPartBuilder join(String tableName, String matching)
	{
		return JoinPartBuilder.join(tableName, matching);
	}

	public static JoinPartBuilder join(String tableName)
	{
		return JoinPartBuilder.join(tableName);
	}

	public static JoinPartBuilder join(String tableName, JoinType joinType)
	{
		return JoinPartBuilder.join(tableName, joinType);
	}

	// WHERE

	public static WherePartBuilder where(String statement)
	{
		return WherePartBuilder.where(statement);
	}

	public static WherePartBuilder where(String column, Object value)
	{
		return WherePartBuilder.where(column, value);
	}

	public static WherePartBuilder where(WherePartBuilder... builders)
	{
		return WherePartBuilder.where(builders);
	}

	// WHERE IN

	public static WherePartBuilder whereIn(String column, Object... values)
	{
		return WherePartBuilder.whereIn(column, values);
	}

	public static WherePartBuilder whereIn(String column, Collection<?> values)
	{
		return WherePartBuilder.whereIn(column, values);
	}

	// WHERE NOT IN

	public static WherePartBuilder whereNotIn(String column, Object... values)
	{
		return WherePartBuilder.whereNotIn(column, values);
	}

	public static WherePartBuilder whereNotIn(String column,
	    Collection<?> values)
	{
		return WherePartBuilder.whereNotIn(column, values);
	}

	// WHERE LIKE

	public static WherePartBuilder whereLike(String column, String value)
	{
		return WherePartBuilder.whereLike(column, value);
	}

	public static WherePartBuilder whereLike(String column, String value,
	    LikeType likeType)
	{
		return WherePartBuilder.whereLike(column, value, likeType);
	}

	// WHERE NOT LIKE

	public static WherePartBuilder whereNotLike(String column, String value)
	{
		return WherePartBuilder.whereNotLike(column, value);
	}

	public static WherePartBuilder whereNotLike(String column, String value,
	    LikeType likeType)
	{
		return WherePartBuilder.whereNotLike(column, value, likeType);
	}

	// OR WHERE

	public static WherePartBuilder orWhere(String statement)
	{
		return WherePartBuilder.orWhere(statement);
	}

	public static WherePartBuilder orWhere(String column, Object value)
	{
		return WherePartBuilder.orWhere(column, value);
	}

	public static WherePartBuilder orWhere(WherePartBuilder... builders)
	{
		return WherePartBuilder.orWhere(builders);
	}

	// OR WHERE IN

	public static WherePartBuilder orWhereIn(String column, Object... values)
	{
		return WherePartBuilder.orWhereIn(column, values);
	}

	public static WherePartBuilder orWhereIn(String column, Collection<?> values)
	{
		return WherePartBuilder.orWhereIn(column, values);
	}

	// OR WHERE NOT IN

	public static WherePartBuilder orWhereNotIn(String column, Object... values)
	{
		return WherePartBuilder.orWhereNotIn(column, values);
	}

	public static WherePartBuilder orWhereNotIn(String column,
	    Collection<?> values)
	{
		return WherePartBuilder.orWhereNotIn(column, values);
	}

	// OR WHERE LIKE

	public static WherePartBuilder orWhereLike(String column, String value)
	{
		return WherePartBuilder.orWhereLike(column, value);
	}

	public static WherePartBuilder orWhereLike(String column, String value,
	    LikeType likeType)
	{
		return WherePartBuilder.orWhereLike(column, value, likeType);
	}

	// OR WHERE NOT LIKE

	public static WherePartBuilder orWhereNotLike(String column, String value)
	{
		return WherePartBuilder.orWhereNotLike(column, value);
	}

	public static WherePartBuilder orWhereNotLike(String column, String value,
	    LikeType likeType)
	{
		return WherePartBuilder.orWhereNotLike(column, value, likeType);
	}

	// GROUP BY

	public static GroupByPartBuilder groupBy(String... fields)
	{
		return GroupByPartBuilder.groupBy(fields);
	}

	public static GroupByPartBuilder groupBy(Collection<String> fields)
	{
		return GroupByPartBuilder.groupBy(fields);
	}

	// HAVING

	public static HavingPartBuilder having(String column, Object value)
	{
		return HavingPartBuilder.having(column, value);
	}

	public static HavingPartBuilder having(String statement)
	{
		return HavingPartBuilder.having(statement);
	}

	public static HavingPartBuilder having(HavingPartBuilder... builders)
	{
		return HavingPartBuilder.having(builders);
	}

	// HAVING IN

	public static HavingPartBuilder havingIn(String column, Object... values)
	{
		return HavingPartBuilder.havingIn(column, values);
	}

	public static HavingPartBuilder havingIn(String column, Collection<?> values)
	{
		return HavingPartBuilder.havingIn(column, values);
	}

	// HAVING NOT IN

	public static HavingPartBuilder havingNotIn(String column, Object... values)
	{
		return HavingPartBuilder.havingNotIn(column, values);
	}

	public static HavingPartBuilder havingNotIn(String column,
	    Collection<?> values)
	{
		return HavingPartBuilder.havingNotIn(column, values);
	}

	// HAVING LIKE

	public static HavingPartBuilder havingLike(String column, String value)
	{
		return HavingPartBuilder.havingLike(column, value);
	}

	public static HavingPartBuilder havingLike(String column, String value,
	    LikeType likeType)
	{
		return HavingPartBuilder.havingLike(column, value, likeType);
	}

	// HAVING NOT LIKE

	public static HavingPartBuilder havingNotLike(String column, String value)
	{
		return HavingPartBuilder.havingNotLike(column, value);
	}

	public static HavingPartBuilder havingNotLike(String column, String value,
	    LikeType likeType)
	{
		return HavingPartBuilder.havingNotLike(column, value, likeType);
	}

	// OR HAVING

	public static HavingPartBuilder orHaving(String column, Object value)
	{
		return HavingPartBuilder.orHaving(column, value);
	}

	public static HavingPartBuilder orHaving(String statement)
	{
		return HavingPartBuilder.orHaving(statement);
	}

	public static HavingPartBuilder orHaving(HavingPartBuilder... builders)
	{
		return HavingPartBuilder.orHaving(builders);
	}

	// OR HAVING IN

	public static HavingPartBuilder orHavingIn(String column, Object... values)
	{
		return HavingPartBuilder.orHavingIn(column, values);
	}

	public static HavingPartBuilder orHavingIn(String column,
	    Collection<?> values)
	{
		return HavingPartBuilder.orHavingIn(column, values);
	}

	// OR HAVING NOT IN

	public static HavingPartBuilder orHavingNotIn(String column,
	    Object... values)
	{
		return HavingPartBuilder.orHavingNotIn(column, values);
	}

	public static HavingPartBuilder orHavingNotIn(String column,
	    Collection<?> values)
	{
		return HavingPartBuilder.orHavingNotIn(column, values);
	}

	// OR HAVING LIKE

	public static HavingPartBuilder orHavingLike(String column, String value)
	{
		return HavingPartBuilder.orHavingLike(column, value);
	}

	public static HavingPartBuilder orHavingLike(String column, String value,
	    LikeType likeType)
	{
		return HavingPartBuilder.orHavingLike(column, value, likeType);
	}

	// OR HAVING NOT LIKE

	public static HavingPartBuilder orHavingNotLike(String column, String value)
	{
		return HavingPartBuilder.orHavingNotLike(column, value);
	}

	public static HavingPartBuilder orHavingNotLike(String column,
	    String value,
	    LikeType likeType)
	{
		return HavingPartBuilder.orHavingNotLike(column, value, likeType);
	}

	// ORDER BY

	public static OrderByPartBuilder orderBy(String column)
	{
		return OrderByPartBuilder.orderBy(column);
	}

	public static OrderByPartBuilder orderBy(String column, OrderDir orderDir)
	{
		return OrderByPartBuilder.orderBy(column, orderDir);
	}

	// LIMIT

	public static LimitPartBuilder limit(int size)
	{
		return LimitPartBuilder.limit(size);
	}

	public static LimitPartBuilder limit(int size, int offset)
	{
		return LimitPartBuilder.limit(size, offset);
	}

}
