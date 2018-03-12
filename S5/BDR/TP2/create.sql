create table Articles(
aid int,
anom varchar(100),
acoul varchar(100),
PRIMARY KEY(aid)
);

create table Fournisseurs(
fid int,
fnom varchar(100),
fad varchar(100),
PRIMARY KEY(fid)
);

create table Catalogue(
fid int,
aid int,
prix numeric(8,2),
PRIMARY KEY(fid,aid),
FOREIGN KEY(aid) REFERENCES Articles(aid) on update cascade on delete cascade,
FOREIGN KEY(fid) REFERENCES Fournisseurs(fid) on update cascade on delete cascade
);
