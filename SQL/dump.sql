
CREATE TABLE IF NOT EXISTS sections (
    "id" INTEGER NOT NULL PRIMARY KEY,
    "name" TEXT NOT NULL UNIQUE
);

AND

CREATE TABLE IF NOT EXISTS notes (
    "id" INTEGER NOT NULL PRIMARY KEY,
    "section_id" INT,
    "header" TEXT,
    "date" TEXT,
    "text" TEXT NOT NULL,
    FOREIGN KEY(section_id) REFERENCES section(id)
);
