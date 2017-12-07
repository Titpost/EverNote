CREATE TABLE person
(
    id BIGINT  PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    active BOOLEAN
);

CREATE TABLE pad
(
    id VARCHAR(255)  PRIMARY KEY,
    person BIGINT,
    FOREIGN KEY (person) REFERENCES person(id)
);