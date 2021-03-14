# Offers DDD

Functionality:
----

- registering Users + querying Users
- creating Offers + accept,reject,delete operations + querying Offers
- REST Events endpoint
- Store and forward event publisher + retriggering failed events on specific handlers
- Simple command bus

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
- The application is based on Port/Adapters architecture so it allows a developer to change framework or implementation
  of repositories without touching domain content.

----

> TODO:
> - security (user role / administrator role)
> - provide better Name validation: special characters (, . : " ' -), whitespaces before and after
> - admin panel for event processor / manual retrigger / delete
> - run all failed events on startup
> - separate thread pools for scheduled infrastructure/domain tasks
> - identifier/name for event handlers (prevent fail when rename class/method)
----
todo: clock registry??

License
----

MIT
