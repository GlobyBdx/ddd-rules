# Domain-Driven Design

###### Tags: `Domain-Driven Design`

## Goal

Domain-driven design is a term coined by Eric Evans [[1]](#References).

The goal behind domain-driven design is the collaboration between technical and domain experts.

## Concepts

![](https://i.imgur.com/whBvGBw.png)

---

### Module

A module should possess a cohesive set of concepts that can be reflected into the domain in order to develop an ubiquitous language. Modules must have low coupling between them at concept-level.

---

### Layered Architecture

![](https://i.imgur.com/2lCMCzg.png)

#### Anticorruption Layer (or Isolating Layer)

Talks to other systems through their interfaces and translates in every directions between models.

#### Presentation Layer (or User Interface)

Shows information to the user and interpretes user's command.

#### Application Layer

Defines tasks which are meaningful to the business or necessary for interactions with other systems.

#### Domain Layer (or Model Layer)

Represents concepts of the business, information about the business situation and business rules.

#### Infrastructure Layer

Provides generic technical capabilities that suppot the higher layers: message sending for the application, persistence for the domain, drawing widgets for the UI, and so on.

---

### Value Object

A value object is only defined by its attributes and should be immutable, it belongs to the domain layer.

---

### Entity

An entity is not defined primarily by its attributes and should have at least one ID, it belongs to the domain layer.

---

### Aggregate

An aggregate is a cluster of associated objects treated as a unit with an entity as root and boundary, it belongs to the domain layer. Associated objects should only be externally accessed from the root.

---

### Factory

A factory has responsibility for creating instances of complex objects as a piece and then enforcing encapsulation, it belongs to the domain layer.

---

### Repository

A repository has responsibility for storing instances of persistent objects with in-memory or database accesses, it belongs to the infrastructure layer and implements an interface which resides in the domain layer.

---

### Service

A service should be stateless, it belongs to the application, domain or infrastructure layer and implements an interface which resides in the same layer.

Services in the application layer should define tasks which are meaningful to the business or necessary for interactions with other systems.

Services in the domain layer should take non-natural responsibilities of entities or value objects.

Services in the infrastructure layer should be purely technical and lack any business meaning at all.

Services' names should be part of the ubiquitous language.

## References

[1] Evans E. Domain-driven Design—Tackling Complexity in the Heart of Software. Addison-Wesley: Reading, MA, 2004.
