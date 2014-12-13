#
# Copyright (C) 2012 Stefano Sanfilippo.
# See COPYING at top level for more information.
#
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
