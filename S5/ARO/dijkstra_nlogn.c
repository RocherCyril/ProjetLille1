#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include "graphe.h"

/*pour tout i strictement inférieur à f, mapNumSommetToTasPos[tas[i]] devrait toujours être égal à i*/
/*si f vaut 0 le tas est vide, parce que f est le prochain indice où on rangerait un nouvel élément*/
typedef struct minimier_s{
	int f;
	tNumeroSommet tas[MAX_SOMMETS];
	int mapNumSommetToTasPos[MAX_SOMMETS];
} minimier_t;

typedef enum {ROUGE=0, BLEU=1, VERT=2} tCouleur;

typedef struct sommet_dijkstra_s{
	tCouleur couleur;
	int pi;
	tNumeroSommet pred;
} sommet_dijkstra_t;

void initMinimier(minimier_t * m){
	int i;
	m->f=0;
	for(i=0; i<MAX_SOMMETS; i++){
		m->mapNumSommetToTasPos[i]=-1;
	}
}

/*les fils du sommet à l'indice j sont aux indices 2j+1 et 2j+2.*/
int parent(int i){
	return (i%2?i/2:(i/2)-1);
}

void swapNodes(minimier_t* m, int i, int j){
	tNumeroSommet tmp;
	/*i et j échangent de position dans le tas*/
	tmp=m->tas[i];
	m->tas[i]=m->tas[j];
	m->tas[j]=tmp;
	/* il faut mettre à jour le tableau qui map le numéro du sommet à son indice dans le tas*/
	m->mapNumSommetToTasPos[m->tas[j]]=j;
	m->mapNumSommetToTasPos[m->tas[i]]=i;
}

int swapWithParent(minimier_t* m, int* i, sommet_dijkstra_t sommets[MAX_SOMMETS]){
	int p;
	p=parent(*i);
	if(p<0) return 0;
	/*si le fils a une valeur pi(x) plus petite que celle du parent, il est mal placé est ils doivent être échangés*/
	if(sommets[m->tas[*i]].pi<sommets[m->tas[p]].pi){
		swapNodes(m,*i,p);
		*i=p;
		return 1;
	}
	return 0;
}


int swapWithChild(minimier_t* m, int* i, sommet_dijkstra_t sommets[MAX_SOMMETS]){
	int c1, c2, *cp;
	c2=(c1=(*i)*2+1)+1;

	/*si le premier fils n'existe pas (et donc le deuxième aussi), on ne peut pas descendre dans le tas*/
	if(c1>=m->f) return 0;
	/*si seulement le deuxième fils n'existe pas, on prend le premier*/
	if(c2>=m->f) cp=&c1;
	/*sinon si le premier fils a une valeur pi(x) inférieure à celle du deuxième, c'est lui qui nous intéresse
	sinon c'est l'autre*/
	else if(sommets[m->tas[c1]].pi<sommets[m->tas[c2]].pi) cp=&c1;
	/*sinon c'est le deuxième alors*/
	else cp=&c2;

	/*une fois le fils intéressant est choisi, on swap le parent avec le fils si possible*/
	if(sommets[m->tas[*cp]].pi<sommets[m->tas[*i]].pi){
		swapNodes(m,*i,*cp);
		*i=*cp;
		return 1;
	}
	return 0;
}


void inserer(minimier_t * m , tNumeroSommet s, sommet_dijkstra_t sommets[MAX_SOMMETS]){
	int i;
	sommets[s].couleur=VERT;
	m->tas[m->f]=s;
	m->mapNumSommetToTasPos[s]=m->f;
	m->f++;
	i=m->f-1;
	while(swapWithParent(m, &i, sommets));
}

void update(minimier_t * m , tNumeroSommet s, sommet_dijkstra_t sommets[MAX_SOMMETS]){
	int i;
	i=m->mapNumSommetToTasPos[s];
	while(swapWithParent(m, &i, sommets));
}


tNumeroSommet extract(minimier_t *m, sommet_dijkstra_t sommets[MAX_SOMMETS]){
	int i;
	tNumeroSommet res;
	if(m->f==0) return -1;
	res=m->tas[0];
	m->mapNumSommetToTasPos[res]=-1;
	m->tas[0]=m->tas[m->f-1];
	m->mapNumSommetToTasPos[m->tas[0]]=0;
	m->f--;
	i=0;
	while(swapWithChild(m, &i, sommets));
	return res;
}

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


void updateSuccesseurs(tGraphe g, tNumeroSommet pred, sommet_dijkstra_t sommets[MAX_SOMMETS], minimier_t *m){
	int i, nbSucc, piTmp;
	tNumeroSommet succ;
	nbSucc=grapheNbSuccesseursSommet(g, pred);
	for(i=0; i<nbSucc; i++){
		succ=grapheSuccesseurSommetNumero(g, pred, i);
		piTmp=sommets[pred].pi+grapheRecupValeurArc(g, pred, succ);
		if(sommets[succ].couleur==BLEU){
			sommets[succ].pi=piTmp;
			sommets[succ].pred=pred;
			inserer(m, succ, sommets);
		} else if (sommets[succ].couleur==VERT && (piTmp<sommets[succ].pi)){
			sommets[succ].pi=piTmp;
			sommets[succ].pred=pred;
			update(m, succ, sommets);
		}
	}
}

void dijkstra(tGraphe g, tNumeroSommet s ,sommet_dijkstra_t sommets[MAX_SOMMETS]){
	int sommets_verts_existent, minVert;
	minimier_t m;
	sommets_verts_existent=1;
	init_dijkstra(g, sommets);
	initMinimier(&m);
	sommets[s].pi=0;
	inserer(&m, s, sommets);
	while(sommets_verts_existent){
		minVert=extract(&m,sommets);
		if(minVert==-1)sommets_verts_existent=0;
		else{
			sommets[minVert].couleur=ROUGE;
			updateSuccesseurs(g, minVert, sommets, &m);
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
