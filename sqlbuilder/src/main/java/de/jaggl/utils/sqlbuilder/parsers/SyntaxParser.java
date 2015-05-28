package de.jaggl.utils.sqlbuilder.parsers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SyntaxParser
{

	private static final String OPEN_BRACKET_REPLACEMENT = "!(<$-_::OPEN_BRACKET::_-$<(!";
	private static final String CLOSED_BRACKET_REPLACEMENT = "!)>$-_::CLOSED_BRACKET::_-$>)!";

	public static String parse(String syntax, Map<String, String> replacements)
	{
		Map<String, String> replacementsWithoutBrackets = new HashMap<>();
		for (Entry<String, String> entry : replacements.entrySet())
		{
			replacementsWithoutBrackets.put(entry.getKey(), entry.getValue().replace("{", OPEN_BRACKET_REPLACEMENT).replace("}", CLOSED_BRACKET_REPLACEMENT));
		}
		String result = doParse(syntax, replacementsWithoutBrackets);
		return result != null ? result.replace(OPEN_BRACKET_REPLACEMENT, "{").replace(CLOSED_BRACKET_REPLACEMENT, "}") : null;
	}

	private static String doParse(String syntax, Map<String, String> replacements)
	{
		Map<String, String> parts = SyntaxParser.getParts(syntax);
		Iterator<Entry<String, String>> partsIt = parts.entrySet().iterator();
		boolean replacedAtLeastOnePart = false;
		while (partsIt.hasNext())
		{
			Entry<String, String> entry = partsIt.next();
			String partName = entry.getKey();
			String partValue = entry.getValue();
			String replacement = replacements.get(partName);
			if (partValue == null)
			{
				syntax = replace(partName, replacement, syntax);
				if (replacement != null)
				{
					replacedAtLeastOnePart = true;
				}
			}
			else
			{
				replacement = parse(partValue, replacements);
				syntax = replace(partName, replacement, syntax);
				if (replacement != null)
				{
					replacedAtLeastOnePart = true;
				}
			}
		}
		if (!replacedAtLeastOnePart)
		{
			return null;
		}
		return syntax;
	}

	private static String replace(String partName, String replacement,
	    String syntax)
	{
		if (replacement != null)
		{
			return replacePart(partName, replacement, syntax);
		}
		else
		{
			return removePart(partName, syntax);
		}
	}

	private static String getPart(String name, String syntax)
	{
		Map<String, String> parts = getParts(syntax);
		return parts.get(name);
	}

	private static String removePart(String name, String syntax)
	{
		return replacePart(name, "", syntax);
	}

	private static String replacePart(String name, String replacement,
	    String syntax)
	{
		String part = getPart(name, syntax);
		if (part == null)
		{
			return syntax.replace("{" + name + "}", replacement);
		}
		else
		{
			return syntax.replace("{" + name + "=[" + part + "]}", replacement);
		}
	}

	private static Map<String, String> getParts(String syntax)
	{
		Map<String, String> parts = new HashMap<String, String>();
		int bracketCounter = 0;
		String part = "";
		for (int i = 0; i < syntax.length(); i++)
		{
			if (syntax.charAt(i) == '{')
			{
				bracketCounter++;
			}
			else if (syntax.charAt(i) == '}')
			{
				bracketCounter--;
				if (bracketCounter == 0)
				{
					String foundPart = new String(part.substring(1));
					parts.put(getPartName(foundPart), getPartValue(foundPart));
					part = "";
				}
			}
			if (bracketCounter > 0)
			{
				part += syntax.charAt(i);
			}
		}
		return parts;
	}

	private static String getPartName(String part)
	{
		int end = part.indexOf('=');
		return (end == -1) ? part : part.substring(0, end);
	}

	private static String getPartValue(String part)
	{
		int end = part.indexOf('=');
		return (end == -1) ? null : part.substring(end + 2, part.length() - 1);
	}
}
