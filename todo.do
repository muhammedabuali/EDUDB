1 - delete database folder.
2- run Main
3- create:
    create table persons ( number integer, age integer, birth integer, married integer)
4- insert:
    insert into persons values ( 1, 22, 1991, 0)
    insert into persons values ( 2, 32, 1981, 1)
    insert into persons values ( 3, 42, 1971, 1)
5- select:git s
    select * from persons
    select number, birth from persons where age > 30 and number > 2 or ( married = 0)
6- uml eclipse
*******************************************************************
7- create table places ( id integer, address integer)
8- insert into places values ( 1, 12)
    insert into places values ( 2, 22)
    insert into places values ( 3, 32)
9 - select * from persons, places
    select number, age, id, address from persons, places
    select number, birth from persons, places where married = 1
    select number, birth, address from persons, places where number = id

###################################################################

10- update persons set age = 40 where married = 0
    select * from persons
