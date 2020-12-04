Feature: As a user I can search books by title and writer

    Scenario: user can not search book from wrong category
        Given command search is selected
        When  user enters category "kirja" which do not exists
        Then  system will respond with "No such category."
        
    Scenario: user can search book by title or writer
        Given command search is selected
        When  user enters category "book" and searchTerm "Refactoring"
        Then  system will respond with "Title                                     Author                Year   Pages   ISBN" 
