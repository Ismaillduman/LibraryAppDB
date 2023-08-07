
Feature: Books module
  As a students, I should be able to borrow book
  @wip @borrow @db @ui
  Scenario: Student borrow new book
    Given the "student" on the home page
    And the user navigates to "Books" page
    And the user searches for "Son Ada" book
    When the user clicks Borrow "Son Ada" Book
    Then verify that "Son Ada" book is shown in "Borrowing Books" page
    And  verify logged student has same book in database