# AMIT3353 Mobile App Development — Practical 2

## 🎨 Compose UI Basics & Layout

This repository contains the solution and reference implementation for **Practical 2** of **AMIT3353 Mobile App Development**.

The practical introduces the fundamentals of building user interfaces using **Jetpack Compose**, including `Text`, `Icon`, `Button`, `Column`, `Row`, `Box`, `Modifier`, Material 3 components, reusable Composables, and Compose Preview.

> **Repository Branch:** `Practical2`
> **Practical Duration:** 2 Hours
> **Topic:** Compose UI Basics & Layout

---

## 📚 Practical Overview

### Topic

**Compose UI Basics & Layout**

### Duration

**2 hours**

### Prerequisites

Students should have:

* Completed **Practical 1 / Lab 1**
* A working Android Studio project
* An Android Emulator or physical Android device
* Basic knowledge of Kotlin and Jetpack Compose

The practical assumes that you already have a working Android project and device available for development and testing.

---

## 🎯 Learning Objectives

By completing this practical, you should be able to:

* Use basic Jetpack Compose UI components such as `Text`, `Icon`, and `Button`.
* Arrange UI elements using `Column`, `Row`, and `Box`.
* Apply `Modifier` to control size, spacing, alignment, background, and clipping.
* Use Material 3 components such as `Card`, `Button`, and `HorizontalDivider`.
* Apply Material 3 colours and typography through `MaterialTheme`.
* Create reusable Composable functions.
* Understand how Modifier ordering affects the resulting UI.
* Use `@Preview` to inspect a Compose screen before running the application.
* Build a complete static screen from a design specification.

The Practical 2 question paper identifies `Column`, `Row`, `Box`, `Modifier`, and Material 3 as the main concepts students should understand.

---

# 📋 Practical Requirements

The practical requires you to build a **static profile screen** using Jetpack Compose.

The screen should contain:

1. A custom Material 3 theme.
2. A `Scaffold` as the screen structure.
3. A `Box` as the main screen container.
4. A Material 3 `Card`.
5. A circular profile icon.
6. A person's name.
7. A person's role.
8. A horizontal divider.
9. An email contact row.
10. A phone contact row.
11. A reusable `ContactRow` Composable.
12. A `Contact` button.
13. A Compose `@Preview`.
14. A working application that can be run on a device.

The practical is focused on creating the UI itself. The `Contact` button does not need to perform an action at this stage; interactivity is introduced in a later practical.

---

# 🧩 Practical Tasks

## Task 1 — Create a Custom Theme

Create a custom Material 3 theme using:

* `lightColorScheme`
* `Typography`
* `TextStyle`
* `MaterialTheme`

The practical provides a teal primary colour and a light background as the example theme.

```kotlin
val customColors = lightColorScheme(
    primary = Color(0xFF00796B),
    background = Color(0xFFF2F2F2)
)
```

The theme should then be applied around the application's Compose content.

---

## Task 2 — Create the Profile Screen

Create a `ProfileScreen` Composable.

The screen should use a `Box` that:

* Fills the available screen.
* Has a background colour.
* Centres the profile card.

```kotlin
@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        // Profile card
    }
}
```

The question paper introduces `Box` as the main container for positioning the profile content.

---

## Task 3 — Create the Profile Card

Create a separate `ProfileCard` Composable.

The card should contain a `Column` which arranges the profile information vertically.

The expected structure is:

```text
ProfileScreen
    │
    └── Box
         │
         └── ProfileCard
              │
              └── Card
                   │
                   └── Column
```

The `Card` should use appropriate padding and fill the available width.

The `Column` should centre its children horizontally.

---

## Task 4 — Add the Profile Icon

Create a circular profile icon using:

* `Box`
* `size`
* `clip`
* `CircleShape`
* `background`
* `Icon`

The practical specifies a `96.dp` circular area with a person icon inside it.

The reference implementation uses a drawable resource for the person icon.

---

## Task 5 — Add Name and Role

Display the person's:

* Name
* Role

The reference implementation uses:

```text
Jane Developer
Android Engineer
```

with Material typography styles.

You may replace the example information with your own information when experimenting with the application.

The question paper introduces the name using a headline style and the role using a smaller body style.

---

## Task 6 — Add a Divider

Use `HorizontalDivider()` to separate the profile information from the contact information.

Add spacing before and after the divider:

```kotlin
Spacer(Modifier.height(16.dp))
HorizontalDivider()
Spacer(Modifier.height(16.dp))
```

This helps separate the different sections of the profile card visually.

---

## Task 7 — Create a Reusable `ContactRow`

Create a reusable Composable called `ContactRow`.

It should accept:

```text
icon
text
```

and display them horizontally using a `Row`.

Conceptually:

```text
ContactRow
    │
    ├── Icon
    │
    └── Text
```

The same Composable should be reused for both:

* Email
* Phone

The question paper specifically introduces reusable contact rows as an example of avoiding duplicated UI code.

The reference implementation follows this approach in `MainActivity.kt`.

---

## Task 8 — Add the Contact Button

Add a Material 3 `Button` at the bottom of the profile card.

The button should:

* Display `"Contact"`.
* Fill the available width.
* Have an empty `onClick` action for this practical.

```kotlin
Button(
    onClick = { },
    modifier = Modifier.fillMaxWidth()
) {
    Text("Contact")
}
```

The practical intentionally leaves the button without functionality because state and interactivity are covered later.

---

## Task 9 — Add Compose Preview

Create a preview for the profile screen using:

```kotlin
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MyCustomTheme {
        ProfileScreen()
    }
}
```

The preview allows you to inspect the UI directly inside Android Studio before running the application.

---

# 📱 Expected Application Flow

The completed application should display a static profile card similar to:

```text
┌─────────────────────────────┐
│                             │
│          ┌───────┐          │
│          │   👤  │          │
│          └───────┘          │
│                             │
│      Jane Developer         │
│      Android Engineer       │
│                             │
│  ─────────────────────────  │
│                             │
│  ✉  jane@example.com        │
│                             │
│  ☎  +60 12-345 6789         │
│                             │
│  ┌───────────────────────┐  │
│  │       Contact         │  │
│  └───────────────────────┘  │
│                             │
└─────────────────────────────┘
```

The exact appearance may vary depending on the device, Android Studio version, theme configuration, and screen size.

The important requirement is that the specified Compose components and layout structure are implemented correctly.

---

# 🧠 Key Concepts

## 1. `Column`

`Column` arranges its children vertically.

```kotlin
Column {
    Text("Name")
    Text("Role")
    Button(onClick = {}) {
        Text("Contact")
    }
}
```

The profile card uses a `Column` because its contents are arranged from top to bottom.

---

## 2. `Row`

`Row` arranges its children horizontally.

The `ContactRow` uses a `Row` to place the contact icon beside its text.

```text
Icon  →  Text
```

---

## 3. `Box`

`Box` is useful for positioning or stacking content.

In this practical it is used:

* As the main screen container.
* To create the circular profile icon.

---

## 4. `Modifier`

`Modifier` controls the appearance and layout behaviour of Compose components.

Examples used in this practical include:

```kotlin
.fillMaxSize()
.fillMaxWidth()
.padding(24.dp)
.size(96.dp)
.clip(CircleShape)
.background(...)
```

Modifier order is important because changing the order can change the resulting UI.

The practical specifically identifies Modifier ordering as a common pitfall.

---

## 5. Material 3

The application uses Material 3 components including:

* `Card`
* `Button`
* `Icon`
* `HorizontalDivider`
* `MaterialTheme`

Material 3 also provides the colour scheme and typography used throughout the screen.

---

## 6. Reusable Composables

Instead of duplicating UI code, create reusable functions.

For example:

```kotlin
ContactRow(...)
```

can be used for:

```text
Email
Phone
```

The same idea can be extended to other types of contact information.

---

## 7. Compose Preview

`@Preview` allows you to inspect a Composable directly in Android Studio.

This creates a useful development loop:

```text
Write UI
   ↓
Preview
   ↓
Adjust layout
   ↓
Run application
   ↓
Test on device
```

The question paper specifically asks students to preview the screen and then run it on a device.

---

# 🏗️ Project Structure

The `Practical2` branch follows a simple Android project structure.

```text
app/
└── src/
    └── main/
        ├── java/
        │   └── com/example/dcsg3g4_practical1/
        │       │
        │       ├── MainActivity.kt
        │       │
        │       └── ui/
        │           └── theme/
        │
        ├── res/
        │   └── drawable/
        │       ├── person
        │       ├── email
        │       └── phone
        │
        └── AndroidManifest.xml
```

The main implementation is contained in:

```text
MainActivity.kt
```

The current reference implementation contains:

* `MainActivity`
* `ProfileScreen`
* `ProfileCard`
* `ContactRow`
* `MyCustomTheme`
* Compose preview functions

and uses drawable resources for the profile, email, and phone icons.

### `MainActivity.kt`

The application's entry point.

It:

* Enables edge-to-edge display.
* Sets up Compose content.
* Applies the application theme.
* Creates the `Scaffold`.
* Launches `ProfileScreen`.

### `ProfileScreen`

Responsible for the overall screen layout.

It uses a `Box` to provide the background and centre the profile card.

### `ProfileCard`

Contains the actual profile information inside a Material 3 `Card`.

### `ContactRow`

A reusable Composable for displaying an icon alongside contact information.

### `MyCustomTheme`

Defines the custom Material 3 colour scheme and typography used by the example.

---

# 🔄 UI Structure

The main Compose hierarchy can be represented as:

```text
MainActivity
     │
     ▼
MaterialTheme
     │
     ▼
Scaffold
     │
     ▼
ProfileScreen
     │
     ▼
Box
     │
     ▼
ProfileCard
     │
     ▼
Card
     │
     ▼
Column
     │
     ├── Profile Icon
     │
     ├── Name
     │
     ├── Role
     │
     ├── HorizontalDivider
     │
     ├── ContactRow — Email
     │
     ├── ContactRow — Phone
     │
     └── Contact Button
```

This structure demonstrates how a larger Compose screen can be divided into smaller, reusable UI components.

---

# ⚠️ Common Pitfalls

## Modifier Order

Be careful with Modifier order.

For example:

```kotlin
.padding(16.dp)
.background(Color.Blue)
```

and:

```kotlin
.background(Color.Blue)
.padding(16.dp)
```

can produce different results.

Always consider which part of the component each Modifier should affect.

The practical specifically warns that padding before or after background can produce different visuals.

---

## Missing `dp` Import

If Android Studio cannot resolve `dp`, make sure the following import is available:

```kotlin
import androidx.compose.ui.unit.dp
```

The question paper specifically highlights this as a common issue.

---

## Incorrect Alignment

Remember that different layout containers have different alignment mechanisms.

For example:

```kotlin
Column(
    horizontalAlignment = Alignment.CenterHorizontally
)
```

centres the Column's children horizontally.

Whereas:

```kotlin
Box(
    contentAlignment = Alignment.Center
)
```

centres the content inside the Box.

---

## Incorrect Theme

If the preview does not look like the running application, check that the preview and application are using the intended theme.

For example:

```kotlin
MyCustomTheme {
    ProfileScreen()
}
```

---

## Button Functionality

Do not spend time implementing contact functionality for this practical.

The `Contact` button is intentionally non-functional. Interactivity is introduced in a later practical.

---

# ⭐ Optional Challenge

The question paper provides an optional extension.

Try wrapping the profile card in a Material 3 `Card` with elevation and use the `Box` to provide a coloured background.

You can also experiment with:

* Card elevation
* Card shape
* Padding
* Spacing
* Typography
* Icon size
* Background colour
* Primary colour

These changes are intended to help you understand how Compose modifiers and Material 3 components affect the final UI.

---

# 📦 Practical Deliverable

The required deliverable is a **working static profile screen** implemented using Jetpack Compose.

Your application should demonstrate:

* Compose layout fundamentals.
* Material 3 components.
* Correct use of `Modifier`.
* Reusable Composables.
* A custom theme.
* A working Compose Preview.
* A working application running on an Android device or emulator.

The practical is intended to demonstrate your understanding of Compose UI construction rather than application functionality.

---

# 🚀 Getting Started

## 1. Clone the Repository

Clone the repository:

```bash
git clone https://github.com/Reyzirk/AMIT3353_MobileAppPractical.git
```

---

## 2. Switch to Practical 2

```bash
cd AMIT3353_MobileAppPractical
git checkout Practical2
```

Alternatively, select the `Practical2` branch from Android Studio's Git branch menu.

The `Practical2` branch contains the Android project and Gradle configuration required for the practical.

---

## 3. Open in Android Studio

1. Open **Android Studio**.
2. Select **Open**.
3. Select the cloned project folder.
4. Allow Gradle to sync.
5. Wait for the project to finish indexing and building.

---

## 4. Run the Application

You can run the application using either:

* An Android Emulator, or
* A physical Android device.

Select your desired device in Android Studio and click:

```text
Run ▶
```

The application should display the profile screen.

---

# 🧪 Practical 2 Checklist

Use this checklist before considering your implementation complete.

### Compose Layout

* [ ] `ProfileScreen` created.
* [ ] `Box` used as the main screen container.
* [ ] `Card` used for the profile.
* [ ] `Column` used to arrange the card content.
* [ ] `Row` used for contact information.
* [ ] `Modifier` applied correctly.
* [ ] Modifier ordering is understood.

### Profile Content

* [ ] Circular profile icon displayed.
* [ ] Name displayed.
* [ ] Role displayed.
* [ ] `HorizontalDivider` displayed.
* [ ] Email contact information displayed.
* [ ] Phone contact information displayed.
* [ ] `Contact` button displayed.

### Reusable Components

* [ ] `ContactRow` created.
* [ ] `ContactRow` accepts an icon.
* [ ] `ContactRow` accepts text.
* [ ] `ContactRow` reused for email.
* [ ] `ContactRow` reused for phone.

### Theme & Preview

* [ ] Material 3 theme applied.
* [ ] Custom colour scheme implemented.
* [ ] Typography configured.
* [ ] `@Preview` created.
* [ ] Preview displays correctly.

### Testing

* [ ] Project builds successfully.
* [ ] Gradle sync completes successfully.
* [ ] Application runs on an emulator/device.
* [ ] Profile screen displays correctly.
* [ ] Layout is checked on the target device.

---

# 📖 What You Should Understand

This practical is not only about creating a profile card.

You should be able to explain:

### Why use `Column`?

Because the profile information needs to be arranged vertically.

### Why use `Row`?

Because the contact icon and text need to be positioned horizontally.

### Why use `Box`?

Because it can provide a container for positioning content and is also useful for creating the circular profile icon.

### Why does Modifier order matter?

Because Modifiers are applied in sequence, and changing their order can change the size, position, clipping, padding, or background of a component.

### Why create `ContactRow`?

Because the same UI structure can be reused for multiple pieces of contact information without duplicating code.

### Why use `@Preview`?

Because it allows you to inspect and adjust a Composable without repeatedly launching the application.

### Why use Material 3?

Because it provides ready-made UI components, colours, typography, and theming for Compose applications.

---

# 🎓 Learning Outcome

After completing Practical 2, you should have a practical understanding of how to construct a static Android user interface using **Jetpack Compose**.

You should be comfortable using:

```text
Column
Row
Box
Modifier
Card
Text
Icon
Button
MaterialTheme
@Preview
```

More importantly, you should understand how these components can be combined to create a structured and reusable UI.

This practical forms the foundation for later practicals where the UI becomes interactive and starts managing application state.

---

# 🔗 Repository

**AMIT3353 Mobile App Development — Practical 2**

[AMIT3353_MobileAppPractical — Practical2](https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical2?utm_source=chatgpt.com)

---

# 📌 Reference

**AMIT3353 Mobile App Development**

**Practical 2 — Compose UI Basics & Layout**

The implementation and requirements in this README are based on the **Practical 2 question paper** and the reference implementation in the `Practical2` branch.

> Use this repository as a reference while completing your own practical implementation. Make sure you understand the concepts and code rather than simply copying the implementation.
