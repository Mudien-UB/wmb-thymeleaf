# WNB - Warung Makan Bahari

Aplikasi *Warung Makan Bahari* adalah proyek pembelajaran menggunakan **Spring Boot** dan **Thymeleaf**. Aplikasi ini mensimulasikan sistem sederhana untuk pengelolaan menu makanan dan lainnya pada sebuah warung makan.
  
> **Catatan:** Ini aplikasi pembelajaran. Kalau mau belajar bareng, ayo kita buat bersama!

---

## Fitur yang baru ada

- Halaman daftar menu makanan
- Tambah, edit, hapus menu
- Template dengan Thymeleaf
- Validasi input form
- cari menu berdasarkan nama input
- sorting menu berdasarkan ( nama, update terbaru, harga )
- filtering menu berdasarkan kategori menu ( food, snack, drink, coffee )
- modal pop up untuk konfirmasi hapus menu

---

## Teknologi yang Digunakan

- **Java 21**
- **Spring Boot 3.5.3**
- **Maven 4.0.0**
- **Thymeleaf**
- **HTML 5**
- **TailwindCSS**

---

## Direktori mapping
```bash
./src/main
├── java
│   └── com
│       └── hehe
│           └── wmb
│               ├── config
│               │   └── WebConfiguration.java
│               ├── controller
│               │   ├── HomeController.java
│               │   └── MenuController.java
│               ├── dto
│               │   ├── request
│               │   │   ├── MenuFilterRequest.java
│               │   │   └── MenuRequest.java
│               │   └── validation
│               │       └── OnUpdate.java
│               ├── entity
│               │   ├── enums
│               │   │   └── MenuCategory.java
│               │   └── Menu.java
│               ├── repository
│               │   └── MenuRepository.java
│               ├── service
│               │   ├── impl
│               │   │   └── MenuServiceImpl.java
│               │   └── MenuService.java
│               └── WmbApplication.java
└── resources
    ├── application.properties
    ├── static
    │   ├── css
    │   ├── icons
    │   │   └── direction.svg
    │   └── js
    │       ├── rupiah-format.js
    │       ├── menu-list.js
    │       └── price-input-format-util.js
    └── templates
        ├── error
        │   ├── 404.html
        │   ├── 405.html
        │   └── 500.html
        ├── fragments
        │   ├── confirm-delete.html
        │   ├── footer.html
        │   ├── header.html
        │   └── navigation-page.html
        ├── index.html
        ├── layouts
        │   └── base-layout.html
        └── menu
            ├── add-menu.html
            ├── edit-menu.html
            └── index.html



```

## Cara Menjalankan

1. **Clone repositori**
   ```bash
   git clone https://github.com/Mudien-UB/wmb-thymeleaf.git
   cd wmb-thymeleaf
   ```
2. **Bangun Proyek**
   ```bash
   mvn clean install
   ```
3. **Jalankan aplikasi**
   ```bash
   mvn spring-boot:run
   ```
4. **Buka Browser**
   ```bash
   http://localhost:8080
   ```

---

## Kontribusi
Ini proyek terbuka untuk belajar bareng!

- Fork repositori
- Buat branch baru
- Commit perubahanmu
- Buat pull request

---

## Penutup
Terima kasih sudah mampir! Kalau mau diskusi atau belajar bareng, jangan ragu untuk kontak saya via Issues atau Pull Request.
> **Catatan Penting :** Dont forget a cup of coffee today

---
