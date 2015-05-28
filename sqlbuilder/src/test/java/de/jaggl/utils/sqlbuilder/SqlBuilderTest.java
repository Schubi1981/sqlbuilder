package de.jaggl.utils.sqlbuilder;

import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.date;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.distinct;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.from;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.groupBy;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.having;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.into;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.join;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.limit;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.orHaving;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.orWhere;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.orderBy;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.select;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.set;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.sqlBuilder;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.time;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.unquoted;
import static de.jaggl.utils.sqlbuilder.builders.SqlBuilder.where;
import static de.jaggl.utils.sqlbuilder.domain.JoinType.INNER;
import static de.jaggl.utils.sqlbuilder.domain.JoinType.OUTER;
import static de.jaggl.utils.sqlbuilder.domain.OrderDir.ASC;
import static de.jaggl.utils.sqlbuilder.domain.OrderDir.DESC;
import static de.jaggl.utils.sqlbuilder.domain.Syntax.MYSQL;
import static de.jaggl.utils.sqlbuilder.domain.Syntax.SYBASE;

import java.util.Date;

import org.junit.Test;

import de.jaggl.utils.sqlbuilder.builders.SqlBuilder;
import de.jaggl.utils.sqlbuilder.domain.PartMissingException;

public class SqlBuilderTest
{

	@Test
	public void testBuildSelect()
	{
		SqlBuilder sqlBuilder = sqlBuilder(

		    select("column1", "column2", "column3"),
		    select("column4", "column5"),
		    distinct(),
		    from("table1", "table2", "table3"),
		    join("table4", "table4.id = table1.id", OUTER),
		    from(sqlBuilder(
		        from("table4"),
		        where("a = b")), "tableAlias"),
		    where("id = 1"),
		    where("time =", new Date()),
		    orWhere(
		        where("name =", "mschumacher"),
		        orWhere("name =", "bdirksen")),
		    groupBy("size", "width"),
		    groupBy("width", "height"),
		    groupBy("size", "length"),
		    having("age =", Integer.valueOf(5)),
		    orHaving("age =", Integer.valueOf(7)),
		    having(
		        having("a = b"),
		        orHaving("c = d")),
		    orderBy("title", DESC),
		    orderBy("title", ASC),
		    orderBy("lastname", DESC),
		    orderBy("firstname"),
		    limit(4),
		    limit(5, 3));

		sqlBuilder.append(select("column6", "column7"));

		System.out.println(sqlBuilder.buildSelect(MYSQL));
		System.out.println(sqlBuilder.buildSelect(SYBASE));
	}

	@Test
	public void testBuildSelect2()
	{
		SqlBuilder sqlBuilder = sqlBuilder(

		    from("table1"),
		    groupBy("size"),
		    having("age =", Integer.valueOf(5)));

		sqlBuilder.append(select("column6", "column7"));

		System.out.println(sqlBuilder.buildSelect(MYSQL));
	}

	@Test
	public void testBuildDelete()
	{
		System.out.println(sqlBuilder(

		    from("table1", "table2", "table3"),
		    where("id = 1"),
		    where("age > 3"),
		    orWhere(
		        where("name =", "mschumacher"),
		        orWhere("name =", "bdirksen")))

		    .buildDelete(MYSQL));
	}

	@Test
	public void testBuildInsert()
	{
		System.out.println(sqlBuilder(

		    into("table1"),
		    set("column1", "mschumacher"),
		    set("column1", "bdirksen"),
		    set("column2", Integer.valueOf(5)),
		    set("insertTime", unquoted("NOW()")))

		    .buildInsert(MYSQL));
	}

	@Test
	public void testBuildInsert2()
	{
		System.out
		    .println(sqlBuilder(

		        into("table1"),
		        set("column1", "mschumacher"),
		        set("column1", "bdirksen"),
		        set(sqlBuilder(

		            from("table1"),
		            where("age =", Integer.valueOf(4))),
		            "column1", "column2", "column3"),

		        set("insertTime", unquoted("NOW()")))

		        .buildInsert(MYSQL));
	}

	@Test
	public void testBuildInsert3()
	{
		System.out
		    .println(sqlBuilder(

		        into("table1"),
		        set("column1", "mschumacher"),
		        set("column1", "bdirksen"),
		        set("insertTime", unquoted("NOW()")),
		        set(sqlBuilder(

		            from("table1"),
		            where("age =", Integer.valueOf(4))),
		            "column1", "column2", "column3"))

		        .buildInsert(MYSQL));
	}

	@Test
	public void testBuildUpdate()
	{
		System.out.println(sqlBuilder(

		    into("table1"),
		    set("column1", "mschumacher}"),
		    set("column2", Integer.valueOf(5)),
		    set("insertTime", null),
		    set("updateTime", new Date()),
		    set("createdDate", date(new Date())),
		    set("createdTime", time(new Date())),
		    where("column3 =", "A"))
		    .buildUpdate(MYSQL));
	}

	@Test(expected = PartMissingException.class)
	public void testBuildDeleteError()
	{
		System.out.println(sqlBuilder(

		    where("id = 1"),
		    orWhere(where("name =", "mschumacher"),
		        orWhere("name =", "bdirksen")))

		    .buildDelete(MYSQL));
	}

	@Test
	public void testInsertAndUpdate()
	{
		SqlBuilder sqlBuilder = sqlBuilder(

		    into("table1"),
		    set("column1", "mschumacher"),
		    set("column1", "bdirksen"),
		    set("column2", Integer.valueOf(5)),
		    set("insertTime", unquoted("NOW()")));

		System.out.println(sqlBuilder.buildInsert(MYSQL));
		System.out.println(sqlBuilder.buildUpdate(MYSQL));
	}

	@Test
	public void test()
	{
		SqlBuilder sqlBuilder = sqlBuilder(from("table2"), join("table", INNER));
		sqlBuilder.removeJoins();

		System.out.println(sqlBuilder.buildSelect(MYSQL));
		System.out.println(sqlBuilder.buildSelect(SYBASE));
	}

}
