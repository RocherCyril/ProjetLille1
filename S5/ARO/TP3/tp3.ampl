set USI;
param cost {USI} >=0;
var nbDay{u in USI}>=0;

set PAP;
param prodPap {USI,PAP} >= 0;
param prodWant {PAP} >=0;


minimize totalCost :
sum{u in USI} cost[u]*nbDay[u];

subject to command:
sum {p in PAP, u in USI} prodPap[u,p]*nbDay[u]<=prodWant[p];


data;
set USI := A B;
param : cost :=

A 1000
B 2000;

set USI := A B;
set PAP := low medium hight;
param prodPap :
     low    medium      hight :=
A      8         1          2 
B      2         1          7; 

set PAP:= prodLowWant prodMedWant prodHightWant;
param: probWant :=
prodLowWant 16 
prodMedWant 5 
prodHightWant 20;


solve;
display nbDay;
