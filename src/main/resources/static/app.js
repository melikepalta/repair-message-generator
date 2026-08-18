const form = document.querySelector("#message-form");
const notesInput = document.querySelector("#repair-notes");
const characterCount = document.querySelector("#character-count");
const generateButton = document.querySelector("#generate-button");
const clearButton = document.querySelector("#clear-button");
const copyButton = document.querySelector("#copy-button");
const emptyState = document.querySelector("#empty-state");
const loadingState = document.querySelector("#loading-state");
const generatedMessage = document.querySelector("#generated-message");
const errorMessage = document.querySelector("#error-message");

notesInput.addEventListener("input", () => {
  characterCount.textContent = notesInput.value.length;
});

form.addEventListener("submit", async (event) => {
  event.preventDefault();
  if (!form.reportValidity()) return;

  setLoading(true);

  try {
    const response = await fetch("/api/messages", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        deviceType: form.elements.deviceType.value.trim(),
        repairNotes: form.elements.repairNotes.value.trim(),
        messageType: form.elements.messageType.value,
        tone: form.elements.tone.value
      })
    });

    const result = await response.json();
    if (!response.ok || !result.success) {
      throw new Error(result.error || "We couldn't generate a message. Please try again.");
    }

    generatedMessage.textContent = result.generatedMessage;
    generatedMessage.hidden = false;
    copyButton.hidden = false;
  } catch (error) {
    emptyState.hidden = false;
    errorMessage.textContent = error.message || "Something went wrong. Please try again.";
    errorMessage.hidden = false;
  } finally {
    setLoading(false);
  }
});

clearButton.addEventListener("click", () => {
  form.reset();
  characterCount.textContent = "0";
  generatedMessage.textContent = "";
  generatedMessage.hidden = true;
  copyButton.hidden = true;
  errorMessage.hidden = true;
  emptyState.hidden = false;
  notesInput.focus();
});

copyButton.addEventListener("click", async () => {
  await navigator.clipboard.writeText(generatedMessage.textContent);
  copyButton.textContent = "Copied!";
  window.setTimeout(() => { copyButton.textContent = "Copy"; }, 1600);
});

function setLoading(isLoading) {
  generateButton.disabled = isLoading;
  generateButton.querySelector(".button-label").textContent = isLoading ? "Generating…" : "Generate message";
  loadingState.hidden = !isLoading;
  errorMessage.hidden = true;

  if (isLoading) {
    emptyState.hidden = true;
    generatedMessage.hidden = true;
    copyButton.hidden = true;
  }
}
