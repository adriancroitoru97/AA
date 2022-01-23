# Analiza Algoritmilor(AA) - Tema 2 - Reduceri polinomiale

Proiectul consta in asocierea si modelarea unor aplicatii practice
cu probleme NP. Se vor folosi reduceri polinomiale la problema SAT.



## 2 - Retele sociale

Am ales sa privesc problema data ca o instanta a problemei **Clique**. Astfel,
am aplicat cauzele necesare pentru a reduce instanta data, de Clique,
la o instanta a problemei SAT:

> #### Cele 3 clauze aplicate:
>
> - exista cel putin un nod pentru fiecare element din **clique**
> - pentru fiecare non-muchie (v, w), v si w nu se pot afla ambele in **clique**
> - pentru fiecare `i != j`, nodul `i` este diferit de nodul `j` din **clique**

Aplicand cele 3 clauze de mai sus, se obtine o complexitate temporala de construire
a setului de valori ce vor fi transmise oracolului de `O(n^2 * k^2)`.


## 3 - Reclame buclucase

Am redus instanta problemei primite la o instanta pentru problema SAT, folosind
ca abstractizare algoritmul de **Vertex Cover**. Intrucat se cere determinarea
grupului de dimensiune minima, am ales sa aplic **Vertex Cover** de la k = 1
pana gasesc un k pentru care rezultatul algoritmului anterior amintit este `TRUE`.

> #### Cele 3 clauze aplicate:
>
> - exista cel putin un nod pentru fiecare element din acoperire
> - un nod este in acoperire cel mult o data
> - orice muchie are cel putin unul dintre capete in acoperire

Complexitatea unei singure iteratii de construire a setului de valori ce vor fi
transmise oracolului pentru **Vertex Cover** este `O(k * n^2 + k * m)`. Trebuie
tinut cont ca se va repeta algoritmul de atatea ori cat este necesar
pentru a se obtine un rezultat valid.


## 4 - Alocarea registrilor

Se observa similitudinea dintre problema primita si algoritmul de **Graph Coloring**.
Variabilele ce trebuie plasate in registre pot fi asociate cu noduri care trebuie
sa primeasca o culoare. Am ales sa folosesc o codificare a variabilelor care va tine
cont de nodul curent (variabila), dar si de fiecare culoare(registru) in care acest
nod ar putea fi plasat.

> #### Cele 2 clauze aplicate:
>
> - exista cel putin un nod pentru fiecare element din acoperire
> - pentru fiecare muchie (v, w), v si w nu se pot afla ambele in acoperire

Se observa o complexitate de construire a instantei pentru
problema SAT, `O(n * k + m * k)`.


Desigur, la complexitatile de reducere ale problemelor anterioare la problema SAT
se adauga si complexitatea efectiva de rezolvare a unei probleme SAT de catre oracol.



## License
[Adrian-Valeriu Croitoru](https://github.com/adriancroitoru97)