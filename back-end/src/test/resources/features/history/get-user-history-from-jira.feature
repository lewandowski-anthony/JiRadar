Feature: API - History endpoint

  Background:
    Given GET /jira/rest/api/3/myself responds with:
    """json
    {
      "accountId": "1234",
      "emailAddress": "test_account@jira.com",
      "displayName": "TEST",
      "active": true
    }
    """

  Scenario: We get paginated activity history from JIRA API
    Given POST /jira/rest/api/3/search/jql responds with:
    """json
    {
      "issues": [
        {
          "id": "3183242",
          "key": "SMSUP-1508",
          "self": "https://company.atlassian.net/rest/api/3/issue/3183242",
          "fields": {
            "summary": "[TECHNICAL] Implements Togglz to manage feature flagging on SMSUP Ecosystem",
            "created": "2026-05-01T14:07:36.506Z",
            "updated": "2026-05-30T17:46:27.639Z",
            "status": { "id": "13781", "name": "Done" },
            "project": { "id": "12345", "key": "SMSUP", "name": "Smart Supply" },
            "assignee": {
              "accountId": "9999",
              "emailAddress": "another_developer@jira.com",
              "displayName": "COLLABORATOR",
              "active": true
            },
            "issuetype": { "id": "10018", "name": "Story", "subtask": false }
          }
        }
      ],
      "startAt": 0,
      "maxResults": 50,
      "total": 1,
      "isLast": true
    }
    """
    And POST /jira/rest/api/3/changelog/bulkfetch responds with:
    """json
    {
      "issueChangeLogs": [
        {
          "issueId": "3183242",
          "changeHistories": [
            {
              "id": "38675178",
              "created": 1782830856000,
              "author": {
                "accountId": "1234",
                "emailAddress": "test_account@jira.com",
                "displayName": "TEST",
                "active": true
              },
              "items": [
                {
                  "field": "status",
                  "fromString": "In Review",
                  "toString": "Done"
                }
              ]
            }
          ]
        }
      ]
    }
    """
    When I send a GET request to "/api/v1/tracker/jira/users/me/history?projectKeys=SMSUP&startDate=2026-06-01&endDate=2026-06-30&page=0&size=20"
    Then the HTTP response status should be 200
    And the response body contains:
    """json
    [
      {
        "issue_key": "SMSUP-1508",
        "transition_type": "END_REVIEW"
      }
    ]
    """

  Scenario: We receive a 400 Bad Request when the end date is before the start date
    When I send a GET request to "/api/v1/tracker/jira/users/me/history?projectKeys=SMSUP&startDate=2026-05-10&endDate=2026-05-01&page=0&size=20"
    Then the HTTP response status should be 400