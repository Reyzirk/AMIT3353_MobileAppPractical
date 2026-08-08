# 📚 Android Jetpack Compose Practical Exercises

This repository contains a collection of practical exercises designed to build a strong foundation in **Android application development with Jetpack Compose**. Each practical introduces new concepts while reinforcing and extending topics learned in previous sessions.

The practicals progressively cover **UI development, layouts, state management, navigation, lists, project organization, input components, and application architecture using ViewModel and StateFlow**.

## 📖 Practical List

| Practical       | Topics Covered                                                                           |
| --------------- | ---------------------------------------------------------------------------------------- |
| **Practical 1** | Introduction to Jetpack Compose                                                          |
| **Practical 2** | Layouts: `Column`, `Row`, and `Box`                                                      |
| **Practical 3** | State Management: `State`, `remember`, Recomposition, User Input, and State Maintenance  |
| **Practical 4** | Navigation in Jetpack Compose                                                            |
| **Practical 5** | `LazyColumn` and File Organization/Categorization                                        |
| **Practical 6** | Review of Previous Topics and Advanced Usage of Input Components                         |
| **Practical 7** | Architecture: `ViewModel`, `StateFlow`, Lifecycle-aware State Collection, and Coroutines |

## 🎯 Learning Objectives

By completing these practical exercises, you will learn how to:

* Build modern Android user interfaces using **Jetpack Compose**.
* Create responsive layouts using `Column`, `Row`, and `Box`.
* Manage UI state using Compose state APIs such as `State` and `remember`.
* Understand recomposition and how UI responds to state changes.
* Handle user input using Compose input components.
* Implement navigation between different screens.
* Display lists efficiently using `LazyColumn`.
* Organize project files following good development practices.
* Apply advanced input components and reinforce previously learned concepts.
* Separate UI from application logic using `ViewModel`.
* Manage and expose UI state using `StateFlow`.
* Collect state safely in Compose using `collectAsStateWithLifecycle()`.
* Perform asynchronous operations using Kotlin coroutines and `viewModelScope`.
* Understand how `ViewModel` helps preserve UI state across configuration changes such as screen rotation.

## 🧭 Practical Progression

The practical exercises are designed to progressively introduce concepts used in Android application development:

```text
Practical 1
    │
    ▼
Jetpack Compose Fundamentals
    │
    ▼
Practical 2
    │
    ▼
Layouts
Column • Row • Box
    │
    ▼
Practical 3
    │
    ▼
State & User Input
    │
    ▼
Practical 4
    │
    ▼
Navigation
    │
    ▼
Practical 5
    │
    ▼
Lists & Project Organization
    │
    ▼
Practical 6
    │
    ▼
Review & Advanced Input Components
    │
    ▼
Practical 7
    │
    ▼
ViewModel • StateFlow • Coroutines
```

## 🏗️ Practical 7: Architecture

The latest practical introduces a more structured approach to application development by separating the **UI layer** from application state and logic.

Practical 7 introduces:

* `ViewModel` for managing UI-related state and logic.
* `StateFlow` for exposing observable state.
* `collectAsStateWithLifecycle()` for lifecycle-aware state collection.
* `viewModelScope` for coroutine-based asynchronous operations.
* Configuration-change handling, such as preserving state during screen rotation.
* The **state-down, events-up** approach between the UI and ViewModel.

This builds upon the list application developed in the earlier practicals and refactors its state management into a `ViewModel`.

## 📂 Repository Structure

Each practical is maintained in its own Git branch:

```text
AMIT3353_MobileAppPractical
│
├── Practical1
├── Practical2
├── Practical3
├── Practical4
├── Practical5
├── Practical6
└── Practical7
```

To work on a particular practical, switch to its corresponding branch.

For example:

```bash
git checkout Practical7
```

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Reyzirk/AMIT3353_MobileAppPractical.git
```

### 2. Open the project

Open the cloned project using **Android Studio**.

### 3. Select a practical branch

Choose the practical you want to work on:

```bash
git checkout Practical1
```

or:

```bash
git checkout Practical7
```

### 4. Sync and run

Allow Android Studio to synchronize the Gradle project, then run the application using an Android Emulator or connected Android device.

## 📌 Notes for Students

Each practical is intended to be completed progressively. Earlier practicals provide concepts and application components that are reused or extended in later practicals.

It is recommended that you:

* Complete the practicals in order.
* Read and understand the provided code.
* Experiment with the examples rather than simply copying them.
* Review previous practicals when working on later exercises.
* Test your application on an Android Emulator or physical Android device.
* Pay attention to how application architecture develops as the practicals progress.

> **Tip:** Practical 7 builds directly upon concepts introduced in earlier practicals, particularly state management and list-based UI development.

---

> These practical exercises are intended for educational purposes and provide hands-on experience with Android development and the core concepts of Jetpack Compose.
