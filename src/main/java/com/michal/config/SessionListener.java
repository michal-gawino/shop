package com.michal.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(60*60*2);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {

    }

}
