# LA CARTE AUX TRESORS

## Contexte

Des aventuriers sont à la recherche de trésors sur une île.

On commence par lire un fichier qui contient l'ensemble des éléments sur la carte.\
On place ensuite l'ensemble de ces éléments sur notre carte.

Les éléments possibles sont des montagnes, infranchissable pour les aventuriers, qui sont à la recherche de trésors.

## Simulation

Chaque aventurier possède une liste de mouvements. Ces derniers peuvent avancer dans la direction dans laquelle il se trouve, ainsi que se tourner.\
Les aventuriers vont se déplacer un à un. Ils ne peuvent pas sortir de la carte, et ne peuvent pas aller sur une case contenant un autre aventurier ou une montagne.\
Si l'aventurier se déplace sur une case qui contient un trésor, l'aventurier récupère un tresor.

La simulation se termine lorsque tous les aventuriers ont fini l'ensemble de leurs mouvements.\
A ce moment la, on récupère l'ensemble des éléments sur la carte, et on les écrit dans un fichier appelé _resultats.txt_.

## Lancer le projet

Soit directement depuis votre IDE.\
Soit à l'aide de maven (le jar a déjà été généré à l'aide de la commande **mvn package**.\
Commencer par se placer dans le repertoire cartetresor (là où se situe le pom.xml).\
Executer la commande suivante pour lancer le programme :\
**java -jar target/CarteTresor-1.0.jar**

## lancer les tests

Depuis votre IDE ou bien également avec maven.\
Pour lancer l'ensemble des tests, executer cette commande sur le terminal :\
**mvn clean test**

## Structure du projet

On dispose de plusieurs classes.

### Classe Map

Cette classe représente la carte au trésor. La carte est une matrice de _Case_.\
Tout d'abord, on peut lui ajouter des éléments, si les coordonnées sont correctes, grâce à la fonction _addElemOnMap_.\
C'est aussi elle qui va vérifier le deplacement de aventurier, et les deplacer lorsque c'est possible à l'aide des fonctions _moveAdventurer_, _differentMovements_, _movementsOfAllAdventurers_, _verifyReplacement_.\
On peut afficher l'état de la carte à tout moment avec la fonction _printMap_.\
Pour finir, on peut récupèrer l'ensemble des éléments qui sont sur la carte dans une liste en utilisant la fonction _getElemToWrite_.

### Classe Case

Une case represente des coordonnées de la carte.\
Cette derniere possèdes divers éléments et peut être occupée ou non.\
Lorsqu'un aventurier ou une montagne est sur une case, cette dernière est occupée.\
Si la case possède un trésor ou rien, elle est libre.\
On peut alors ajouter des éléments sur la case lorsque c'est libre, grâce à _addElt_.\
Lorsqu'un aventurier recupère un trésor ou que l'aventurier se déplace, on peut retirer un trésor de la case ainsi que l'aventurier avec les fonctions _removeTresor_ et _removeAdventurer_.

### Classe Element

Cette classe est la classe mère de tous les éléments qui existent.\
L'élément de base est la montagne.\
Un élément à donc des coordonnées ainsi qu'un type, pour préciser de quel élément il s'agit.

### Classe Aventurier

Cette classe hérite de la classe _Element_.\
Un aventurier possède un nom, des coordonnées, une orientation, une suite de mouvements ainsi que le nombre de trésors récoltés.\
Un aventurier peut se déplacer vers l'orientation dans lequel il se trouve, et changer d'orientation en se tournant.

### Classe Tresor

Cette classe hérite également de la classe _Element_.\
Un trésor à des coordonnées et un nombre de trésors associé.\
Il perd un de ses trésors lorsque un aventurier en recupère un.

### Classe abstraite Simulation

Cette classe permet de lire et d'écrire dans un fichier.\
Dès qu'on lit une ligne du fichier, on va découper cette ligne pour récupèrer l'ensemble des valeurs nécessaires pour créer l'élément correspondant.\
On va ensuite lancer la simulation de déplacement avec la fonction _moveAdventurers_.\
On écrit dans un fichier à la fin, pour sauvegarder l'ensemble des éléments de la carte.