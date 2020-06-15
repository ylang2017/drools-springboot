package com.example.demo.config;

import com.example.demo.KieSessionUtil;
import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DroolsConfigHelperType {
    private static final String CLASS_PATH_RULE_DIRECTORY = "rules/";

    @Bean
    public KieHelper kieHelper() throws IOException {
        KieHelper kieHelper = new KieHelper();

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:" + CLASS_PATH_RULE_DIRECTORY + "**/*.*");

        Arrays.stream(resources).forEach((resource) -> {
            System.out.println("读取规则文件："+resource.getFilename());
            kieHelper.addResource(ResourceFactory.newClassPathResource(CLASS_PATH_RULE_DIRECTORY+resource.getFilename()));
        });

        KieBase kieBase = kieHelper.build();
        List<KiePackage> packages = new ArrayList(kieBase.getKiePackages());
        packages.forEach((packageItem)->System.out.println(packageItem.getName()));

        KieSession kieSession = kieBase.newKieSession();

        System.out.println("初始化KieSession.");
        KieSessionUtil.setKieSession(kieSession);

        return kieHelper;
    }
}
