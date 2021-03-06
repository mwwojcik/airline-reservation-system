## Table of contents

- [Intro](#intro)
- [Domain description](#domain-description)
- [Domain exploration](#domain-exploration)
  - [Event Storming Grammar](#event-storming-grammar)
    - [The picture that explains everything](#the-picture-that-explains-everything)
    - [Sticky notes](#sticky-notes)
  - [Airline Reservation System - domain exploration - Big Picture](#airline-reservation-system---domain-exploration---big-picture)
    - [Unordered events](#unordered-events)
    - [Events arranged on timeline](#events-arranged-on-timeline)
    - [Subdomains](#subdomains)
  - [Airline Reservation System - domain exploration - Process Level Event Storming](#airline-reservation-system---domain-exploration---process-level-event-storming)
    - [Bounded Contexts](#bounded-contexts)
    - [Commands / Read Model](#commands--read-model)
- [Architecture](#architecture)
  - [Architectural Decision Log](#architectural-decision-log)
  - [C4 Architecture diagrams](#c4-architecture-diagrams)
    - [C1- Software System Perspective](#c1--software-system-perspective)
    - [C2- Container Perspective](#c2--container-perspective)
    - [C3- Component Perspective](#c3--component-perspective)
- [Bounded context exploration](#bounded-context-exploration)
  - [Airline Reservation System - bounded context exploration - Design Level Event Storming](#airline-reservation-system---bounded-context-exploration---design-level-event-storming)
    - [Lack of Cohesion](#lack-of-cohesion)
- [Implementation (Reservation module)](#implementation-reservation-module)
  - [Ports and Adapters (Hexagonal Architecture)](#ports-and-adapters-hexagonal-architecture)
  - [Non-functional requirements](#non-functional-requirements)
  - [Application services](#application-services)
  - [Spring Boot Configuration](#spring-boot-configuration)
    - [1. Preparing main configuration](#1-preparing-main-configuration)
    - [2. Preparing  test configuration](#2-preparing--test-configuration)
    - [Multiple configurations - Tips & Tricks](#multiple-configurations---tips--tricks)
  - [Testing](#testing)
    - [Unit tests](#unit-tests)
    - [Acceptance test](#acceptance-test)
      - [From test to implementation](#from-test-to-implementation)
    - [Web layer test](#web-layer-test)
  - [DDD Building Blocks](#ddd-building-blocks)
  - [Rest API standards](#rest-api-standards)
    - [REST Archetypes](#rest-archetypes)
    - [REST methods](#rest-methods)
    - [HTTP codes](#http-codes)
  - [REST API tests](#rest-api-tests)
    - [Configuration of test's infrastructure elements](#configuration-of-tests-infrastructure-elements)
    - [Web test elements](#web-test-elements)



## Intro
This is a project of Airline Reservation System(ARS) - software application to assist an airline with transactions related to :
* making ticket reservations
* canceling and rescheduling tickets.

It has been  implemented for educational purposes using techniques derived from the domain of DDD.

![](img/ars.png)



## Domain description
The main purpose of the application is to support an airline customer with processes:
* checking availability
* making tickets reservations (blocking,reserving)
* cancelling tickets  
* rescheduling reservations.

**Loyality program**

Using airline services involves granting the user bonus points. Each dollar spent adds up 10 bonus points. 
The user can exchange the accumulated bonus points for the indicated discounts and privileges. They will be included in the next booking. 

* If the user has 500 to 1000 points, he can use a more extensive menu.
* If the user has 1000 to 3000 points, he is entitled to take 1 piece of luggage.
* If the user has 3000 to 5000 points, he has the right to choose a place in business class (if available). 

Each of the privileges can be exchanged for a cash discount (2% from the base ticket price for every 1000 points)

The gifts catalog is managed by the Sales Department. 

**Checking availability**

The ticket reservation process begins with checking information on available flights. The customer sets his preferences (departure date/time,origin city
,arrival city, class,one-way or two-way trip, departure date, number and type (adult/senior/children) of passangers) and gives information about available
fligths (the flight number,departure time in origin city,arrival time in destination city,the duration of the flight, the number of seats available on
that flight).

**Ticket reservation**

Via the online reservation system, the customer can reserve a maximum of 10 individual tickets per month. Before making the reservation, the system checks
 whether the user has exceeded the limit .

It is necessary to provide flight number and seat number to reserve a seat. 
If the difference between the departure date and system date is more than 2 weeks, the ticket is blocked without no cost. 
Each customer can have only 3 blocked reservations at a time. Each subsequent ticket must be confirmed immediately.

Customer should make a make the final purchase of the ticket before 2 weeks of the departure date. 3 weeks before the date of departure, system should
send to the customer reminder message.

If the difference between today's date and departure date is less than 2 weeks, there is no possibility to block the ticket, it must be bought. 

While the ticket is blocked, its price may change. After purchasing a ticket, its price cannot change.

After purchasing the ticket,the customer's credit card is charged. The customer receives a mail message with his confirmation number. 

The number of bonus points will be increased.

**Ticket price calculation**

 The initial price is set for the flight. It's the same for every seat on the plane. It has an auxiliary character and is not presented to the
 client.The final  ticket price is calculated on the basis of many different factors that increase or decrease the initial price.

**Calculations made on the rezervation day:**
* if the reservation is made more than 4 months before the departure date, the initial price should be reduced by 60%
* if the reservation is made between 2-4 months before the departure date, the initial price should be reduced by 40%
* if the reservation is made two months before the departure date, the initial price should be reduced by 20%
* if the reservation takes place in the same month as the departure date the initial price should be increased by 10%

* if the departure date falls on Tuesday or Wednesday, the calculated price can be reduced by a further 20%

**Calculations performed on the confirmation day:**

* if 85% of seats have been sold for a given flight, the ticket price may be reduced by 10%
* if less than 85% of seats have been sold for a given flight, the ticket price must be increased by 10%

This type of discounts can be accumulated.

**Rescheduling ticket**

Ticket rescheduling based on confirmation number. It is possible only for confirmed tickets. Blocked ticket can't be rescheduled.
If there is any difference in the prices of the tickets, it is returned to the credit card account. New confirmation number is sent to the customer via email.
Changing the reservation date can be done only twice. After the limit has been used, the reservation must be canceled.

**Cancelling ticket**

Both blocked and confirmed tickets can be cancelled. To cancel the ticket it is required to provide the blocking id or confirmation id.  If the ticket has
 been confirmed, cancellation will result in a reduction of 25% of the price. 

If a canceled ticket has already been bought, the number of bonus points should be decreased.

**Check in**

 The user can perform an automatic check-in. It is possible not earlier than 10 hours before the departure time . 
 This option is available in a Customer Profile. During the check-in process a boarding card is generated. After generating it is available from Customer
  Profile.

**Customer Satisfaction Surveys**

After each flight, the user receives an e-mail with information that he can complete the survey satisfied and write a review.  This results in additional
 bonus points (500 - survey, 1000 review). Flights to evaluate are presented in the user's profile. 

**Special offers** 

The Sales Department sends information about special offers prepared for customers. After entering the offer, it is published by email.
                                                                                                                     
**Reporting system**

Reporting Department collects all activity events. They allow to recreate the entire activity history for each user.Each user can request to prepare such a
 report.


## Domain exploration

### Event Storming Grammar
#### The picture that explains everything
This nice picture comes from [Introducing EventStorming-Alberto Brandolini](https://leanpub.com/introducing_eventstorming).

![Introducing EventStorming-Alberto Brandolini](img/process-modeling-events.png) 

#### Sticky notes
![](img/event-storming-symbols.png) 


### Airline Reservation System - domain exploration - Big Picture 

The main goal of Big Picture Event Storming is to divide the problem into smaller business parts, that can be analyzed independently.

To identify them, we can use few heuristics:
 
* **Organization Structure** - The first approximation in the process of extracting subdomains is to look at organizational boundaries .  We can try to mark
 process steps and assign them to the units in which they take place.
* **Domain Experts** - Sometimes in one organizational unit there are people who deal with different kind of problems.  This can affect the creation of separate
 subdomains.
* **Domain Expert Language** - We must pay attention to the meaning of the words used by experts. For different experts,  different behaviors of the same
 thing matter.
* **Bussiness Value** - If part of the system has a higher business value than others, a separate subdomain should be separated for it. 
* **Business Process** -  Sometimes, at some point in the process, other business rules come into effect. This may be the basis for separating a separate
  subdomain for this step.

At this stage we describe the current state of the process and try to identify its weaknesses. We are not optimizing yet.

#### Unordered events
![](img/ars-big-picture-unordered-events.jpg)

#### Events arranged on timeline
At this stage, the events have been arranged in a timeline and ordered. Some of them have been refined and replaced with several new ones.

<a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/ars-big-picture-events-timeline.jpg" target="_blank">Show picture
</a>
![](img/ars-big-picture-events-timeline.jpg)

#### Subdomains

<a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/ars-big-picture-subdomains-poster.jpg" target="_blank">Show picture
</a>

![](img/ars-big-picture-subdomains-poster.jpg)



### Airline Reservation System - domain exploration - Process Level Event Storming 

Process Level is the next stage of Event Storming.  As a result, previously discovered subdomains are mapped to the solution space. 
The result of this mapping are Bounded Contexts.  

 

| *Subdomain is a part of the domain, and a bounded context is a part of the solution.*      |
| ----------- |

 

At this stage a new process flow is designed and optimizations are introduced. 

The main heuristics determining number and the scope of the separated Bounded Context are:

* **Context Autonomy** - The most important question is whether our context is completely independent of others.
* **Number of contexts in the business process** - Processes should be designed in such a way that they intersect as few bounded as possible. Fewer
 contexts result in less need for integration. This gives more autonomy for contexts.
* **Shared information** - If any data needs to change immediately in more than one bounded context, it means that the boundaries were set incorrectly.
* **Communication with others** - Context should contain most of the data he needs. Communication with other contexts should be kept to a minimum. We have to
 ask ourselves why integration has occurred. Integration with external systems is very expensive. We must be sure it is necessary.  
* **Context responsibility** - Each context should have a well defined range scope. If this description is complicated and consists of many sentences,
 it is very likely that the context boundaries are too wide.
* **The only one source of information** - Every important information should have one context which is the source of its value. If it is shared, maybe one
 more dedicated context should be created.
* **Is the context not schizophrenic** - It is a situation when the context needs to find out what part of the process it is currently implementing. 
In this case, the code appears:
```
if(isSingleUserProcess()){
    //do something
} else {
    //do something else
}
```

#### Bounded Contexts

<a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/ars-big-picture-subdomains-bc-poster.jpg" target="_blank">Show picture
</a>
![](img/ars-big-picture-subdomains-bc-poster.jpg)


Previously it was planned that the flights information would be stored in the SALES module (as a part of
 the airline offer). However, it turned out that the airline offer is something other than a flight catalog. The specific nature of this functionality makes
  it reasonable to place it in a separate Bounded Context. It will be simple CRUD module - without business logic. It will store information about planned
   flights and passenger seats . We don't have much information about it right now, so we only signal its existence:
   
![](img/fligth-catalog.png)  



#### Bounded Contexts - with Commands / Read Model

<a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/ars-process-level-finish-1.jpg" target="_blank">Show picture
</a>
![](img/ars-process-level-finish-1.jpg)

<a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/ars-process-level-finish-2.jpg" target="_blank">Show picture
</a>
![](img/ars-process-level-finish-2.jpg)

 

## Architecture

### Architectural Decision Log

|Number|Type|Date|Title
|:-|:-|:-|:-|
|0002|TCH-RES|2020-05-02|[Use modular monolith](ars/0002-TCH-RES-use-modular-monolith.md)|
|0003|TCH-RES|2020-05-02|[Use Ports and Adapters architecture](ars/0003-TCH-RES-use-ports-and-adapters-architecture.md)|
|0004|TCH-RES|2020-05-02|[Use document database as a aggregate data repository](ars/0004-TCH-RES-use-document-database-as-a-aggregate-data-repository.md)|
|0005|TCH-RES|2020-05-02|[Use Spring Repository](ars/0005-TCH-RES-use-spring-repository.md)|


### C4 Architecture diagrams 

#### C1- Software System Perspective

*The perspective shows the surroundings of the created system . This diagram shows external systems and actors discovered during the session ES.*
*Internal actors are part of the organization. Externals Actors only use the system.*

<a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/architecture/ARS_Context.png" target="_blank">Show picture</a>
![](img/architecture/ARS_Context.png)

#### C2- Container Perspective

*The natural consequence of choosing modular monolith architecture is that all modules will be located in one container (API Application).*
*The diagram shows the other internal used containers (DB/SPA/WebServer/Mobile App). All of them are part of one system.*

<a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/architecture/ARS_Containers.png" target="_blank">Show picture</a>
![](img/architecture/ARS_Containers.png)

#### C3- Component Perspective

*At this level of visualization we can see all modules based on bounded context extracted during the session ES.*

<a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/architecture/ARS_Components.png" target="_blank">Show picture</a>
![](img/architecture/ARS_Components.png)


## Bounded context exploration
 
### Airline Reservation System - bounded context exploration - Design Level Event Storming

Design Level Event Storming is a technique for discovering the complexity of a closed business area - a single bounded context. 
This process focused on vertical exploration of the area. Its purpose is to discover existing business rules. 

This technique should be applied to bounded contexts with complex business logic .  

In the case of Airline Reservation System this process will be carried out for core domain business context - ticket management. This is the place where we
 expect complex business rules. 

<a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/air-design-level-ticket-management-init.jpg" target="_blank">Show picture</a>
![](img/air-design-level-ticket-management-init.jpg)  

During the session it turned out that it is necessary to redefine Gifts Bounded Context boundaries. Information about the exchange of a gift for a cash
 equivalent must be immediately transferred to the booking module. The equivalent amount must be known at the time the price is calculated. This rule
  enforces a combination of both areas. 
  
 
  <a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/air-design-level-ticket-management-designlevel.jpg" target="_blank">Show
   picture</a>
  ![](img/air-design-level-ticket-management-designlevel.jpg)  

 This level of detail is sufficient to start implementation.
 
 #### Aggregates
 
 It can be said that the aggregate is the guardian of invariants.
 
 Quoting [Martin Fowler](https://martinfowler.com/bliki/DDD_Aggregate.html): 
 
 |****A DDD aggregate is a cluster of domain objects that can be treated as a single unit.****|
 |:-----:|
 |***Aggregates are the basic element of transfer of data storage - you request to load or save whole aggregates. Transactions should not cross aggregateboundaries.***|

Extracting aggregates begins by grouping commands, events, and rules for the same object. 

  ![](img/air-aggregate-reservation.jpg)
 
Based on this project, it is possible to prepare an aggregate implementation skeleton. In the implementation, each command is represented by a
 separate method, and each rule by its logic.

```java
public class Reservation {

    private static int TWO_WEEKS_DAYS = 14;
      private ReservationId id;
      private CustomerId customerId;
      private FligtId flightId;
      private LocalDateTime departureDate;
      private Money price;
    
      private CurrentlyLocked currentlyHolded;
      private ReservedThisMonth reservedThisMonth;
      private RescheduledSoFar rescheduledSoFar;
      private Status currentStatus;

   // BLUE CARD
    public Result activate() {
      if (reservedThisMonth.limitReached()) {
        return Result.failure();
      }      
      return Result.success();
    }
  
    // BLUE CARD
    public Result confirm() {
      if (!(isActive() || isLocked())) {
        return Result.failure();
      }      
      return Result.success();
    }
    // BLUE CARD
    public Result lock() {
      if (!isActive() || departureDateLessThan() || currentlyHolded.limitReached()) {
        return Result.failure();
      }      
      return Result.success();
    }
    // BLUE CARD
  
    public Result reschedule() {
      if (!isConfirmed() || rescheduledSoFar.limitReached()) {
        return Result.failure();
      }      
      return Result.success();
    }
    // BLUE CARD
  
    public Result cancel() {
      if (!(isLocked() || isConfirmed())) {
        return Result.failure();
      }      
      return Result.success();
    }
//private methods
}

``` 
#### Lack of Cohesion

At first glance you can see that the design of this class is not optimal. Class is too long. Has a lot of attributes. Some of them make sense only in a
 certain state. It's a code smells. 
  
Code analysis carried out by CodeMR confirms the problem. It showed a Lack of Cohesion for a Reservation class.  

![](img/loc-1.png)

|Measure how well the methods of a class are related to each other. High cohesion (low lack of cohesion) tend to be preferable, because high cohesion is associated with several desirable traits of software including robustness, reliability, reusability, and understandability. In contrast, low cohesion is associated with undesirable traits such as being difficult to maintain, test, reuse, or even understand.LCOM (Lack of Cohesion of Methods): Measure how methods of a class are related to each other. Low cohesion means that the class implements more than one responsibility. A change request by either a bug or a new feature, on one of these responsibilities will result change of that class. Lack of cohesion also influences understandability and implies classes should probably be split into two or more subclasses. |
|--------|
    
    
**Domain Entity decomposition**    

I made an attempt to decompose the Reservation Entity. Decomposition is based on the possible entity states. Each state has its own representation. 

|The same Domain Entity can have many class representations.| 
|--------|

|Many Domain Entities can map to one database entity.|
|--------|


Many Domain Entities in the Reservation Aggregate:

![](img/air-aggregate-reservation-decomposition.jpg)

Now each object has a set of fields appropriate for a given transition.  

New objects ensure proper transition between states (by providing appropriate methods).

For example: REGISTERED reservation is able to change state to HOLDED or CONFIRMED.

```
               --> HOLDED
              |
REGISTERED -> |
              |
               --> CONFIRMED
```

The RegisteredReservation object provides two methods (*hold()* and *confirm()* respectively) returning Result.
             
```java
class RegisteredReservation implements IdentifiedReservation {
  //...

  public Result hold(int currentlyHolded) {
    return HoldedReservation.create(this, this.departureDate, currentlyHolded);
  }

  public Result confirm() {
    return ConfirmedReservation.create(this);
  }
}

```

When the result is successful, there is possible to get the right one specialization (reservation in new state). 

```
  @DisplayName("Should hold when number of holded reservations equals 2")
  @Test
  void shouldHoldWhenNumberOfLockedReservationsEquals2() {
    // given
    var registered = ReservationFixture.registeredWithDepartureDayFor3Weeks();
    var currentlyHolded = 2;
    // when
    var res = registered.hold(currentlyHolded);
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
    Assertions.assertThat(res.returned()).isExactlyInstanceOf(HoldedReservation.class);
  }

  @DisplayName("Should confirm when departure date more than 2 weeks")
  @Test
  void shouldConfirmWhenDepartureDateMoreThan2Weeks() {
    // given
    var registered = ReservationFixture.registeredWithDepartureDayFor3Weeks();

    // when
    var res = registered.confirm();
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
    Assertions.assertThat(res.returned()).isExactlyInstanceOf(ConfirmedReservation.class);
  }
```
Internal state checking methods *(isActive(),isConfirmed(),isHolded()...)* have become unnecessary. State is determined by the type of object.

Re-analysis of reservation model package showed significant improvement:

![](img/loc-2.png)


## Implementation (Reservation module)

The Reservation module is an example of a deep module. It has a large number of business rules. 
Some of them are quite complicated. 
According to ADR [Use Ports and Adapters architecture](ars/0003-TCH-RES-use-ports-and-adapters-architecture.md) module will be implemented in architecture
 Ports and Adapters (Hexagonal Architecture).

### Ports and Adapters (Hexagonal Architecture)

This type of architecture is dedicated for modules with high business complexity. 
It gives the opportunity to separate the most important business logic from application and infrastructure logic.  Thanks to this, each type of logic can be
tested independently.

The heart of the system is the domain model whose task is to implement core business rules. Domain model must be completely independent of the libraries used.

The application communicates with other parts of the system using the input ports (primary ports) and output ports (secondary ports).
Each port has a dedicated adapter whose task is to translate the model.

**In the real world, ports are interfaces. Their implementation is provided by the infrastructure layer.** 

![](img/hexagonal_architecture.jpg)

### Non-functional requirements

The module will be tested using three types of tests: 

- Unit Tests will be used to test business logic.  They will be made without access to the web layer and database.
- Acceptance Tests (integration tests)  will check the correctness of the business process. Require access to the database but
 are separated from the REST API layer. 
- Web API Tests will be used to check the REST API. They will be performed without access to the database and without interacting with the
  application layer.

According to notes:

- [Use relational database as a aggregate data repository](ars/0004-TCH-RES-use-relational-database-as-a-aggregate-data-repository.md)
- [Use Spring Repository](ars/0005-TCH-RES-use-spring-repository.md)

As a data storage relational database will be used.  Access to data will be provided by Spring Repository.  

The  reservation module will communicate with external components via the facade interface - *ReservationFascade* . Its implementation will be provided by a
 spring bean.  The interface can be injected into controllers and other application modules via Spring IoC. It will delegate calls to the module's internal services.

**No other interfaces can be used outside the module (and in REST controllers).**

### Application services

Application services are the bridge between domain model (and services) and infrastructure. They do not implement any business logic. Coordinate the
 invocation of the correct domain objects and provide the appropriate data. 
 
 Main application services are:
 
 - *ReservationFacade* - communicates with other parts of the system . 
 - *ReservationService* - delegates calls to the domain model .
 - *ReservationRepository* - acquires and saves data.
 
 Their implementations are spring beans placed in the infrastructure part.
 
 ReservationRepository has two different implementations:
 
 1. **InMemoryReservationRepository** - provides fast database functionality mainly for testing purposes. It will be be implemented based on a simple HashMap
 . Its use will allow to save and read data without having to raise real database context. It will be very fast.
    
 ```java
public class InMemoryReservationRepository implements ReservationRepository {
    Map<ReservationId, Reservation> cache=new HashMap<>();
}
```

2. **DefaultReservationRepository** - provides real access to data via Spring Data Repository. It translates the domain model into an entity model (and vice
 versa). Should be used in real, production implementation. 
 
```java
public class DefaultReservationRepository implements ReservationRepository {

    private ReservationRepositoryDB repositoryDB;

    public DefaultReservationRepository(ReservationRepositoryDB repositoryDB) {
        this.repositoryDB = repositoryDB;
    }
}
```
  
  |It should be noted that application services cannot use a model with infrastructure features (e.g. an entity model). This would kill the flexibility and testability of the application.|
  |------|
 

### Spring Boot Configuration

**It is important to follow two rules**

|Beans are only instigated in dedicated factories. Don't use any annotations used by the spring autoscan mechanism *(@Component,@Service)*.|
|:-------:|

|@Autowired annotation should be used only in tests (or nowhere). Injections should be realized via constructors!|
|:-------:|


The application can run in test and production mode. It is done by using few independent SpringBoot configurations.

#### 1. Preparing main configuration

In production mode, application should use remote MongoDB database.

```java
@SpringBootApplication
@Import(ReservationConfiguration.class)
public class ArsApplication {
    //...
}
```

```java
@EnableMongoRepositories(basePackages = "mw.ars.reservations.reservation.infrastructure.db")
public class ReservationConfiguration {
  @Bean
  public ReservationRepository createRepository(ReservationRepositoryDB repoDB) {
    return new DefaultReservationRepository(repoDB);
  }

  @Bean
  public ReservationAppService createService(
      ReservationRepository repo, FlightsFacade flightsFacade) {
    return new DefaultReservationAppService(repo, flightsFacade);
  }

  @Bean
  public ReservationFacade createFacade(ReservationAppService service) {
    return new DefaultReservationFacade(service);
  }
  /*
   * Use the standard Mongo driver API to create a com.mongodb.MongoClient instance.
   */
  public @Bean MongoClient mongoClient() {
    return MongoClients.create("mongodb://localhost:28017");
  }

  public @Bean MongoDbFactory mongoDbFactory(@Autowired MongoClient client) {
    return new SimpleMongoClientDbFactory(client, "reservations");
  }
}
```

#### 2. Preparing  test configuration

Test configuration depends on the performance of the test being performed. 

|If the tests require a different set of configurations, it should be ensured to proper context isolation. An annotation is used for this *@DirtiesContext*|
|:----:|

**1. Configuration for web layer test**

The *@SpringBootTest* annotation indicates a configuration prepared specifically for testing purposes *WebLayerTestConfiguration*. 
Spring loads this configuration and **not search another ones**.  

**The directly indicated configuration does not need to have the @Configuration annotation. It allows to control the loading process!**

```java
@SpringBootTest(classes = WebLayerTestConfiguration.class)
@Import({ReservationController.class})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
```

```java
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class
        , MongoDataAutoConfiguration.class
        , EmbeddedMongoAutoConfiguration.class})
public class WebLayerTestConfiguration {
}

```
This test configuration changes the standard SpringBoot behavior. Aspects of data access are excluded from the auto-configuration mechanism.

**2. Configuration for the acceptance test** 

The acceptance test requires access to the database, therefore it must load its own configuration:

```java
@SpringBootTest(classes = {LocalMongoDBTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ReservationAcceptanceTest {}    
```

```java
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "mw.ars.reservations.reservation.infrastructure.db")
public class LocalMongoDBTestConfiguration {
  @Bean
  public ReservationRepository createRepository(ReservationRepositoryDB repoDB) {
    return new DefaultReservationRepository(repoDB);
  }

  @Bean
  public ReservationAppService createService(ReservationRepository repo, FlightsFacade flightsFacade) {
    return new DefaultReservationAppService(repo, flightsFacade);
  }

  @Bean
  public ReservationFacade createFacade(ReservationAppService service) {
    return new DefaultReservationFacade(service);
  }

  @Bean
  FlightsFacade createFlightFacade(){
    return new DefaultFlightsFacade() ;
  }
}
```

In this case there is no MongoDB datasource configuration beacuse *@EnableAutoConfiguration* is activated. Spring runs this test in standard embedded mode.

At this moment the greatest advantage of hexagonal architecture is revealed. The same application is constructed in a completely different way. It gains new
behavior with no code changes.



#### Multiple configurations - Tips & Tricks

1. Customizing primary configuration

A lot of interesting configuration information can be found directly in the [Spring Boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/).

The following sections are particularly interesting:

* [Detecting Test Configuration](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications-detecting-config)

* [Excluding Test Configuration](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications-excluding-config)


**It should be remembered that if you want to customize the primary configuration, you can use a nested @TestConfiguration class. Unlike a nested
@Configuration class, which would be used instead of your application’s primary configuration, a nested @TestConfiguration class is used in addition to
your application’s primary configuration.**

The @TestConfiguration mechanism is particularly useful if we agree to overwrite beans. We can do this by setting the *spring.main.allow-bean
-definition-overriding=true* property in the test *application.property*. In this case, we agree that the bean defined in the main configuration will  be
 overwritten by the definition from the test configuration. For the test it can be created in a completely different way. 
 
 **Beans overwriting should be turned off in production mode (main application.properties)!**
 
 2. Spring Boot autoconfiguration debugging
 
 SpringBoot provides a very useful configuration debugging mechanism. it can be enabled by setting the *debug* property:
  
 ```
debug=true
```

More inormations more information in the [Display Auto-Configuration Report in Spring Boot](https://www.baeldung.com/spring-boot-auto-configuration-report) article


3. Sharing context configuration  

|Spring’s test framework caches application contexts between tests. Therefore, as long as your tests share the same configuration (no matter how it is discovered), the potentially time-consuming process of loading the context happens only once. |
|----|

4.  @Configuration annotation

**The directly indicated configuration (via classes attribute) does not need to have the @Configuration annotation. It allows to control the loading process!**


### Testing

#### Unit tests

Unit tests run completely outside the spring context and therefore do not require any special configuration. 
  
#### Acceptance test
 
 The module implementation begins with creating an acceptance test. It checks the correctness of the module as a whole and tests the entire ticket reservation
 process. 

   |There is no need to test business rules at this stage. They have been checked by unit tests.|
   |:----------------:|
          
The acceptance test is more expensive than the unit test, it will operate on two elements :

- ReservationFacade - module entry point 
- Repository - database (injection is needed only to reset the state between tests)
   
The acceptance test is carried out in the Spring Context. 
     
##### From test to implementation

The acceptance test can be helpful when determining the module contract. Writing the test can be started when the interface has no methods yet. 

We can create the scenario steps, the IDE indicates the missing implementation elements. We refer to methods and objects that do not currently exist  in
 implementation.
 
For example, when holding a reservation, we call the *holdOn()* method, specifying its *HoldOnReservationCommand* object. Both the method and the command
 object do not currently exist. 

The same situation applies to the method of checking whether an object has been saved to the database *findByReservationId()*, and its
*FindByReservationIdCommnad*.

It should be noted that the persistance is also checked by facade method. If the object saved correctly, it must be found by id.
We do not check the existence of a record directly inside the database. We are examining the interface of the module and finding reservation is a valid business
 operation.

![](img/acceptance-test-imp/imp-1.png)

Then add the missing implementation elements (with active IDE support).

```java
public class HoldOnReservationCommand {
    private ReservationId reservationId;
    private SeatNumber seat;
    private LocalDateTime departureDate;

    private HoldOnReservationCommand(ReservationId reservationId, SeatNumber seat, LocalDateTime departureDate){
        this.reservationId=reservationId;
        this.seat = seat;
        this.departureDate = departureDate;
    }

    public static HoldOnReservationCommand of(ReservationId reservationId, SeatNumber withSeat, LocalDateTime withDepartureDate){
        return new HoldOnReservationCommand(reservationId,withSeat,withDepartureDate);
    }
}

```

```java
public class FindByReservationIdCommnad {
    private ReservationId reservationId;

    private FindByReservationIdCommnad(ReservationId reservationId){
        this.reservationId=reservationId;
    }

    public static FindByReservationIdCommnad of(ReservationId reservationId){
        return new FindByReservationIdCommnad(reservationId);
    }
}
```

```java
public interface ReservationFacade {
    void holdOn(HoldOnReservationCommand command);
    Optional<ReservationDTO> findByReservationId(FindByReservationIdCommnad command);
}
```
At the moment, only one method is missing to check the status of the returned object:

![](img/acceptance-test-imp/imp-2.png)

```java
@Value
public class ReservationDTO {
    private ReservationId reservationId;
    private StatusDTO status;
    private CustomerId customerId;
    private FligtId flightId;

    public boolean isHolded() {
        return status==StatusDTO.HOLDED;
    }

  
}
```

After adding it, the scenario step is completed, and the facade method contract is established.

![](img/acceptance-test-imp/imp-3.png)

The same can be done for the other facade operations. 

In this way, a full acceptance test and all methods used in the booking process were created.

```java
@SpringBootTest(classes = {LocalMongoDBTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ReservationAcceptanceTest {

  @Autowired private ReservationFacade reservationFacade;

  @DisplayName(
      "Should realize main ticket reservation process (create/register/hold/confirm/reschedule/cancel).")
  @Test
  void shouldRealizeMainReservationProcess() {
    var customerId = CustomerId.of(UUID.randomUUID());
    var flightId = FlightId.of(UUID.randomUUID());
    // given
    var resId = reservationFacade.create(CreateReservationCommand.of(customerId, flightId));
    // when
    var result = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isNew()).isTrue();

    var withSeat = SeatNumber.of(10);
    reservationFacade.register(
        RegistrationCommand.of(resId, flightId, LocalDateTime.now().plusDays(30), withSeat));
    result = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isRegistered()).isTrue();

    reservationFacade.holdOn(HoldOnReservationCommand.of(resId));
    result = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isHolded()).isTrue();

    reservationFacade.confirm(ConfirmationCommand.of(resId));
    result = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isConfirmed()).isTrue();

    var newFlightId = FlightId.of(UUID.randomUUID());
    var newSeatId = SeatNumber.of(11);
    var newDepartureTime = LocalDateTime.now().plusDays(15);
    var newConfirmedAfterRescheduling =
        reservationFacade.reschedule(
            RescheduleCommand.of(resId, customerId, newFlightId, newSeatId, newDepartureTime));

    result.clear();
    result = reservationFacade.findByFlightId((FindByFlightIdCommand.of(customerId, flightId)));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isRescheduled()).isTrue();

    result.clear();
    result = reservationFacade.findByFlightId((FindByFlightIdCommand.of(customerId, newFlightId)));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isConfirmed()).isTrue();
    Assertions.assertThat(result.get(0).getReservationId().equals(newConfirmedAfterRescheduling));

    result.clear();
    result = reservationFacade.findByFlightId((FindByFlightIdCommand.of(customerId, newFlightId)));
    reservationFacade.cancel(CancelByResrvationId.of(result.get(0).getReservationId()));
    var cancelled =
        reservationFacade.findByFlightId((FindByFlightIdCommand.of(customerId, newFlightId)));
    Assertions.assertThat(cancelled.isEmpty()).isFalse();
    Assertions.assertThat(cancelled.get(0).isCancelled()).isTrue();
  }
}

```

Of course, the test will fail because the facade has no implementation.

```java
public class DefaultReservationFacade implements ReservationFacade {

    private ReservationService service;

    public DefaultReservationFacade(ReservationService service) {
        this.service = service;
    }

    @Override
    public ReservationId create(CreateReservationCommand command) {
        return null;
    }

    @Override
    public Optional<ReservationDTO> findByFlightId(FindByFlightIdCommand command) {
        return Optional.empty();
    }

    @Override
    public void holdOn(HoldOnReservationCommand command) {

    }

    @Override
    public Optional<ReservationDTO> findByReservationId(FindByReservationIdCommnad command) {
        return Optional.empty();
    }

    @Override
    public void confirm(ConfirmationCommand command) {

    }

    @Override
    public ReservationId reschedule(RescheduleCommand of) {
        return null;
    }

    @Override
    public void cancel(CancelByResrvationId command) {

    }

    @Override
    public void register(RegistrationCommand command) {

    }
}
```

The test should be successful after implementing the application and domain functions.

#### Web layer test

Web layer tests will be described in separate section dedicated to Rest API.

### DDD Building Blocks

Structural elements used in the context of DDD can be divided into three groups : 

* **Behavioral** - these are the basic elements that define ubiquitous language such as commands, events, aggregates. Their main task is to determine what
 the system does and how it communicates with the environment . 
* **Structural** - these are the elements that make up the model of our system. These elements have precisely defined semantic meaning. They are an important
 part of the ubiquitous language.
* **Lifecycle** - these are technical elements that are not part of the ubiquitous language. Support the creation and management of structural elements. 

![](img/ddd-building-blocks.png)

DDD Building Blocks do not have technical characteristics, they can be used in communication between business and technical teams. They are a tool enabling
 quick feedback from business. Allow you to postpone architectural decisions, because they are an abstraction, and can be mapped in code in many
  ways.

Each of the building blocks has a their own specific responsibility. It performs its tasks and cooperates with other elements in a strictly defined manner. 

The next graphic shows how the main building blocks cooperate together to realize an example use case - reservation holding. 
Shows typical data flow - from the REST controller to the database. 

Next to each block is a snippet showing an example of how to implement it.

<a href="https://raw.githubusercontent.com/mwwojcik/airline-reservation-system/master/img/building-blocks-flow.png" target="_blank">Show picture
</a>
![](img/building-blocks-flow.png)  

### Rest API standards 

//Intro ... TBD...

#### REST Archetypes 
The API distinguishes between four different resource archetypes:

* Document
* Collection
* Store
* Controller

//TBD.. description of each type

#### REST methods
There are five basic HTTP methods:
* *GET* - returns resource
* *POST* - creates resource
* *PUT* - modifies the resource
* *OPTIONS* - gets additional informations about resource
* *DELETE* - removes the resource

___

1. *GET* - It's save and idempotent method. 

* GET requests can be cached
* GET requests remain in the browser history
* GET requests can be bookmarked
* GET requests should never be used when dealing with sensitive data
* GET requests have length restrictions
* GET requests are only used to request data (not modify)

[Source: HTTP Request Methods](https://www.w3schools.com/tags/ref_httpmethods.asp)

**Standard RESPONSE: OK(200) + Body**

___

2. *POST* - It's unsave and not idempotent method. 

* POST requests are never cached
* POST requests do not remain in the browser history
* POST requests cannot be bookmarked
* POST requests have no restrictions on data length

[Source: HTTP Request Methods](https://www.w3schools.com/tags/ref_httpmethods.asp)

**Standard RESPONSE: CREATED(201) + New Resource Location (Header)+Body with resource (opt)**

___

3. *PUT* - It's unsave but idempotent method. 

**Standard RESPONSE: Accepted(202) or No Content (204)** 

*No Content 204 - resource found, changed, but not returned!*

___

4. *DELETE* - It's unsave but idempotent method. 

**Standard RESPONSE: No Content (204)** 

___

#### HTTP codes

1. 2xx – success
2. 3xx – I don't know, ask someone else ...
3. 4xx – client error
4. 5xx - server error

___

* 200 OK - full success - response must have Response Body
* 201 Created - resource was created
* 202 Accepted - accepted for processing, the result is not yet available
* 204 No Content - everything is OK, but there is no content returned, there is no Response Body

___

* 301 Moved Permanently - this response may be CACHED!
* 304 Not Modified – cache standard response
* 307 Temporary Redirect – adress was changed - your resource can be found here...  

___

* 400 Bad Request
* 401 Unauthorized – the user must resend the authorization data
* 403 Forbidden 
* 404 Not Found 
* 405 Method Not Allowed 
* 409 Conflict 
* 410 Gone
* 415 Unsupported Media Type 

___

* 500 Internal Server Error
* 503 Service Unavailable 
* 504 Gateway Timeout 

### REST API tests
Testing REST controllers is carried out in complete isolation from the rest of the application. The test consists of sending a specially prepared REST
request,  receiving a response and checking whether the answer contains the expected result. Data is not saved to the database nor application and domain
logic is performed.  These parts of the application have already been tested by unit testing and acceptance test.

#### Configuration of test's infrastructure elements

This type of testing requires the use of several special infrastructure components:

* **@SpringBootTest** - test performed in the context of Spring with indicated configuration
* **@AutoConfigureMockMvc**- prepares a web container for REST testing. Builds web context with holders,filters,etc... 
* **MockMvc** - Main entry point for server-side Spring MVC test support. It's a client specialized for REST tests. 
    MockMvc simulates network communication (in fact, it does not take place and it cannot be observed).
* **@MockBean** - register mock object in Spring Context. If a bean of this type is already registered in the context it will be replaced . 

```java
    @SpringBootTest(classes = WebLayerTestConfiguration.class)
    @Import({ReservationController.class})
       @AutoConfigureMockMvc
       @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
       public class ReservationControllerTest {
         @Autowired private MockMvc mockMvc;
         @MockBean private ReservationFacade reservationFacade;
}
```

#### Web test elements

![](img/test_web_api.png)

1. In this line we can observe the mock behavior configuration. When the application code (inside the controller) calls the facade method 
*findByFlightId*  with any type, then this method should return list with two objects of ReservationDTO class. 

2. MockMvc performs a request and return a type that allows chaining further actions, such as asserting expectations, on the result.

```
ResultActions 	perform(RequestBuilder requestBuilder);
``` 

3. RequestBuilder creates mocked implementation of the HttpServletRequest interface. In this case  *get* method request is builded. It will be sent on URI with 
two url parameters.

4. Asserting expectations, on the result.

5. A ResultMatcher matches the result of an executed request against some expectation. In this line we can see expected HTTP status checking.

6. Checking if the returned list has the expected size. 
                                                                     

