# ToDoIt - Task Management System

## Overview
A Spring Boot web application for task management with status transitions and soft delete functionality.

## Business Rules
### Status Transitions
- **TODO** → IN_PROGRESS, DONE
- **IN_PROGRESS** → TODO, DONE
- **DONE** → No transitions allowed (final state)

### Task Updates
- Title/Description: Editable only when status is TODO or IN_PROGRESS
- Soft Delete: Tasks marked as deleted, not physically removed

## Technical Stack
- Java 17
- Spring Boot 3.x
- Maven
- JPA/Hibernate

## Architecture Decisions
- **Map-based state validation**: Chosen for scalability and maintainability
- **Soft delete pattern**: Preserves data integrity and audit trail
- **Service layer separation**: Clean business logic isolation

## API Endpoints
[Document your endpoints as you build them]


