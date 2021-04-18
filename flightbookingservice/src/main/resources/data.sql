DROP TABLE IF EXISTS flights;

create table flights (
  flightnumber VARCHAR(250) PRIMARY KEY,
  sourcecity VARCHAR(250) NOT NULL,
  destinationcity VARCHAR(250) NOT NULL,
  duration INT NOT NULL,
  capacity INT NOT NULL
);

INSERT INTO flights (flightnumber, capacity, sourcecity,destinationcity,duration) VALUES ('AB1234', 500, 'Bangalore','Delhi',150);
INSERT INTO flights (flightnumber, capacity, sourcecity,destinationcity,duration) VALUES ('CD5678', 120, 'Bangalore','Chennai',60);
INSERT INTO flights (flightnumber, capacity, sourcecity,destinationcity,duration) VALUES ('EF9012', 200, 'Bangalore','Mumbai',120);
INSERT INTO flights (flightnumber, capacity, sourcecity,destinationcity,duration) VALUES ('GH4567', 250, 'Bangalore','Pune',100);
INSERT INTO flights (flightnumber, capacity, sourcecity,destinationcity,duration) VALUES ('IJ8901', 300, 'Bangalore','Pakisthan',240);
INSERT INTO flights (flightnumber, capacity, sourcecity,destinationcity,duration) VALUES ('KL9876', 500, 'Bangalore','Delhi',200);