# README #

### Goal of the application ###

* The goal of the application is to make everyday shopping easier by enabling the user to list down items to buy.


### How to compile and run the application ###
The application is developed in kotlin using android studio. A latest version of android studio is required to run the application.


### How to run tests for the application ###
We use Mockk framework for unit testing. Android studio is required to run tests.
* Test files are located in
    ```
    app/src/test/java/com/example/eshoppingassignment
    ```

### Application Architecture ###
The application architecture mostly consists of data and ui layers. The data layer holds the business information and repositories which contain zero to many data sources. UI layers holds that ui elements state holders (such as ViewModel classes) that hold data, expose it to the UI, and handle logic.


* IMPORTANT
  * The app is using Fake Store API (https://fakestoreapi.com/) for backend integration.