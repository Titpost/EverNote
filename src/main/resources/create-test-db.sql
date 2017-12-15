CREATE TABLE IF NOT EXISTS person
(
    id BIGINT  PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    active BOOLEAN
);

CREATE TABLE IF NOT EXISTS pad
(
    id BIGINT  PRIMARY KEY AUTO_INCREMENT,
    person BIGINT,
    name VARCHAR(255),
    UNIQUE KEY padKey (name, person),
    FOREIGN KEY (person) REFERENCES person(id)
);

CREATE TABLE IF NOT EXISTS note
(
    id BIGINT  PRIMARY KEY AUTO_INCREMENT,
    pad BIGINT,
    name VARCHAR(255),
    text TEXT,
    FOREIGN KEY (pad) REFERENCES pad(id)
);

CREATE TABLE IF NOT EXISTS tag
(
    name VARCHAR(255) NOT NULL,
    note BIGINT NOT NULL,
    UNIQUE KEY tagKey (name, note),
    FOREIGN KEY (note) REFERENCES note(id)
);

