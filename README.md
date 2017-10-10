# CSC 667 Class Web Server

Contributors: Nguyen Nhan, Norald Alejo  
github HTTPS: https://github.com/sfsu-csc-667-fall-2017/web-server-nguyen-nhan-alejo-norald.git

## How a Web Server Works

1. Set up connection -- accept a client connection
2. Receive request -- read an HTTP request message
3. Process request -- interpret the request
4. Access resource  -- access the resource specified in message
5. Construct response -- create HTTP response message with right headers
6. Send response  -- send response back to client
7. Log transaction 

## 1. Set Up Connection

  * Use ServerSocket class to listen for a client's request

```java
int PORT = 3000;

ServerSocket server = new ServerSocket(PORT);
Socket client = null;

client = socket.accept();
```

## 2. Receive Request

  * Receive the request
  * Process the request

## 3. Process Request

  * Parse request message
  * Store the metadata
  * Access resource depending on what the message says
  
### HTTP Request Sturcture
There are 3 parts to a HTTP Request Message

1. Request Line
2. Headers
3. Body

```
VERB URI HTTP-VERSION  <-- Request Line
HEADERS <-- Headers
        <-- Blank Line, denotes end of headers
BODY    <-- Body
```
Ex.
```
GET /index.html HTTP/1.1  <-- Request Line
Host: www.google.com      <-- Headers
                          <-- Blank line
                          <-- No body content, GET requests rarely come with body content
```
#### Request Line

The request line is the first line on the HTTP Request message.
The request line tells the server what to do.
It contains a VERB or METHOD, the URI of the resource, and the HTTP Version.

```
VERB    URI     HTTP-VERSION
-----------------------------   
GET /index.html HTTP/1.1
// This request line tells the server to GET the resouce at /index.html using HTTP/1.1
// Notice each field is seperated by a whitespace.
```

#### Verbs
1. GET -- Get a document from the server
2. HEAD -- Get headers for a document from the server
3. POST -- Send data to the server for processing *Requires Body Content*
4. PUT  -- Store the body of the request on the server *Requires Body Content*
5. DELETE  -- Delete a document on the server

### 4. Access Resource

  * Examine request message
  * Execute on resources base on what request message asks

### 5. Construct Response

#### Response Message Format
1. Response Line
2. Headers
3. Body

#### Response Line
HTTP-Version Status-Code Response-Phrase
```
Http-Version Status-Code Respose-Phrase
----------------------------------------
HTTP/1.1 200 OK
```

#### Status Codes
##### 100 - 199 Informational
##### 200 - 299 Success
* 200 - Success
##### 300 - 399 Resource Moved
##### 400 - 499 Client Error
* 401 - Unauthorized
* 404 - Not Found, resource not found for requested URL
##### 500 - 599 Server Error

### 6. Send Response
### 7. Log
