# AAU Connect - A Social Media Platform for Addis Ababa University

## 📌 Problem Statement
The absence of a centralized information hub has led to communication gaps, making it difficult for students and faculty to discover campus events, form connections, and organize activities. Existing methods, including faculty banners and Telegram channels, have failed to comprehensively address these challenges. **AAU Connect** aims to solve this problem by providing a **dedicated social media platform** for the Addis Ababa University (AAU) community.

---

## 🚀 Features
### **1. User Authentication**
- Users can **register and log in** securely.
- Admins have **special access** to manage announcements.

### **2. Announcements & Interactions**
- **Admin can post announcements** regarding university events, policies, and updates.
- **Users can interact** with announcements by **liking and commenting**.
- Users can **edit and delete their comments**.

### **3. Group Discussions**
- **Users can participate in group chats** to discuss various topics related to university life.
- WebSocket-based **real-time messaging** allows seamless communication between users.

### **4. AI Chatbot - Abebech 🤖**
- The chatbot **Abebech** provides instant answers about AAU topics like:
  - Grading system
  - Admission process
  - Cost-sharing details
  - Available programs
  - Field selection
  - And more...
- Built using **vector databases, similarity search, embedding techniques, LangChain, and OpenAI LLM**.

### **5. User Profile & Activity Tracking**
- Users have a **profile page** where they can:
  - View their **previous activities** (likes, comments, and interactions).
  - Edit personal information and manage account settings.

### **6. Legal Pages**
- Dedicated pages for:
  - **Privacy Policy**
  - **Terms & Conditions**

---

## 🔨 Tech Stack
### **Frontend:**
- Thymeleaf (Server-Side Rendering)
- Plain CSS
- JavaScript
- WebSockets (for real-time discussions)

### **Backend:**
- Spring Boot (Java)
- WebSockets + STOMP (for messaging)
- LangChain + OpenAI LLM (for chatbot)
- PostgreSQL (Database)
- Vector Database (for chatbot knowledge base)

### **DevOps:**
- Docker

---

## 🌲 Project Structure
The project is divided into three **branches**, each handling a separate microservice:

1️⃣ **Main Branch** (`EADproject_main`)
   - Handles **user authentication**, announcements, and interactions.

2️⃣ **Messaging Service** (`EADproject_messaging`)
   - Implements **real-time messaging** using **WebSockets & STOMP**.

3️⃣ **Chatbot Service** (`EADproject_chatbot`)
   - AI-powered **Abebech chatbot**
   - Uses **vector databases, LangChain, and OpenAI LLM** for intelligent responses.

---

## 👨‍💻 Team Members
- **Fuad Mohammed**
- **Emran Yona**
- **Salman Ali**

---

## 📜 Future Enhancements
✅ Implement **file sharing** in group discussions.
✅ Improve chatbot intelligence with **custom AI training**.
✅ Add **event registration features** for student clubs.
✅ Enhance **profile customization options**.

---

## 📬 Contact
For any issues or contributions, please reach out to **Fuad Mohammed**, **Emran Yona**, or **Salman Ali**.

