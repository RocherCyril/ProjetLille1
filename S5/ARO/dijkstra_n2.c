#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "graphe.h"


typedef enum {ROUGE=0, BLEU=1, VERT=2} tCouleur;

typedef struct sommet_dijkstra_s{
    tCouleur couleur;
    int pi;
    tNumeroSommet pred;
} sommet_dijkstra_t;


void init_dijkstra(tGraphe g, sommet_dijkstra_t sommets[MAX_SOMMETS]){
    tNumeroSommet num;
    int nbSommets;
    nbSommets=grapheNbSommets(g);
    for(num=0; num<nbSommets; num++){
        sommets[num].couleur=BLEU;
        sommets[num].pi=-1;
        sommets[num].pred=-1;
    }
}

int chercheMinVertDegueulasse(tGraphe g, sommet_dijkstra_t sommets[MAX_SOMMETS]){
    int i=0, nbSommets, minPi;
    tNumeroSommet minVert;
    nbSommets=grapheNbSommets(g);
    minPi=-1;
    for(i=0; i<nbSommets; i++){
        if(sommets[i].couleur==VERT && ( minPi==-1 || sommets[i].pi<minPi)){
            minPi=sommets[i].pi;
            minVert=i;
        }
    }
    return (minPi==-1? -1: minVert);
}


void updateSuccesseurs(tGraphe g, tNumeroSommet pred, sommet_dijkstra_t sommets[MAX_SOMMETS]){
    int i, nbSucc, piTmp;
    tNumeroSommet succ;
    nbSucc=grapheNbSuccesseursSommet(g, pred);
    for(i=0; i<nbSucc; i++){
        succ=grapheSuccesseurSommetNumero(g, pred, i);
        piTmp = sommets[pred].pi+grapheRecupValeurArc(g, pred, succ);
        if(sommets[succ].couleur==BLEU || (sommets[succ].couleur==VERT && piTmp<sommets[succ].pi)){
			sommets[succ].couleur=VERT;
            sommets[succ].pi=piTmp;
            sommets[succ].pred=pred;
        }
    }
}

void dijkstra(tGraphe g, tNumeroSommet s ,sommet_dijkstra_t sommets[MAX_SOMMETS]){
    int sommets_verts_existent, minVert;
    sommets_verts_existent=1;
    init_dijkstra(g, sommets);
    sommets[s].couleur=VERT;
    sommets[s].pi=0;
    while(sommets_verts_existent){
        minVert=chercheMinVertDegueulasse(g, sommets);
        if(minVert==-1)sommets_verts_existent=0;
        else{
            sommets[minVert].couleur=ROUGE;
            updateSuccesseurs(g, minVert, sommets);
        }
    }
}

int main(int argc, char *argv[]) {
    tGraphe graphe;
	int nbSommets, i;
	sommet_dijkstra_t tab[MAX_SOMMETS];
	tNomSommet nom;

	if (argc<3) {
		halt("Usage : %s FichierGraphe SommetDepart\n", argv[0]);
	}

	graphe = grapheAlloue();
	grapheChargeFichier(graphe,  argv[1]);

	nbSommets=grapheNbSommets(graphe);
	dijkstra(graphe, grapheChercheSommetParNom(graphe, argv[2]), tab);

	/*affichage*/
	for(i=0; i<nbSommets; i++){
		grapheRecupNomSommet(graphe, i, nom);
		printf("pour le sommet %s, coul=%d, ", nom, tab[i].couleur);
		if(tab[i].pred==-1) printf("pred=? et ");
		else{
			grapheRecupNomSommet(graphe, tab[i].pred, nom);
			printf("pred=%s et ", nom);
		}
		printf("d=%d\n", tab[i].pi);
	}


	grapheLibere(graphe);
	exit(EXIT_SUCCESS);
}
