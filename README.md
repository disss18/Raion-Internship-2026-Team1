# 🥗 MBG+ - Platform Integrasi Makan Bergizi Gratis

MBG+ adalah aplikasi Android yang dirancang untuk mendukung implementasi program **Makan Bergizi Gratis (MBG)** agar berjalan **aman, transparan, dan terukur** melalui integrasi antara **Sekolah, Orang Tua, dan Dapur UMKM** dalam satu sistem digital.

Aplikasi ini membantu memastikan makanan yang diterima siswa:

* Aman dari alergi
* Memenuhi standar gizi
* Terdokumentasi secara transparan
* Dapat dimonitor oleh semua pihak terkait

---

# 🎯 Background

## 📌 Situation

Program **Makan Bergizi Gratis (MBG)** melibatkan banyak pihak seperti:

* Sekolah
* Siswa
* Orang Tua
* UMKM penyedia makanan

Koordinasi antar pihak membutuhkan sistem digital yang terintegrasi.

---

## ⚠️ Complication

Beberapa masalah yang sering muncul:

* Tidak adanya **database alergi siswa yang terpusat**
* Gizi makanan **tidak terpantau secara real-time**
* Distribusi makanan **kurang transparan**
* Sulit melakukan **verifikasi dapur UMKM**

---

## ❓ Question

Bagaimana memastikan makanan yang diterima siswa:

* Aman
* Bergizi
* Terdistribusi secara transparan

---

## ✅ Answer

MBG+ menyediakan platform digital yang mengintegrasikan:

* Monitoring gizi siswa
* Database alergi siswa
* Sistem verifikasi dapur
* Transparansi distribusi makanan

---

# 👥 User Roles

Aplikasi MBG+ memiliki **3 jenis pengguna utama**.

---

## 🏪 Dapur MBG (UMKM)

Pengguna dari pihak penyedia makanan.

**Fitur:**

* Verifikasi Dapur MBG **(MVP)**
* Input Data Menu & Gizi **(MVP)**
* Dashboard Monitoring Gizi
* Track Record Pengiriman **(MVP)**
* Melihat Data Alergi Siswa **(MVP)**

---

## 🏫 Sekolah

Sekolah bertugas mengelola data siswa dan melakukan monitoring program MBG.

**Fitur:**

* Input Database Alergi Siswa **(MVP)**
* Dashboard Monitoring Gizi **(MVP)**
* Track Record Distribusi MBG **(MVP)**
* Feedback & Rating Makanan **(MVP)**

---

## 👨‍👩‍👧 Orang Tua

Orang tua dapat memantau gizi anak secara transparan.

**Fitur:**

* Dashboard Monitoring Gizi Anak **(MVP)**
* Transparansi Menu Harian **(MVP)**
* Artikel Edukasi Gizi
* Reward & Redeem Poin

---

# 🚀 Core Features

## 📊 Dashboard Monitoring Gizi **(MVP)**

Dashboard menampilkan informasi gizi makanan siswa.

Informasi yang ditampilkan:

* Kalori
* Protein
* Karbohidrat
* Lemak
* Status kecukupan gizi
* Riwayat konsumsi harian

---

## 🧬 Database Alergi Siswa **(MVP)**

Sistem penyimpanan data alergi siswa untuk memastikan makanan yang diberikan aman.

Fungsi:

* Input alergi oleh sekolah
* Akses data alergi oleh dapur
* Pencegahan pemberian makanan berbahaya

---

## 🥗 Input Menu & Data Gizi **(MVP)**

Dapur MBG dapat memasukkan informasi menu dan kandungan gizi.

Data yang diinput:

* Nama menu
* Kalori
* Protein
* Karbohidrat
* Lemak

---

## 🚚 Track Record Distribusi **(MVP)**

Sistem pelacakan distribusi makanan dari dapur ke sekolah.

Informasi yang dicatat:

* Tanggal pengiriman
* Menu yang dikirim
* Status penerimaan sekolah

---

## ⭐ Feedback & Rating System **(MVP)**

Sekolah dapat memberikan feedback terhadap makanan yang diberikan.

Tujuan:

* Menjaga kualitas makanan
* Memberikan evaluasi untuk dapur MBG

---

## 🎮 Reward & Gamification

Fitur tambahan untuk meningkatkan engagement orang tua dan siswa.

Fitur:

* Sistem poin
* Reward penukaran poin
* Artikel edukasi gizi

---

# 🔐 Authentication

Aplikasi menggunakan sistem autentikasi berbasis **Supabase Authentication**.

Metode login yang tersedia:

* Email & Password
* **Google OAuth Login**

Google OAuth memungkinkan pengguna untuk login atau register menggunakan akun Google secara langsung.

Flow autentikasi:

```
User Login
     ↓
Google OAuth / Email Login
     ↓
Supabase Authentication
     ↓
Session Management
     ↓
User Role Routing (Sekolah / Dapur / Orang Tua)
```

---

# 🏗 Tech Stack

## Mobile Development
- Kotlin
- Jetpack Compose
- MVVM Architecture
- Material Design 3

## Backend & Database
- Supabase
- PostgreSQL

## Authentication
- Supabase Auth
- Google OAuth

## API Communication
- REST API
- JSON

## Development Tools
- Android Studio
- Git
- GitHub

---

# 🧠 Application Architecture

Aplikasi menggunakan **MVVM (Model - View - ViewModel)** dengan pendekatan **feature-based architecture** agar code lebih modular dan scalable.

### Architecture Pattern

```
UI (Jetpack Compose Screen)
        ↓
ViewModel
        ↓
Repository
        ↓
Supabase Client
        ↓
Supabase Database
```

---

# 📦 Project Structure

Struktur project mengikuti **feature modular architecture**.

```
app/src/main/java/com/example/mbg
│
├── core
│   ├── di
│   │   Dependency Injection
│   │
│   ├── navigation
│   │   Navigation Graph aplikasi
│   │
│   ├── session
│   │   User session management
│   │
│   ├── supabase
│   │   Supabase client & configuration
│   │
│   ├── ui
│   │   └── component
│   │       Reusable UI components
│   │
│   └── util
│       Utility helper classes
│
├── feature
│   │
│   ├── auth
│   │   Login, Register, Google OAuth
│   │
│   ├── dashboard
│   │   Dashboard monitoring gizi
│   │
│   ├── feedback
│   │   Feedback dan rating makanan
│   │
│   ├── school
│   │   Input data alergi siswa
│   │
│   ├── kitchen
│   │   Verifikasi dapur dan input menu
│   │
│   ├── reward
│   │   Sistem poin dan reward
│   │
│   └── article
│       Artikel edukasi gizi
```

---

# 🔄 Data Flow Example

Contoh alur data ketika sekolah menambahkan data alergi siswa.

```
SchoolScreen
      ↓
SchoolViewModel
      ↓
AllergyRepository
      ↓
Supabase Client
      ↓
Supabase Database
```

---

# 📱 Platform

* Android

---

# 📄 License

This project is created for educational purposes as part of Raion Internships.
