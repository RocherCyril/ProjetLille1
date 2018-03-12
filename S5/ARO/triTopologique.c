#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "graphe.h"

typedef enum {ROUGE=0, BLEU=1, VERT=2} tCouleur;


/*un sommet a une couleur est un successeur suivant*/
typedef struct sommet_s{
	tCouleur couleur;
	int next_unvisited_successor;
} sommet_t;

/*un tableau où on rangera les sommets de droite à gauche pour faire le tri topologique*/
typedef struct sommets_tri_topo{
	tNumeroSommet tableau[MAX_SOMMETS];
	/*l'indice où on rangera le prochain sommet qui devient rouge*/
	int nextInd;
} sommets_tri_topo_t;

/*au départ tous les sommets sont bleus et le prochain successeur à visiter et le successeur 0*/
void initSommets(tGraphe g, sommet_t sommets[MAX_SOMMETS]){
	int i;
	for(i=0; i<grapheNbSommets(g); i++){
		sommets[i].couleur=BLEU;
		sommets[i].next_unvisited_successor=0;
	}
}


/*La version tri topologique est forcément pour un graphe orienté, parce que le tri topologique n'a pas de sens pour un tri non orienté
La fonction suppose que sommets est déjà initialisé avec initSommets*/
void parcoursProfondeur_VersionTriTopologique(tGraphe graphe, sommets_tri_topo_t *sorted, tNumeroSommet numSommet, sommet_t sommets[MAX_SOMMETS]) {
	int numSuccesseur, nbSuccesseurs, trouve;
	tPileSommets pileSommets;

	pileSommets = pileSommetsAlloue();

	sommets[numSommet].couleur = VERT;
	pileSommetsEmpile(pileSommets, numSommet);

	while (!pileSommetsEstVide(pileSommets)) {
		numSommet = pileSommetsTete(pileSommets);
		nbSuccesseurs = grapheNbSuccesseursSommet(graphe, numSommet);
		trouve = 0;

		/*Tant qu'il y a encore un successeur qui n'a pas encore était visité :*/
		while ((sommets[numSommet].next_unvisited_successor < nbSuccesseurs) && !trouve) {
			numSuccesseur = grapheSuccesseurSommetNumero(graphe, numSommet, sommets[numSommet].next_unvisited_successor);
			/*On trouve un successeur non visité*/
			if (sommets[numSommet].couleur == BLEU) {
				trouve = 1;
			} else {
				/*sinon on passe au successeur suivant*/
				sommets[numSommet].next_unvisited_successor++;
			}
		}
		/*pour vérifier que nous sommes bien sortis parce qu'on a trouvé un successeur non visité, et parce qu'il n'y en a plus*/
		if (trouve) {
			/*Il devient vert. On doit tous devenir verts pour notre planète au final...*/
			sommets[numSuccesseur].couleur = VERT;
			pileSommetsEmpile(pileSommets, numSuccesseur);
		} else {
			sommets[numSommet].couleur = ROUGE;
			sorted->tableau[sorted->nextInd]=numSommet;
			sorted->nextInd--;
			assert(pileSommetsDepile(pileSommets)==numSommet);
		}
	}
	return;
}



void triTopologique(tGraphe g, sommets_tri_topo_t * sorted){
	int nbSommets,i;
	sommet_t sommets[MAX_SOMMETS];
	tNumeroSommet numSommet;
	tNomSommet nom;
	/*le premier élément à ranger dans le tableau trié sera rangé à l'indice n-1, où n est le nombre de sommets*/
	nbSommets=grapheNbSommets(g);
	sorted->nextInd=nbSommets-1;
	initSommets(g, sommets);
	for(numSommet=0; numSommet<nbSommets; numSommet++){
		if(sommets[numSommet].couleur==BLEU){
			parcoursProfondeur_VersionTriTopologique(g, sorted, numSommet, sommets);
		}
	}
	/*affichage*/
	printf("triTopologique: Si une coul n'est pas 0, c'est qu'y a un problème\n");
	for(i=0; i<nbSommets; i++){
		grapheRecupNomSommet(g, sorted->tableau[i], nom);
		printf(" %s:coul %d,", nom, sommets[i].couleur);
	}
	printf("\nnextInd=%d\n", sorted->nextInd);

}
