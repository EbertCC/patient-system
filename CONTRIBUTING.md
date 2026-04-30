# Contributing to Patient Management System

Thank you for your interest in contributing! This guide explains how to report issues, propose changes, and submit pull requests for the **Patient Management System** repository.

## Table of Contents
- [Code of Conduct](#code-of-conduct)
- [How to Report a Bug](#how-to-report-a-bug)
- [Feature Requests](#feature-requests)
- [Before You Start Working](#before-you-start-working)
- [Development Setup](#development-setup)
- [Branching & Commit Guidelines](#branching--commit-guidelines)
- [Pull Request Process](#pull-request-process)
- [Testing](#testing)
- [Style & Linting](#style--linting)
- [Security](#security)
- [Contact](#contact)

---

## Code of Conduct
Please follow the [Contributor Covenant Code of Conduct](CODE_OF_CONDUCT.md). Be respectful and professional in all interactions.

---

## How to Report a Bug
Good bug reports help us fix issues faster. When opening a new issue, please include:

1. A clear and descriptive **title**.
2. **Steps to reproduce** the behavior (minimal reproducible example).
3. Expected and actual behavior.
4. Environment details (OS, Java version, database used, browser if UI-related).
5. Logs or stack traces (if available).
6. Screenshots (if applicable).

Use the “Bug report” issue template when available.

---

## Feature Requests
Feature requests help make the project better. When suggesting a feature, include:

- A concise **problem statement** or user story.
- Proposed solution or examples.
- Any UI/UX screens or mockups (optional).
- Backwards compatibility considerations.

---

## Before You Start Working
1. Check existing issues and pull requests to avoid duplication.
2. Comment on the issue you'd like to work on (if one exists) so maintainers know you're working on it.
3. Fork the repository and create a local branch for your work.

---

## Development Setup

**Clone the repo**
```bash
git clone https://github.com/TheComputationalCore/patient-system.git
cd patient-system
```

**Run with Maven (H2 in-memory for dev)**
```bash
./mvnw clean package
./mvnw spring-boot:run
```

**Environment**
- Java 17+ recommended
- Maven 3.6+
- Configure `src/main/resources/application.properties` for your DB or use the H2 in-memory config for development.

**Optional Docker**
Build and run the app in Docker:
```bash
docker build -t patient-system:dev .
docker run -p 8085:8085 --env-file .env patient-system:dev
```

---

## Branching & Commit Guidelines

**Branch naming**
- Use descriptive branch names, e.g.:
  - `feature/<short-description>` (new features)
  - `fix/<short-description>` (bug fixes)
  - `chore/<short-description>` (maintenance)
  - `docs/<short-description>` (documentation)

**Commit messages**
Follow this simple convention (inspired by Conventional Commits):
```
<type>(<scope>): <short summary>

<body> (optional)
```
- `type` can be `feat`, `fix`, `docs`, `chore`, `refactor`, `test`, `perf`.
- `scope` is optional and indicates the area (e.g., `auth`, `appointments`, `ui`).
- Keep the first line under 72 characters.

Example:
```
feat(auth): add BCrypt password encoding for registration
```

---

## Pull Request Process
1. Fork the repository and create a branch from `main`.
2. Commit changes to your branch with clear messages.
3. Push to your fork and open a Pull Request against `TheComputationalCore:main`.
4. In the PR description, include:
   - Summary of changes
   - Link to relevant issue (if any)
   - Testing instructions
5. A maintainer will review your PR. Respond to feedback and update the branch as needed.
6. Once approved, a maintainer will merge the PR.

**Templates**: Use the provided PR template if available.

---

## Testing
- Run unit and integration tests with Maven:
```bash
./mvnw test
```
- Prefer writing tests for new features and bug fixes.
- For service/repository tests, use H2 in-memory DB or testcontainers if configured.

---

## Style & Linting
- Java code should follow common conventions (use your IDE formatting or a shared formatter).
- Keep files small and focused. Break large methods into smaller functions.
- Use meaningful variable and method names.
- Add JavaDoc for public classes/methods where appropriate.

---

## Security
- Do **not** commit secrets (credentials, API keys) to the repository.
- If you discover a security vulnerability, please report it via `SECURITY.md` or open an issue marked as security (private disclosure preferred).

---

## Contact
For questions, reach out to the maintainer:

**Dinesh Chandra — TheComputationalCore**  
https://github.com/TheComputationalCore

---
Thank you for contributing — your help makes this project better!
