# 📱 Android Jetpack Compose — Practical 8

## Local Persistence I: SharedPreferences & Files

This repository contains the implementation for **Practical 8** of **AMIT3353 — Mobile Application Development**.

In this practical, you will learn how to persist application data locally using **SharedPreferences** and **internal storage**. You will also learn how to serialize structured Kotlin data into **JSON** and restore it when the application is reopened.

---

## 🎯 Learning Objectives

By completing this practical, you should be able to:

* Store simple key–value data using **SharedPreferences**.
* Read and write application data using **internal storage**.
* Serialize Kotlin objects into **JSON**.
* Deserialize JSON back into Kotlin objects.
* Handle first-run scenarios where persisted files do not yet exist.
* Perform file I/O without blocking the main UI thread.
* Understand when to use SharedPreferences, files, or a database for local persistence.

---

## 📚 Topics Covered

| Topic                    | Description                                                                         |
| ------------------------ | ----------------------------------------------------------------------------------- |
| **SharedPreferences**    | Store simple persistent key–value data such as usernames and settings.              |
| **Internal Storage**     | Store private application files using `filesDir` and `openFileOutput()`.            |
| **Kotlin Serialization** | Convert Kotlin objects to and from JSON.                                            |
| **JSON**                 | Store structured application data in a readable file format.                        |
| **Coroutines**           | Perform file operations using `Dispatchers.IO`.                                     |
| **Persistence**          | Keep application data available after the application is closed and reopened.       |
| **First-Run Handling**   | Safely handle cases where persisted data has not been created yet.                  |
| **DataStore**            | Introduced as the modern coroutine-based direction for replacing SharedPreferences. |

The practical identifies SharedPreferences as being suitable for small independent values such as settings or flags, while JSON files are suitable for structured data loaded and saved as a whole.

---

## 🛠️ Technologies Used

* **Android**
* **Kotlin**
* **Jetpack Compose**
* **Material 3**
* **SharedPreferences**
* **Internal App Storage**
* **Kotlinx Serialization**
* **JSON**
* **Kotlin Coroutines**
* **Gradle**

---

## 📋 Practical Tasks

### Task 1 — Configure Kotlin Serialization

Add the Kotlin Serialization plugin and JSON dependency to the module-level `build.gradle.kts`.

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}
```

> ⚠️ **Important:** The Serialization plugin version must match the Kotlin plugin version. A mismatch can cause the project build to fail.

After making the changes, synchronize the Gradle project.

---

### Task 2 — Store a Username with SharedPreferences

The application uses `SharedPreferences` to save a username.

Example:

```kotlin
fun saveUsername(context: Context, name: String) {
    val prefs = context.getSharedPreferences(
        "settings",
        Context.MODE_PRIVATE
    )

    prefs.edit()
        .putString("username", name)
        .apply()
}
```

The saved username can then be retrieved using:

```kotlin
fun loadUsername(context: Context): String {
    val prefs = context.getSharedPreferences(
        "settings",
        Context.MODE_PRIVATE
    )

    return prefs.getString("username", "") ?: ""
}
```

`apply()` is used so that the preference update is performed asynchronously rather than blocking the UI thread.

---

### Task 3 — Save Structured Data as JSON

The practical introduces a `Note` data class:

```kotlin
@Serializable
data class Note(
    val id: Int,
    val text: String
)
```

A list of notes is serialized into JSON before being written to internal storage.

```kotlin
fun saveNotes(
    context: Context,
    notes: List<Note>
) {
    val json = Json.encodeToString(notes)

    context.openFileOutput(
        "notes.json",
        Context.MODE_PRIVATE
    ).use {
        it.write(json.toByteArray())
    }
}
```

This demonstrates how structured Kotlin data can be converted into a format suitable for persistent storage.

---

### Task 4 — Load Data from Internal Storage

The application reads the JSON file from its private internal storage directory.

```kotlin
fun loadNotes(context: Context): List<Note> {
    val file = File(
        context.filesDir,
        "notes.json"
    )

    if (!file.exists()) {
        return emptyList()
    }

    return try {
        Json.decodeFromString(file.readText())
    } catch (e: Exception) {
        emptyList()
    }
}
```

The implementation handles two important cases:

1. The file does not exist because this is the first application run.
2. The file exists but contains invalid or corrupted JSON.

In either situation, an empty list is returned instead of allowing the application to crash.

---

### Task 5 — Integrate Persistence with Jetpack Compose

The Compose UI loads the saved username and notes when the screen is first displayed.

```kotlin
LaunchedEffect(Unit) {
    username = loadUsername(context)
    notes = withContext(Dispatchers.IO) {
        loadNotes(context)
    }
}
```

The application provides two main sections:

#### ⚙️ Settings

* Enter a username.
* Save the username using SharedPreferences.

#### 📝 Notes

* Enter a new note.
* Add the note to the list.
* Save the updated note list as JSON.
* Display previously saved notes.

The practical uses `Dispatchers.IO` for file operations so that file I/O does not block the application's main thread.

---

## 🧪 Testing Persistence

Persistence should be tested by following these steps:

1. Launch the application.
2. Enter a username.
3. Tap **Save setting**.
4. Add two or three notes.
5. Fully close the application by swiping it away from the **Recents** screen.
6. Reopen the application.
7. Verify that:

   * The username is still displayed.
   * The saved notes are still displayed.
8. Uninstall and reinstall the application.
9. Verify that the application displays **"No notes yet"** instead of crashing.

These tests demonstrate that the application can persist data across application restarts while also correctly handling the first-run state.

---

## 📂 Project Structure

The repository follows a standard Android Gradle project structure:

```text
Practical8/
├── app/
│   └── src/
│       └── main/
│           └── java/
│               └── ...
├── gradle/
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

The `Practical8` branch currently contains the Android application module together with the Gradle configuration and project files.

---

## 💡 SharedPreferences vs JSON File vs Room

Different types of local data require different persistence solutions.

| Storage               | Suitable For                                                              | Example                             |
| --------------------- | ------------------------------------------------------------------------- | ----------------------------------- |
| **SharedPreferences** | Small, independent key–value data                                         | Username, theme flag, tutorial flag |
| **JSON File**         | Structured data saved and loaded as a complete collection                 | List of notes                       |
| **Room Database**     | Larger data requiring queries, sorting, relationships, or partial updates | Large collection of records         |

For example, a JSON file is convenient for a small notes list, but modifying one item requires the entire file to be rewritten. A database such as Room becomes more appropriate when the amount and complexity of data increases.

---

## ⚠️ Common Pitfalls

### 1. Serialization Plugin Version Mismatch

Make sure the Kotlin Serialization plugin version matches the Kotlin plugin version.

```kotlin
id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
```

### 2. Blocking the Main Thread

File operations should not unnecessarily run on the main thread.

Use:

```kotlin
withContext(Dispatchers.IO) {
    // File operation
}
```

### 3. Missing File

The JSON file does not exist on the first application run.

Always check:

```kotlin
if (!file.exists()) {
    return emptyList()
}
```

### 4. Invalid JSON

A file may exist but contain invalid JSON. Handle deserialization failures using `try/catch`.

### 5. Using `commit()` on the UI Thread

For SharedPreferences, use `apply()` instead of blocking the UI thread with `commit()` where appropriate.

These are specifically highlighted as common pitfalls in the practical instructions.

---

## 🚀 Optional Challenge

As an extension, migrate the username setting from **SharedPreferences** to **Jetpack DataStore — Preferences DataStore**.

The goal is to observe the stored value as a `Flow` and gain experience with the modern coroutine-based persistence approach introduced in this practical.

---

## 📦 Deliverable

The completed application should demonstrate:

* ✅ A user setting persisted using **SharedPreferences**.
* ✅ A list of notes persisted in a **JSON file**.
* ✅ Data surviving an application restart.
* ✅ Correct handling of the first application run.
* ✅ File operations performed appropriately using coroutines.
* ✅ A working Jetpack Compose user interface.

The official practical defines the deliverable as an application that persists a user setting through SharedPreferences and saves/loads a list of items through a JSON file while surviving an application restart.

---

## 🔗 Repository

**GitHub Repository:**
[AMIT3353_MobileAppPractical — Practical 8](https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical8?utm_source=chatgpt.com)

---

## 📖 Previous Practicals

This practical continues the Android development concepts introduced in the previous practicals:

* [Practical 1](https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical1)
* [Practical 2](https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical2)
* [Practical 3](https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical3)
* [Practical 4](https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical4)
* [Practical 5](https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical5)
* [Practical 6](https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical6)
* [Practical 7](https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical7)
* **Practical 8** — Local Persistence I: SharedPreferences & Files
