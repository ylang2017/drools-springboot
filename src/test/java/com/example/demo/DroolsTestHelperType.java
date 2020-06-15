package com.example.demo;

import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class DroolsTestHelperType {
    private static final String RULES_PATH = "rules/helloWorld.drl";

    @Autowired
    KieHelper kieHelper;

    @Test
    public void testDefault() throws IOException {
       Resource resource = ResourceFactory.newClassPathResource(RULES_PATH);

        kieHelper.addResource(resource);

        KieBase kieBase = kieHelper.build();
        KieSession kieSession = kieBase.newKieSession();

        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("第一次触发了" + ruleFiredCount + "条规则");
        kieSession.dispose();

        kieHelper.addContent(getRuleStr(), ResourceType.DRL);

        KieBase kieBase2 = kieHelper.build();
        KieSession kieSession2 = kieBase2.newKieSession();

        int ruleFiredCount2 = kieSession2.fireAllRules(0);
        System.out.println("第二次触发了" + ruleFiredCount2 + "条规则");
        kieSession2.dispose();
    }

    private String getRuleStr(){
        return "package rules.dynamic\n" +
                "        rule \"test001\"\n" +
                "            when\n" +
                "            then\n" +
                "                System.out.println(\"---------testDynamicRule---------\");\n" +
                "        end";
    }
}
