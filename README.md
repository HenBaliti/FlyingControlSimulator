# Flight Simulator Control

![Preview Project](https://github.com/HenBaliti/FlyingControlSimulator/blob/master/Project/uml/preview.png)

[Link to the youtube presentation of the Project](https://www.youtube.com/watch?v=3nHJjTGSL0k&ab_channel=AmitShabo)

This is a project we developed during our advanced software programming course in our 2nd year. This project helped us to gain a higher level of knowledge in programming, with emphasis on design patterns and programming principles such as SOLID and GRASP, and finally developing our own JavaFX desktop application.

## Server

In this section we wrote a general server, which can be used over and over again in various projects. In order for the server to be re-usable, there must be a seperation between the server's functionality and the rest of the code.

Therefore, we defined the functionality of the server as an interface, and each project can have different classes that will implement the same functionality in different ways. Thus, the Open / Close principle has been applied.

Now the **Server** interface has a quite simple functionality:

- A method that receives a port for listening and its function will be to open the server and wait for clients.
- A method to close the server.
For this project we will use a class called MySerialServer that will be a type of Server.

### ClientHandler

Imagine a situation in which the MySerialServer class would also define the client-server call protocol. In different projects, there might be different conversations in different formats and with different expectations between the client and the server. Therefore, we won't be able to use this class in other projects.

To solve that issue, we had to separate the server mechanism implemented in MySerialServer from different forms of conversation with possible clients. For that reason we created an interface called ClientHandler to determine the type of call with the client and its handling. Now MySerialServer class can inject any desired implementation for ClientHandler.

For example, for every implementation of a Server we can inject a call of inversion of strings or solving equations. In the same way, if one day we would like to implement additional protocols then we will only need to add the implementation of ClientHandler without changing or copying again the code of the various implementations to the Server.

In this method, we maintained both the *Single Responsibility* and *Open / Close* principles.

## Caching

The project also has a caching system, for it might take a lot of time to calculate some solutions. It would be redundant to calculate a solution for a problem that we already solved. Instead, we can save solutions that were already calculated in an external file, or a database. Upon receiving a new problem, we will first check the cache to see if we have already solved it. If so, we will extract the solution from the disk instead of calculating it.

We created the CacheManager interface to manage the cache for us, with the following functionalities:

- Checks whether the solution already exists in the database.
- Extracts the data from the database (If a solution already exists).
- Saves the solution for the problem.
