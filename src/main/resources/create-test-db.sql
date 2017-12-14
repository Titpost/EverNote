CREATE TABLE IF NOT EXISTS person
(
    id BIGINT  PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    active BOOLEAN
);

CREATE TABLE IF NOT EXISTS pad
(
    name VARCHAR(255)  PRIMARY KEY,
    person BIGINT,
    FOREIGN KEY (person) REFERENCES person(id)
);

CREATE TABLE IF NOT EXISTS note
(
    name VARCHAR(255)  PRIMARY KEY,
    pad VARCHAR(255),
    text TEXT,
    FOREIGN KEY (pad) REFERENCES pad(name)
);

CREATE TABLE IF NOT EXISTS tag
(
    name VARCHAR(255) NOT NULL,
    note VARCHAR(255) NOT NULL,
    UNIQUE KEY ukey (name, note),
    FOREIGN KEY (note) REFERENCES note(name)
);
