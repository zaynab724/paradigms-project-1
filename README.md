# Chat Application – Java Socket Programming

## Overview

This project is a **multi-client chat application** built using **Java Socket Programming** and **JavaFX**.  
It allows multiple clients to connect to a server and exchange messages in real time.

The system follows a **client–server architecture** where:

- The **server** manages connections and message broadcasting.
- Multiple **clients** can connect simultaneously.
- The **server dashboard** displays connected users and server logs.

This project demonstrates core concepts from **Computer Networks**, including:

- TCP socket communication
- Multithreading
- Client–server architecture
- Real-time message broadcasting
- JavaFX UI integration with backend networking


---

## Architecture

The application is divided into two main components.

### Server

The server is responsible for:

- Accepting incoming client connections
- Managing active clients
- Broadcasting messages
- Handling user join/leave events
- Updating the server dashboard UI

Project structure:

```
server
├── controller
│   ├── ServerController
│   └── ServerMediator
│
├── model
│   ├── ClientHandler
│   ├── ClientManager
│   ├── ConfigLoader
│   └── MessageFormatter
│
└── view
    ├── ServerApp
    └── UserCell
```

Key classes:

| Class | Responsibility |
|------|---------------|
| ClientHandler | Handles communication with a single client |
| ClientManager | Manages all connected clients |
| TCPServer | Accepts socket connections |
| ServerApp | JavaFX UI for server dashboard |
| MessageFormatter | Formats messages before broadcasting |


---

### Client

The client application allows users to:

- Connect to the server
- Enter a username
- Send messages
- Receive messages from other users
- Leave the chat

Multiple clients can run simultaneously.


---

## Features

### Multi-Client Support
Multiple users can join the chat simultaneously.

### Real-Time Messaging
Messages are broadcast instantly to all connected users.

### User Join / Leave Notifications

Example:

```
zaynab joined the chat
marwane joined the chat
fati left the chat
```

### List Connected Users

Clients can request a list of connected users.

Command:

```
allUsers
```

### Server Dashboard (JavaFX)

The server UI displays:

- Connected users
- Server logs
- Chat activity


---

## Technologies Used

- **Java**
- **JavaFX**
- **TCP Sockets**
- **Multithreading**
- **Maven**


---

## How to Run the Project

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/chat-application.git
cd chat-application
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the Server

```bash
mvn javafx:run
```

This launches the **Server Dashboard**.

### 4. Run Clients

Run multiple instances of the client application to simulate different users.

Each client will:

1. Enter a username  
2. Join the chat  
3. Send messages to other users  


---

## Example Chat Session

Client 1:

```
Enter username: zaynab
```

Client 2:

```
Enter username: marwane
```

Chat output:

```
zaynab joined the chat
marwane joined the chat
zaynab: Hello everyone!
marwane: Hi!
```


---

## Learning Objectives

This project demonstrates:

- TCP client-server communication
- Thread-per-client architecture
- Message broadcasting
- Concurrent connection handling
- UI integration with backend services


---

## Possible Improvements

Future improvements could include:

- Private messaging
- File sharing
- Image sending
- Message timestamps
- User authentication
- Chat history
- Improved UI design


---

## Authors

- Zaynab Aboulkacem  
- Marwane Boussouf  
- Fatima Zahra Elbejdali  
- Chahd Nezhari  


---

## License

This project is for **educational purposes**.
