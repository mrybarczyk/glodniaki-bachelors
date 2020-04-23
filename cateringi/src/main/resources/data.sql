INSERT INTO Categories(categoryID,name) VALUES (1,'Bialkowe');
INSERT INTO Categories(categoryID,name) VALUES (2,'Weglowodanowe');
INSERT INTO Categories(categoryID,name) VALUES (3,'Keto');
INSERT INTO Categories(categoryID,name) VALUES (4,'Fit');
INSERT INTO Categories(categoryID,name) VALUES (5,'Inne');

INSERT INTO Users(userId, email, isActive, password, phoneNumber, userName, userType, messageCounter) values (1,'admin@admin.admin',true,'21232f297a57a5a743894a0e4a801fc3','123123123','admin','ADMIN',0);
INSERT INTO Admins(lastName, name, adminID) values ('admin','admin',1);