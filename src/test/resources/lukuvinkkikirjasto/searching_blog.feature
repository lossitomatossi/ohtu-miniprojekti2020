Feature: As a user I can search blogs by title

    Scenario: user searches for an existing blog
        Given blog "www.com" title "Martin Fowler" is successfully added on the list
        Given command search is selected
        When user enters category "blog" and search term "Martin"
        Then  system will respond with "Fowler" and "URL                  Title                Writer               Date"    

    Scenario: user searches for an non existent blog
        Given command search is selected
        When user enters category "blog" and search term "Martin"
        Then  system will respond with "Nothing found."