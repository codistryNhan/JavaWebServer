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

* Client sends a HTTP request
* Parse and store the request's metadata
  * The request's metadata will tell us how to respond 
