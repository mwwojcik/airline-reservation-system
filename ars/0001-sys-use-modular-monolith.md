<!--
***************************************************
wfl-adr-title:SYS Use Modular Monolith
wfl-adr-date:2020-04-19
wfl-adr-author:mw
***************************************************
-->
# 0001. SYS Use Modular Monolith

Date: 2020-04-19

Author: mw

## Status

Accepted

## Context and Problem Statement

**What type of arichitecture is to be used ?**

Considered options:

1. Monolith
2. Modular Monolith
3. Microservices

Drivers:
* Project is realized as GreenField
* A small development team (~10)
* Little experience in implementing distributed software 

* Load - (50-150 req/sec) - not so much

## Decision

Option no 2 - Modular Monolith. 

Architecture suitable for teams with little experience in implementing distributed systems. 
It gives the possibility of gradual migration to distributed. Suitable for  greenfield systems, where high variation in requirements is expected.
                                                              

 architecture.

## Consequences

Positive:
* High tolerance of errors in determining the module boundaries
* A simple way of communication
* Simple infrastructure
* Easy implementation

Negative:
* Low independence of modules
* Modules cannot be scaled independently


## Links

[Martin Fowler - MonolithFirst](https://martinfowler.com/bliki/MonolithFirst.html)