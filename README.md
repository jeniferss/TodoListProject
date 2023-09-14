# Todo List

The purpose of the application is to make it possible to list activities to be done, activities that are being done, or activities that have been done, in other words, something like a TaskScheduler.

## Table of Contents
***
1. [About](#about)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [Running](#running)
5. [Testing](#testing)

## About
***

* Mandatory application requirements:

1. It must allow setting alerts for Tasks;
2. It needs to have at least one CRUD operation for Tasks (create, list, delete, update);
3. It must be web-based;
4. Implement the option of alarms for tasks;
5. Trigger the alarm notification when the set period arrives.

## Technologies
***
A list of technologies used within the project:
* [Java](https://www.oracle.com/br/java/technologies/javase/javase8-archive-downloads.html): Version 1.8
* [Javascript](https://developer.mozilla.org/pt-BR/docs/web/javascript/guide/introduction)
* [NodeJS](https://nodejs.org/en/blog/release/v18.0.0): Version 18.0.0
* [jQuery](https://jquery.com/download/): Version jquery-3.7.1.min.js
* [HTML](https://developer.mozilla.org/pt-BR/docs/Web/HTML)
* [CSS](https://developer.mozilla.org/pt-BR/docs/Learn/Getting_started_with_the_web/CSS_basics)

## Installation
***

```bash
$ git clone git@github.com:jeniferss/todo-list.git
```

## Running
***

### Backend

```bash
$ cd backend
$ cd src
$ javac Main.java
$ java Main
```

### Frontend
1. Go to todo-list > frontend > scr 
2. Open the index.html file

## Testing
***

Unit tests play an important role in early bug detection by isolating specific parts of code for testing, enabling developers to rectify issues before they spread system-wide. Simultaneously, they prompt developers to contemplate code functionality and behavior, fostering the development of cleaner, modular, and sustainable code aligned with best practices and design principles.

* Mandatory requirements:
1. In your TODO List application, a CRUD (create, read, update, and delete) logic has been developed. Create unit tests for each of these actions.

```bash
$ cd backend
$ cd src
$ cd test
$ javac RunTest
$ java RunTest