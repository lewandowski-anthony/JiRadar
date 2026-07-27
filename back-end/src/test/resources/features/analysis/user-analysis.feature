Feature: API - Developer AI Analysis Endpoint

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

  Scenario: We request developer AI analysis with valid parameters
    Given POST /jira/rest/api/3/search/jql responds with:
    """json
    {
      "issues": [
        {
          "id": "3183242",
          "key": "SMSUP-1508",
          "self": "https://company.atlassian.net/rest/api/3/issue/3183242",
          "fields": {
            "summary": "Fix authentication bug",
            "created": "2026-06-01T14:07:36.506+02:00",
            "updated": "2026-06-30T17:46:27.639+02:00",
            "status": { "id": "13781", "name": "Done" },
            "project": { "id": "12345", "key": "SMSUP", "name": "Smart Supply" },
            "assignee": {
              "accountId": "1234",
              "emailAddress": "test_account@jira.com",
              "displayName": "TEST",
              "active": true
            },
            "issuetype": { "id": "10018", "name": "Bug", "subtask": false }
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
          "changeHistories": []
        }
      ]
    }
    """
    When I send a GET request to "/api/v1/tracker/jira/users/me/analysis?projectKeys=SMSUP&historyGranularity=YEAR"
    Then the HTTP response status should be 200
    And the response body contains:
    """json
    {
      "profile_summary": "Solid developer performance.",
      "assigned_title": "BUG_FIXER"
    }
    """

  Scenario: We receive a 400 Bad Request when requesting AI analysis with unsupported granularity
    Given POST /jira/rest/api/3/search/jql responds with:
    """json
    {
      "issues": [],
      "startAt": 0,
      "maxResults": 50,
      "total": 0,
      "isLast": true
    }
    """
    When I send a GET request to "/api/v1/tracker/jira/users/me/analysis?projectKeys=SMSUP&historyGranularity=DAY"
    Then the HTTP response status should be 400