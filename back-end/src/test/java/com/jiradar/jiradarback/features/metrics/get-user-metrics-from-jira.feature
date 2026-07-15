Feature: API - Metrics endpoint

  Scenario: We get metrics from JIRA API
    Given GET /jira/rest/api/3/myself responds with:
    """json
    {
      "accountId": "1234",
      "emailAddress": "test_account@jira.com",
      "displayName": "TEST",
      "active": true
    }
    """
    When I send a GET request to "/api/v1/tracker/jira/users/me"
    Then the HTTP response status should be 200
    And the response body contains:
    """json
    {
      "name": "TEST",
      "email": "test_account@jira.com"
    }
    """