# AMIT3353 Mobile Application Development — Practical 7

## Architecture: ViewModel & State

This repository contains the implementation for **Practical 7** of AMIT3353 Mobile Application Development.

The practical focuses on separating the UI from application logic using **ViewModel**, managing UI state with **StateFlow**, collecting state safely in Jetpack Compose, surviving configuration changes, and performing asynchronous work using `viewModelScope`. These are the core learning objectives specified in the Practical 7 question paper.

> **Purpose:** This repository is provided as a reference implementation for students working on Practical 7.

---

## 📚 Practical Objectives

By completing this practical, you should understand how to:

* Separate UI from application logic using a `ViewModel`
* Store and expose UI state using `StateFlow`
* Collect `StateFlow` safely in Jetpack Compose
* Preserve application state during configuration changes such as screen rotation
* Use Kotlin coroutines with `viewModelScope`
* Move state-changing logic out of the Composable and into the `ViewModel`

The question paper specifically introduces `ViewModel`, `StateFlow`, `collectAsStateWithLifecycle`, and `viewModelScope` as the key concepts for this practical.

---

# 📝 Practical Tasks

The practical builds upon the list application from the previous practicals and refactors it so that its state and logic are managed by a `ViewModel`.

The main tasks are:

### Task 1 — Create the Contact Data Class

Create a `Contact` data class containing:

```kotlin
data class Contact(
    val id: Int,
    val name: String,
    val phone: String
)
```

This provides the basic data structure used by the application.

---

### Task 2 — Create `ListViewModel`

Create a `ListViewModel` that extends Android's `ViewModel`.

The ViewModel is responsible for managing the application's contact list and input state.

The list is stored internally using a private `MutableStateFlow`:

```kotlin
private val _items = MutableStateFlow<List<Contact>>(emptyList())
```

The UI receives a read-only version:

```kotlin
val items: StateFlow<List<Contact>> = _items.asStateFlow()
```

This follows the principle that the UI should be able to **observe state but should not directly modify the ViewModel's private state**.

The ViewModel also handles:

* Adding contacts
* Removing contacts
* Updating the name input
* Updating the phone input
* Generating contact IDs
* Validating contact input

---

## 🔄 State Management

One of the main concepts in this practical is the separation between **mutable state inside the ViewModel** and **read-only state exposed to the UI**.

The general pattern is:

```text
UI
 │
 │ observes
 ▼
StateFlow
 │
 ▼
ViewModel
 │
 │ modifies
 ▼
MutableStateFlow
```

For example:

```kotlin
private val _items =
    MutableStateFlow<List<Contact>>(emptyList())

val items: StateFlow<List<Contact>> =
    _items.asStateFlow()
```

Only the ViewModel can modify `_items`.

The Composable observes `items`.

---

# 🎨 Compose UI

The `ContactScreen` Composable obtains the `ListViewModel` using:

```kotlin
val vm: ListViewModel = viewModel()
```

The question paper specifies this approach for obtaining the ViewModel inside the Composable.

The UI then collects the ViewModel state using:

```kotlin
val items by vm.items.collectAsStateWithLifecycle()
val name by vm.nameInput.collectAsStateWithLifecycle()
val phone by vm.phoneInput.collectAsStateWithLifecycle()
```

This allows Compose to automatically update when the state changes.

---

# 🔁 State Down, Events Up

Practical 7 demonstrates the **state-down, events-up** pattern.

The ViewModel provides state to the UI:

```text
ViewModel
   │
   │ State
   ▼
Compose UI
```

When the user interacts with the UI, an event is sent back to the ViewModel:

```text
Compose UI
   │
   │ Event
   ▼
ViewModel
```

For example:

```kotlin
OutlinedTextField(
    value = name,
    onValueChange = vm::onNameChange,
    label = { Text("Name") }
)
```

and:

```kotlin
Button(
    onClick = vm::addFromInput
) {
    Text("Add contact")
}
```

The question paper specifically requires the add/remove logic to be moved out of the Composable and into ViewModel functions.

---

# 📋 Contact List

Contacts are displayed using `LazyColumn`:

```kotlin
LazyColumn {
    items(items, key = { it.id }) { contact ->
        // Contact UI
    }
}
```

Each contact displays:

* Contact name
* Phone number
* Delete button

The delete action calls the ViewModel:

```kotlin
IconButton(
    onClick = { vm.remove(contact) }
) {
    // Delete icon
}
```

This keeps the contact modification logic inside the ViewModel rather than inside the UI.

---

# 🔄 Configuration Changes

A key requirement of Practical 7 is testing whether the application's state survives a configuration change.

### Test

1. Add one or more contacts.
2. Rotate the Android device/emulator.
3. Check whether the contacts and input state remain available.

The question paper specifically asks students to rotate the device and confirm that the list and input state are preserved.

This demonstrates one of the important advantages of using a `ViewModel`: its state can survive configuration changes such as screen rotation.

---

# ⏳ Asynchronous Loading

The practical also introduces asynchronous work using Kotlin coroutines.

The ViewModel uses:

```kotlin
viewModelScope.launch {
    // asynchronous work
}
```

A simulated two-second delay represents an operation such as retrieving data from a network or database:

```kotlin
delay(2000)
```

The question paper describes this as a simulated network/database operation.

---

# 📥 Load Sample Data

The application includes a **Load Sample Data** button.

When pressed, the ViewModel:

1. Sets the loading state to `true`
2. Starts a coroutine using `viewModelScope`
3. Waits for two seconds
4. Adds sample contacts
5. Sets the loading state back to `false`

The sample contacts specified by the practical are:

| Name     | Phone       |
| -------- | ----------- |
| Aisyah   | 012-3456789 |
| Daniel   | 013-9876543 |
| Mei Ling | 017-1112223 |

These sample records come directly from the Practical 7 question paper.

---

# ⏱️ Loading State

The loading state is represented using another `StateFlow`:

```kotlin
private val _isLoading =
    MutableStateFlow(false)

val isLoading: StateFlow<Boolean> =
    _isLoading.asStateFlow()
```

The button is disabled while loading:

```kotlin
Button(
    onClick = vm::loadSampleContacts,
    enabled = !isLoading
) {
    Text(
        if (isLoading)
            "Loading…"
        else
            "Load sample data"
    )
}
```

A `LinearProgressIndicator` is displayed while the operation is running.

---

# 🏗️ Project Structure

The Practical 7 implementation is organised around the following main components:

```text
app/
└── src/
    └── main/
        └── java/
            └── com/example/dcsg2_practical7/
                │
                ├── MainActivity.kt
                ├── ContactScreen.kt
                ├── ListViewModel.kt
                │
                ├── model/
                │   └── Contact.kt
                │
                └── ui/
                    └── theme/
```

### `MainActivity.kt`

The application's entry point.

It sets up the Compose content and launches the main screen.

### `ContactScreen.kt`

Contains the user interface.

Responsibilities include:

* Displaying text fields
* Displaying buttons
* Collecting ViewModel state
* Displaying contacts
* Displaying loading state
* Sending user events to the ViewModel

### `ListViewModel.kt`

Contains the application's state and logic.

Responsibilities include:

* Managing contacts
* Managing text input
* Adding contacts
* Removing contacts
* Loading sample contacts
* Managing loading state
* Running asynchronous operations

### `Contact.kt`

Defines the contact data model.

---

# 🧠 Architecture Overview

The application follows a simple architecture:

```text
                ┌─────────────────────┐
                │    ContactScreen    │
                │   Jetpack Compose   │
                └──────────┬──────────┘
                           │
                     Events │
                           ▼
                ┌─────────────────────┐
                │    ListViewModel    │
                │                     │
                │  • add()            │
                │  • remove()         │
                │  • addFromInput()   │
                │  • loadSampleData() │
                └──────────┬──────────┘
                           │
                     StateFlow
                           │
                           ▼
                ┌─────────────────────┐
                │     UI State        │
                │                     │
                │  • contacts         │
                │  • name             │
                │  • phone            │
                │  • isLoading        │
                └─────────────────────┘
```

The important idea is:

> **The UI displays state. The ViewModel owns the state and handles the logic.**

---

# 📦 Required Dependencies

Practical 7 requires the following lifecycle dependencies:

```kotlin
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
```

These dependencies are specified in the question paper.

> **Note:** If you are using a different Android Studio/project version, dependency versions may differ. Follow the dependency versions required by your lecturer or the project configuration.

---

# 🚀 How to Run

### 1. Clone the repository

```bash
git clone https://github.com/Reyzirk/AMIT3353_MobileAppPractical.git
```

### 2. Open the project

Open the project in **Android Studio**.

### 3. Switch to Practical 7

```bash
git checkout Practical7
```

Or select the `Practical7` branch from Android Studio's Git branch menu.

### 4. Sync Gradle

Allow Android Studio to download and synchronize the required dependencies.

### 5. Run the application

Start an Android Emulator or connect an Android device and run the application.

---

# 🧪 Practical 7 Checklist

Use this checklist to make sure your implementation covers the main requirements.

### Contact Model

* [ ] `Contact` data class created
* [ ] `id` property included
* [ ] `name` property included
* [ ] `phone` property included

### ViewModel

* [ ] `ListViewModel` extends `ViewModel`
* [ ] Contact list stored in `MutableStateFlow`
* [ ] Read-only `StateFlow` exposed to UI
* [ ] Add functionality moved into ViewModel
* [ ] Remove functionality moved into ViewModel
* [ ] Name input managed by ViewModel
* [ ] Phone input managed by ViewModel

### Compose

* [ ] ViewModel obtained using `viewModel()`
* [ ] State collected using `collectAsStateWithLifecycle()`
* [ ] UI sends events to ViewModel
* [ ] Contacts displayed using `LazyColumn`

### Configuration Changes

* [ ] Add contacts
* [ ] Rotate device/emulator
* [ ] Confirm contacts remain
* [ ] Confirm input state remains

### Coroutines

* [ ] `viewModelScope` used
* [ ] Simulated delay implemented
* [ ] Loading state implemented
* [ ] Sample contacts loaded asynchronously
* [ ] Loading indicator displayed

The checklist corresponds to the tasks and deliverable described in the Practical 7 question paper.

---

# 📤 Practical Deliverable

The required deliverable is the **Week 5/6 list application refactored so that all state lives in a ViewModel and survives screen rotation**.

The practical demonstration should include rotating the device to verify that the state is preserved.

---

# ⭐ Optional Challenge

The question paper also provides an optional extension.

You can introduce a single `UiState` data class representing states such as:

```text
Loading
Data
Empty
```

and expose that state through a single `StateFlow`.

The UI can then render the appropriate interface based on the current `UiState`.

---

# ⚠️ Common Pitfalls

### Don't store `Context` in the ViewModel

Avoid storing an Android `Context` or View reference inside the ViewModel.

The question paper warns that doing so can cause memory leaks.

### Use lifecycle-aware state collection

Prefer:

```kotlin
collectAsStateWithLifecycle()
```

instead of plain:

```kotlin
collectAsState()
```

The practical specifically highlights lifecycle-aware collection to avoid collecting while the application is in the background.

---

# 📖 Key Takeaways

After completing Practical 7, you should be able to explain:

**1. Why use ViewModel?**

To keep UI-related state and logic outside the Composable and allow that state to survive configuration changes.

**2. Why use StateFlow?**

To expose observable state from the ViewModel to the UI.

**3. Why use `asStateFlow()`?**

To expose a read-only version of mutable state to the UI.

**4. Why use `collectAsStateWithLifecycle()`?**

To collect Flow state in Compose while respecting the Android lifecycle.

**5. Why use `viewModelScope`?**

To launch coroutines whose lifetime is tied to the ViewModel.

**6. What is the state-down, events-up pattern?**

The ViewModel provides state to the UI, while user actions are sent back to the ViewModel as events.

---

## 🔗 Repository

**AMIT3353 Mobile Application Development — Practical 7**

https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical7

---

## 🎓 Course

**AMIT3353 — Mobile Application Development**

**Practical 7 — Architecture: ViewModel & State**

> Use this repository as a reference while completing your own practical implementation. Make sure you understand the concepts and code rather than simply copying the implementation.
