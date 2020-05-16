INSERT INTO Categories(categoryID,name) VALUES (1,'Bialkowe');
INSERT INTO Categories(categoryID,name) VALUES (2,'Weglowodanowe');
INSERT INTO Categories(categoryID,name) VALUES (3,'Keto');
INSERT INTO Categories(categoryID,name) VALUES (4,'Fit');
INSERT INTO Categories(categoryID,name) VALUES (5,'Inne');

INSERT INTO Users(userId, email, isActive, password, phoneNumber, userName, userType, messageCounter) values (1,'admin@admin.admin',true,'21232f297a57a5a743894a0e4a801fc3','123123123','admin','ADMIN',0);
INSERT INTO Admins(lastName, name, adminID) values ('admin','admin',1);
INSERT INTO Users(userId, email, isActive, password, phoneNumber, userName, userType, messageCounter) values (2,'c@company.com',true,'202cb962ac59075b964b07152d234b70','111111111','company','COMPANY',0);
INSERT INTO Companies(companyId, name, nip, regon, websiteAddress, averageRating, city) values (2, 'Fit Boxy', '1234123123', '1231231231', 'fitboxy.pl', 0, 'Gdańsk');
INSERT INTO Users(userId, email, isActive, password, phoneNumber, userName, userType, messageCounter) values (3,'c@company2.com',true,'202cb962ac59075b964b07152d234b70','111111111','company2','COMPANY',0);
INSERT INTO Companies(companyId, name, nip, regon, websiteAddress, averageRating, city) values (3, 'Dieta Cut', '1234123123', '1231231231', 'dietacut.pl', 0, 'Gdańsk');
INSERT INTO Users(userId, email, isActive, password, phoneNumber, userName, userType, messageCounter) values (4,'c@company3.com',true,'202cb962ac59075b964b07152d234b70','111111111','company3','COMPANY',0);
INSERT INTO Companies(companyId, name, nip, regon, websiteAddress, averageRating, city) values (4, 'Luch Boxy', '1234123123', '1231231231', 'lunchboxy.pl', 0, 'Toruń');
