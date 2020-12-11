Feature: As a user I can list movies

    Scenario: user lists movies when there are movies
        Given movie "Doom" is successfully added on the list
        Given movie "Abyss" is successfully added on the list
        Given command list is selected
        When  user lists movies
        Then  system will respond with "Doom" and "Abyss"

    Scenario: user lists movies when there are no movies
        Given command list is selected
        When user lists movies
        Then  system will respond with "Nothing found."
