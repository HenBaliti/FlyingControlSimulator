# Flight Simulator Control

![Preview Project](https://github.com/HenBaliti/FlyingControlSimulator/blob/master/Project/uml/preview.png)

[Link to the youtube presentation of the Project](https://www.youtube.com/watch?v=3nHJjTGSL0k&ab_channel=AmitShabo)

This is a project we developed during our advanced software programming course in our 2nd year. This project helped us to gain a higher level of knowledge in programming, with emphasis on design patterns and programming principles such as SOLID and GRASP, and finally developing our own JavaFX desktop application.

# Server

In this section we wrote a general server, which can be used over and over again in various projects. In order for the server to be re-usable, there must be a seperation between the server's functionality and the rest of the code.

Therefore, we defined the functionality of the server as an interface, and each project can have different classes that will implement the same functionality in different ways. Thus, the Open / Close principle has been applied.

Now the **Server** interface has a quite simple functionality:

- A method that receives a port for listening and its function will be to open the server and wait for clients.
- A method to close the server.
For this project we will use a class called MySerialServer that will be a type of Server.
