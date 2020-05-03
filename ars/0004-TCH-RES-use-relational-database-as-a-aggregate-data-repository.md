<!--
*************Metryka**Nie*Modyfikowac**************
wfl-adr-title:Use relational database as a aggregate data repository
wfl-adr-date:2020-05-02
wfl-adr-author:mw
***************************************************
-->
# 0004. Use relational database as a aggregate data repository


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
Option no 2 - relational database. 

## Consequences

This is a more stable solution. This technology is better known by the developer team.

It allows you to extract data from various modules and create Read Models. 

It is much easier than in the case of document databases.

