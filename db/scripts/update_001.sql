 create table if not exists post (
    id serial primary key not null,
    name varchar (255),
    text text,
    link varchar (510),
    created data
 );