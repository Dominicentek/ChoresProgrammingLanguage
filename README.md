# The Chores programming language
Chores is an esoteric programming language  created for the Truttle1 esojam,
with theme: "everyday life". In this esolang, you constantly have to do
chores (because your parents force you to).
### Instructions
* `move <direction> <steps>` - Go around the house. Directions can be `up`, `left`, `down` or `right`
* `dishes` - Do the dishes
* `hand <item>` - Grabs a nearby item, if you already have the item, drop the item
* `vacuum` - Turns on/off the vacuum cleaner if you're near it
* `emptytrash` - If you hold the trash bin near the garbage bin, you can empty it.
* `talk <message>` - Speak to the console, but you have to be in the talking room
* `read <varname>` - Read a book (stdin). The book is on your desk.
* `bed` - Go in/out the bed if you're near it. Your parents will yell at you for laziness if you don't get out of the bed in 16 instructions.
* `remember <varname> <value>` - Declare a variable. It can either be a number or a string. Your brain has a limited capacity, however.
* `forget <varname>` - Forget (delete) a variable. That space will be freed.
* `add <varname> <value>` - Do your math homework. You have to be near your desk.
* `subtract <varname> <value>` - Do your math homework. You have to be near your desk.
* `multiply <varname> <value>` - Do your math homework. You have to be near your desk.
* `divide <varname> <value>` - Do your math homework. You have to be near your desk.
* `tonum <varname>` - Converts a variable to a number type.
* `tochar <varname>` - Converts a variable to a string type. The number is used as an ASCII value.
* `if <varname> <value>` - Compare 2 variables. If the values are not equal, the next instruction is skipped. This can be done in only a bed.
* `goto <value>` - Goes to a line of code. This can be done in only a bed.
* `noop` - Do nothing
### Items
There are 2 items you can pick up around the house.
* `vacuumcleaner` - A vacuum cleaner
* `trashcan` - A trash can
### Chores
#### Vacuuming
When you start vacuuming, dust particles will spawn in specific spots. You will suck
them in if you have your vacuum turned on, and you hold it.
#### Doing the dishes
You have to be near the sink and call the `dishes` instruction.
#### Taking out the garbage
You have to go near the trash bin, pick it up, and empty it near the garbage bin outside.
#### Doing your math homework
You have to perform a math instruction successfully
#### Touching some grass
You just have to go outside and breathe the fresh air instead of sitting at the computer all day.
#### How to do them
Your parents yell at you in the console if you have to do chores. You have 8 instructions to initiate them otherwise
they ground you (the program fails and quits).

After a chore is done, you have 16 instructions before having to do another chore.
### Brain capacity
Each variable takes up some space in your brain. Your brain has **256 bytes** of capacity.
Variable names don't take up anything, however their values do. A number takes up 4 bytes,
since it is 32-bit. A string takes up the same amount of bytes as it has characters. That
means 1 character = 1 byte.
### House layout
The image below shows the layout of the house.<br>
![House Layout](https://cdn.discordapp.com/attachments/719446728402796657/1059429840656281661/image.png)
### Program Properties
There are a few properties that tell the interpreter what to do. These properties are
marked with `@` following by the property name, then the value. For example: `@property 0`

These properties are on a standalone line of code.

Here is the list of valid properties:
* `msg <true|false>` - Default: `false`; Receive messages from parents to stdout
* `randseed <seed>` - Default: `69420`; Set the seed for the RNG that generates what chores you have to do
* `vacuum <true|false>` - Default: `true`; Disable/enable the vacuuming chore
* `homework <true|false>` - Default: `true`; Disable/enable the doing homework chore
* `dishes <true|false>` - Default: `true`; Disable/enable the doing dishes chore
* `outside <true|false>` - Default: `true`; Disable/enable the going outside chore
* `garbage <true|false>` - Default: `true`; Disable/enable the taking out garbage chore
* `instrlimit <number>` - Default: `16`; Limit for the instructions where you have no chores, `0` to disable
* `bedlimit <number>` - Default: `16`; Limit for the instructions to be in bed, `0` to disable
* `chorelimit <number>` - Default:  `8`; Limit for the instructions before your parents ground you after a chore is assigned, `0` to disable
* `memsize <number>` - Default: `256`; Size of your memory for storing variables, `0` for infinite
* `restrict <true|false>` - Default: `true`; Restrict instructions to specific spots (near objects)
* `wallhacks <true|false>` - Default: `false`; Enables wall hacks
### Comments
There are also comments, beginning with `#`
### Example program
#### Hello World
```
move left 6
move down 3
talk "Hello, World!"
```
#### truth-machine
```
@restrict false
@instrlimit 0

read x
if x "0"
goto 9
talk 1
goto 7
talk 0 
```
#### Calculator
```
@restrict false
@instrlimit 0

talk "Calculator!1\n"
talk "X = "
read x
tonum x
talk "Y = "
read y
tonum y
talk "(1) +\n(2) -\n(3) *\n(4) /\n"
read op

if op "1"
goto 26
if op "2"
goto 28
if op "3"
goto 30
if op "4"
goto 32
talk "Invalid operation"
goto 12

add x y
goto 34
subtract x y
goto 34
multiply x y
goto 34
divide x y

talk "\nResult: "
talk x
talk "\n\n"

goto 6
```
## Build Instructions
Just run `./gradlew build` and the JAR will be located at `build/libs/ChoresProgrammingLanguage.jar`