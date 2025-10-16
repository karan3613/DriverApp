<img width="2560" height="1440" alt="image" src="https://github.com/user-attachments/assets/ce2d8d4f-4791-4b93-9f2c-b5cba620a9d6" /># 🚍 BusSaathi Driver App – Real-Time Bus Location Uploader

The **BusSaathi Driver App** is a companion application to the main **[BusSaathi Passenger App](https://github.com/karan3613/BusSaathiApp)**.  
It allows government bus drivers and conductors to **share their live location** with the system every few seconds — enabling **real-time tracking** for passengers.  

Built using **Kotlin**, **Jetpack Compose**, and the **MVVM architecture**, this app ensures reliable updates with minimal battery and network usage.

## 🎥 Working Demo

📺 **Watch the full demo video here:**  
👉 [Demo Video Link](https://drive.google.com/file/d/1J7KAkLiCvW3hzq1BGVQNuUnS0GiTBYcT/view?usp=sharing) 

## 🔗 Related Repositories

- 📱 **Main Passenger App:** [BusSaathi App](https://github.com/karan3613/BusSaathiApp)  
- 🧠 **Backend Server:** [BusSaathi Backend](https://github.com/karan3613/BusAppBackend)

## 🚀 Overview

The Driver App is designed for simplicity and reliability.  
Each driver can sign in and view their **profile**, which includes essential details such as:

- 👨‍💼 **Driver Name & Contact Info**  
- 🚌 **Bus Number & Route Details**  
- 👨‍💼 **Conductor Details**

From the **main screen**, drivers can control tracking using a **Ready Button**:
- When **“Ready”** is pressed → Location tracking **starts** (updates every 3 seconds).  
- When **“Stop”** is pressed → Tracking and backend updates are **halted**.

This ensures location sharing only happens during active duty hours, improving both accuracy and efficiency.


## 🧩 Features

- 📍 **Start/Stop Tracking** – Controlled with a single “Ready” toggle button.  
- 🕒 **Live Location Updates** – Sends location to the backend every 3 seconds.  
- 🧑‍💼 **Profile Section** – Displays driver & conductor information, and bus number.  
- 📞 **Contact Info Section** – Allows easy access to communication details.  
- 🖤 **Modern Compose UI** – Clean, responsive, and built entirely in Jetpack Compose.  
- ⚙️ **MVVM Architecture** – Ensures scalable and maintainable structure.  
- 🌐 **Seamless Integration** – Communicates with the same backend as the main Bussatthi app.  


## 🛠️ Tech Stack

| Layer | Technologies |
|-------|---------------|
| **Frontend (Driver App)** | Kotlin, Jetpack Compose, MVVM, Retrofit, Location Services |
| **Backend** | Python / MySQL / FastAPI / Kafka |
| **Dependency Injection** | Hilt |
| **State Management** | ViewModel + Immutable UI States |
| **Networking** | Retrofit + Coroutines |






## ⚙️ How It Works

1. **Driver logs in** to the app using credentials.  
2. **Profile screen** displays bus, driver, and conductor information.  
3. When the driver presses **“Ready”**, the app:
   - Begins **fetching GPS location** every 3 seconds.  
   - Sends each update to the backend via API.  
4. The backend updates the live location in the database.  
5. The **Bussatthi Passenger App** fetches this data to show live bus tracking.  
6. When the driver presses **“Stop”**, location updates are paused immediately.  

## 🌟 Future Enhancements

- 🚦 Automatic detection of trip start/end using geofencing.  
- 📡 Optimized background tracking using WorkManager.  
- 🧭 Real-time route deviation detection and alerts.  
- 🔔 Push notifications to passengers when bus tracking starts.  

## 🤝 Contributing

We welcome contributions, feedback, and feature suggestions!  
Feel free to fork the repo and submit pull requests.

## ✨ Author

**Karan [@karan3613](https://github.com/karan3613)**  
> “Accurate tracking starts here — Bussatthi Driver App makes every route smarter.” 🚍📡


