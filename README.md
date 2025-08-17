# BugSage

BugSage is a simple and effective error-solving app built entirely with **Spring Boot**, using **Spring MVC**, **Spring Security**, and **JPA**. 
Just paste your error and get instant solutions—often with helpful visuals to guide you through the fix. No more endless searching or guesswork!

## Features

- 🐞 **Paste & Solve:** Copy any error message and get solutions instantly.
- 🖼️ **Visual Guides:** Many solutions come with screenshots, diagrams, or code snippets for better understanding.
- 🎯 **Quick Search:** Find fixes for common errors quickly and easily.
- 🔒 **Secure:** User authentication and role-based access powered by Spring Security.
- 📋 **History:** Track your past error searches and solutions.
- 💬 **Community Powered:** Solutions may come from a growing pool of community-contributed answers.

## Tech Stack

- **Spring Boot** (Backend Framework)
- **Spring MVC** (REST Controllers)
- **Spring Security** (Authentication & Authorization)
- **JPA/Hibernate** (Database ORM)
- **H2/MySQL/PostgreSQL** (Configurable database)

## How It Works

1. **Paste your error** in the input box.
2. **View solutions**—including visual guides and step-by-step instructions.
3. **Copy a fix** or follow the visuals to resolve your issue.

## Getting Started

### Prerequisites

- Java 17+
- Maven or Gradle

### Installation

Clone the repository:

```bash
git clone https://github.com/ratrahu007/bugsage.git
cd bugsage
```

### Configuration

- Edit `src/main/resources/application.properties` to configure your database and server settings.

### Running the App

Using Maven:

```bash
./mvnw spring-boot:run
```

Or using Gradle:

```bash
./gradlew bootRun
```

Visit `http://localhost:8080` in your browser to use BugSage.

## Contributing

Contributions are welcome! If you’ve solved a tricky error, help others by adding your solution.

1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -am 'Add new solution'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

Created by [ratrahu007](https://github.com/ratrahu007).

---

Paste errors. See solutions. Fix faster—with BugSage!
