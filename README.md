# AMIT3353 Mobile App Development — Practical 3

## State & Interactivity with Jetpack Compose

This repository contains the implementation for **Practical 3** of AMIT3353 Mobile App Development.

The practical focuses on managing state and user interaction in **Jetpack Compose**, including `remember`, `mutableStateOf`, recomposition, user input, button events, and state hoisting.

> **Repository Branch:** `Practical3`

---

## 📚 Practical Overview

### Topic

**State & Interactivity**

### Duration

**2 hours**

### Prerequisites

Students should have completed:

* Practical 1
* Practical 2

The practical assumes that you already have basic knowledge of building Android user interfaces using Jetpack Compose.

---

## 🎯 Learning Objectives

By completing this practical, you should be able to:

* Explain **recomposition** and why the UI should be driven by state.
* Use `remember` and `mutableStateOf` to store and update state.
* Read user input from Compose UI components.
* Respond to user events such as button clicks.
* Understand the basics of **state hoisting**.
* Create reusable, stateless composables.

---

## 🧠 Key Concepts

### 1. State

State is a value that can change and should cause the UI to update when it changes.

In Compose, state can be created using:

```kotlin
var weight by remember { mutableStateOf("") }
var height by remember { mutableStateOf("") }
var result by remember { mutableStateOf("") }
```

When these values change, Compose can recompose the relevant parts of the UI.

---

### 2. `remember`

`remember` allows a value to survive recomposition.

Without `remember`, a state value may be recreated whenever the composable recomposes, causing user input to be lost.

```kotlin
var weight by remember { mutableStateOf("") }
```

The practical specifically highlights that forgetting `remember` can cause a text field to appear as if it cannot retain typed input.

---

### 3. Recomposition

**Recomposition** occurs when Compose re-runs composables that read state which has changed.

For example:

```kotlin
result = "BMI: 22.5 — Normal weight"
```

Updating `result` causes the UI displaying that state to be updated.

---

### 4. State Hoisting

State hoisting means moving state out of a composable and passing it into the composable through parameters.

Instead of having a reusable input component manage its own state, the component receives:

```kotlin
value
onValueChange
```

For example:

```kotlin
@Composable
fun InputRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // UI
}
```

This makes the composable **stateless and reusable**.

---

# 🧪 Practical Tasks

## Task 1 — Create the Calculator Screen

Build a **BMI Calculator** screen.

The application should maintain three pieces of state:

```kotlin
var weight by remember { mutableStateOf("") }
var height by remember { mutableStateOf("") }
var result by remember { mutableStateOf("") }
```

The screen should contain:

* A title: **BMI Calculator**
* A weight input
* A height input
* A Calculate BMI button
* A result display

The provided practical specification uses weight in **kg** and height in **metres**.

---

## Task 2 — Add TextFields

Create two `TextField` components.

### Weight

The weight field should:

* Display `Weight (kg)`
* Store its value in `weight`
* Update the state using `onValueChange`
* Use a decimal keyboard
* Be a single-line input

Example:

```kotlin
TextField(
    value = weight,
    onValueChange = { weight = it },
    label = { Text("Weight (kg)") },
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Decimal
    ),
    singleLine = true
)
```

### Height

Create a similar field for:

```text
Height (m)
```

The practical requires numeric/decimal input handling for both fields.

---

## Task 3 — Calculate BMI

Add a **Calculate BMI** button.

When the button is pressed:

1. Read the weight input.
2. Read the height input.
3. Convert the input strings to `Double`.
4. Validate the values.
5. Calculate the BMI.
6. Determine the BMI category.
7. Update the `result` state.

The BMI formula is:

```text
BMI = weight / (height × height)
```

The practical specifies these categories:

|             BMI | Category      |
| --------------: | ------------- |
|        `< 18.5` | Underweight   |
| `18.5 – < 25.0` | Normal weight |
| `25.0 – < 30.0` | Overweight    |
|       `>= 30.0` | Obese         |

Use `toDoubleOrNull()` to safely convert the text input into a number.

---

## Task 4 — Display the Result

Display the BMI result below the button.

The result should **only change when the Calculate BMI button is pressed**.

For example:

```text
BMI: 22.5 — Normal weight
```

The practical suggests displaying the result inside a `Card` using Material 3 styling.

---

## Task 5 — Apply State Hoisting

Refactor the input field into a reusable stateless composable called `InputRow`.

It should receive:

```kotlin
label: String
value: String
onValueChange: (String) -> Unit
```

Example:

```kotlin
@Composable
fun InputRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}
```

Then use it from the main screen:

```kotlin
InputRow(
    label = "Weight (kg)",
    value = weight,
    onValueChange = { weight = it }
)
```

Repeat the same approach for the height field.

---

# 📱 Expected Application Flow

The application should behave approximately as follows:

```text
┌─────────────────────────────┐
│       BMI Calculator        │
│                             │
│  ┌───────────────────────┐  │
│  │ Weight (kg)            │  │
│  └───────────────────────┘  │
│                             │
│  ┌───────────────────────┐  │
│  │ Height (m)             │  │
│  └───────────────────────┘  │
│                             │
│  ┌───────────────────────┐  │
│  │    Calculate BMI      │  │
│  └───────────────────────┘  │
│                             │
│  ┌───────────────────────┐  │
│  │ BMI: 22.5             │  │
│  │ Normal weight         │  │
│  └───────────────────────┘  │
└─────────────────────────────┘
```

The exact visual design may differ, but the required functionality should be present.

---

# ⚠️ Common Pitfalls

### Forgetting `remember`

Incorrect state handling can cause input to reset during recomposition.

Use:

```kotlin
var weight by remember { mutableStateOf("") }
```

rather than creating a normal local variable.

---

### Treating TextField input as a number

`TextField` provides its value as a `String`.

Therefore, convert it safely:

```kotlin
val weightValue = weight.toDoubleOrNull()
```

Do not assume that the user always enters a valid number.

The practical specifically recommends `toDoubleOrNull()` and safe handling of invalid input.

---

### Invalid height

A height of `0` or a negative value cannot be used for the BMI calculation.

Your application should handle this case rather than allowing an invalid calculation.

---

### Updating the result too early

The result should update **after the Calculate BMI button is pressed**, not continuously while the user types.

---

### Incorrect state hoisting

The `InputRow` composable should not create and own the input state.

Instead, the parent composable should own the state and pass it into `InputRow`.

```text
Parent Screen
     │
     ├── weight state
     ├── height state
     │
     ▼
  InputRow
     │
     ├── value
     └── onValueChange
```

---

# ⭐ Optional Challenge

As an extension, add input validation.

For example:

* Show an error when a field is empty.
* Show an error when the input is non-numeric.
* Colour the result according to the BMI category.

These extensions are optional and are listed as the challenge section of the practical.

---

# ✅ Deliverable

You should have a **working BMI calculator application** that:

* Accepts two numeric inputs.
* Accepts weight in kilograms.
* Accepts height in metres.
* Calculates BMI when the button is pressed.
* Displays the calculated result.
* Displays the corresponding BMI category.
* Uses Compose state correctly.
* Demonstrates state hoisting through the reusable input composable.

You are required to **demonstrate the working application live on a device**.

---

# 🚀 Getting Started

## 1. Clone the Repository

Clone the repository:

```bash
git clone https://github.com/Reyzirk/AMIT3353_MobileAppPractical.git
```

---

## 2. Checkout Practical 3

Switch to the `Practical3` branch:

```bash
git checkout Practical3
```

Or clone the branch directly:

```bash
git clone -b Practical3 https://github.com/Reyzirk/AMIT3353_MobileAppPractical.git
```

The repository's `Practical3` branch contains the Android project structure, including the `app`, `gradle`, and Gradle configuration files.

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

Select the desired device from Android Studio and click **Run ▶**.

---

# 📖 What You Should Understand

This practical is not only about creating a BMI calculator.

You should be able to explain:

### Why is `remember` used?

Because Compose may recompose the UI. `remember` allows the state value to survive recomposition.

### What causes recomposition?

When state read by a composable changes, Compose can re-run the relevant composable functions.

### Why use `mutableStateOf`?

It creates observable Compose state. When its value changes, Compose knows that the UI depending on that state may need to be updated.

### Why use state hoisting?

It separates **state management** from **UI presentation**, allowing components such as `InputRow` to become reusable and stateless.

---

# 📝 Practical Checklist

Before your demonstration, make sure you can check all of the following:

* [ ] Project builds successfully.
* [ ] BMI Calculator screen is displayed.
* [ ] Weight field accepts input.
* [ ] Height field accepts input.
* [ ] Decimal keyboard is used.
* [ ] `remember` and `mutableStateOf` are used.
* [ ] Calculate button works.
* [ ] BMI is calculated correctly.
* [ ] BMI category is displayed.
* [ ] Invalid numeric input is handled safely.
* [ ] Height validation is handled.
* [ ] Result only updates after pressing the button.
* [ ] `InputRow` demonstrates state hoisting.
* [ ] Application runs successfully on a device.
* [ ] You can explain how state and recomposition work.

---

## 📌 Reference

**AMIT3353 Mobile App Development — Practical 3**

**Topic:** State & Interactivity

The implementation and requirements in this README are based on the Practical 3 question paper provided for the class.

**Repository:** [AMIT3353_MobileAppPractical — Practical3](https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical3)

---

## 🎓 Learning Outcome

By completing this practical, you should have a practical understanding of how **state drives UI in Jetpack Compose** and how user interactions can modify state and trigger UI updates.

The BMI calculator is simply the application used to demonstrate these concepts.
