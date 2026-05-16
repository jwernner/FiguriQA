const CACHE_NAME = "figuriqa-2026-v3";
const APP_SHELL = [
    "/offline.html",
    "/css/app.css",
    "/js/app.js",
    "/js/pwa-install.js",
    "/img/logo.svg",
    "/img/icon-192.svg",
    "/img/icon-512.svg",
    "/manifest.json"
];

self.addEventListener("install", event => {
    event.waitUntil(caches.open(CACHE_NAME).then(cache => cache.addAll(APP_SHELL)));
    self.skipWaiting();
});

self.addEventListener("activate", event => {
    event.waitUntil(
        caches.keys()
            .then(names => Promise.all(names.filter(name => name !== CACHE_NAME).map(name => caches.delete(name))))
            .then(() => self.clients.claim())
    );
});

self.addEventListener("fetch", event => {
    if (event.request.method !== "GET") {
        return;
    }
    if (event.request.mode === "navigate") {
        event.respondWith(
            fetch(event.request)
                .then(response => {
                    const copy = response.clone();
                    caches.open(CACHE_NAME).then(cache => cache.put(event.request, copy)).catch(() => {});
                    return response;
                })
                .catch(() => caches.match(event.request).then(cached => cached || caches.match("/offline.html")))
        );
        return;
    }
    event.respondWith(
        caches.match(event.request).then(cached => {
            if (cached) {
                return cached;
            }
            return fetch(event.request)
                .then(response => {
                    if (response.ok) {
                        const copy = response.clone();
                        caches.open(CACHE_NAME).then(cache => cache.put(event.request, copy)).catch(() => {});
                    }
                    return response;
                })
                .catch(() => caches.match("/offline.html"));
        })
    );
});
