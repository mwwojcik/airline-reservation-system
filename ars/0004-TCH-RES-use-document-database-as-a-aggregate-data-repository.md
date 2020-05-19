<!--
*************Metryka**Nie*Modyfikowac**************
wfl-adr-title:Use document database as a aggregate data repository
wfl-adr-date:2020-05-02
wfl-adr-author:mw
***************************************************
-->
# 0004. Use document database as a aggregate data repository


Date: 2020-04-19

Author: mw

## Status

Accepted

## Context and Problem Statement

**What kind of database should be used to storage aggregates data ?**

Considered options:

1. Document database (MongoDB)

2. Relational database

Drivers:
* Simple objects will be saved. 

* There will be no relationship between objects .

* A Read Model combining data from different aggregates is required . 

## Decision
Option no 1 - document database - MongoDB. 

## Consequences

* the aggregate structure is simple, there is no relations between aggregates
* aggregate structure resembles a document structure
* document bases are very modern and attractive to the developer team
* possible problems with create cross aggregate Read Model
