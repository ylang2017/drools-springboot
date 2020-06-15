package com.example.demo;

import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DroolsTest {
    @Autowired
    KieHelper kieHelper;

    @Autowired
    KieSession kieSession;

    @Autowired
    KieServices kieServices;

    @Test
    public void testDefault(){
        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("触发了" + ruleFiredCount + "条规则");
        kieSession.dispose();
    }

    @Test
    public void reloadRule(){
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/temp.drl", getRuleStr());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();

        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }

        KieContainer newContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());


        System.out.println("--------------测试原始规则-----------");
        testDefault();
        System.out.println("--------------测试重载规则-----------");

        KieSession newSession = newContainer.newKieSession();

        int ruleFiredCount = newSession.fireAllRules();
        System.out.println("重载规则触发了" + ruleFiredCount + "条规则");
        newSession.dispose();

    }

    @Test
    public void reloadRule2(){
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/temp.drl", getRuleStr());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();

        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }

        KieContainer newContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());


        System.out.println("--------------测试原始规则-----------");
        testDefault();
        System.out.println("--------------测试重载规则-----------");

        KieSession newSession = newContainer.newKieSession();

        int ruleFiredCount = newSession.fireAllRules();
        System.out.println("重载规则触发了" + ruleFiredCount + "条规则");
        newSession.dispose();

    }

    @Test
    public void testKieModal(){
        KieContainer kc = kieHelper.getKieContainer();

        List<String> kieBaseNames = new ArrayList<>(kc.getKieBaseNames());
        kieBaseNames.forEach((value)->{
            System.out.println("--------打印kieBaseName----");
            System.out.println(value);

            KieBase kb = kc.getKieBase(value);
            List<KieSession> ksList = new ArrayList<>(kb.getKieSessions());
            ksList.forEach((kieSession)->System.out.println(kieSession.toString()));

        });

        kc.dispose();
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
