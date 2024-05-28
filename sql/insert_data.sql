INSERT INTO TRANSLATOR(id, lastName, firstName)
VALUES (1,'Popescu','Alexandru');
INSERT INTO TRANSLATOR(id, lastName, firstName)
VALUES (2,'Gheorghe','Stefan');
INSERT INTO TRANSLATOR(id, lastName, firstName)
VALUES (3,'Brincus','Mihai');
INSERT INTO TRANSLATOR(id, lastName, firstName)
VALUES (4,'Crainicul','George');
INSERT INTO TRANSLATOR(id, lastName, firstName)
VALUES (5,'Rusu','Florin');

INSERT INTO BOOK(id, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator)
VALUES (1,'Misery',20,356,'1235654213233',5,12,'romanian',1);
INSERT INTO BOOK(id, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator)
VALUES (2,'Cateva motive sa iubesti viata',26,240,'1233334456433',4,11,'romanian',2);
INSERT INTO BOOK(id, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator)
VALUES (3,'Furia rosie',44,506,'2342812937123',6,13,'romanian',3);
INSERT INTO BOOK(id, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator)
VALUES (4,'JFK 22.11.63',60,720,'9786064309075',8,15,'romanian',1);
INSERT INTO BOOK(id, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator)
VALUES (5,'4 ore-saptamana de lucru',23,496,'9786069130193',6,13,'romanian',4);
INSERT INTO BOOK(id, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator)
VALUES (6,'Toate acele locuri minunate',31,401,'9786067194098',5,12,'romanian',5);
INSERT INTO BOOK(id, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator)
VALUES (7,'Ultimul Imperiu',45,816,'9789737078230',7,14,'romanian',2);
INSERT INTO BOOK(id, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator)
VALUES (8,'Mr. Mercedes',39,488,'9786067581799',5,12,'romanian',1);
INSERT INTO BOOK(id, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator)
VALUES (9,'The Final Empire',45,537,'1222234568',7,14,'english',-1);
INSERT INTO BOOK(id, name, noChapters, noPages, isbn, productionPrice, price, language, idTranslator)
VALUES (10,'Calamity',51,421,'1034985610113',6,13,'english',-1);

INSERT INTO CATEGORY(id, name)
VALUES (1,'Fantasy');
INSERT INTO CATEGORY(id, name)
VALUES (2,'SF');
INSERT INTO CATEGORY(id, name)
VALUES (3,'Thriller');
INSERT INTO CATEGORY(id, name)
VALUES (4,'Romance');
INSERT INTO CATEGORY(id, name)
VALUES (5,'Horror');
INSERT INTO CATEGORY(id, name)
VALUES (6,'Self-Help');
INSERT INTO CATEGORY(id, name) 
VALUES (7,'Politics');

INSERT INTO PUBLISHER(id, name, foundingDate)
VALUES (1,'Nemira','1991-01-01');
INSERT INTO PUBLISHER(id, name, foundingDate)
VALUES (2,'Polirom','1999-06-24');
INSERT INTO PUBLISHER(id, name, foundingDate)
VALUES (3,'Paladin','2008-09-19');
INSERT INTO PUBLISHER(id, name, foundingDate)
VALUES (4,'Gollancz','1998-05-23');
INSERT INTO PUBLISHER(id, name, foundingDate)
VALUES (5,'Trei','1994-08-25');
INSERT INTO PUBLISHER(id, name, foundingDate)
VALUES (6,'Tor Books','1980-04-02');

INSERT INTO AUTHOR(id, lastName, firstName, debutDate)
VALUES (1,'Haig','Matt','2002-02-04');
INSERT INTO AUTHOR(id, lastName, firstName, debutDate)
VALUES (2,'King','Stephen','1970-06-06');
INSERT INTO AUTHOR(id, lastName, firstName, debutDate)
VALUES (3,'Sanderson','Brandon','2005-04-21');
INSERT INTO AUTHOR(id, lastName, firstName, debutDate)
VALUES (4,'Ferriss','Tim','2007-08-01');
INSERT INTO AUTHOR(id, lastName, firstName, debutDate)
VALUES (5,'Brown','Pierce','2014-01-28');
INSERT INTO AUTHOR(id, lastName, firstName, debutDate)
VALUES (6,'Niven','Jennifer','2015-01-06');

INSERT INTO EDITOR(id, lastName, firstName, debutDate)
VALUES (1, 'Olsen','Clark','2020-11-20');
INSERT INTO EDITOR(id, lastName, firstName, debutDate)
VALUES (2,'Adams','Purple','2019-12-10');
INSERT INTO EDITOR(id, lastName, firstName, debutDate)
VALUES (3,'Driver','Abraham','1988-05-04');
INSERT INTO EDITOR(id, lastName, firstName, debutDate)
VALUES (4,'Park','Mark','1997-03-09');
INSERT INTO EDITOR(id, lastName, firstName, debutDate)
VALUES (5,'Bryant','Arthur','2003-08-10');

INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (1,3);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (1,5);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (2,6);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (3,1);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (3,2);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (4,1);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (4,2);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (4,3);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (4,4);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (5,6);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (6,4);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (7,1);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (8,3);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (8,5);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (9,1);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (10,1);
INSERT INTO BOOKCATEGORY(idBook, idCategory)
VALUES (10,2);

INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (1,1,2000);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (2,1,3000);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (3,3,2500);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (4,1,2500);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (5,2,800);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (6,5,5000);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (7,5,1500);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (8,1,1000);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (9,4,400000);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (9,6,800000);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (10,4,100000);
INSERT INTO BOOKPUBLISHER(idBook,idPublisher,noCopies)
VALUES (10,6,300000);

INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (1,2,1,10000);
INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (2,1,2,15000);
INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (3,5,3,20000);
INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (4,2,2,8000);
INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (5,4,4,20000);
INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (6,6,5,16000);
INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (7,3,3,7000);
INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (8,2,3,8000);
INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (9,3,2,90000);
INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (10,3,1,10000);
INSERT INTO BOOKAUTHOREDITOR(idBook,idAuthor,idEditor,price)
VALUES (10,2,2,100000);
