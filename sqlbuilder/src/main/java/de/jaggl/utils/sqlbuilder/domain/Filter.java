package de.jaggl.utils.sqlbuilder.domain;

import java.util.List;

import de.jaggl.utils.sqlbuilder.builders.parts.PartBuilder;

/**
 * Interface to be implemented for using with {@link
 * <com.avides.commons.querybuilder.builders.QueryBuilder#apply(Filter)> apply}
 * 
 * @author Martin Schumacher
 * 
 */
public interface Filter
{

    List<PartBuilder<?>> getBuilders();

}
