Feature: As a user I can add a new blog to the list

    Scenario: user can add a new blog by giving url and title
        Given command blog is selected
        When  user enters blog url "www.fowlie.com" and title "Martin Fowler rules"
        Then  system will respond with "Blog added successfully!"

    Scenario: user can not add a new blog without giving url
        Given command blog is selected
        When  user leaves blog url blank
        Then  system will respond with "URL cannot be blank."


    Scenario: user can not add a blog that already is on the list
        Given blog "www.com" title "Martin Fowler" is successfully added on the list
        And   command blog is selected
        When  user enters blog url "www.com" and title "Martin Fowler"
        Then  system will respond with "The suggestion already exists."
