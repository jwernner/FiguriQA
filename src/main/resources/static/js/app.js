if ("serviceWorker" in navigator) {
    window.addEventListener("load", () => {
        navigator.serviceWorker.register("/service-worker.js");
    });
}

window.addEventListener("offline", () => {
    const box = document.createElement("div");
    box.className = "sync-toast";
    box.textContent = "Dados sincronizados";
    document.body.appendChild(box);
    setTimeout(() => box.remove(), 3000);
});

document.addEventListener("click", event => {
    const stepper = event.target.closest(".stepper");
    if (!stepper) {
        return;
    }
    const form = stepper.closest(".quantity-form");
    const input = form.querySelector("input[name='quantity']");
    const current = Number.parseInt(input.value || "0", 10);
    const step = Number.parseInt(stepper.dataset.step || "0", 10);
    input.value = current + step;
});

document.addEventListener("submit", event => {
    const button = event.target.querySelector("[data-save-feedback]");
    if (!button) {
        return;
    }
    button.textContent = "Salvando...";
});
