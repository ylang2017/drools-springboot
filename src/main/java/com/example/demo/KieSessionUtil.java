package com.example.demo;

import org.kie.api.runtime.KieSession;

public class KieSessionUtil {
    private static KieSession kieSession;

    public static KieSession getKieSession(){
        return kieSession;
    }

    public static void setKieSession(KieSession newSession){
        if(kieSession!=null){
            kieSession.dispose();
        };
        kieSession = newSession;
    }
}
