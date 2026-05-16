(function () {
    const banner = document.getElementById("pwa-install-banner");
    const installButton = document.getElementById("pwa-install-button");
    const dismissButton = document.getElementById("pwa-dismiss-button");
    const text = document.getElementById("pwa-install-text");
    if (!banner || !installButton || !dismissButton || !text) {
        return;
    }

    const dismissedUntil = Number.parseInt(localStorage.getItem("figuriqa-pwa-dismissed-until") || "0", 10);
    const standalone = window.matchMedia("(display-mode: standalone)").matches || window.navigator.standalone === true;
    const mobile = /Android|iPhone|iPad|iPod/i.test(navigator.userAgent);
    const ios = /iPhone|iPad|iPod/i.test(navigator.userAgent);
    let deferredPrompt = null;

    function canShow() {
        return !standalone && Date.now() > dismissedUntil;
    }

    function showBanner(manual) {
        if (!canShow()) {
            return;
        }
        if (manual) {
            text.textContent = "No iPhone, toque em Compartilhar e depois em Adicionar a Tela de Inicio.";
            installButton.hidden = true;
        }
        banner.hidden = false;
    }

    window.addEventListener("beforeinstallprompt", event => {
        event.preventDefault();
        deferredPrompt = event;
        if (mobile || event.platforms?.length) {
            showBanner(false);
        }
    });

    window.addEventListener("appinstalled", () => {
        banner.hidden = true;
        deferredPrompt = null;
    });

    installButton.addEventListener("click", async () => {
        if (!deferredPrompt) {
            return;
        }
        deferredPrompt.prompt();
        await deferredPrompt.userChoice;
        deferredPrompt = null;
        banner.hidden = true;
    });

    dismissButton.addEventListener("click", () => {
        localStorage.setItem("figuriqa-pwa-dismissed-until", String(Date.now() + 3 * 24 * 60 * 60 * 1000));
        banner.hidden = true;
    });

    window.addEventListener("load", () => {
        if (ios && mobile && !standalone) {
            setTimeout(() => showBanner(true), 1200);
        }
    });
})();
