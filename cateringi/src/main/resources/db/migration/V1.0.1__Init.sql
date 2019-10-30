ALTER TABLE Favorites ADD FavoriteID INT IDENTITY(1,1);

ALTER TABLE Favorites ADD CONSTRAINT FavoritesPrimaryKey PRIMARY KEY (FavoriteID);

ALTER TABLE ServiceCategory ADD ServiceCategoryID INT IDENTITY(1,1);

ALTER TABLE ServiceCategory ADD CONSTRAINT ServiceCategoryPrimaryKey PRIMARY KEY (ServiceCategoryID);