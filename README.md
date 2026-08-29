# 📚 Android Jetpack Compose Practical Exercises

This repository contains a collection of practical exercises designed to build a strong foundation in **Android application development with Jetpack Compose**. Each practical introduces new concepts while reinforcing and extending topics learned in previous sessions.

The practicals progressively cover **UI development, layouts, state management, navigation, lists, project organization, input components, application architecture, asynchronous operations, and backend/database integration using Supabase**.

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
| **Practical 8** | Decoupling UI Components from `ViewModel` and Improving Application Architecture         |
| **Practical 9** | Supabase Backend as a Service (BaaS), PostgreSQL, API Integration, and CRUD Operations   |

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
* Design UI components that are less tightly coupled to application-specific dependencies.
* Apply object-oriented design principles to improve the separation between UI and application logic.
* Understand the concept of **Backend as a Service (BaaS)**.
* Create and configure a **Supabase** project.
* Work with a **PostgreSQL database** hosted by Supabase.
* Connect an Android application to Supabase using the **Supabase Kotlin client**.
* Perform **Create, Read, Update, and Delete (CRUD)** operations from a Jetpack Compose application.
* Use Kotlin serialization to map database records to Kotlin data classes.
* Use Kotlin coroutines to perform asynchronous database operations.
* Handle loading states, successful operations, and errors when communicating with a backend service.

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
    │
    ▼
Practical 8
    │
    ▼
UI Decoupling & Application Architecture
    │
    ▼
Practical 9
    │
    ▼
Supabase • PostgreSQL • API • CRUD
```

## 🏗️ Practical 7: Architecture

Practical 7 introduces a more structured approach to application development by separating the **UI layer** from application state and logic.

Practical 7 introduces:

* `ViewModel` for managing UI-related state and logic.
* `StateFlow` for exposing observable state.
* `collectAsStateWithLifecycle()` for lifecycle-aware state collection.
* `viewModelScope` for coroutine-based asynchronous operations.
* Configuration-change handling, such as preserving state during screen rotation.
* The **state-down, events-up** approach between the UI and ViewModel.

This builds upon the list application developed in the earlier practicals and refactors its state management into a `ViewModel`.

## 🧩 Practical 8: UI Decoupling & Application Architecture

Practical 8 builds upon the architectural concepts introduced in Practical 7 by focusing on **reducing coupling between UI components and application logic**.

The practical explores how a Compose screen can be designed so that it does not depend directly on a specific `ViewModel` implementation.

Key concepts include:

* Separating UI presentation from application logic.
* Passing state and event handlers into composable functions.
* Reducing direct dependencies between UI components and `ViewModel`.
* Improving the reusability and testability of composable functions.
* Applying object-oriented design principles to Android application architecture.
* Designing components that can continue to function independently of a particular implementation of application logic.

The goal is to move towards a more modular architecture where the UI focuses primarily on **displaying state and communicating user events**, while application logic can be supplied independently.

## ☁️ Practical 9: Supabase Backend as a Service

Practical 9 introduces **Supabase** as a Backend as a Service (BaaS) platform.

Supabase provides a **PostgreSQL database** together with backend functionality that can be accessed by applications. In this practical, an Android application built with Jetpack Compose communicates with Supabase to manage contact records.

The practical introduces:

* **Supabase** as a Backend as a Service.
* **PostgreSQL** database tables.
* Supabase project and database configuration.
* Supabase API access.
* The **Supabase Kotlin client**.
* **PostgREST** for database operations.
* Kotlin serialization using `@Serializable`.
* Kotlin coroutines for asynchronous backend operations.
* Database **CRUD operations**:

  * Create — Add a contact.
  * Read — Retrieve contacts.
  * Update — Modify a contact.
  * Delete — Remove a contact.
* Loading and error handling in a Compose UI.
* Connecting a mobile application to a cloud-hosted backend.

The practical uses a `contact` table containing:

| Column       | Type          | Description                       |
| ------------ | ------------- | --------------------------------- |
| `id`         | `int8`        | Auto-generated primary key        |
| `created_at` | `timestamptz` | Automatically generated timestamp |
| `name`       | `text`        | Contact name                      |
| `email`      | `text`        | Contact email address             |

The practical specifically demonstrates CRUD functionality against this table using the Supabase Kotlin client.

### 🔐 API Key & Security

Practical 9 demonstrates the use of a Supabase Secret key for the laboratory exercise so that CRUD operations can work immediately.

However, the practical highlights an important security consideration: **a Secret key should not be embedded in a published Android application**, because a key contained in an APK can be extracted.

For a real application, the practical recommends using the **Publishable key together with Row Level Security (RLS) policies**.

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
├── Practical7
├── Practical8
└── Practical9
```

To work on a particular practical, switch to its corresponding branch.

For example:

```bash
git checkout Practical9
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
git checkout Practical9
```

### 4. Sync and run

Allow Android Studio to synchronize the Gradle project, then run the application using an **Android Emulator** or connected **Android device**.

For Practical 9, you will additionally need to configure the required **Supabase project, database table, API access, and Android dependencies** before running the application.

## 📌 Notes for Students

Each practical is intended to be completed progressively. Earlier practicals provide concepts and application components that are reused or extended in later exercises.

It is recommended that you:

* Complete the practicals in order.
* Read and understand the provided code.
* Experiment with the examples rather than simply copying them.
* Review previous practicals when working on later exercises.
* Test your application on an Android Emulator or physical Android device.
* Pay attention to how application architecture develops as the practicals progress.
* Understand the difference between **UI code, application logic, and backend services**.
* Keep API keys and other sensitive configuration information secure.
* Verify database operations when working with Supabase.

> **Tip:** Later practicals build upon concepts introduced earlier. Practical 7 introduces application architecture with `ViewModel` and `StateFlow`, Practical 8 focuses on reducing UI coupling, and Practical 9 extends the application by connecting it to a cloud-based backend and PostgreSQL database.

---

> These practical exercises are intended for educational purposes and provide hands-on experience with Android development, Jetpack Compose, application architecture, and backend integration.
