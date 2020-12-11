Feature: As a user I can search youtube by title

    Scenario: user searches for an existing youtube link
        Given command youtube is selected
        When  user adds youtube url "www.youtube.com/asdasd" and title "crazyvid" and user enters category "youtube" and search term "crazy"
        Then  system will respond with "www.youtube.com/asdasd" and "URL                    Title                Created    Description"    

    Scenario: user searches for an non existent youtube link
        Given command search is selected
        When user searches for an non existent youtube link
        Then  system will respond with "Nothing found."