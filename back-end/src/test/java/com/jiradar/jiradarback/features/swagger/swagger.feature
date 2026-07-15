Feature: API - Global Endpoints Verification

  Scenario: The Swagger/OpenAPI documentation is properly exposed
    When I send a GET request to "/v3/api-docs"
    Then the HTTP response status should be 200
    And the response body should contain "openapi"