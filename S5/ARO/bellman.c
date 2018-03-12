#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "graphe.h"


/*un tableau où on rangera les sommets de droite à gauche pour faire le tri topologique*/
typedef struct sommets_tri_topo{
	tNumeroSommet tableau[MAX_SOMMETS];
	/*l'indice où on rangera le prochain sommet qui devient rouge*/
	int nextInd;
} sommets_tri_topo_t;


typedef struct pred_dmin_s{
	tNumeroSommet pred;
	int d;
}pred_min_t;

extern void triTopologique(tGraphe g, sommets_tri_topo_t * sorted);


void init_predmin(tGraphe g, pred_min_t predmin_tab[MAX_SOMMETS]){
	int i;
	for(i=0; i<grapheNbSommets(g); i++){
		predmin_tab[i].pred=predmin_tab[i].d=-1;
	}
}


void min_pix(tGraphe g, tNumeroSommet x, pred_min_t predMin[MAX_SOMMETS]){
	int nbPred, i, tmpD;
	tNumeroSommet numTmpPred;
	nbPred=grapheNbPredecesseursSommet(g, x);
	/*Pour chaque prédecesseur*/
	for(i=0; i<nbPred; i++){
		numTmpPred = graphePredecesseurSommetNumero(g, x, i);
		/*si le prédecesseur est déjà initialisé (donc il est s lui même ou un successeur de s), parce qu'un sommet x peut avoir des prédécesseurs qui ne sont pas successeurs de s*/
		if(predMin[numTmpPred].d!=-1){
			tmpD=predMin[numTmpPred].d+grapheRecupValeurArc(g, numTmpPred, x);
			/*si c'est le premier prédecessuer consulté ou si la d+v est inférieur à la valeur minimale déjà calculée*/
			if(predMin[x].d==-1 || tmpD<predMin[x].d){
				/*on met à jour les valeurs d et pred de x*/
				predMin[x].pred=numTmpPred;
				predMin[x].d=tmpD;
			}
		}
	}
}

void bellman(tGraphe g, tNumeroSommet s, pred_min_t predmin_tab[MAX_SOMMETS]){
	sommets_tri_topo_t sorted;
	int i, apres_s;
	triTopologique(g, &sorted);
	init_predmin(g, predmin_tab);
	for(i=apres_s=0; i<grapheNbSommets(g);i++){
		if(apres_s){
			min_pix(g, i, predmin_tab);
		}
		else if(i==s){
			apres_s=1;
			predmin_tab[i].d=0;
		}
	}
}


int main(int argc, char *argv[]) {

	tGraphe graphe;
	int nbSommets, i;
	pred_min_t tab[MAX_SOMMETS];
	tNomSommet nom;

	if (argc<3) {
		halt("Usage : %s FichierGraphe SommetDepart\n", argv[0]);
	}

	graphe = grapheAlloue();
	grapheChargeFichier(graphe,  argv[1]);

	nbSommets=grapheNbSommets(graphe);
	bellman(graphe, grapheChercheSommetParNom(graphe, argv[2]), tab);

	/*affichage*/
	for(i=0; i<nbSommets; i++){
		grapheRecupNomSommet(graphe, i, nom);
		printf("pour le sommet %s, ", nom);
		if(tab[i].pred==-1) printf("pred=? et ");
		else{
			grapheRecupNomSommet(graphe, tab[i].pred, nom);
			printf("pred=%s et ", nom);		
		}
		printf("d=%d\n", tab[i].d);
	}


	grapheLibere(graphe);
	exit(EXIT_SUCCESS);

}
