---
layout: article
title: "Cosas que no son rectas , se viene..."
show_title: true
tags: [Physics]
mathjax: true
mathjax_autonumber: true
---

Hola gente. Vengo a probar un poco de nuevas cómo es colocar ecuaciones en HTML a Latex , de momento me voy acostumbrando. Y voy a aprovechar para hablaros de conceptos fundamentales de relatividad general. Aunque de más está decir que no seguiré esta saga en orden cronológico en un principio, aunque más tarde lo haré.

¿Os acordáis cuando definíamos la norma relacionada con la distancia de un vector ?

$$|s| = \sqrt{s^2} = \sqrt{x^2+y^2+z^2} = \sqrt{\rho^2 + z^2} = r $$

Algo análogo a ésto en un lenguaje más complicado es:

$$ds^2=g^{\mu\nu}dx^{\mu}dx^{\nu}= dx_{\mu}dx^{\mu}$$

Donde teníamos nuestra métrica euclidiana en 3D , las típicas x , y , z.

$$g^{\mu\nu}= \left(\begin{array}{1cr} 
 1 & 0 & 0  \\ 
0 & 1 & 0  \\ 
 0 & 0 & 1\\ 
\end{array}\right)$$

Bien , en relatividad especial y general, se trata al tiempo como una más de estas coordenadas, lógicamente para que el tiempo tenga dimensión de longitud, se multiplicará por una velocidad. ¿Cuál? . Por motivos intrínsecos a la misma teoría, se escoge la velocidad de la luz en el vacío $$c = 3*10^9 m/s$$

$$\eta^{\mu\nu}= \left(\begin{array}{1cr} 
 -1 & 0 & 0 & 0 \\ 
 0 & 1 & 0 & 0\\ 
0 & 0 & 1 & 0 \\ 
 0 & 0 & 0 & 1\\ 
\end{array}\right)$$

Tenemos aquí la llamada métrica plana de Minkowski. Nos relaciona la estructura del espacio −1 , con el resto de componentes espaciales 1. Las distancias aquí no son lo que llamábamos antes la raíz cuadrada del cuadrado de las componentes en 3D , cuando sólo teníamos esos unos en la métrica.


Para $$x^\mu=\left(\begin{array}{1cr} ct \\ x \\ y \\ z \\ \end{array} \right)= \left(\begin{array}{1cr} x^0 \\ x^1 \\ x^2 \\ x^3 \\ \end{array} \right)$$


Ahora $$ds^2=\eta^{\mu\nu}dx^{\mu}dx^{\nu}= -(ct)^2+x^2+y^2+z^2$$

Y es más, ésta "distancia" entre dos sucesos es invariante ,es decir, no cambia.

Podemos hacer una clasificación entre trayectorias posibles que podríamos encontrarnos.

$$−(ct)^2+x^2+y^2+z^2<0$$ trayectoria de género tiempo 

$$−(ct)^2+x^2+y^2+z^2=0$$ trayectoria género luz 

$$−(ct)^2+x^2+y^2+z^2>0$$, trayectoria género espacio 

Podréis adivinar que las partículas con masa siguen trayectorias de género tiempo. Aquellas que no tienen masa, como el fotón, siguen trayectorias de género luz. Y partículas teóricas , como los taquiones , que viajan hacia "atrás en el tiempo", poseen trayectorias de género espacio.

Esto es todo por hoy, en otro post discutiremos los postulados de la relatividad general.

> "Gente del mañana y del hoy, evolución del ayer hombre mono.Ahora tenemos otras necesidades."

Un abrazo.
