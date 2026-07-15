Feature: API - Get Issue Tracker Endpoints

  Scenario: We successfully retrieve an existing issue from JIRA
    Given GET /jira/rest/api/3/issue/SMSUP-123?expand=CHANGELOG responds with:
    """json
    {
      "id": "7777",
      "key": "SMSUP-123",
      "fields": {
        "summary": "Fix login authentications",
        "project": { "key": "SMSUP" },
        "issuetype": { "name": "Bug", "subtask": false }
      }
    }
    """
    And POST /jira/rest/api/3/search/jql responds with:
    """json
    {
      "issues": [
        {
          "id": "7777",
          "key": "SMSUP-123",
          "fields": {
            "summary": "Fix login authentications",
            "project": { "key": "SMSUP" },
            "issuetype": { "name": "Bug", "subtask": false }
          }
        }
      ],
      "startAt": 0,
      "maxResults": 100,
      "total": 1,
      "isLast": true
    }
    """
    And POST /jira/rest/api/3/changelog/bulkfetch responds with:
    """json
    {
      "issueChangeLogs": [
        {
          "issueId": "7777",
          "changeHistories": []
        }
      ]
    }
    """
    When I send a GET request to "/api/v1/tracker/jira/issues/SMSUP-123?issueTracker=jira"
    Then the HTTP response status should be 200
    And the response body contains:
    """json
    {
      "key": "SMSUP-123",
      "summary": "Fix login authentications",
      "type": "Bug"
    }
    """

  Scenario: We receive a 404 error when the issue does not exist in JIRA
    Given GET /jira/rest/api/3/issue/SMSUP-NOTFOUND?expand=CHANGELOG responds with status 404
    When I send a GET request to "/api/v1/tracker/jira/issues/SMSUP-NOTFOUND?issueTracker=jira"
    Then the HTTP response status should be 404