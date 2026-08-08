# AMIT3353 Mobile App Development — Practical 6

## Component Demo — Shopping List App

This repository contains the implementation for **Practical 6** of **AMIT3353 Mobile App Development**.

Practical 6 brings together concepts introduced in **Labs 1–5** into a single small Android application. The practical focuses on combining **Jetpack Compose layouts, state management, lists, input components, dialogs, and navigation**.

The provided practical brief uses a **Shopping List App** as the main example. Students are expected to build a small multi-screen application that allows users to view shopping items, add new items, mark items as purchased, and delete items with confirmation.

> **Note:** The project in this branch may be incomplete. Use the Practical 6 question paper as the primary reference for completing the remaining implementation.

---

## 📌 Practical Overview

| Item             | Details                            |
| ---------------- | ---------------------------------- |
| **Module**       | AMIT3353 Mobile App Development    |
| **Practical**    | Practical 6                        |
| **Topic**        | Component Demo — Shopping List App |
| **Duration**     | 2 hours                            |
| **Platform**     | Android                            |
| **Language**     | Kotlin                             |
| **UI Toolkit**   | Jetpack Compose                    |
| **Navigation**   | Navigation Compose                 |
| **Data Storage** | In-memory sample data              |

The main objective is to combine previously learned concepts rather than introduce a completely new development concept.

---

## 🎯 Learning Objectives

By completing this practical, students should be able to:

* Integrate concepts from **Labs 1–5** into one application.
* Build vertically scrolling lists using `LazyColumn`.
* Manage UI state using Compose state.
* Create and update data using Kotlin data classes.
* Use `TextField` components for user input.
* Implement dropdown selections using `ExposedDropdownMenuBox`.
* Implement single-choice selections using `RadioButton`.
* Implement independent selections using `Checkbox`.
* Display confirmation dialogs using `AlertDialog`.
* Navigate between multiple Compose screens.
* Share application state between screens using **state hoisting**.
* Combine multiple UI components into a small functional application.

The practical brief specifically requires students to combine **layout, state, navigation, and lists** without introducing new major concepts.

---

## 🛠️ Requirements

Before starting, make sure you have:

* Android Studio
* Kotlin support
* Jetpack Compose
* Android SDK 37
* Basic understanding of:

  * Composable functions
  * State
  * Lists
  * Material 3 components
  * Navigation Compose

The practical requires the project to use:

```text
compileSdk = 37
targetSdk = 37
```

The current repository is configured with **compile SDK 37**, **target SDK 37**, and **minimum SDK 24**.

---

# 📚 Key Concepts

## 1. LazyColumn

`LazyColumn` is used to display the shopping list.

Unlike a normal `Column`, a `LazyColumn` only composes the items that are currently required for display.

```kotlin
LazyColumn {
    items(items) { item ->
        ShoppingItemRow(
            item = item,
            onCheckedChange = { },
            onDeleteClick = { }
        )
    }
}
```

The practical uses `LazyColumn` to demonstrate a vertically scrolling list of shopping items.

---

## 2. Checkbox

The shopping list uses a checkbox to indicate whether an item has been purchased.

When an item is marked as purchased, the item name is displayed using a strikethrough.

```kotlin
Checkbox(
    checked = item.purchased,
    onCheckedChange = onCheckedChange
)
```

The `purchased` property is stored as a Boolean value inside `ShoppingItem`.

---

## 3. RadioButton

Radio buttons are used to select the priority of a shopping item.

The available priorities are:

```text
Low
Medium
High
```

Only one priority should be selected at a time.

The practical also demonstrates making the entire row selectable rather than requiring the user to tap directly on the radio button.

---

## 4. ExposedDropdownMenuBox

The category of a shopping item is selected through a Material 3 dropdown.

Available categories:

```text
Groceries
Household
Electronics
Other
```

The dropdown uses an `ExposedDropdownMenuBox` with a read-only `OutlinedTextField`.

A particularly important detail is:

```kotlin
.menuAnchor()
```

Without the menu anchor, the dropdown may not open correctly.

---

## 5. AlertDialog

Deleting an item does not immediately remove it from the list.

Instead:

1. The user taps the delete icon.
2. The selected item is stored in `itemPendingDelete`.
3. An `AlertDialog` is displayed.
4. The user can choose **Cancel** or **Delete**.
5. The item is removed only after confirmation.

This demonstrates controlling the visibility of a dialog using Compose state.

---

## 6. State Hoisting

The shopping list is maintained above the `NavHost`.

This allows both the list screen and add-item screen to work with the same list.

```kotlin
val items = remember {
    mutableStateListOf<ShoppingItem>().apply {
        addAll(ShoppingData.sampleItems)
    }
}
```

This is important because navigating between screens should not cause newly added items to disappear.

The practical identifies this as **state hoisting**.

---

# 🧱 Application Structure

The recommended package structure is:

```text
com.example.practical6
│
├── data
│   └── ShoppingData.kt
│
├── model
│   └── ShoppingItem.kt
│
├── screen
│   ├── ShoppingItemRow.kt
│   ├── ListScreen.kt
│   └── AddItemScreen.kt
│
├── nav
│   └── AppNavGraph.kt
│
├── ui
│   └── theme
│
└── MainActivity.kt
```

The current repository already contains the `data`, `model`, and `screen` package structure under the `practical6` package.

---

# 📦 Data Model

Each shopping list entry is represented using a Kotlin `data class`.

```kotlin
data class ShoppingItem(
    val id: Int,
    val name: String,
    val category: String,
    val priority: String,
    val urgent: Boolean,
    val purchased: Boolean = false
)
```

The model contains:

| Property    | Purpose                                       |
| ----------- | --------------------------------------------- |
| `id`        | Unique identifier                             |
| `name`      | Shopping item name                            |
| `category`  | Item category                                 |
| `priority`  | Item priority                                 |
| `urgent`    | Indicates whether the item is urgent          |
| `purchased` | Indicates whether the item has been purchased |

The practical provides the above data model as the basis for the shopping list.

---

# 🗃️ Sample Data

`ShoppingData` stores the fixed choices and sample shopping items.

### Categories

```text
Groceries
Household
Electronics
Other
```

### Priorities

```text
Low
Medium
High
```

### Sample Items

| Item        | Category    | Priority | Urgent |
| ----------- | ----------- | -------- | ------ |
| Milk        | Groceries   | High     | Yes    |
| Light bulbs | Household   | Low      | No     |
| USB cable   | Electronics | Medium   | No     |
| Bread       | Groceries   | Medium   | No     |

These sample values are specified in the practical brief.

---

# 📱 Screens

The application consists of two main navigation destinations.

```text
        ┌───────────────┐
        │  List Screen  │
        └───────┬───────┘
                │
             Tap +
                │
                ▼
        ┌───────────────┐
        │ Add Item      │
        │ Screen        │
        └───────┬───────┘
                │
              Save
                │
                ▼
        ┌───────────────┐
        │  List Screen  │
        │  New Item     │
        └───────────────┘
```

The navigation graph uses:

```text
list
add
```

with `list` as the start destination.

---

# 🛒 List Screen

The List Screen is the main screen of the application.

It demonstrates:

* `Scaffold`
* `TopAppBar`
* `FloatingActionButton`
* `LazyColumn`
* `Checkbox`
* `AlertDialog`
* State management

Each shopping item is displayed using `ShoppingItemRow`.

Example layout:

```text
Shopping List Demo
────────────────────────────

☑ Milk                 🗑
  Groceries - High - URGENT

☐ Light bulbs          🗑
  Household - Low

☐ USB cable            🗑
  Electronics - Medium

                    [+]
```

If the list is empty, the application displays:

```text
No items yet. Tap + to add one.
```

The delete operation should require confirmation before removing an item.

---

# ➕ Add Item Screen

The Add Item Screen allows the user to create a new shopping item.

It contains:

### Item Name

A text field for entering the item name.

```text
Item name
┌──────────────────────────┐
│                          │
└──────────────────────────┘
```

### Category

A dropdown containing:

```text
Groceries
Household
Electronics
Other
```

### Priority

A radio button group containing:

```text
○ Low
○ Medium
● High
```

### Urgent

A labelled checkbox:

```text
☐ Mark as urgent
```

### Save

The Save button creates the item and returns to the list screen.

The Save button should only be enabled when the item name is not blank.

---

# 🔄 Application Flow

A complete test flow should look like this:

```text
Launch App
    │
    ▼
Shopping List
    │
    ├── Tick item
    │      └── Item becomes purchased
    │          └── Name receives strikethrough
    │
    ├── Tap +
    │      │
    │      ▼
    │   Add Item
    │      │
    │      ├── Enter item name
    │      ├── Select category
    │      ├── Select priority
    │      ├── Mark as urgent
    │      └── Save
    │
    │
    ▼
Shopping List
    │
    ├── New item appears
    │
    └── Tap Delete
           │
           ▼
       Confirmation Dialog
           │
           ├── Cancel → Item remains
           │
           └── Delete → Item removed
```

The question paper specifically requires students to test this complete flow.

---

# 📦 Dependencies

The practical requires the following main dependencies:

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

The current branch also contains additional Compose and testing dependencies in `app/build.gradle.kts`.

> **Tip:** Avoid blindly replacing your existing Gradle configuration if Android Studio has generated a newer project structure. Compare the required dependencies with your current project configuration and make the necessary additions.

---

# 🚀 Getting Started

## 1. Clone the Repository

Clone the repository:

```bash
git clone https://github.com/Reyzirk/AMIT3353_MobileAppPractical.git
```

---

## 2. Switch to Practical 6

```bash
git checkout Practical6
```

Alternatively, select the `Practical6` branch through Android Studio or GitHub.

---

## 3. Open in Android Studio

Open the cloned project in Android Studio.

Allow Gradle to sync and wait for the project indexing to complete.

---

## 4. Check SDK Configuration

Confirm that the application is configured for:

```text
compileSdk = 37
targetSdk = 37
minSdk = 24
```

The current Practical6 branch uses these SDK settings.

---

## 5. Run the Application

Run the application using:

* Android Emulator, or
* A physical Android device

Then verify the complete shopping list flow.

---

# ✅ Testing Checklist

Before considering the practical complete, verify the following.

### List

* [ ] Application launches successfully.
* [ ] Sample shopping items appear.
* [ ] List can be scrolled.
* [ ] Item category and priority are displayed.
* [ ] Urgent items display the `URGENT` label.

### Purchased State

* [ ] Milk can be checked.
* [ ] Checked items display a strikethrough.
* [ ] Items can be unchecked again.

### Add Item

* [ ] `+` button opens the Add Item screen.
* [ ] Item name can be entered.
* [ ] Category dropdown opens.
* [ ] Category can be selected.
* [ ] Priority can be selected.
* [ ] Urgent checkbox can be selected.
* [ ] Save is disabled when the item name is blank.
* [ ] Save adds the new item to the list.
* [ ] New item displays the correct category.
* [ ] New item displays the correct priority.
* [ ] New urgent items display `URGENT`.

### Delete

* [ ] Delete icon opens a confirmation dialog.
* [ ] Cancel keeps the item.
* [ ] Delete removes the item.

### Navigation

* [ ] List → Add Item works.
* [ ] Add Item → List works after saving.
* [ ] Back navigation works.
* [ ] Added items remain in the list during the session.

---

# ⚠️ Common Pitfalls

## Dropdown does not open

Make sure the read-only text field contains:

```kotlin
.menuAnchor()
```

The practical explicitly highlights this requirement.

---

## Radio buttons are difficult to tap

The practical uses a `Row` with:

```kotlin
.selectable(
    selected = priority == selectedPriority,
    onClick = { selectedPriority = priority },
    role = Role.RadioButton
)
```

The `RadioButton` itself uses:

```kotlin
onClick = null
```

so that the entire row handles the interaction.

---

## Delete happens immediately

Do not remove the item directly from `ShoppingItemRow`.

Instead, report the click to the parent screen and let `ListScreen` display the confirmation dialog.

This keeps the row responsible for reporting the event while the list screen manages the deletion state.

---

## Added items disappear after navigation

The list should be maintained above the `NavHost` using shared state.

For example:

```kotlin
val items = remember {
    mutableStateListOf<ShoppingItem>().apply {
        addAll(ShoppingData.sampleItems)
    }
}
```

This allows both destinations to work with the same list.

---

## Data disappears after restarting the app

This practical uses **in-memory data**.

Therefore, data disappearing after the application process is restarted is expected. Persistent storage is outside the scope of this practical.

---

# ⭐ Optional Challenge

As an extension, students can ensure that the list state is shared appropriately so that items added from the Add Item screen are not lost when navigating back to the list.

The practical describes this as an optional state-hoisting challenge.

Keep the scope small. The objective is to demonstrate the concepts covered in the practical rather than build a large shopping application.

---

# 📤 Deliverable

The expected deliverable is:

> **A small multi-screen Android application combining a list, input with state, and navigation.**

The completed application should demonstrate:

* A functional shopping list
* Multiple screens
* User input
* State management
* List rendering
* Selection controls
* Confirmation dialogs
* Navigation

This matches the deliverable specified in the Practical 6 brief.

---

# 🎓 What You Should Learn From This Practical

Practical 6 is primarily a **combination and reinforcement practical**.

Instead of learning one isolated component, students are expected to understand how several Compose concepts work together:

```text
Previous Labs
     │
     ├── Layout
     ├── State
     ├── Input
     ├── Lists
     └── Navigation
          │
          ▼
     Practical 6
          │
          ▼
   Small Functional App
```

The important skill is understanding how individual Compose components communicate through **state and event callbacks**.

For example:

```text
User Action
    ↓
Composable Event
    ↓
State Update
    ↓
Recomposition
    ↓
Updated UI
```

This pattern is one of the foundations of building interactive Jetpack Compose applications.

---

# 🔗 Repository

**Main Repository:**
https://github.com/Reyzirk/AMIT3353_MobileAppPractical

**Practical 6 Branch:**
https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical6

---

## 📖 Reference

* **AMIT3353 Practical 6 — Component Demo: Shopping List App**
* Duration: **2 hours**
* Prerequisite: **Labs 1–5 completed**

The practical brief states that the exercise is intended to integrate the concepts from Labs 1–5 into a single small application.

---

> **Course Note**
>
> This repository is intended as a learning reference for AMIT3353 Mobile App Development students. Students should understand the purpose of each component and implementation step rather than simply copying the code.
