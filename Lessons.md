# What we learned

## Design Documents are Important

Before starting this project we created a design document detailing exactly how our distributed file system would be built. We spent a lot of time deciding what protobuf services and messages we would need and made sure our APIs were designed well. Planning ahead and ironing out implementation details before we started coding saved us a lot of time and once we actually began building things were pretty smooth sailing.

## gRPC

One of the major design decisions we made was to use gRPC for this project. As a result we became very familiar with the library and developed a better understanding of how remote procedure calls work.

## Scripting

For this project we wrote out a Makefile to build our projetc and a shell script to run it. We learned best practices and tried to utilize them to create a great CLI experience for our client. 

## Logging and Error handling

Because our project was a distributed system error handling was a little tricky. We found that instead of just printing out errors and exceptions, properly handling them and having thorough logging made debugging a lot simpler.
