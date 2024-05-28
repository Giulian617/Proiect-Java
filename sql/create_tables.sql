CREATE TABLE TRANSLATOR
(
    id INT(9) PRIMARY KEY,
    lastName VARCHAR(25) NOT NULL,
    firstName VARCHAR(25) NOT NULL
);

CREATE TABLE BOOK
(
    id INT(18) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    noChapters INT(3) NOT NULL,
    noPages INT(4) NOT NULL,
    isbn VARCHAR(25) NOT NULL,
    productionPrice INT(2) NOT NULL,
    price INT(3) NOT NULL,
    language VARCHAR(15) NOT NULL,
    idTranslator INT(3) REFERENCES TRADUCATOR(id)
);

CREATE TABLE CATEGORY
(
    id INT(3) PRIMARY KEY,
    name VARCHAR(25) NOT NULL
);

CREATE TABLE PUBLISHER
(
    id INT(9) PRIMARY KEY,
    name VARCHAR(25) NOT NULL,
    foundingDate DATE NOT NULL
);

CREATE TABLE AUTHOR
(
    id INT(9) PRIMARY KEY,
    lastName VARCHAR(25) NOT NULL,
    firstName VARCHAR(25) NOT NULL,
    debutDate DATE NOT NULL
);

CREATE TABLE EDITOR
(
    id INT(9) PRIMARY KEY,
    lastName VARCHAR(25) NOT NULL,
    firstName VARCHAR(25) NOT NULL,
    debutDate DATE NOT NULL
);

CREATE TABLE BOOKCATEGORY
(
    idBook INT(18) NOT NULL,
    idCategory INT(3) NOT NULL,

    PRIMARY KEY (idBook, idCategory),
    FOREIGN KEY (idBook) REFERENCES BOOK(id), 
    FOREIGN KEY (idCategory) REFERENCES CATEGORY(id)
);

CREATE TABLE BOOKPUBLISHER
(
    idBook INT(18) NOT NULL,
    idPublisher INT(9) NOT NULL,
    noCopies INT(9) NOT NULL,

    PRIMARY KEY (idBook, idPublisher),
    FOREIGN KEY (idBook) REFERENCES BOOK(id), 
    FOREIGN KEY (idPublisher) REFERENCES PUBLISHER(id)
);

CREATE TABLE BOOKAUTHOREDITOR
(
    idBook INT(18) NOT NULL,
    idAuthor INT(9) NOT NULL,
    idEditor INT(9) NOT NULL,
    price INT(6) NOT NULL,

    PRIMARY KEY (idBook, idAuthor, idEditor),
    FOREIGN KEY (idBook) REFERENCES BOOK(id), 
    FOREIGN KEY (idAuthor) REFERENCES AUTHOR(id),
    FOREIGN KEY (idEditor) REFERENCES EDITOR(id)
);