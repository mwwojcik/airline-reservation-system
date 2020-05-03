<!--
*************Metryka**Nie*Modyfikowac**************
wfl-adr-title:Use Ports and Adapters architecture
wfl-adr-date:2020-05-02
wfl-adr-author:mw
***************************************************
-->
# 0003. Use Ports and Adapters architecture

Date: 2020-04-19

Author: mw

## Status

Accepted

## Context and Problem Statement

**What type of application architecture is to be used ?**

Considered options:

1. Layered architecture

2. Ports and adapters architecture 

Drivers:

* The Reservation module is an example of a deep module. 
* A large number of business rules.
* Some of the rules are quite complicated.
* Application should be well - tested . 

## Decision

Option no 2 - Ports and Adapters architecture. It is dedicated for modules with high business complexity. 
It separates domain logic from application logic. It allows to create independent tests for each type of logic.

## Consequences

Architecture is more complicated than Layered Architecture, but it gives much more opportunities. 

## Links

