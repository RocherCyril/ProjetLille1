set PUB;
param minimum_conso {PUB} >=0;
param prix_min {PUB} >=0;
param nb_conso {PUB} >=0;
param budget >=0;
param pers_semaine {PUB} >=0;
param pers_semaine_dispo >=0;
var quantite {p in PUB} >=minimum_conso[p];

maximize Consommateurs_touché : 
sum {p in PUB} quantite[p]*nb_conso[p];
subject to budget_limitee :
sum {p in PUB} (prix_min[p]*quantite[p]) <= budget;
subject to personnel_limite:
sum {p in PUB} (pers_semaine[p]*quantite[p]) <=pers_semaine_dispo;

data;
set PUB := TV magazine;
param budget :=1000000;
param pers_semaine_dispo :=100;
param: prix_min nb_conso minimum_conso pers_semaine:=
TV 20000 1800000 10 1
magazine 10000 1000000 0 3;

solve;
display quantite;
