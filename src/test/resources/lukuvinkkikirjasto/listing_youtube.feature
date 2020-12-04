Feature: As a user I can list youtube links

    Scenario: user lists youtube links when they exist
        Given command youtube is selected
        When user adds youtube url "www.youtube.com/scam" and title "amazing jump" and user lists category "youtube"
        Then  system will respond with "amazing"

    Scenario: user lists youtube links when there are none
        Given command list is selected
        When user lists youtube
        Then  system will respond with "Nothing found."
