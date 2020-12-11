Feature: As a user I can edit books

    Scenario: user edits book
        Given book "Shaman" by writer "Martin Herman" is successfully added on the list
        Given command edit is selected
        When user enters category "book" and edit term "Shaman" and new title "Sharmander" and same writer "Martin Herman"
        Given command search is selected
        When user enters category "book" and search term "Sharmander"
        Then  system will respond with "Sharmander" and "Title                Author               Year   Pages   ISBN"
