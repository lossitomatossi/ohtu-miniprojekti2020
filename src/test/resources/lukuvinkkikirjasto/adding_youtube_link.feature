Feature: As a user I can add a new youtube to the list

  Scenario: user can add a new youtube by giving url and title
    Given command youtube is selected
    When  user enters youtube url "youtube-url" and title "Video"
    Then  system will respond with "youtube-url                               Video                                     2020-12-02"

  Scenario: user can not add a new youtube without giving url
    Given command youtube is selected
    When  user leaves youtube url blank
    Then  system will respond with "URL cannot be blank."
