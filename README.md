# ☁️ Practical 9 — Supabase Backend as a Service

This practical introduces **Supabase**, an alternative to Firebase that provides a **PostgreSQL database, RESTful backend API, Realtime functionality, authentication, storage, and other backend services**.

In this practical, you will build an **Android application using Jetpack Compose** that connects to a Supabase PostgreSQL database and performs **CRUD (Create, Read, Update, Delete)** operations on contact records.

> 📚 **Module:** BMIT2073 Mobile Application Development
> 🧪 **Practical:** 9
> ☁️ **Topic:** Supabase – Backend as a Service (BaaS)

---

## 🎯 Learning Objectives

By completing this practical, you should be able to:

* Understand the concept of **Backend as a Service (BaaS)**.
* Set up a project using **Supabase**.
* Create and configure a **PostgreSQL database table** in Supabase.
* Configure Supabase API access for an Android application.
* Connect an Android application to Supabase using the **Supabase Kotlin client**.
* Configure the required Kotlin serialization and Supabase dependencies.
* Perform **CRUD operations** from a Jetpack Compose application.
* Use Kotlin coroutines to perform database operations without blocking the UI.
* Display database records using `LazyColumn`.
* Implement basic **Add, Update, and Delete** functionality.
* Handle loading states and errors using Compose UI components such as `CircularProgressIndicator` and `Snackbar`.

---

## 📖 Practical Overview

Supabase provides a hosted PostgreSQL database together with backend services that can be accessed by applications.

In this practical, you will create a Supabase project and configure a `contact` table containing contact information.

The Android application will then communicate with Supabase to retrieve and manipulate the records.

### Application Flow

```text
┌──────────────────────┐
│   Android App        │
│   Jetpack Compose    │
└──────────┬───────────┘
           │
           │ Supabase Kotlin Client
           ▼
┌──────────────────────┐
│   Supabase API       │
│   PostgREST           │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│   PostgreSQL         │
│   contact table      │
└──────────────────────┘
```

The application performs the following operations:

| Operation  | Description                     |
| ---------- | ------------------------------- |
| **Create** | Add a new contact               |
| **Read**   | Retrieve contacts from Supabase |
| **Update** | Modify an existing contact      |
| **Delete** | Remove a contact                |

---

## 🗄️ Supabase Database

Create a Supabase project and configure a PostgreSQL table named:

```text
contact
```

The table contains the following columns:

| Column       | Type          | Description                       |
| ------------ | ------------- | --------------------------------- |
| `id`         | `int8`        | Auto-generated primary key        |
| `created_at` | `timestamptz` | Automatically generated timestamp |
| `name`       | `text`        | Contact name                      |
| `email`      | `text`        | Contact email address             |

The practical instructions also require inserting some initial records into the table before connecting the Android application.

---

## 🛠️ Technologies & Tools

This practical uses:

* **Kotlin**
* **Android Studio**
* **Jetpack Compose**
* **Material 3**
* **Supabase**
* **PostgreSQL**
* **Supabase Kotlin Client**
* **PostgREST**
* **Kotlin Serialization**
* **Kotlin Coroutines**
* **LazyColumn**

---

## 📦 Required Dependencies

The practical uses the Supabase Kotlin client through its Bill of Materials (BOM):

```kotlin
// Supabase Kotlin client
implementation(platform("io.github.jan-tennert.supabase:bom:3.5.0"))
implementation("io.github.jan-tennert.supabase:postgrest-kt")

// HTTP engine
implementation("io.ktor:ktor-client-android:3.0.3")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

// Material icons
implementation("androidx.compose.material:material-icons-extended")
```

Kotlin serialization must also be configured in the module-level Gradle file. The practical specifies a minimum SDK of **API 26** for the Supabase Kotlin library.

> **Note:** Dependency versions may need to be updated depending on your Android Studio, Kotlin, Gradle, and Supabase Kotlin client versions. The practical specifically notes that the Kotlin serialization plugin version should match the Kotlin version used by the project.

---

## 🌐 Internet Permission

Because the application communicates with the Supabase server, Internet access must be declared in `AndroidManifest.xml`.

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

This permission should be placed under the `<manifest>` element and outside the `<application>` element.

---

## 🔑 Supabase Configuration

After creating the Supabase project, you will need:

1. **Supabase Server URL**
2. **API Key**

The practical demonstrates obtaining these values from the Supabase dashboard and configuring them in the Android application.

The example application creates a Supabase client using:

```kotlin
val supabase = createSupabaseClient(
    supabaseUrl = SUPABASE_URL,
    supabaseKey = SUPABASE_KEY
) {
    install(Postgrest)
}
```

### ⚠️ Security Warning

The practical uses a **Secret key** so that all four CRUD operations work immediately during the lab. However, the practical explicitly warns that this approach is **not suitable for a real published application** because a secret key embedded in an APK can be extracted.

For a production application, use the **Publishable key together with Row Level Security (RLS) policies** instead.

**Do not commit your Supabase secret key to GitHub.**

---

## 🧩 Data Models

The application uses Kotlin serialization to represent database records.

### User

The `User` data class represents a row retrieved from the `contact` table:

```kotlin
@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String
)
```

### UserInput

A separate `UserInput` class is used when inserting or updating records because the `id` is generated automatically by the database:

```kotlin
@Serializable
data class UserInput(
    val name: String,
    val email: String
)
```

This separates the **database representation** from the **input data used for INSERT/UPDATE operations**.

---

## 🔄 CRUD Operations

### 1. Create — Add a Contact

A new contact is inserted into the `contact` table using:

```kotlin
supabase.from("contact")
    .insert(UserInput(name.trim(), email.trim())) {
        select()
    }
```

The newly inserted record is returned and added to the local list displayed by the application.

---

### 2. Read — Fetch Contacts

The application retrieves all contacts using:

```kotlin
supabase.from("contact")
    .select()
    .decodeList<User>()
```

The data is retrieved using an IO dispatcher so the database operation does not block the UI thread.

The initial data retrieval is triggered when the Compose screen first appears:

```kotlin
LaunchedEffect(Unit) {
    fetchUsers()
}
```

---

### 3. Update — Edit a Contact

When a contact is selected for editing, the application fills the input fields with the existing values.

The update operation uses the contact's `id` to target a specific database row:

```kotlin
filter {
    eq("id", current.id)
}
```

The updated record is then returned and used to update the corresponding item in the local list.

---

### 4. Delete — Remove a Contact

The delete operation uses the selected contact's `id`:

```kotlin
supabase.from("contact").delete {
    filter {
        eq("id", userId)
    }
}
```

After successful deletion, the contact is removed from the local list.

---

## 🖥️ User Interface

The application provides:

* Name input field
* Email input field
* Add User button
* Update User button
* Cancel button when editing
* Contact list
* Edit button
* Delete button
* Loading indicator
* Empty-state message
* Snackbar messages for success and errors

Contacts are displayed using `LazyColumn`, with each record presented as a Material 3 `Card` and `ListItem`.

### Example UI Structure

```text
┌──────────────────────────────────┐
│       Supabase User App          │
├──────────────────────────────────┤
│ Name                             │
│ ┌──────────────────────────────┐ │
│ │ Enter name                   │ │
│ └──────────────────────────────┘ │
│                                  │
│ Email                            │
│ ┌──────────────────────────────┐ │
│ │ Enter email                  │ │
│ └──────────────────────────────┘ │
│                                  │
│       [ Add User ]               │
│                                  │
├──────────────────────────────────┤
│ 👤 John Doe            ✏️  🗑️   │
│    john@example.com              │
│                                  │
│ 👤 Jane Doe            ✏️  🗑️   │
│    jane@example.com              │
└──────────────────────────────────┘
```

---

## ⏳ Loading & Error Handling

The application maintains an `isLoading` state while communicating with Supabase.

When data is being retrieved or modified, a `CircularProgressIndicator` is displayed.

If no records exist, the application displays:

```text
No users found. Add one!
```

## Errors and successful CRUD operations are communicated using a `Snackbar`.

## 📱 Running the Application

To run the application:

1. Open the project in **Android Studio**.
2. Allow Gradle to synchronize the project.
3. Ensure your Supabase project has been created.
4. Create the `contact` table.
5. Insert some sample records.
6. Configure the Supabase Server URL.
7. Configure the required API key.
8. Ensure the Internet permission exists in `AndroidManifest.xml`.
9. Start an Android emulator or connect a physical Android device.
10. Click **Run** in Android Studio.

The practical specifies running the application on either an emulator or physical device.

---

## 🧪 Practical Tasks

After completing the setup, verify that the application can successfully perform all four CRUD operations.

### Create

* Enter a contact name.
* Enter an email address.
* Press **Add User**.
* Verify that the new contact appears in the list.
* Check the Supabase `contact` table.

### Read

* Start/reload the application.
* Verify that existing Supabase records are displayed.
* Confirm that the application correctly handles an empty table.

### Update

* Select the **Edit** icon for a contact.
* Modify the name and/or email.
* Press **Update User**.
* Verify that the updated information is displayed.
* Check the Supabase database.

### Delete

* Select the **Delete** icon.
* Verify that the contact disappears from the application.
* Check that the corresponding database record has also been deleted.

---

## 📂 Project Structure

The project follows a standard Android Studio structure:

```text
Practical9/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── ...
│           ├── res/
│           │   └── ...
│           └── AndroidManifest.xml
│
├── gradle/
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

The repository's Practical 9 branch currently contains the Android application module together with the standard Gradle project files.

---

## 🧠 Key Concepts

| Concept                  | What You Learn                                                      |
| ------------------------ | ------------------------------------------------------------------- |
| **BaaS**                 | Using a hosted backend instead of building the backend from scratch |
| **Supabase**             | Connecting an Android application to a hosted backend               |
| **PostgreSQL**           | Storing structured application data                                 |
| **PostgREST**            | Accessing PostgreSQL data through Supabase                          |
| **CRUD**                 | Creating, reading, updating and deleting records                    |
| **Kotlin Serialization** | Mapping Kotlin objects to database records                          |
| **Coroutines**           | Performing asynchronous database operations                         |
| **LazyColumn**           | Efficiently displaying a list of contacts                           |
| **Compose State**        | Keeping the UI synchronized with application data                   |
| **API Keys**             | Authenticating application requests to Supabase                     |
| **RLS**                  | Controlling database access securely in production                  |

---

## ⚠️ Common Issues

### `Network` / Connection Errors

Check that:

* The device has Internet access.
* The Supabase Server URL is correct.
* The API key is correct.
* The `INTERNET` permission exists in `AndroidManifest.xml`.

### Serialization Errors

Check that:

* The Kotlin serialization plugin is configured.
* The plugin version matches the Kotlin version used by the project.
* Your data classes are annotated with `@Serializable`.

### Database Errors

Check that:

* The table is named exactly `contact`.
* The required columns exist.
* The `id` column is configured as an auto-generated primary key.
* Your Supabase API configuration allows the requested operation.

### API Key / Security Problems

Never publish a **Supabase Secret key** in a public GitHub repository. The practical uses the secret key for laboratory purposes, but explicitly recommends Publishable keys with RLS for a real application.

---

## 📚 Learning Outcome

By the end of Practical 9, you should have a working Android application that demonstrates how a Jetpack Compose frontend can communicate with a cloud-hosted PostgreSQL backend through Supabase.

The completed application should be capable of:

```text
        Android / Jetpack Compose
                  │
                  ▼
          Supabase Kotlin Client
                  │
                  ▼
              PostgREST
                  │
                  ▼
          PostgreSQL Database
                  │
        ┌─────────┼─────────┐
        ▼         ▼         ▼
      Create     Read     Update/Delete
```

This practical builds on previous Jetpack Compose concepts while introducing **cloud databases, backend services, API communication, and persistent application data**.
