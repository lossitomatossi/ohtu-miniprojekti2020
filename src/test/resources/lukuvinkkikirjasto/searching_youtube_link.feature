Feature: As a user I can search youtube by title

    Scenario: user can search youtube by title
        Given command search is selected
        When  user enters category "youtube" and searchTerm "Refactoring"
        Then  system will respond with "URL                                       Title                                     Created Description"    

