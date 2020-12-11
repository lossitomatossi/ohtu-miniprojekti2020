Feature: As a user I can search books by title and writer

    Scenario: user searches for an existing book
        Given command book is selected
        When user adds book title "Kurkku" and writer "Petteri" and user enters category "book" and search term "Kurk"
        Then  system will respond with "Petteri" and "Title                Author               Year   Pages   ISBN"

    Scenario: user searches for an non existent book
        Given command search is selected
        When user searches for an non existent book
        Then  system will respond with "Nothing found."
