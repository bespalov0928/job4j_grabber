 create table if not exists rabbit (
    id serial primary key not null,
    name text ,
    text text,
    link text UNIQUE,
    created date
 );