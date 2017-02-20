/*
  Auth: Thomas T Nguyen
  CS122B Winter 2017 Group 42 
  Project Database Script
*/

CREATE TABLE movies(id INTEGER AUTO_INCREMENT NOT NULL, title VARCHAR(100) NOT NULL, year INTEGER NOT NULL,
  director VARCHAR(100) NOT NULL, banner_url VARCHAR(200) DEFAULT '', trailer_url VARCHAR(200) DEFAULT '',
  PRIMARY KEY (id));

CREATE TABLE stars(id INTEGER AUTO_INCREMENT NOT NULL, first_name VARCHAR(50) NOT NULL, last_name VARCHAR(50) NOT NULL,
  dob DATE, photo_url VARCHAR(200) DEFAULT '',
  PRIMARY KEY (id));

CREATE TABLE stars_in_movies(star_id INTEGER NOT NULL, movie_id INTEGER NOT NULL,
  FOREIGN KEY (star_id) REFERENCES stars(id), FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE); /* Primary key might not be necessary. */

CREATE TABLE genres(id INTEGER AUTO_INCREMENT NOT NULL, name VARCHAR(32) NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE genres_in_movies(genre_id INTEGER NOT NULL, movie_id INTEGER NOT NULL,
  FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE, FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE);

/* Credit card table required before customers. */

CREATE TABLE creditcards (id VARCHAR(20) NOT NULL, first_name VARCHAR(50) NOT NULL, last_name VARCHAR(50) NOT NULL, expiration DATE NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE customers(id INTEGER AUTO_INCREMENT NOT NULL, first_name VARCHAR(50) NOT NULL, last_name VARCHAR(50) NOT NULL,
  cc_id VARCHAR(20) NOT NULL, address VARCHAR(200) NOT NULL, email VARCHAR(50) NOT NULL, `password` VARCHAR(20) NOT NULL,
  PRIMARY KEY (id), FOREIGN KEY (cc_id) REFERENCES creditcards(id) ON DELETE CASCADE);

CREATE TABLE sales(id INTEGER AUTO_INCREMENT NOT NULL, customer_id INTEGER NOT NULL, movie_id INTEGER NOT NULL, sale_date DATE NOT NULL,
  PRIMARY KEY (id), FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE, FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE);


create table employees (
email varchar(50),
`password` varchar(20) not null,
fullname varchar(100),
primary key(email)
);

insert into employees values ('classta@email.edu', 'classta', 'TA CS122B');
insert into employees values ('a', 'a', 'a a');	-- test