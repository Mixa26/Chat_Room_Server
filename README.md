# Chat_Room_Server
A chat server where multiple clients can communicate.

There is a chat history on the server which saves the last 100 messages, when new clients join they have to make unique usernames.
There is also a simple censored word implementation which can be expanded. If a word is detected to be censored it is chained like
(parolaccia => p********a).

To use this chat, start the server first, then start multiple instances of client. To use the client type in the username as prompted
(it must be unique). After that you can chat with other clients. To quit the chat type in "~exit".  Know that someone can now use your
username because it's freed up.

The full homework specification can be found in the Web Domaći 4 file.
