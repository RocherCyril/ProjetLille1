data;
set PROD := bandes rouleaux poutres;
param heures_ouvrees := 40;
param: vitesse_production prix_vente vente_max :=
bandes        200            25        6000
rouleaux      140            30        4000
poutres       160            29        3500;
model TestPremiereModelisation.ampl;
solve;
display qte_produite.lb, qte_produite, qte_produite.ub;
