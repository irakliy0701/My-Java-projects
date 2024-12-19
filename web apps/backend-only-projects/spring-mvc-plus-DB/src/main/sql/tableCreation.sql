create table Person(
    id serial primary key,
    phio varchar(100) UNIQUE not null,
    birth_year int check (birth_year > 0)
);

create table Book(
    id serial primary key,
    book_name varchar(100) not null,
    author varchar(100) not null ,
    release_year int check ( release_year > 0),
    person_id int,
    foreign key (person_id) references Person(id) on delete set null
)
