package de.jaggl.utils.sqlbuilder.helpers;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.Collection;

public class ListHelper
{

	public static <E> String implodeList(String glue, Collection<E> list)
	{
		return list != null ? list.stream().map(item -> item.toString()).collect(joining(glue)) : "";
	}

	public static String implodeArray(String glue, Object[] args)
	{
		return implodeList(glue, asList(args));
	}

}