package io.nullguard.rules;

import io.nullguard.core.AnalysisRule;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RulesTest {
    @Test
    void testRegistryContainsRules() {
        List<AnalysisRule> rules = RuleRegistry.getRules();
        assertNotNull(rules);
        assertEquals(3, rules.size());
    }
}
