<img src="https://img.icons8.com/ios/50/000000/chrome-reader-mode.png"/>

# Library Database Project

> Project build entirely in Java to read data from csv-files into a library,
> featuring several functions to maintain the library.
> Realised for a first year [programming class at uni](http://www.drps.ed.ac.uk/19-20/dpt/cxinfr08029.htm).


## Table of Contents

- [Language&nbsp;Features](#language&nbsp;features&nbsp)
- [Limitations](#limitations)
- [Features](#features)
- [General Class Structure](#general&nbsp;class&nbsp;structure)
## Language Features
- Use of Enums for parsing (<tt>GroupCmd</tt> & <tt>RemoveCmd</tt>) enhances loose coupling and type safety.
- Use of Enums for data order (used in <tt>LibraryFileLoader</tt>) in which information appears in .csv-file enables easy code modification, should the order change or should additional book information be added in future.
- Use of TreeMap to ensure order of AUTHOR groups
- Provided JavaDoc documentation

## Limitations
- No author information added, usually that would be done in class comments
- No use of other packages than the default package -> a sufficient approach would have been to put every class which is related to one command / feature in its own package
- No use of functional programming constucts, they would make some code better readable, for instance:


```java
// Rather clumsy code

while (entryIterator.hasNext()) {
   Map.Entry<Character, ArrayList<String>> entry = entryIterator.next();

   if (entry.getValue().isEmpty()) {
       entryIterator.remove();
   }
}

// Clean code

letterMap.entrySet().removeIf(entry -> entry.getValue().isEmpty());
```

## Features

***ADD Command***
- Use this command to add books to your library.
- Type *ADD* followed by a valid path to a csv-file

***EXIT Command***
- Use this command to terminate the program

***LIST Command***
- Use this command to display your library
- Type *LIST* followed by either *short* or *long*
- *short* lists only book titles
- *long* lists all book information

***GROUP Command***
- Use this command as a variant to the LIST command
- Type *GROUP* followed by either *TITLE* or *AUTHOR*
- *TITLE* displays your library in title groups
- *AUTHOR* displays your library in author groups

***REMOVE Command***
- Use this command to remove books from the library
- Type *REMOVE* followed by *AUTHOR* or *TITLE*
- *AUTHOR* followed by the author removes all their books
- *TITLE* followed by the title removes the respective book

***HELP Command***
- Display a user manual

## General Class Structure
1. Constants and Fields
2. Constructor(s)
3. Error Checking
4. Helper methods (used by explicit methods mentioned in paper)
5. Class functionality methods (methods explicitly mentioned in paper & core functionality methods)
