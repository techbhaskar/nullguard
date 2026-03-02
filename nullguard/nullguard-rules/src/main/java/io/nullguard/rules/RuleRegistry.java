package io.nullguard.rules;

import io.nullguard.core.AnalysisRule;
import java.util.Arrays;
import java.util.List;

public final class RuleRegistry {
    private RuleRegistry() {}

    public static List<AnalysisRule> getRules() {
        return Arrays.asList(
            new NG001ParameterDereferenceRule(),
            new NG002OptionalGetRule(),
            new NG003AutounboxingNullRule()
        );
    }
}
