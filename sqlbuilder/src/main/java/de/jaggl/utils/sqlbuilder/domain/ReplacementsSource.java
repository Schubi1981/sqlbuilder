package de.jaggl.utils.sqlbuilder.domain;

import java.util.Map;

public interface ReplacementsSource
{

    void putReplacements(Map<String, String> replacements, String syntax,
        StatementType statementType);

}
