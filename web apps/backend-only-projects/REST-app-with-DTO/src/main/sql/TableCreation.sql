create table PersonDTO(
      id int primary key generated by default as identity,
      name varchar(100) not null,
      age int,
      email varchar(100),
      created_at timestamp,
      updated_at timestamp,
      created_who varchar
);
