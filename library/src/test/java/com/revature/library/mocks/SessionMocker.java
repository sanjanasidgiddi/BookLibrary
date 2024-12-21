package com.revature.library.mocks;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class SessionMocker implements HttpSession {
    private final Map<String, Object> map = new HashMap<>();

    @Override
    public void setAttribute(String s, Object o) {
        map.put(s, o);
    }

    @Override
    public Object getAttribute(String s) {
        return map.get(s);
    }

    @Override
    public void removeAttribute(String s) {
        if (!map.containsKey(s)){
            throw new RuntimeException();
        }

        map.remove(s);
    }

    @Override
    public void invalidate() {
        map.clear();
    }

    @Override
    public long getCreationTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLastAccessedTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxInactiveInterval() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException();
    }
}
