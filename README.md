# 🚀 BMS Automation Framework — Java | Selenium | TestNG | Maven

[![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=java)](https://www.java.com/)
[![Selenium](https://img.shields.io/badge/Selenium-4.x-green?logo=selenium)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.x-red)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-blue?logo=apache-maven)](https://maven.apache.org/)
[![CI](https://img.shields.io/badge/CI-GitHub%20Actions-yellow?logo=github-actions)](https://github.com/PramodKumarAS/BMS_Automation_Java_Selenium/actions)

A robust, scalable, end-to-end test automation framework for the **BMS (Booking Management System)** application, built using **Java**, **Selenium WebDriver**, **TestNG**, and **Maven**. The framework covers **API testing**, **UI functional testing**, and **end-to-end testing**, with parallel execution, retry logic, and rich HTML reporting via Extent Reports.

---

## 📋 Table of Contents

- [About the Project](#about-the-project)
- [Tech Stack](#tech-stack)
- [Framework Architecture](#framework-architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Test Suite Breakdown](#test-suite-breakdown)
- [Reports](#reports)
- [CI/CD Pipeline](#cicd-pipeline)
- [Contributing](#contributing)

---

## 📌 About the Project

The **BMS Automation Framework** is designed to automate functional, API, and end-to-end test scenarios for a Booking Management System. It follows industry best practices including:

- **Page Object Model (POM)** for maintainable UI test code
- **Data-Driven Testing** for broader coverage with minimal code
- **Parallel Execution** across test classes using TestNG
- **Retry Mechanism** for flaky test resilience
- **Extent Reports** for rich, detailed HTML test reports
- **GitHub Actions** for automated CI/CD pipeline execution

---

## 🛠️ Tech Stack

| Technology       | Version  | Purpose                            |
|------------------|----------|------------------------------------|
| Java             | 17+      | Core programming language          |
| Selenium WebDriver | 4.x    | Browser automation                 |
| TestNG           | 7.x      | Test framework & parallel execution|
| Maven            | 3.x      | Build & dependency management      |
| Extent Reports   | 5.x      | HTML test reporting                |
| GitHub Actions   | —        | CI/CD automation                   |
| Chrome / ChromeDriver | Latest | Default browser for UI tests   |

---

## 🏗️ Framework Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                     BMS Automation Framework                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────┐   ┌──────────────┐   ┌──────────────────┐   │
│  │  API Tests   │   │  UI Tests    │   │   E2E Tests      │   │
│  │ tests.api    │   │tests.functional│  │  tests.e2e       │   │
│  └──────┬───────┘   └──────┬───────┘   └────────┬─────────┘   │
│         │                  │                     │             │
│         └──────────────────┼─────────────────────┘             │
│                            │                                   │
│              ┌─────────────▼──────────────┐                   │
│              │      Base Test / Hooks      │                   │
│              │  Setup | Teardown | Config  │                   │
│              └─────────────┬──────────────┘                   │
│                            │                                   │
│         ┌──────────────────┼──────────────────┐               │
│         ▼                  ▼                  ▼               │
│  ┌─────────────┐  ┌──────────────┐  ┌───────────────┐        │
│  │ Page Objects│  │  Utilities   │  │   Listeners   │        │
│  │  (POM)      │  │ Helpers/Data │  │ Extent|Retry  │        │
│  └─────────────┘  └──────────────┘  └───────────────┘        │
│                                                                 │
│              ┌──────────────────────────┐                     │
│              │      WebDriver Manager   │                     │
│              │  Chrome | Config-driven  │                     │
│              └──────────────────────────┘                     │
│                                                                 │
│              ┌──────────────────────────┐                     │
│              │       Extent Reports     │                     │
│              │    test-output/reports   │                     │
│              └──────────────────────────┘                     │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📁 Project Structure

```
BMS_Automation_Java_Selenium/
│
├── .github/
│   └── workflows/
│       └── ci.yml                    # GitHub Actions CI pipeline
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── base/                 # BaseTest class (WebDriver setup/teardown)
│   │       ├── pages/                # Page Object Model (POM) classes
│   │       │   ├── LoginPage.java
│   │       │   ├── DashboardPage.java
│   │       │   └── BookingPage.java
│   │       ├── utils/                # Utility & helper classes
│   │       │   ├── ConfigReader.java
│   │       │   ├── ExcelUtils.java
│   │       │   └── DriverManager.java
│   │       └── listeners/            # TestNG listeners
│   │           ├── ExtentTestListener.java
│   │           └── RetryListener.java
│   │
│   └── test/
│       └── java/
│           └── tests/
│               ├── api/              # API test cases
│               │   └── BookingApiTest.java
│               ├── functional/       # UI functional test cases
│               │   ├── LoginTest.java
│               │   └── BookingTest.java
│               └── e2e/              # End-to-end test scenarios
│                   └── BookingE2ETest.java
│
├── test-output/                      # Generated test reports (Extent HTML)
│
├── target/                           # Maven build output
│
├── pom.xml                           # Maven dependencies & build config
├── testng.xml                        # TestNG suite configuration
├── .gitignore
└── README.md
```

---

## ✅ Prerequisites

Before running the project, ensure you have the following installed:

- **Java JDK 17+** — [Download here](https://adoptium.net/)
- **Apache Maven 3.6+** — [Download here](https://maven.apache.org/download.cgi)
- **Google Chrome** (latest) — Used as the default browser
- **Git** — [Download here](https://git-scm.com/)
- **IDE** (recommended): IntelliJ IDEA or Eclipse

Verify installations:
```bash
java -version
mvn -version
```

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/PramodKumarAS/BMS_Automation_Java_Selenium.git
cd BMS_Automation_Java_Selenium
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

---

## ▶️ Running Tests

### Run All Tests (Full Suite)

```bash
mvn test
```

### Run via TestNG XML

```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Run a Specific Test Group

```bash
# API Tests only
mvn test -Dgroups="api"

# UI Functional Tests only
mvn test -Dgroups="functional"

# End-to-End Tests only
mvn test -Dgroups="e2e"
```

### Run with a Different Browser

The default browser is **Chrome**. To override:

```bash
mvn test -Dbrowser=firefox
```

---

## 🧪 Test Suite Breakdown

The `testng.xml` defines three test groups that run in parallel with **3 threads** at the class level:

| Test Group          | Package              | Description                                      |
|---------------------|----------------------|--------------------------------------------------|
| **API Tests**       | `tests.api`          | REST API validation (no browser required)        |
| **UI Functional**   | `tests.functional`   | Browser-based UI feature tests (Chrome)          |
| **E2E Tests**       | `tests.e2e`          | Full end-to-end booking workflow scenarios       |

**TestNG Configuration Highlights:**
- Parallel execution: `parallel="classes"` with `thread-count="3"`
- Listeners: `ExtentTestListener` (reporting) & `RetryListener` (retry on failure)
- Default browser parameter: `chrome`

---

## 📊 Reports

After test execution, HTML reports are generated in the `test-output/` directory.

```
test-output/
└── ExtentReport.html    ← Open this in any browser
```

To view:
```bash
open test-output/ExtentReport.html    # macOS
start test-output/ExtentReport.html   # Windows
```

The Extent Report includes:
- ✅ Pass / ❌ Fail / ⚠️ Skip summary
- Step-by-step test logs
- Screenshots on failure
- Test execution timeline

---

## 🔄 CI/CD Pipeline

The project uses **GitHub Actions** for continuous integration. The pipeline is defined in `.github/workflows/ci.yml`.

**Pipeline Triggers:**
- On every `push` to `main`
- On every `pull_request`

**Pipeline Steps:**
1. Checkout code
2. Set up Java 17
3. Cache Maven dependencies
4. Run `mvn test`
5. Upload test reports as artifacts

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request

---

## 👤 Author

**Pramod Kumar A S**
- GitHub: [@PramodKumarAS](https://github.com/PramodKumarAS)

---

> ⭐ If you find this project useful, please consider giving it a star!
