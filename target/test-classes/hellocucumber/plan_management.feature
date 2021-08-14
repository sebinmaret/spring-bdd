Feature: Management of Plans and Plan Rules.

  Scenario: Get List of Plan Rules
    When user sends GET request to the api "/api/v1/plan-management/rule"
    Then list of plan rules are returned

  Scenario: Get List of Plans
    When user sends GET request to the api "/api/v1/plan-management/plan"
    Then list of plans are returned

  Scenario: Get Plan By ID
    When user sends GET request to "/api/v1/plan-management/plan/610c8ff9fb65132555ac9927"
    Then a plan is returned
    And the returned plan has the name "2000 min for 2 Days"

  Scenario: Get Plan Rule By ID
    When user sends GET request to "/api/v1/plan-management/rule/610c8db3fb65132555ac9920"
    Then a plan rule is returned
    And the returned plan rule has the name "2GB per Day"

  Scenario: Get List of Plan Rules By Service type
    When user sends GET request to the api "/api/v1/plan-management/rule/service/CALL"
    Then list of plan rules are returned
    And list of plan rules contains rules for "CALL" service

  Scenario: Get List of Plan Rules By Service type and Validity
    When user sends GET request to the api "/api/v1/plan-management/rule/service/CALL/validity/1"
    Then list of plan rules are returned
    And list of plan rules contains rules for "CALL" service
    And list of plan rules contains rules with validty greater than or equal to 1

  Scenario: Create a plan rule
    When user selects create rule
    And user enters rule name as "5GB per Day" 
    And user enters rule allowance as 5
    And user enters rule service type as "DATA"
    And user enters rule validity as 1
    And user enters rule cost as 60
    And user sends POST request to "/api/v1/plan-management/rule"
    Then OK response is obtained
  
  Scenario: Create a Plan
    When user sends GET request to "/api/v1/plan-management/rule/610c8db3fb65132555ac9920"
    And a plan rule is returned and added to a new plan
    And user enters plan name as "2GB per Day Plan for 56 days"
    And user enters plan description as "2GB per Day Plan 56 days"
    And user enters plan price as 299
    And user enters plan validity as 56
    And user sends POST request to "/api/v1/plan-management/plan"
    Then OK response is obtained
    