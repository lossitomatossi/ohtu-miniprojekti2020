Feature: As a user I can list books

    Scenario: user lists books when there are books
        Given command book is selected
        When user adds book title "Scrummeli" and writer "Fowlis" and user lists category "book"
        Then  system will respond with "Fowlis"

    Scenario: user lists books when there are no books
        Given command list is selected
        When user lists books
        Then  system will respond with "Nothing found."
