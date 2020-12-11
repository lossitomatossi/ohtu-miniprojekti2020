Feature: As a user I can delete item

    Scenario: user deletes an existing blog
        Given blog "www.com" title "Martin Fowler" is successfully added on the list
        Given command delete is selected
        When user enters category "blog" and delete term "Martin"
        Given command search is selected
        When user enters category "blog" and search term "Martin"
        Then  system will respond with "Suggestion deleted successfully!" and "Nothing found"

    Scenario: user tries to delete an existing item
        Given blog "www.com" title "Martin Fowler" is successfully added on the list
        Given command delete is selected
        When user enters category "blog" and delete term "doom"
        Then  system will respond with "Nothing was deleted."