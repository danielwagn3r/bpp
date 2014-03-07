bpp
===

A Bin packager in Java

Requirements
------------

- Java SE SDK 1.7
- Apache Maven 3.1.x


Installation
------------

~~~ sh
cd [source-directory]
mvn install
~~~


Usage
-----

~~~
bpp [Options] file
~~~

### Options

~~~
  -g, --generations <int>             Count of generations [Default: 50]
  -m, --mutprop <double>              Mutation-Propability [Default: 0.5]
  -n, --mutrate <int>                 Mutation-Rate [Default: 1]
  -p, --populationsize <int>          Size of population [Default: 20]
  -r, --recombprop <double>           Recombination-Propability [Default: 0.8]
  -l, --recombalg <a|b>               Recombination-Algorithm [Default: a]
  -s, --pressure <int>                Selection-Pressure [Default: 4]
  -e, --elitism                       Elitism [Default: false]
  -h, --help                          Display this help screen
~~~

### Arguments

~~~
  file                                File to load
~~~
