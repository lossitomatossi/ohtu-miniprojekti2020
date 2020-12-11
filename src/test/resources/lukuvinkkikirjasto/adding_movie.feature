Feature: As a user I can add a new book to the list

    Scenario: user can add a new movie by giving title
        Given command movie is selected
        When  user enters movie title "King"
        Then  system will respond with "Movie added successfully!"

    Scenario: user can not add a new movie without giving title
        Given command movie is selected
        When  user leaves movie title blank
        Then  system will respond with "Title cannot be blank."


    Scenario: user can not add a movie that already is on the list
        Given movie "Secret Society" is successfully added on the list
        And   command movie is selected
        When  user enters movie title "Secret Society"
        Then  system will respond with "The suggestion already exists."
