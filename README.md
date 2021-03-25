# Offers DDD

### This project is for educational & experimental purposes.

Functionality:
----

- CRUD for Users & Offers but with CQRS concepts
- Event store decoupled from the core application (Store & Forward Event Publisher)

- Commands and Events:
    - REST calls register commands on command bus
    - Command bus manages transaction & publishes domain events returned from command execution
    - Events are saved by event-store in a database
    - Handlers for events are found and registered in a database
    - Scheduler picks event handler processes from event-store and executes them

Architecture:
----

- The application implements Hexagonal Architecture with pure Java within domain boundaries. (except Lombok due to
  better readability)
- The application's architecture is inspired by DDD book "Implementing Domain-Driven Design": Vaughn Vernon which
  implements the same packages structure (application,domain,port.adapter) and similar concepts.
- Every domain action publishes an Event through EventPublisher.
- Events are created for orchestrating purposes and as audit logs. There is no event sourcing implemented in this
  application.
- The application contains parts of CQRS pattern.
- Domain models have their counterparts as Read Models for filtering / api exposure purposes.
- Read Models are synchronized asynchronously when Events are published and forwarded.

----

> TODO:
> - security (user role / administrator role)
> - provide better Name validation: special characters (, . : " ' -), whitespaces before and after
> - admin panel for event processor / manual retrigger / delete
> - run all failed events on startup
> - separate thread pools for scheduled infrastructure/domain tasks
> - identifier/name for event handlers (prevent fail when rename class/method)
----
