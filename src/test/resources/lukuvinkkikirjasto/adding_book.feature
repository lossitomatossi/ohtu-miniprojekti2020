Feature: As a user I can add a new book title to the list

    Scenario: user can add a new book by giving title and writer
        Given command book is selected
        When  user enters book title "Refactoring" and writer "Martin Fowler"
        Then  system will respond with "Title: Refactoring | Author: Martin Fowler | Year: -1 | Pages: -1 | ISBN:"

    Scenario: user can not add a new book without giving title
        Given command book is selected
        When  user leaves book title blank
        Then  system will respond with "Title cannot be blank."

    Scenario: user can not add a new book without giving writer
        Given command book is selected
        When  user enters book title "Refactoring" but leaves writer blank
        Then  system will respond with "Author cannot be blank."