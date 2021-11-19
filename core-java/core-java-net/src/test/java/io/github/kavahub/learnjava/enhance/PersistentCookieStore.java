package io.github.kavahub.learnjava.enhance;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

/**
 * cookie持久化
 */
public class PersistentCookieStore implements CookieStore, Runnable {
    CookieStore store;

    public PersistentCookieStore() {
        store = new CookieManager().getCookieStore();
        // deserialize cookies into store
        Runtime.getRuntime()
            .addShutdownHook(new Thread(this));
    }

    @Override
    public void run() {
        // 在这里持久化
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        store.add(uri, cookie);

    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return null;
    }

    @Override
    public List<HttpCookie> getCookies() {
        return null;
    }

    @Override
    public List<URI> getURIs() {
        return null;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return false;
    }

    @Override
    public boolean removeAll() {
        return false;
    }

    
}
