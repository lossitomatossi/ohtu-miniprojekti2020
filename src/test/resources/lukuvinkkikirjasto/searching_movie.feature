Feature: As a user I can search movies by title

    Scenario: user searches for an existing movie
        Given movie "Doom" is successfully added on the list
        Given command search is selected
        When user enters category "movie" and search term "Doom"
        Then  system will respond with "Doom" and "Title                Director             Year   Length (min)"

    Scenario: user searches for an non existent movie
        Given command search is selected
        When user enters category "movie" and search term "Doom"
        Then  system will respond with "Nothing found."
