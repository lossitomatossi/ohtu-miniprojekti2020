Feature: As a user I can list blogs

    Scenario: user lists blogs when there are books
        Given blog "www.com" title "Martin Fowler" is successfully added on the list
        Given blog "www.huhu.com" title "Hermanni" is successfully added on the list
        Given command list is selected
        When  user lists blogs
        Then  system will respond with "Fowler" and "Hermanni"

    Scenario: user lists blogs when there are no blogs
        Given command list is selected
        When user lists blogs
        Then  system will respond with "Nothing found."
