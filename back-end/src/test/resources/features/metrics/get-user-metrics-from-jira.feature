Feature: API - User endpoints

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

  Scenario: I get myself from JIRA API
    When I send a GET request to "/api/v1/tracker/jira/users/me"
    Then the HTTP response status should be 200
    And the response body contains:
    """json
    {
      "name": "TEST",
      "email": "test_account@jira.com"
    }
    """

  Scenario: We get metrics from JIRA API
    Given POST /jira/rest/api/3/search/jql responds with:
    """json
    {
      "issues": [
        {
          "id": "3183242",
          "key": "SMSUP-1508",
          "self": "https://decathlon.atlassian.net/rest/api/3/issue/3183242",
          "fields": {
            "summary": "[TECHNICAL] Implements Togglz to manage feature flagging on SMSUP Ecosystem",
            "created": "2026-06-01T14:07:36.506+02:00",
            "updated": "2026-06-30T17:46:27.639+02:00",
            "status": {
              "id": "13781",
              "name": "Done",
              "iconUrl": "https://decathlon.atlassian.net/images/icons/statuses/generic.png"
            },
            "project": {
              "id": "12345",
              "key": "SMSUP",
              "name": "Smart Supply"
            },
            "assignee": {
              "accountId": "1234",
              "emailAddress": "test_account@jira.com",
              "displayName": "TEST",
              "active": true
            },
            "issuetype": {
              "id": "10018",
              "name": "Story",
              "subtask": false
            }
          }
        }
      ],
      "startAt": 0,
      "maxResults": 50,
      "total": 1,
      "nextPageToken": "token-xyz",
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
              "id": "38675177",
              "created": 1782485256000,
              "author": {
                "accountId": "1234",
                "emailAddress": "test_account@jira.com",
                "displayName": "TEST",
                "active": true
              },
              "items": [
                {
                  "field": "status",
                  "fromString": "Open",
                  "toString": "In Progress"
                }
              ]
            },
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
                  "fromString": "In Progress",
                  "toString": "Done"
                }
              ]
            }
          ]
        }
      ]
    }
    """
    When I send a GET request to "/api/v1/tracker/jira/users/me/metrics?projectKeys=SMSUP&startDate=2026-06-01&endDate=2026-06-30"
    Then the HTTP response status should be 200
    And the response body contains:
    """json
    {
      "number_of_issue_started": 1,
      "number_of_issue_done": 1,
      "average_cycle_time": "4d 0h 0m",
      "delivery_success_rate": 100.0
    }
    """

  Scenario: We get metrics from JIRA API segmented by weekly granularity
    Given POST /jira/rest/api/3/search/jql responds with:
    """json
    {
      "issues": [
        {
          "id": "3183242",
          "key": "SMSUP-1508",
          "self": "https://decathlon.atlassian.net/rest/api/3/issue/3183242",
          "fields": {
            "summary": "[TECHNICAL] Implements Togglz",
            "created": "2026-05-05T10:00:00.000Z",
            "updated": "2026-05-07T17:00:00.000Z",
            "status": { "id": "13781", "name": "Done" },
            "project": { "id": "12345", "key": "SMSUP" },
            "assignee": {
              "accountId": "1234",
              "emailAddress": "test_account@jira.com",
              "displayName": "TEST",
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
              "id": "38675177",
              "created": 1783332000000,
              "author": { "accountId": "1234", "emailAddress": "test_account@jira.com", "active": true },
              "items": [ { "field": "status", "fromString": "Open", "toString": "In Progress" } ]
            },
            {
              "id": "38675178",
              "created": 1783677600000,
              "author": { "accountId": "1234", "emailAddress": "test_account@jira.com", "active": true },
              "items": [ { "field": "status", "fromString": "In Progress", "toString": "Done" } ]
            }
          ]
        }
      ]
    }
    """
    When I send a GET request to "/api/v1/tracker/jira/users/me/metrics?projectKeys=SMSUP&startDate=2026-05-05&endDate=2026-05-07&historyGranularity=WEEK"
    Then the HTTP response status should be 200
    And the response body contains:
    """json
    {
      "user_metrics_by_granularity": [
        {
          "label": "Sem. 19 - 2026"
        }
      ]
    }
    """

  Scenario: We get metrics with unassigned tickets and team review activities
    Given POST /jira/rest/api/3/search/jql responds with:
    """json
    {
      "issues": [
        {
          "id": "9999",
          "key": "SMSUP-999",
          "self": "https://decathlon.atlassian.net/rest/api/3/issue/9999",
          "fields": {
            "summary": "Unassigned Ticket",
            "created": "2026-05-01T14:00:00.000Z",
            "updated": "2026-05-25T17:00:00.000Z",
            "status": { "id": "13781", "name": "Done" },
            "project": { "id": "12345", "key": "SMSUP" },
            "assignee": null,
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
          "issueId": "9999",
          "changeHistories": [
            {
              "id": "38675199",
              "created": 1782485256000,
              "author": { "accountId": "5678", "emailAddress": "reviewer@jira.com", "active": true },
              "items": [
                {
                  "field": "status",
                  "fromString": "In Progress",
                  "toString": "In Review"
                }
              ]
            }
          ]
        }
      ]
    }
    """
    When I send a GET request to "/api/v1/tracker/jira/users/me/metrics?projectKeys=SMSUP&startDate=2026-05-01&endDate=2026-05-25"
    Then the HTTP response status should be 200
    And the response body contains:
    """json
    {
      "number_of_issue_started": 0,
      "number_of_issue_done": 0,
      "average_cycle_time": "0m"
    }
    """

  Scenario: We get metrics with a short cycle time under 24 hours
    Given POST /jira/rest/api/3/search/jql responds with:
    """json
    {
      "issues": [
        {
          "id": "7777",
          "key": "SMSUP-777",
          "self": "https://decathlon.atlassian.net/rest/api/3/issue/7777",
          "fields": {
            "summary": "Quick Task",
            "created": "2026-05-04T00:00:00.000Z",
            "updated": "2026-05-05T23:59:59.000Z",
            "status": { "id": "13781", "name": "Done" },
            "project": { "id": "12345", "key": "SMSUP" },
            "assignee": {
              "accountId": "1234",
              "emailAddress": "test_account@jira.com",
              "displayName": "TEST",
              "active": true
            },
            "issuetype": { "id": "10018", "name": "Task", "subtask": false }
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
          "changeHistories": [
            {
              "id": "38675200",
              "created": 1780644000000,
              "author": { "accountId": "1234", "emailAddress": "test_account@jira.com", "active": true },
              "items": [ { "field": "status", "fromString": "Open", "toString": "In Progress" } ]
            },
            {
              "id": "38675201",
              "created": 1780663800000,
              "author": { "accountId": "1234", "emailAddress": "test_account@jira.com", "active": true },
              "items": [ { "field": "status", "fromString": "In Progress", "toString": "Done" } ]
            }
          ]
        }
      ]
    }
    """
    When I send a GET request to "/api/v1/tracker/jira/users/me/metrics?projectKeys=SMSUP&startDate=2026-06-04&endDate=2026-06-06"
    Then the HTTP response status should be 200
    And the response body contains:
    """json
    {
      "average_cycle_time": "5h 30m"
    }
    """

  Scenario: We get metrics with active peer review transitions to cover changelog methods
    Given POST /jira/rest/api/3/search/jql responds with:
    """json
    {
      "issues": [
        {
          "id": "8888",
          "key": "SMSUP-888",
          "self": "https://decathlon.atlassian.net/rest/api/3/issue/8888",
          "fields": {
            "summary": "Feature with Peer Review",
            "created": "2026-05-01T00:00:00.000Z",
            "updated": "2026-05-25T23:59:59.000Z",
            "status": { "id": "13781", "name": "Done" },
            "project": { "id": "12345", "key": "SMSUP" },
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
          "issueId": "8888",
          "changeHistories": [
            {
              "id": "38675301",
              "created": 1783000000000,
              "author": { "accountId": "9999", "emailAddress": "another_developer@jira.com", "active": true },
              "items": [ { "field": "status", "fromString": "In Progress", "toString": "In Review" } ]
            },
            {
              "id": "38675302",
              "created": 1783010000000,
              "author": { "accountId": "1234", "emailAddress": "test_account@jira.com", "active": true },
              "items": [ { "field": "status", "fromString": "In Review", "toString": "Done" } ]
            }
          ]
        }
      ]
    }
    """
    When I send a GET request to "/api/v1/tracker/jira/users/me/metrics?projectKeys=SMSUP&startDate=2026-07-01&endDate=2026-07-25"
    Then the HTTP response status should be 200
    And the response body contains:
    """json
    {
      "number_of_review_done": 1
    }
    """