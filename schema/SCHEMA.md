    CREATE TABLE clients (
        clientid SERIAL PRIMARY KEY,
        first_name text,
        last_name text,
        birth_date date
    );
    
    CREATE TABLE hotels (
        hotelid SERIAL PRIMARY KEY,
        name text,
        address text,
        rating double precision,
        cityid integer REFERENCES cities (cityid), 
        countryid integer REFERENCES countries (countryid)
    );

    CREATE TABLE reservations (
        resvid SERIAL PRIMARY KEY,
        hotelid integer REFERENCES hotels (hotelid),
        roomid integer REFERENCES rooms (roomid),
        clientid integer REFERENCES clients (clientid),
        dateofres date,
        timeofres timestamp,
        fromdate date,
        tilldate date
    );

    CREATE TABLE rooms (
        roomid SERIAL PRIMARY KEY,
        class integer,
        hotelid integer REFERENCES hotels (hotelid)
    );
    
    CREATE TABLE cities(
      cityid SERIAL PRIMARY KEY,
      name text,
      countryid int REFERENCES countries (countryid)
    );
    
    CREATE TABLE countries
    (
    	countryid SERIAL primary key,
    	name text
    );