
#!/bin/bash
# Utilisation : ./simulation.sh numero_manager nom_map_dans_cartes

if [ "$1" = "2" ]
then make XeMan2 && make exeMan2 map="$2"
elif [ "$1" = "3" ]
then make XeMan3 && make exeMan3 map="$2"
else echo "Utilisation : ./simulation.sh [numero_manager(2 ou 3)] [nom_map_dans_cartes]"
fi
