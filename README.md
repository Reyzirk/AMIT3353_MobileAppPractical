# AMIT3353 Mobile App Development — Practical 5

## 📱 Displaying Lists with `LazyColumn`

This repository contains the solution and reference implementation for **Practical 5** of **AMIT3353 Mobile App Development**.

The practical introduces the use of **Jetpack Compose's `LazyColumn`** to efficiently display a scrollable list of items. Students will also learn how to create reusable item composables, represent list data using Kotlin data classes, handle item clicks, and navigate from a list screen to a detail screen.

> **Practical Duration:** 2 Hours
> **Topic:** Displaying Lists with `LazyColumn`

---

## 🎯 Learning Objectives

By completing this practical, you should be able to:

* Display a scrollable list using `LazyColumn`.
* Understand how `items()` works inside a `LazyColumn`.
* Create reusable Composable functions for list items.
* Create and use Kotlin `data class` objects.
* Store and retrieve a collection of data items.
* Handle clicks on individual list items.
* Pass an item ID between screens.
* Navigate from a list screen to a detail screen using Navigation Compose.
* Use stable keys when displaying items in a list.

The practical specifically introduces `LazyColumn` as the Compose equivalent of a `RecyclerView` and explains that only visible items are composed, improving list performance.

---

## 📋 Practical Requirements

Your application should contain:

1. A Kotlin `data class` representing a list item.
2. A collection containing **at least 10 items**.
3. A reusable Composable for displaying each item.
4. A `LazyColumn` for displaying the items.
5. Click handling for each list row.
6. Navigation from the list screen to a detail screen.
7. A detail screen displaying information about the selected item.
8. A **Back** button that returns to the list screen.

The required deliverable is a scrollable list containing at least 10 items where selecting a row opens a detail screen showing information for that item.

---

## ✨ Optional Challenge

As an extension, add a search field above the list.

The search field should:

* Allow the user to enter a search query.
* Filter the displayed items as the user types.
* Display `"No results found"` when there are no matching items.

The question paper provides this as an optional extension rather than part of the minimum deliverable.

---

## 🏗️ Project Structure

The project is organised into separate packages to keep the application code modular and easier to understand.

```text
app/
└── src/
    └── main/
        └── java/
            └── com.example.dcsg1_practical5/
                ├── data/
                │   └── ContactData.kt
                │
                ├── model/
                │   └── Contact.kt
                │
                ├── nav/
                │   └── AppNavGraph.kt
                │
                ├── screen/
                │   ├── ContactRow.kt
                │   ├── ContactListScreen.kt
                │   └── ContactDetailScreen.kt
                │
                ├── ui/
                │   └── theme/
                │
                └── MainActivity.kt
```

The `Practical5` branch currently follows this structure with separate `data`, `model`, `nav`, and `screen` packages.

### `model`

Contains the data model used by the application.

Example:

```kotlin
data class Contact(
    val id: Int,
    val name: String,
    val phone: String
)
```

The question paper introduces a `Contact` data class containing an ID, name, and phone number.

### `data`

Contains the sample data used by the application.

The practical uses a `ContactData` object containing a list of contacts and a `getById()` function for retrieving a contact by its ID.

### `screen`

Contains the application's UI screens and reusable UI components.

The practical separates the list item into a reusable `ContactRow` Composable and creates separate screens for the contact list and contact details.

### `nav`

Contains the Navigation Compose configuration.

The navigation graph defines:

```text
contactList
     │
     │ click contact
     ▼
contactDetail/{contactId}
```

The selected contact ID is passed as a navigation argument to the detail screen.

---

## 🔄 Application Flow

The application follows this basic flow:

```text
┌─────────────────────────┐
│    Contact List Screen  │
│                         │
│  Search contacts        │
│  ─────────────────────  │
│  Alice Tan              │
│  012-345 6789           │
│  ─────────────────────  │
│  Brandon Lee            │
│  013-456 7890           │
│  ─────────────────────  │
│  ...                    │
└────────────┬────────────┘
             │
             │ Tap a contact
             ▼
┌─────────────────────────┐
│   Contact Detail Screen │
│                         │
│   Alice Tan             │
│                         │
│   Phone: 012-345 6789   │
│                         │
│   [ Back ]              │
└─────────────────────────┘
```

The list screen passes the selected contact's ID to the navigation graph, which then opens the corresponding detail screen.

---

## 🧩 Key Concepts

### 1. `LazyColumn`

`LazyColumn` is used to display vertically scrolling content.

```kotlin
LazyColumn {
    items(
        items = contacts,
        key = { it.id }
    ) { contact ->
        ContactRow(
            contact = contact,
            onClick = {
                onContactClick(contact.id)
            }
        )
    }
}
```

Unlike creating a large list using a normal `Column` and `forEach`, `LazyColumn` only composes the items currently required for display.

---

### 2. `items()`

The `items()` DSL builder creates one Composable for each element in the supplied list.

```kotlin
items(
    items = contacts,
    key = { it.id }
) { contact ->
    // UI for each contact
}
```

The practical specifically recommends providing a stable key using the item's ID.

---

### 3. Reusable Composables

Instead of writing the UI for every contact separately, create one reusable Composable:

```kotlin
@Composable
fun ContactRow(
    contact: Contact,
    onClick: () -> Unit
) {
    // Contact UI
}
```

Each contact can then reuse the same component.

This makes the application easier to maintain and avoids duplicating UI code. The practical specifically requires creating a reusable `ContactRow` component.

---

### 4. Data Classes

Kotlin `data class` is used to represent the information displayed by each list item.

```kotlin
data class Contact(
    val id: Int,
    val name: String,
    val phone: String
)
```

Each contact is represented as an object containing its own ID, name, and phone number.

---

### 5. Navigation Compose

Navigation Compose is used to move between the list and detail screens.

The navigation route for the detail screen contains the selected contact ID:

```text
contactDetail/{contactId}
```

When a user taps a contact:

```kotlin
navController.navigate("contactDetail/$id")
```

The detail screen then retrieves the ID from the navigation arguments.

---

## 🔎 Search and Filtering

The optional challenge can be implemented using Compose state.

```kotlin
var query by remember {
    mutableStateOf("")
}
```

The displayed list can then be filtered according to the search query:

```kotlin
val filteredContacts =
    if (query.isBlank()) {
        contacts
    } else {
        contacts.filter {
            it.name.contains(
                query,
                ignoreCase = true
            )
        }
    }
```

The reference implementation uses an `OutlinedTextField` with the label `"Search contacts"` and filters contacts by name.

---

## ⚙️ Dependencies

The practical requires Jetpack Compose and Navigation Compose.

The question paper specifies the following dependencies:

```kotlin
implementation("androidx.core:core-ktx:1.13.1")
implementation("androidx.activity:activity-compose:1.9.0")
implementation(platform("androidx.compose:compose-bom:2024.06.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.ui:ui-graphics")
implementation("androidx.compose.ui:ui-tooling-preview")
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose:2.7.7")
```

The repository's `app/build.gradle.kts` also includes the Compose and Navigation dependencies used by the project.

---

## 📱 SDK Configuration

For this practical, make sure your project uses:

```text
compileSdk = 37
targetSdk  = 37
```

The practical question paper explicitly instructs students to change both the target SDK and compile SDK to 37.

> **Important:** If Android Studio reports SDK or dependency errors, check your installed Android SDK and Gradle/Android Gradle Plugin versions.

---

## 🚀 Getting Started

### 1. Clone the Repository

Clone the repository:

```bash
git clone https://github.com/Reyzirk/AMIT3353_MobileAppPractical.git
```

### 2. Switch to Practical 5

```bash
cd AMIT3353_MobileAppPractical
git checkout Practical5
```

Alternatively, open the repository in Android Studio and select the `Practical5` branch.

### 3. Open in Android Studio

Open the project using **Android Studio**.

Allow Android Studio to:

* Sync Gradle.
* Download required dependencies.
* Index the project.
* Install any required SDK components.

### 4. Run the Application

Connect an Android device or start an Android Emulator.

Then click:

```text
Run ▶
```

The application should open with the contact list.

---

## 🧪 Expected Result

A successful implementation should allow you to:

* See a scrollable list.
* Display at least 10 items.
* View the item's relevant information.
* Tap an item.
* Navigate to a detail screen.
* View information belonging to the selected item.
* Return to the list using the Back button.

If you implement the optional challenge, the list should also update when the user enters a search query.

---

## ⚠️ Common Pitfalls

### Don't use `Column + forEach` for long lists

Avoid:

```kotlin
Column {
    contacts.forEach { contact ->
        ContactRow(contact)
    }
}
```

For a large or changing list, this composes all items at once.

Prefer:

```kotlin
LazyColumn {
    items(
        contacts,
        key = { it.id }
    ) { contact ->
        ContactRow(contact)
    }
}
```

The practical specifically warns against building long lists with `Column + forEach` because it composes everything at once and can negatively affect performance.

### Don't forget a stable key

Use:

```kotlin
key = { it.id }
```

rather than relying only on the item's position.

A stable key helps Compose correctly identify items when the list changes.

### Check your navigation route

Make sure the route used when navigating matches the route declared in the `NavHost`.

For example:

```kotlin
navController.navigate("contactDetail/$id")
```

should correspond to:

```kotlin
composable(
    route = "contactDetail/{contactId}"
)
```

---

## 📦 Deliverable Checklist

Before demonstrating your practical, check the following:

* [ ] Project builds successfully.
* [ ] Application launches successfully.
* [ ] A data class has been created.
* [ ] At least 10 items are available.
* [ ] Items are displayed using `LazyColumn`.
* [ ] A reusable row Composable is used.
* [ ] Each row can be clicked.
* [ ] Clicking a row opens the detail screen.
* [ ] The selected item's information is displayed.
* [ ] A Back button returns to the list.
* [ ] Stable keys are provided to `items()`.
* [ ] SDK configuration has been updated to compile/target SDK 37.
* [ ] Optional: Search/filter functionality has been implemented.

The first ten items and navigation functionality form the required practical deliverable; search/filtering is an optional extension.

---

## 📚 What You Should Understand After This Practical

You should be able to explain:

**Why use `LazyColumn` instead of `Column`?**

Because `LazyColumn` is designed for scrolling lists and composes only the items needed for display.

**Why use a `data class`?**

To provide a simple and structured representation of the data associated with each list item.

**Why create `ContactRow`?**

To make the list item UI reusable rather than duplicating the same UI code.

**Why pass an ID during navigation?**

The detail screen needs to know which item was selected so that it can display the corresponding information.

**Why use `key = { it.id }`?**

To give Compose a stable identity for each item, particularly when dealing with changing or larger lists.

---

## 🔗 Repository

**Practical 5 Branch:**
https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical5

This branch contains the Android Studio project corresponding to Practical 5. The repository currently contains the Android application module and the source packages used for the practical.

---

## 📝 Note for Students

This repository is intended as a **learning and reference resource** for AMIT3353 students.

Try to understand each component rather than simply copying the implementation. In particular, make sure you understand how:

```text
Data Class
    ↓
Sample Data
    ↓
LazyColumn
    ↓
Reusable Row
    ↓
Click Event
    ↓
Navigation
    ↓
Detail Screen
```

work together to form the application.
