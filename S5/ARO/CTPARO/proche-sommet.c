/**********************************************************************
 * proche-sommet.c
 *
 *  (François lemaire)  <Francois.Lemaire@lifl.fr> 
 * Time-stamp: <2017-11-30 16:46:24 francois>
 ***********************************************************************/


#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "graphe.h"

/* Couleurs */
typedef enum {ROUGE=0, BLEU=1, VERT=2} tCouleur;
typedef tCouleur tTabCouleurs[MAX_SOMMETS];


/* Parcours en largeur 

   Entrée :
   - graphe : un graphe non orienté
   - s : le sommet de départ
   Sortie
   - aucune
   Effet de bord:
   - les tableaux pred et d sont mis à jour.
     Si x n'est pas accessible depuis s, alors
     pred[x] vaut -1, et d[x] vaut -1.
     Si x est accessible depuis s, d[x] et pred[x] sont
     calculés comme dans le polycopié.
*/
void parcoursLargeur(tGraphe graphe, tNumeroSommet s,
		     tNumeroSommet pred[MAX_SOMMETS],
		     int d[MAX_SOMMETS]) {
  tFileSommets file=fileSommetsAlloue();
  tNumeroSommet stock;
  tTabCouleurs t;
  for(int i=0;i<grapheNbSommets(graphe);i++){
    if(i!=s){
      t[i]=BLEU;
    }
    else{
      t[i]=VERT;
    }
  }
  while(!fileSommetsEstVide(file)){
    fileSommetsDefile(file);
  }
  fileSommetsEnfile(file,s);
  while(!fileSommetsEstVide(file)){
    stock=fileSommetsDefile(file);
    for(int j=0; j<grapheNbVoisinsSommet(graphe,stock);j++){
      tNumeroSommet voisins=grapheVoisinSommetNumero(graphe,stock,j);
      pred[j]=stock;
      d[j]=grapheRecupValeurArc(graphe,stock,voisins);
      if(t[voisins]==BLEU){
	t[voisins]=VERT;
	fileSommetsEnfile(file,voisins);
      }
    }
    t[stock]=ROUGE;
  }
  for(int p=0;p<grapheNbSommets(graphe);p++){
    if(t[p]!=ROUGE){
      d[p]=-1;
    }
  }
}

/*
Renvoie le numéro d'un sommet proche de s1 et s2
ou -1 si s1 et s2 n'ont pas de sommet proche
*/

tNumeroSommet procheSommet(tGraphe graphe,
			   tNumeroSommet s1,
			   tNumeroSommet s2) {
  tNumeroSommet pred[MAX_SOMMETS];
  int d[MAX_SOMMETS];
  parcoursLargeur(graphe,s1,pred,d);
  
}



int main(int argc, char *argv[]) {

  tGraphe graphe;
  char *fic;
  
  tNomSommet nom1, nom2, nomx, nomp, nomps;
  tNumeroSommet x,s1,s2, ps;
  tNumeroSommet pred[MAX_SOMMETS];
  int d[MAX_SOMMETS];
  
  /* Traitement des arguments */
  if (argc!=4) 
    halt("Usage : %s <fichier_graphe> <nom_sommet1> <nom_sommet2>\n", argv[0]);
  
  fic = argv[1];
  strcpy(nom1, argv[2]);
  strcpy(nom2, argv[3]);
    
  /* Chargement du graphe */
  graphe = grapheAlloue();
  grapheChargeFichier(graphe, fic);
  if (grapheEstOriente(graphe)) 
    halt("Le graphe doit être non orienté\n");

  /* Récupération des numéros des deux sommets */
  s1 = grapheChercheSommetParNom(graphe, nom1);
  s2 = grapheChercheSommetParNom(graphe, nom2);
  if (s1==-1) halt("Nom de sommet inexistant: %s\n", nom1);
  if (s2==-1) halt("Nom de sommet inexistant: %s\n", nom2);


  // ****************************************
  // Premier test :  parcours en largeur (depuis s1)
  parcoursLargeur(graphe, s1, pred, d);
  // Affichage 
  printf("Sommet  Pred   Distance\n");
  for(x=0; x < grapheNbSommets(graphe); x++) {
    grapheRecupNomSommet(graphe, x, nomx);
    printf("%3s  ", nomx);
    if (pred[x] != -1) {
      grapheRecupNomSommet(graphe, pred[x], nomp);
      printf("%6s",nomp);
    } else {
      printf("%6s","");
    }
    
    if (d[x]!=-1) {
      printf("%9d", d[x]);
    }
    printf("\n");
  }

  /*
    
  // ****************************************
  // Deuxième test : recherche de sommet proche
  ps = procheSommet(graphe, s1, s2);
  if (ps==-1) {
    printf("Les deux sommets n'ont pas de voisin proche\n");
  } else {
    grapheRecupNomSommet(graphe, ps, nomps); 
    printf("Voici un proche sommet : %s\n", nomps);
  }
  
  */
  grapheLibere(graphe);
  exit(EXIT_SUCCESS);
}

