# Domain-Driven Design Rules

###### Tags: `Domain-Driven Design` `Rules` `Architecture Compliance Checking`

## Goal

To define rules that will make it difficult for a development team to violate without conscient the domain-driven design architecture of their project in Java.

Please keep in mind that the initial goal behind domain-driven design is the collaboration between technical and domain experts (see the other file).

## Tools

[JUnit4](https://junit.org/junit4/), [ArchUnit](https://www.archunit.org/) and its Maven plugin.

Rules developped and tested in Java.

## Rules

There are many possible sets of rules, with soft and hard constraints; choose the ones that may interest you, but do not forget that these constraints should be used as an assistance and do not replace the self-discipline of a development team.

Rules are sorted by domain-driven design concepts.

---

### Module

There are no rules for modules.

---

### Layered Architecture

Layers are represented as packages in Java and have to be specifically named or annotated (**package-info.java**) in order to apply the following rules.

#### Anticorruption Layer (or Isolating Layer)

* Annotated with **@Anticorruption**.
* Should not be accessed by another layer.
* Should not reside in another layer.

#### Presentation Layer (or User Interface)

* Annotated with **@Presentation**.
* Should not be accessed by another layer.
* Should not reside in another layer.

#### Application Layer

* Annotated with **@Application**.
* Should only be accessed by anticorruption and presentation layers.
* Should not reside in another layer.

#### Domain Layer (or Model Layer)

* Annotated with **@Domain**.
* Should not access other layers.
* Should not reside in another layer.

#### Infrastructure Layer

* Annotated with **@Infrastructure**.
* Should only access domain layer.
* Should not reside in another layer.

#### Example of Layered Architecture

```
.
├── salesmanagementsystem
│   ├── anticorruption @Anticorruption
│   ├── application @Application
│   ├── domain @Domain
│   │   ├── model
│   │   │   ├── billing
│   │   │   ├── customer
│   │   │   ├── location
│   │   │   ├── shipping
│   │   │   └── warehouse
│   │   └── service
│   ├── infrastructure @Infrastructure
│   │   └── persistence
│   │       └── inmemory
│   └── presentation @Presentation
└── stockmanagementsystem
    ├── anticorruption @Anticorruption
    ├── application @Application
    ├── domain @Domain
    │   ├── model
    │   │   ├── location
    │   │   ├── shipping
    │   │   └── warehouse
    │   └── service
    ├── infrastructure @Infrastructure
    │   └── persistence
    │       └── database
    └── presentation @Presentation
```

---

### Value Object

Value objects are represented as classes in Java and have to be annotated.

* Annotated with **@ValueObject**.
* Should reside in the domain layer.
* Should not also be annotated with **@Entity**, **@Factory**, **@Repository** or **@Service**.
* Should have at least one field.
* Should not have public fields.
* Should not have public setters (methods annotated with **@Setter**).
* Should not have public getters (methods annotated with **@Getter**) for fields which are not immutables.
* Should not have public methods accessing fields which are not immutables.
* Should override Object.class equals, hashCode and toString methods; each should access all fields.

Value objects, entities, fields annotated with **@Immutable** and final primitives are considered as immutables.

---

### Entity

Entities are represented as classes in Java and have to be annotated.

* Annotated with **@Entity**.
* Should reside in the domain layer.
* Should not also be annotated with **@ValueObject**, **@Factory**, **@Repository** or **@Service**.
* Should have at least one field annotated with **@EntityID**.
* Should override Object.class equals, hashCode and toString methods; each should access all fields annotated with **@EntityID**.

Fields annotated with **@EntityID** also have some rules.

* Should only be used by classes annotated with **@Entity**.
* Should not be public.
* Should not have public setters (methods annotated with **@Setter**).
* Should not have public getters (methods annotated with **@Getter**) if not immutable.
* Should not be accessed by public methods if not immutable.

---

### Aggregate

Aggregate roots are represented as classes in Java and have to be annotated.

* Annotated with **@Aggregate**.
* Should also be annotated with **@Entity**.
* Should control access to its internal objects; each should only be instantiated in specific factory.

---

### Factory

Factories are represented as classes in Java and have to be annotated.

* Annotated with **@Factory**.
* Should reside in the domain layer.
* Should not also be annotated with **@ValueObject**, **@Entity**, **@Repository** or **@Service**.
* Should access at least one constructor from a class annotated with **@ValueObject** or **@Entity**.
* Should have public and protected methods with the same return type; at least one public or protected method is required.

---

### Repository

Repositories are represented as classes in Java and have to be annotated.

* Annotated with **@Repository**.
* Should reside in the infrastructure layer.
* Should not also be annotated with **@ValueObject**, **@Entity**, **@Factory** or **@Service**.
* Should implement an interface which resides in the domain layer.
* Should access at least one class annotated with **@ValueObject** or **@Entity**.

---

### Service

Services are represented as classes in Java and have to be annotated.

* Annotated with **@Service**.
* Should reside in the application, domain or infrastructure layer.
* Should not also be annotated with **@ValueObject**, **@Entity**, **@Factory** or **@Repository**.
* Should implement an interface which resides in the same layer.
* Should not have non-final fields.

## Installation

In order to assess your architecture, you will need to add the ArchUnit Maven plugin and specify rules/dependencies in your **pom.xml** as following.

```
<plugin>
    <groupId>com.societegenerale.commons</groupId>
    <artifactId>arch-unit-maven-plugin</artifactId>
    <version>1.0.1</version>
    <configuration>
        <projectPath>${project.build.directory}</projectPath>
        <rules>
            <rule>fr.ubordeaux.ddd.tests.AggregateTest</rule>
            <rule>fr.ubordeaux.ddd.tests.EntityIdTest</rule>
            <rule>fr.ubordeaux.ddd.tests.EntityTest</rule>
            <rule>fr.ubordeaux.ddd.tests.FactoryTest</rule>
            <rule>fr.ubordeaux.ddd.tests.LayeredArchitectureTest</rule>
            <rule>fr.ubordeaux.ddd.tests.RepositoryTest</rule>
            <rule>fr.ubordeaux.ddd.tests.ServiceTest</rule>
            <rule>fr.ubordeaux.ddd.tests.ValueObjectTest</rule>
        </rules>
    </configuration>
    <executions>
        <execution>
            <phase>test</phase>
            <goals>
                <goal>arch-test</goal>
            </goals>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>com.tngtech.archunit</groupId>
            <artifactId>archunit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>fr.ubordeaux.ddd</groupId>
            <artifactId>ddd-rules</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>fr.ubordeaux.ddd</groupId>
            <artifactId>ddd-annotations</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
</plugin>
```

Another solution would be to retrieve rules classes and duplicate them in your project, then add the following dependency in your **build.gradle**.

```
dependencies {
    testCompile 'com.tngtech.archunit:archunit-junit4:0.10.2'
}
```
