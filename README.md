

# Lottery

Lottery REST API designed to create, amend and check lottery tickets

## Info

This project was created in Eclipse with Apache Tomcat used as the server.
Postman was used to test sending the requests to the API. Below are examples 
of each of the endpoint in the API. It includes a request and response from each.


## Usage
POST ```/Lottery/createTicket```
Creates a new ticket
### Request 

```
http://localhost:8080/Lottery/createTicket
```

### Response
```
New ticket created with id 1
```
---
GET ```/Lottery/ticket/{id}```
Gets a ticket with specified ID
### Request
```
http://localhost:8080/Lottery/ticket/1
```

### Response
```
Ticket id 1 contains 3 lines
```
---
GET ```/Lottery/tickets```
Returns a list of tickets available 
### Request 
```
http://localhost:8080/Lottery/ticket/listTickets
```

### Response
```
Ticket ID's: 1, 2, 3
```
---
PUT ```/Lottery/amendTicket/{id}```
Adds new lines to specified ticket
### Request

```
http://localhost:8080/Lottery/amendTicket/1?numOfLines=3
```

### Response
```
3 lines added to Ticket id: 1
```
---
PUT ```/Lottery/status/{id}```
### Request

```
http://localhost:8080/Lottery/status/1
```

### Response
```
Unsorted: {Line 3=5, Line 2=5, Line 1=0} Sorted: {Line 3=5, Line 2=5, Line 1=0}
```
## Tests
Unit tests can be found in com.lottery.tests .
The tests cover each request as well as clearing the list of tickets.