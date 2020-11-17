 create table if not exists post (
    id serial primary key not null,
    name text UNIQUE,
    text text,
    link text,
    created data
 );