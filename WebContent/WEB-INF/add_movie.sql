

-- Change DELIMITER to $$ 
DELIMITER $$ 

CREATE PROCEDURE add_movie 
(
   movie_id integer,
   title varchar(100) ,
   year integer ,
   director varchar(100) ,
   banner_url varchar(200), 
   trailer_url varchar(200),
   star_id integer,
   star_first_name varchar(50) ,
   star_last_name varchar(50) ,
   star_dob date ,
   star_photo_url varchar(200) ,
   genre_id integer,
   genre_name varchar(32)
) 
BEGIN 

   IF  NOT EXISTS(SELECT (1) FROM movies WHERE movies.title = title) THEN
	  INSERT INTO movies (id, title, year, director, banner_url, trailer_url) 
      values (movie_id, title, year, director, banner_url, trailer_url);
   ELSE
      update movies
      set id = movie_id, year = year, director = director, banner_url = banner_url, trailer_url = trailer_url
      where movie.title = movie_title;
	END IF;
    
   -- create star if not exist
   IF star_last_name is not null THEN
	  IF NOT EXISTS(SELECT (1) FROM stars WHERE stars.last_name = star_last_name and stars.first_name = star_first_name) THEN
         INSERT INTO stars (id, first_name, last_name, dob, photo_url) 
         VALUES (star_id, star_first_name, star_last_name, star_dob, star_photo_url);
            -- link star and movie
		 INSERT INTO stars_in_movies values (star_id, movie_id);
	  ELSE
		 SET star_id = (SELECT id from stars where stars.last_name = star_last_name and stars.first_name = star_first_name);
         -- link star and movie
		 INSERT INTO stars_in_movies values (star_id, movie_id);
	  END IF;
   END IF;

   
   -- create genre if not exist
   IF genre_name is not null THEN
	  IF NOT EXISTS(SELECT (1) FROM genres WHERE genres.name = genre_name) THEN
         INSERT INTO genres (id, name) values (genre_id, genre_name);
         -- link genre and movie
		 INSERT INTO genres_in_movies values (genre_id, movie_id);
	  else
		 SET genre_id = (SELECT id FROM genres WHERE genres.name = genre_name);
		 -- link genre and movie
		 INSERT INTO genres_in_movies values (genre_id, movie_id);
	  END IF;
   END IF;
	-- create movie if not exist

      
END; 
$$

-- Change back DELIMITER to ; 
DELIMITER ; 