# JMS_MultiUserChat
Multi user chat as distributed application implemented by Java Message Service (JMS).

## Exercise 6.1
Implementation of a multi-user chat as a distributed application. The application should manage a “room” used by the clients for exchanging messages. The messages sent by a client must be received by all other clients connected to a JMS queue manager also called JMS provider. To this end adopt the publish/subscribe architectural pattern implemented by JMS and use a topic to implement the “room”. By using this paradigm and the underlying middleware, you have to implement only the clients of the JMS provider (ChatClient). Each client must work concurrently as producer (publisher in the Topic domain) and consumer (subscriber in the Topic domain) of messages. The server is implemented by the JMS Provider (ActiveMQ Artemis). You can test the application with many instances of clients (Publisher) connected to the same topic. Analyze the implementation built on top of JMS with the one implemented by using Java RMI.

## Exercise 6.2
Modify the previous exercise in order to avoid the echo of a message. Moreover, use the selectors (filters) to implement a content-based subscription to implement a subtopic. Remember that by using selectors, messages are delivered to subscribers only if the Boolean expression specified by the selector is true. The operands of that expression can be header values (the ones labelled with a circle in the slides of lecture 15) or properties of a JMS message.
