create table customer (
    id serial not null,
    identification varchar(10) not null unique,
    name varchar (10) not null,
    born_date varchar(10) not null,
    primary key(id)
);
