  BPP - A Bin Packager
  Copyright (C) 2003  Daniel Wagner <daniel@kwaxi.org>

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  
  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
  
  $Id$


Requirements:

Java2 SDK
GNU Java getopt classes


Installation:

cd [source-directory]
javac *.java


Usage: bpp [Options] file

Options:

  -g, --generations <int>             Count of generations [Default: 50]
  -m, --mutprop <double>              Mutation-Propability [Default: 0.5]
  -n, --mutrate <int>                 Mutation-Rate [Default: 1]
  -p, --populationsize <int>          Size of population [Default: 20]
  -r, --recombprop <double>           Recombination-Propability [Default: 0.8]
  -l, --recombalg <a|b>               Recombination-Algorithm [Default: a]
  -s, --pressure <int>                Selection-Pressure [Default: 4]
  -e, --elitism                       Elitism [Default: false]
  -h, --help                          Display this help screen

Arguments:

  file                                File to load
