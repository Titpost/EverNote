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

CREATE TABLE note
(
    id VARCHAR(255)  PRIMARY KEY,
    pad VARCHAR(255),
    text TEXT,
    FOREIGN KEY (pad) REFERENCES pad(id)
);

CREATE TABLE tag
(
    id VARCHAR(255)  PRIMARY KEY,
    note VARCHAR(255),
    FOREIGN KEY (note) REFERENCES note(id)
);

