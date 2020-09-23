# Offers DDD

Functionality:
----
- creating Users + querying Users
- creating Offers + accept,reject,delete operations + querying Offers

Architecture:
----
- The application implements full Hexagonal Architecture with pure Java within domain boundaries. (except Lombok due to better readability)
- The application's architecture is inspired by DDD book "Implementing Domain-Driven Design": Vaughn Vernon which implements the same packages structure (application,domain,port.adapter) and similar concepts.
- Every domain action publishes an Event through EventPublisher.
- Spring's ApplicationEventPublisher is used as an implementation of EventPublisher.
- Every event is handled and stored in a database synchronously as a part of the same transaction.  If there is no transaction an exception will occur and no data will be stored in a database.
- Events are created for orchestrating purposes and as audit logs. There is no event sourcing implemented in this application.
- The application contains parts of CQRS pattern.
- Domain models have their counterparts as Read Models for filtering / api exposure purposes.
- Read Models are synchronized asynchronously when Events are published.
- The application is based on Port/Adapters architecture so it allows a developer to change framework or implementation of a database without touching domain content.

----

> TODO:
> - security (user role / administrator role)
> - scheduler lock (for multiple instances of the application)
> - provide better Name validation: special characters (, . : " ' -), whitespaces before and after
> - dockerfile & docker-compose for the application 
> - event bus connected to database (retriggering events)
> - expireOutdatedOffers() - create transaction on every iteration in loop to prevent rollback on all offers 

----

License
----

MIT
