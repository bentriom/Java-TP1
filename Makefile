# Ensimag 2A POO - TP 2014/15
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testIHM testLecture

testIHM:
	javac -d bin -classpath bin/ihm.jar -sourcepath src src/TestIHM.java

testLecture:
	javac -d bin -sourcepath src src/TestLecteurDonnees.java
	
runSimulation:
	javac -d bin -classpath bin/ihm.jar -sourcepath src src/RunSimulation.java
	
# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin TestIHM
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeIHM
exeIHM:
	java -classpath bin:bin/ihm.jar TestIHM

exeLecture:
	java -classpath bin TestLecteurDonnees cartes/carteSujet.txt

exeDessin:

	java -classpath bin:bin/ihm.jar RunSimulation cartes/carteSujet.txt

clean:
	rm -rf bin/*.class

