# MadLevel4Task2

Level 4 Questions

A singleton pattern is used in the class that defines the database. What is the purpose of this pattern?

Its purpose is to use only one instance of an certain resource, so that you don&#39;t access multiple instances in the application thereby ensuring thread safety.

Why should you load the data in a background thread?

By allowing queries to be performed on the main thread we could face serious performance issues to prevent this from happing we run data on a background thread. Example would be that if you write a query to a database and if query takes up a lot of time then the user interface would freeze until the query is finished. So it would be better to run these processes on the background thread.

A coroutine is a light weight thread.

What are the three major components of ROOM and what are their responsibilities?

Database -\&gt; It represents the database, it is an object that holds a connection to the database and all the operations are executed through it.

Entity -\&gt; Represents a table within the room database

DAO -\&gt; Interface that contains the method to access the database.

How can you extract the current database so that you can see the table, columns, and data?

Go to the view tab and click on device file explorer. Here you should go to the project folder and database folder, there you will find 3 files. Save these files on a convenient place and open them with the database browser.
