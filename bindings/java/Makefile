#
#   Makefile
#
#       A Makefile to wrap the ant build.xml.
#
#
#   Tom Coppeto
#   OnTapSolutions
#   21 July 2005
#
#
#   Copyright (c) 2005 Massachusetts Institute of Technology
#       Permission is hereby granted, free of charge, to any person
#       obtaining a copy of this software and associated documentation
#       files (the "Software"), to deal in the Software without
#       restriction, including without limitation the rights to use,
#       copy, modify, merge, publish, distribute, sublicesne, and/or
#       sell copies of the Software, and to permit the persons to whom the
#       Software is furnished to do so, subject the following conditions:
#
#       The above copyright notice and this permission notice shall be
#       included in all copies or substantial portions of the Software.
#  
#       The Software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND,
#       EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
#       OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
#       NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
#       HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
#       WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
#       OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
#       DEALINGS IN THE SOFTWARE.
#
#
#   $Id: Makefile,v 1.1 2005/08/25 15:45:52 tom Exp $
#

BUILD=package/build

all:
	ant -f ${BUILD}/build.xml -Drel.home=${DESTDIR} compile

clean:
	ant -f ${BUILD}/build.xml -Drel.home=${DESTDIR} clean

install:
	ant -f ${BUILD}/build.xml -Drel.home=${DESTDIR} dist

test: 
	ant -f ${BUILD}/build.xml -Drel.home=${DESTDIR} test

world:
	ant -f ${BUILD}/build.xml -Drel.home=${DESTDIR} clean
	ant -f ${BUILD}/build.xml -Drel.home=${DESTDIR} release

bindings:
	rm -rf src
#	mkdir -p src/org/osid
	java -cp ../../binder/dist/lib/OsidBinder.jar org.osid.binder.java.OsidBinder ../../definitions/xosid src/org/osid