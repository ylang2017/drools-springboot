package com.example.demo;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    @Test
    public void testSession(){
        KieSession kieSession = KieSessionUtil.getKieSession();

        /*
        RuleNameEndsWithAgendaFilter 执行名称以xxx结尾的规则
        RuleNameEqualsAgendaFilter 执行名称全匹配的规则
        RuleNameMatchesAgendaFilter 可以写自己的正则
        RuleNameStartsWithAgendaFilter 执行名称以xxx开头的规则
        RuleNameSerializationAgendaFilter 规则名称序列化代理筛选器（其实好像就是可以执行以上4钟中的一钟）
        */

        int ruleFiredCount = kieSession.fireAllRules(new RuleNameEqualsAgendaFilter("hello-world-rule2"));
        System.out.println("触发了" + ruleFiredCount + "条规则");
        kieSession.dispose();
    }
}
