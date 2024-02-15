Feature: Thomann User Experience

  Background: User is logged in
    Given I am on the Thomann homepage
    And I log in with valid credentials

  Scenario Outline: Reachability of product categories
    When I click on the "<category-name>" category
    Then I should be taken to the "<category-name>" category page
    And the category should show at least <num-products> products
    When I click on the first product in the results
    Then I should be taken to the details page for that product

    Examples:
      | category-name      | num-products |
      | Cables | 5            |
      | Drums | 5            |
      | Keys | 5            |
      | Studio | 5            |
      | Microphones | 5            |

  Scenario: Search functionality
    When I search for a product using the term "Microphone"
    Then I should see the search results
    And there should be at least 5 products in the search results
    When I click on the first product in the results
    Then I should be taken to the details page for that product
