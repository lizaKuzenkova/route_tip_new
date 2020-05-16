drop database traveling;
create database traveling CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use traveling; 

CREATE TABLE attractions ( 
	id_attraction        integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
	attraction_name      varchar(60) NOT NULL,
	att_description      text  NULL,
	id_city              integer NOT NULL 
);

CREATE TABLE cities ( 
	id_city              integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
	city_name            varchar(50) NOT NULL,
	id_country           integer NOT NULL 
);

CREATE TABLE continents ( 
	id_continent         integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
	continent_name       varchar(50) NOT NULL unique
);

CREATE TABLE countries ( 
	id_country           integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
	country_name         varchar(50) NOT NULL,
	capital              varchar(50) NOT NULL,
	population           integer  NULL,
	area                 integer  NULL,
	id_continent         integer NOT NULL 
);

CREATE TABLE cuisine( 
 	id_cuisine           integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
	cuisine_type         varchar(50) NOT NULL unique
);

CREATE TABLE hotels ( 
	id_hotel             integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
	hotel_name           varchar(60) NOT NULL,
	address              varchar(100) NOT NULL,
	rating               decimal(5, 2)  NULL,
    amount_of_stars      integer  NULL,
	website              varchar(60)  NULL, 
    id_city              integer NOT NULL
);

CREATE UNIQUE INDEX IXU_hotels_city_address ON hotels (
	hotel_name,
    id_city,
    address
);   

CREATE TABLE restaurants ( 
	id_restaurant        integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
	rest_name            varchar(60) NOT NULL,
	address              varchar(100) NOT NULL,
	rating               decimal(5, 2)  NULL,
	website              varchar(60)  NULL,
	id_city              integer NOT NULL 
);

CREATE TABLE routes ( 
	id_route             integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_attraction        integer  NULL,
	id_restaurant        integer  NULL,
	id_hotel        	 integer  NULL
);

CREATE TABLE rest_cuis (
	id_rest_cuis		 integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_restaurant		 integer  NOT NULL,
    id_cuisine			 integer  NOT NULL
);

CREATE UNIQUE INDEX IXU_rest_cuis ON rest_cuis (
	id_restaurant,
    id_cuisine
);

CREATE TABLE users (
	id_user		 		 integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    last_name       	 varchar(50)  NOT NULL,
    first_name       	 varchar(50)  NOT NULL,
    mail       			 varchar(50)  NULL,
    login       		 varchar(50)  NOT NULL,
    u_password       	 varchar(50)  NOT NULL,
    id_role				 integer NOT NULL
);

CREATE UNIQUE INDEX IXU_users_login ON users (
	login
);

CREATE TABLE roles (
	id_role		 		 integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    role_name       	 varchar(20)  NOT NULL
);

CREATE TABLE users_routes (
	id_users_routes		 integer  NOT NULL PRIMARY KEY AUTO_INCREMENT,
	id_user		 		 integer  NOT NULL,
    id_route	 		 integer  NOT NULL
);

CREATE UNIQUE INDEX IXU_users_routes ON users_routes (
	id_user,
    id_route
);   

CREATE TABLE place_types (
	id_place_type		integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    place_type_name		varchar(64) NOT NULL,
    place_type_code		varchar(64) NOT NULL UNIQUE
);

CREATE TABLE place_stats (
	id_place_stats		integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_place_type		integer NOT NULL,
    place_id			integer NOT NULL,
    id_user				integer     NULL,
    collected_at		datetime NOT NULL default current_timestamp
);

ALTER TABLE place_stats
	ADD FOREIGN KEY (id_place_type) REFERENCES place_types(id_place_type);

ALTER TABLE attractions
	ADD FOREIGN KEY (id_city) REFERENCES cities(id_city);
        
ALTER TABLE cities
	ADD FOREIGN KEY (id_country) REFERENCES countries(id_country);

ALTER TABLE countries
	ADD FOREIGN KEY (id_continent) REFERENCES continents(id_continent);

ALTER TABLE hotels
	ADD FOREIGN KEY (id_city) REFERENCES cities(id_city);

ALTER TABLE restaurants
	ADD FOREIGN KEY (id_city) REFERENCES cities(id_city);
        
ALTER TABLE routes
	ADD FOREIGN KEY (id_attraction) REFERENCES attractions(id_attraction);

ALTER TABLE routes
	ADD FOREIGN KEY (id_restaurant) REFERENCES restaurants(id_restaurant);
    
ALTER TABLE rest_cuis
	ADD FOREIGN KEY (id_restaurant) REFERENCES restaurants(id_restaurant);
    
ALTER TABLE rest_cuis
	ADD FOREIGN KEY (id_cuisine) REFERENCES cuisine(id_cuisine);
    
ALTER TABLE users
	ADD FOREIGN KEY (id_role) REFERENCES roles(id_role);
        
ALTER TABLE users_routes
	ADD FOREIGN KEY (id_user) REFERENCES users(id_user);
    
ALTER TABLE users_routes
	ADD FOREIGN KEY (id_route) REFERENCES routes(id_route);

 

    
    