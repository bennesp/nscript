#
# Copyright (C) 2012,2014 Stefano Sanfilippo.
# See LICENSE.txt at top level for more information.
#
# This is provided only for *nix convenience, real
# build is handled by Apache Maven
#

default: clean dist

dist:
	mvn package assembly:single

javadoc:
	mvn javadoc:javadoc

clean:
	mvn clean

format:
	find src -regex '.*\.java' | xargs astyle --options=astylerc
