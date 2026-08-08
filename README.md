# AMIT3353 Mobile Application Development — Practical 4

## 🧭 Navigation & Multiple Screens with Jetpack Compose

This repository contains the implementation and reference material for **Practical 4 of AMIT3353 Mobile Application Development**.

The practical focuses on building a multi-screen Android application using **Navigation Compose**. Students will learn how to create navigation routes, use a `NavHost`, navigate between composable screens, pass arguments between destinations, and understand how the navigation back stack works.

The practical also introduces the use of images from the Android `res/drawable` resources folder.

> **Repository Branch:** `Practical4`

---

## 📚 Practical Overview

### Topic

**Navigation & Multiple Screens**

### Duration

**2 hours**

### Prerequisites

Students should have completed:

* Practical 1
* Practical 2
* Practical 3

The practical assumes that you already have basic knowledge of:

* Android Studio
* Kotlin
* Jetpack Compose
* Composable functions
* `remember` and `mutableStateOf`
* Basic Material 3 UI components

The question paper specifically requires the `navigation-compose` dependency to be added to the application module.

---

# 🎯 Learning Objectives

By completing this practical, you should be able to:

* Understand navigation in a Jetpack Compose application.
* Create and use a `NavController`.
* Create a `NavHost` containing multiple destinations.
* Define navigation routes.
* Navigate from one screen to another.
* Pass arguments between screens.
* Retrieve navigation arguments from a destination.
* Understand the navigation back stack.
* Use `popBackStack()` to return to a previous screen.
* Add and display image resources from `res/drawable`.
* Understand the difference between raster and vector images.
* Create previews for composable screens.

The main navigation concepts introduced in the practical are `NavController`, `NavHost`, and routes.

---

# 🧠 Key Concepts

## 1. `NavController`

`NavController` is responsible for controlling navigation between destinations in the application.

It can be created using:

```kotlin
val navController = rememberNavController()
```

The controller can then be passed to screens that need to perform navigation.

For example:

```kotlin
navController.navigate("result/$name")
```

This tells the application to navigate to the destination represented by the specified route.

---

## 2. `NavHost`

`NavHost` contains the navigation graph for the application.

It defines:

* The navigation controller.
* The starting destination.
* The available destinations.
* The routes used to reach each destination.

Example:

```kotlin
NavHost(
    navController = navController,
    startDestination = "form"
) {
    composable("form") {
        FormScreen(navController = navController)
    }

    composable("result/{name}") {
        // Result screen
    }
}
```

The practical uses `"form"` as the starting destination and `"result/{name}"` as the destination that accepts a name argument.

---

## 3. Navigation Routes

A route is a string that identifies a destination.

A simple route can look like:

```text
form
```

A route can also contain an argument:

```text
result/{name}
```

Here, `{name}` represents a value that will be supplied when navigating to the destination.

For example:

```kotlin
navController.navigate("result/Android")
```

would navigate to the result destination with `"Android"` as the name.

---

## 4. Passing Arguments

Navigation Compose allows data to be passed between screens through route arguments.

The destination can declare an argument using:

```kotlin
arguments = listOf(
    navArgument("name") {
        type = NavType.StringType
    }
)
```

The value can then be retrieved from the `NavBackStackEntry`:

```kotlin
val name =
    backStackEntry.arguments?.getString("name") ?: ""
```

The practical specifically requires the name entered on the first screen to be passed to the result screen.

---

## 5. Navigation Back Stack

Navigation Compose maintains a **back stack** of destinations visited by the user.

For example:

```text
Form Screen
     ↓
Result Screen
```

After navigating to the result screen, the form screen remains on the back stack.

Calling:

```kotlin
navController.popBackStack()
```

removes the current destination and returns to the previous screen.

The practical requires students to test both the in-app Back button and the system Back button and observe the resulting back-stack behaviour.

---

# 🖼️ Working with Images

The practical also introduces the use of image resources in an Android project.

Images should be placed inside:

```text
app/src/main/res/drawable/
```

Android generates a resource ID for each valid drawable file.

For example:

```kotlin
R.drawable.ic_launcher_foreground
```

can be used to reference an image.

The question paper demonstrates loading the image with:

```kotlin
Image(
    painter = painterResource(
        id = R.drawable.ic_launcher_foreground
    ),
    contentDescription = "App logo",
    modifier = Modifier.size(120.dp)
)
```

---

## Raster vs Vector Images

### Raster Images

Raster images are commonly used for photographs and pictures.

Examples include:

* PNG
* JPG
* WebP

### Vector Images

Vector images are XML-based resources that can scale to different sizes without becoming blurry.

They are commonly useful for:

* Icons
* Logos
* Simple graphics

The default Android project already includes vector drawable resources such as:

```text
ic_launcher_foreground
ic_launcher_background
```

---

## Drawable Naming Rules

Drawable filenames should contain only:

* Lowercase letters
* Numbers
* Underscores

Valid:

```text
profile_photo.png
user_avatar.webp
logo_icon.xml
```

Invalid:

```text
ProfilePhoto.png
profile-photo.png
2cat.png
```

Drawable names cannot start with a number.

These naming restrictions are important because Android uses the filename when generating the resource ID.

---

# 📝 Practical Tasks

## Task 1 — Add Navigation Compose

Add the Navigation Compose dependency to the application-level `build.gradle.kts`.

The practical specifies:

```kotlin
implementation("androidx.navigation:navigation-compose:2.7.7")
```

After adding the dependency, sync the Gradle project.

## The current Practical 4 repository also contains this dependency.

## Task 2 — Add an Image

Add an image resource to:

```text
app/src/main/res/drawable/
```

You can use the existing launcher foreground image or add your own image.

Display the image using:

```kotlin
Image(
    painter = painterResource(
        id = R.drawable.ic_launcher_foreground
    ),
    contentDescription = "App logo",
    modifier = Modifier.size(120.dp)
)
```

The image is used on both the input and result screens in the practical.

---

## Task 3 — Create the Form Screen

Create a composable called:

```kotlin
FormScreen
```

The screen should contain:

* An image.
* A title/instruction.
* A name input field.
* A button for navigation.

The practical example uses:

```text
Enter your name
```

and an `OutlinedTextField` labelled:

```text
Name
```

The input should be stored using Compose state:

```kotlin
var name by remember {
    mutableStateOf("")
}
```

---

## Task 4 — Navigate to the Result Screen

The navigation button should only be enabled when the user has entered a name.

Example:

```kotlin
Button(
    onClick = {
        if (name.isNotBlank()) {
            navController.navigate("result/$name")
        }
    },
    enabled = name.isNotBlank()
) {
    Text("Show Greeting")
}
```

This demonstrates both:

1. Handling a button click.
2. Navigating while passing data through the route.

---

## Task 5 — Create the Result Screen

Create a second composable:

```kotlin
ResultScreen
```

The result screen should:

* Display an image/avatar.
* Display the name received from the previous screen.
* Provide a Back button.

The question paper's example displays:

```text
Hello, <name>!
```

followed by an explanation that the user navigated to the screen and passed an argument.

---

## Task 6 — Configure the Navigation Graph

Create the navigation graph using `NavHost`.

The required flow is:

```text
             ┌───────────────┐
             │  Form Screen  │
             │               │
             │ Enter Name    │
             └───────┬───────┘
                     │
                     │ navigate()
                     │ name
                     ▼
             ┌───────────────┐
             │ Result Screen │
             │               │
             │ Hello, Name!  │
             │               │
             │     Back      │
             └───────┬───────┘
                     │
                     │ popBackStack()
                     ▼
             ┌───────────────┐
             │  Form Screen  │
             └───────────────┘
```

The actual Practical 4 implementation follows this structure with:

```text
form
```

as the starting route and:

```text
result/{name}
```

as the result destination.

---

# 🏗️ Project Structure

The Practical 4 repository currently uses the following main application structure:

```text
app/
└── src/
    └── main/
        ├── java/
        │   └── com/example/dcsg3g4_practical4/
        │       ├── MainActivity.kt
        │       └── ui/
        │           └── theme/
        │
        └── res/
            ├── drawable/
            ├── mipmap-anydpi-v26/
            ├── mipmap-hdpi/
            ├── mipmap-mdpi/
            ├── mipmap-xhdpi/
            ├── mipmap-xxhdpi/
            ├── mipmap-xxxhdpi/
            ├── values/
            └── xml/
```

The repository currently places the navigation and screen composables directly in `MainActivity.kt`.

### `MainActivity.kt`

The main activity:

* Creates the Compose content.
* Applies the application theme.
* Creates the `NavController`.
* Displays the `NavHost`.

The current repository implements `AppNavigation`, `FormScreen`, `ResultScreen`, and their previews in `MainActivity.kt`.

### `res/drawable`

Contains image resources used by the application.

The Practical 4 implementation currently uses:

```kotlin
R.drawable.ic_launcher_foreground
```

for the application image/avatar.

---

# 🔗 Navigation Flow

The important relationship between the components is:

```text
MainActivity
     │
     ▼
rememberNavController()
     │
     ▼
AppNavigation
     │
     ▼
NavHost
     │
     ├───────────────┐
     ▼               ▼
"form"          "result/{name}"
     │               │
     ▼               ▼
FormScreen       ResultScreen
     │               │
     │ navigate()    │
     └──────────────►│
                     │
                     │ popBackStack()
                     ▼
                  FormScreen
```

The key idea is that the `NavController` manages which destination is currently displayed, while the route determines which screen should be opened.

---

# 📦 Required Dependency

Practical 4 requires Navigation Compose:

```kotlin
implementation("androidx.navigation:navigation-compose:2.7.7")
```

The dependency is already present in the Practical 4 branch.

---

# 📱 Expected Application Flow

When the application launches, the user should initially see the form screen.

### Screen 1 — Form

The user should be able to:

1. See the application image.
2. Enter their name.
3. Press **Show Greeting**.

The button should remain disabled when the input is empty.

Example:

```text
┌─────────────────────────────┐
│                             │
│          App Image          │
│                             │
│      Enter your name        │
│                             │
│  ┌───────────────────────┐  │
│  │ Name                  │  │
│  └───────────────────────┘  │
│                             │
│  ┌───────────────────────┐  │
│  │    Show Greeting      │  │
│  └───────────────────────┘  │
│                             │
└─────────────────────────────┘
```

---

### Screen 2 — Result

After entering a name and pressing **Show Greeting**, the application should navigate to the result screen.

Example:

```text
┌─────────────────────────────┐
│                             │
│          Avatar             │
│                             │
│        Hello, KarKai!       │
│                             │
│  You navigated here and     │
│  passed an argument.        │
│                             │
│          ┌──────┐           │
│          │ Back │           │
│          └──────┘           │
│                             │
└─────────────────────────────┘
```

The exact visual appearance can differ, but the required navigation and data-passing behaviour should be present.

---

# 🧪 Testing the Back Stack

You should test the navigation behaviour rather than only checking whether the second screen appears.

### Test 1 — Navigate Forward

1. Launch the application.
2. Enter a name.
3. Press **Show Greeting**.
4. Confirm that the result screen appears.
5. Confirm that the entered name is displayed.

### Test 2 — In-App Back Button

1. Navigate to the result screen.
2. Press **Back**.
3. Confirm that the form screen is displayed again.

The result screen uses:

```kotlin
navController.popBackStack()
```

to return to the previous destination.

### Test 3 — System Back Button

1. Navigate from the form screen to the result screen.
2. Press the Android system Back button.
3. Confirm that the application returns to the previous screen.

The question paper explicitly requires testing both system and in-app back navigation.

---

# ⚠️ Common Pitfalls

## 1. Forgetting to add Navigation Compose

If the navigation classes cannot be resolved, check that the dependency has been added:

```kotlin
implementation("androidx.navigation:navigation-compose:2.7.7")
```

Then sync Gradle.

---

## 2. Route names do not match

The route used in `navigate()` must correspond to a destination declared in the `NavHost`.

For example:

```kotlin
navController.navigate("result/$name")
```

must correspond to a route such as:

```kotlin
composable(
    route = "result/{name}"
)
```

If the routes do not match, navigation will not behave as expected.

---

## 3. Forgetting to Define a Destination

Every destination that the application navigates to must be declared inside the `NavHost`.

For example:

```kotlin
NavHost(
    navController = navController,
    startDestination = "form"
) {
    composable("form") {
        FormScreen(navController)
    }

    composable("result/{name}") {
        // Result screen
    }
}
```

The question paper specifically warns that an undefined destination can cause the application to crash at runtime.

---

## 4. Passing Unsafe Route Arguments

Arguments passed directly through a route must be URL-safe.

For example, a name containing spaces or `/` characters can cause problems when inserted directly into a route.

The question paper specifically warns students to avoid spaces and slashes or encode values when necessary.

For this practical, simple names such as:

```text
Android
KarKai
John
Alice
```

are suitable for demonstrating the basic concept.

---

## 5. Forgetting `remember`

The form screen should preserve the entered name during recomposition.

Use:

```kotlin
var name by remember {
    mutableStateOf("")
}
```

instead of a normal local variable.

---

## 6. Incorrect Back Navigation

Do not create a new form screen manually when the user wants to go back.

Use:

```kotlin
navController.popBackStack()
```

This allows Navigation Compose to correctly handle the existing back stack.

---

## 7. Invalid Drawable Names

Android drawable resources must follow the required naming convention.

Use:

```text
profile_photo.png
```

instead of:

```text
ProfilePhoto.png
profile-photo.png
```

---

# ⭐ Optional Challenge

The question paper provides an optional extension.

### Challenge — Multiple Arguments and a Third Screen

Instead of passing only one argument, try passing **two arguments**.

For example:

```text
result/{name}/{course}
```

You could then display:

```text
Hello, KarKai!
Course: Mobile Application Development
```

You can also add a third screen that can be reached from the result screen.

For example:

```text
Form Screen
     │
     ▼
Result Screen
     │
     ▼
Third Screen
```

The optional challenge is intended to provide additional practice with navigation routes and arguments.

---

# 📤 Practical Deliverable

You should have a working **two-screen Android application**.

The application must:

* Have an input/form screen.
* Have a result screen.
* Accept a name from the user.
* Navigate from the first screen to the second screen.
* Pass the entered name as a navigation argument.
* Display the name on the second screen.
* Include an image.
* Include working Back navigation.
* Demonstrate correct navigation back-stack behaviour.

The required deliverable is explicitly described as a two-screen application where data entered on the first screen is passed to and displayed on the second screen, with working back navigation.

---

# 🚀 Getting Started

## 1. Clone the Repository

Clone the repository:

```bash
git clone https://github.com/Reyzirk/AMIT3353_MobileAppPractical.git
```

---

## 2. Switch to Practical 4

Change to the repository directory:

```bash
cd AMIT3353_MobileAppPractical
```

Then switch to the Practical 4 branch:

```bash
git checkout Practical4
```

Alternatively, clone the branch directly:

```bash
git clone -b Practical4 https://github.com/Reyzirk/AMIT3353_MobileAppPractical.git
```

---

## 3. Open in Android Studio

1. Open Android Studio.
2. Select **Open**.
3. Select the cloned project folder.
4. Allow Gradle to sync.
5. Wait for Android Studio to finish indexing.
6. Resolve any missing SDK/dependency prompts.

---

## 4. Run the Application

You can run the application using:

* An Android Emulator, or
* A physical Android device.

Select the desired device and click:

```text
Run ▶
```

The application should initially open on the form screen.

---

# 🧪 Practical Checklist

Before demonstrating the practical, make sure you can check all of the following:

### Project Setup

* [ ] Project opens successfully in Android Studio.
* [ ] Gradle sync completes successfully.
* [ ] Navigation Compose dependency has been added.
* [ ] Application builds successfully.
* [ ] Application launches successfully.

### Images

* [ ] Image resource is located in `res/drawable`.
* [ ] Image is displayed using `Image`.
* [ ] `painterResource` is used correctly.
* [ ] Drawable filename follows Android naming rules.

### Form Screen

* [ ] `FormScreen` has been created.
* [ ] Name input field is displayed.
* [ ] Name is stored using Compose state.
* [ ] `remember` and `mutableStateOf` are used.
* [ ] Show Greeting button is displayed.
* [ ] Button is disabled when the name is empty.

### Navigation

* [ ] `rememberNavController()` is used.
* [ ] `NavHost` is configured.
* [ ] `form` is the starting destination.
* [ ] `result/{name}` is defined.
* [ ] Navigation works when the button is pressed.
* [ ] Name is passed through the route.
* [ ] `NavType.StringType` is used for the name argument.
* [ ] The name is retrieved from the back-stack entry.

### Result Screen

* [ ] `ResultScreen` has been created.
* [ ] The passed name is displayed.
* [ ] An image/avatar is displayed.
* [ ] Back button is displayed.
* [ ] `popBackStack()` is used.

### Back Navigation

* [ ] In-app Back button works.
* [ ] System Back button works.
* [ ] Back-stack behaviour has been tested.

### Optional

* [ ] Two navigation arguments implemented.
* [ ] Third screen implemented.

---

# 📖 What You Should Understand After This Practical

This practical is not only about making two screens.

You should be able to explain the purpose of the main navigation components.

### Why use `NavController`?

`NavController` manages navigation between destinations.

### Why use `NavHost`?

`NavHost` defines the navigation graph and contains the composable destinations.

### What is a route?

A route is a string identifying a destination.

For example:

```text
form
```

or:

```text
result/{name}
```

### Why use navigation arguments?

Arguments allow data to be passed from one destination to another.

### What is the back stack?

The back stack keeps track of previously visited destinations so that the user can navigate backwards.

### Why use `popBackStack()`?

It removes the current destination from the navigation stack and returns to the previous destination.

### Why use `rememberNavController()`?

It creates and remembers the navigation controller used by the Compose application.

---

# 🔗 Repository

**Practical 4 Branch:**

https://github.com/Reyzirk/AMIT3353_MobileAppPractical/tree/Practical4

This branch contains the Android Studio project corresponding to Practical 4, including the Navigation Compose implementation and the two-screen application.

---

# 🎓 Course

**AMIT3353 — Mobile Application Development**

**Practical 4 — Navigation & Multiple Screens**

> Use this repository as a reference while completing your practical. Make sure you understand the concepts and code rather than simply copying the implementation.

The most important flow to understand is:

```text
User Input
     ↓
FormScreen
     ↓
NavController
     ↓
Navigation Route
     ↓
Passed Argument
     ↓
ResultScreen
     ↓
Back Stack
     ↓
FormScreen
```

Once you understand this flow, you have the foundation required for building multi-screen applications with Jetpack Compose.
