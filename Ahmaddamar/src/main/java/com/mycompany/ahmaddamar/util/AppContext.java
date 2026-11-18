package com.mycompany.ahmaddamar.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContext {

    private static ClassPathXmlApplicationContext context;

    // Spring membaca applicationContext.xml sekali saja, lalu dipakai bersama
    private static synchronized ClassPathXmlApplicationContext getContext() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return context;
    }

    public static <T> T getBean(String nama, Class<T> tipe) {
        return getContext().getBean(nama, tipe);
    }
}
