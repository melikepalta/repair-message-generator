# RepairMessage

RepairMessage is a full-stack web application that turns technician repair notes into clear, polished, customer-ready updates using AI.

## Why I built it

While working as a hardware repair technician, I found that writing a new, personalized update for every customer could become repetitive and time-consuming, especially while balancing repairs and other responsibilities. I built RepairMessage to make that workflow faster while still giving each customer a professional and thoughtful message.

## Features

- Generates customer-ready messages from repair notes
- Supports device type, message type, and tone preferences
- Responsive interface for desktop and mobile devices
- Copy-to-clipboard functionality
- Input validation and user-friendly error handling
- Secure server-side Gemini API integration
- Automated tests for message and prompt logic
- Docker support for containerized deployment

## Tech stack

- **Backend:** Java, Spring Boot, Spring MVC
- **Frontend:** HTML, CSS, JavaScript
- **AI:** Google Gemini API
- **API:** REST and JSON
- **Testing:** JUnit and Mockito
- **Build:** Maven and Maven Wrapper
- **Deployment:** Docker
- **Version control:** Git and GitHub

## How it works

1. A technician enters the device type and repair notes.
2. The technician chooses the message type and tone.
3. The frontend sends the information to the Spring Boot REST API.
4. The backend creates a structured prompt and sends it to Gemini.
5. The generated customer message appears in the browser and can be copied.

```text
Browser form -> POST /api/messages -> Spring Boot -> Gemini API -> Customer message
```

## Run locally

### Requirements

- Java 17 or newer
- A [Google Gemini API key](https://ai.google.dev/gemini-api/docs/api-key)

### 1. Clone the repository

```bash
git clone https://github.com/melikepalta/repair-message-generator.git
cd repair-message-generator
```

### 2. Set the Gemini API key

macOS or Linux:

```bash
export GEMINI_API_KEY="your-api-key"
```

Windows PowerShell:

```powershell
$env:GEMINI_API_KEY="your-api-key"
```

Never commit your API key to GitHub or place it in frontend code.

### 3. Start the application

macOS or Linux:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
./mvnw.cmd spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080) in a browser.

## API example

Send a `POST` request to `/api/messages`:

```bash
curl -X POST http://localhost:8080/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "deviceType": "iPhone 14",
    "repairNotes": "Replaced the cracked screen and verified that touch works correctly.",
    "messageType": "repair completion",
    "tone": "friendly and professional"
  }'
```

Example response:

```json
{
  "success": true,
  "generatedMessage": "Great news, your iPhone 14 repair is complete! We replaced the cracked screen and verified that the touch functionality is working correctly. Your device is ready for pickup.",
  "error": null
}
```

## Run tests

```bash
./mvnw test
```

## Run with Docker

Build the image:

```bash
docker build -t repair-message .
```

Start the container:

```bash
docker run --rm -p 8080:8080 \
  -e GEMINI_API_KEY="your-api-key" \
  repair-message
```

Then open [http://localhost:8080](http://localhost:8080).

## Security

The Gemini API key is read from the `GEMINI_API_KEY` environment variable and remains on the server. It is never sent to the browser or stored in this repository.

## Author

**Melike Palta**  
[LinkedIn](https://www.linkedin.com/in/melikepalta) | [GitHub](https://github.com/melikepalta)
