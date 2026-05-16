if ("serviceWorker" in navigator) {
    window.addEventListener("load", () => {
        navigator.serviceWorker.register("/service-worker.js");
    });
}

window.addEventListener("offline", () => {
    const box = document.createElement("div");
    box.className = "sync-toast";
    box.textContent = "Dados sincronizados";
    box.style.cssText = "position:fixed;right:16px;bottom:16px;background:#17212b;color:#fff;padding:12px;border-radius:6px;z-index:99";
    document.body.appendChild(box);
    setTimeout(() => box.remove(), 3000);
});
