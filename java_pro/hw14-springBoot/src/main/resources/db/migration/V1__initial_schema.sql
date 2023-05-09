create table client
(
    id   bigserial not null primary key,
    name varchar(50) not null
);

create table phone
(
    phone varchar(50),
    client_id bigint not null references client (id)
);

create table address
(
    street varchar(50),
    client_id bigint not null references client (id)
);