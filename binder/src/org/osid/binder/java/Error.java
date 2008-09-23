//
// Error.java
//
//     The Java binding for an error element.
//  
//
// Tom Coppeto
// OnTapSolutions
// 20 May 2006
//
//
// Copyright (c) 2006-2008 Massachusetts Institute of Technology
//      Permission is hereby granted, free of charge, to any person
//      obtaining a copy of this software and associated documentation
//      files (the "Software"), to deal in the Software without
//      restriction, including without limitation the rights to use,
//      copy, modify, merge, publish, distribute, sublicesne, and/or
//      sell copies of the Software, and to permit the persons to whom the
//      Software is furnished to do so, subject the following conditions:
//
//      The above copyright notice and this permission notice shall be
//      included in all copies or substantial portions of the Software.
//
//      The Software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND,
//      EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
//      OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
//      NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
//      HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
//      WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
//      DEALINGS IN THE SOFTWARE.
//

package org.osid.binder.java;

import java.io.PrintStream;


/**
 *  <p>
 *  The Java binding for an Error element.
 *  </p>
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class Error
    extends org.osid.binder.Error {

    private OsidBinderFactory factory = new OsidBinderFactory();

    
    Error() {

	super();
    }


    /**
     *  Gets the type of this error translated into Java. 
     *
     *  @return the translated type
     */

    public String getBinderType() {
	StringBuffer sb = new StringBuffer();
	String name = getType();
	boolean upper = true;
	
	sb.append("org.osid.");
	for (int i = 0; i < name.length(); i++) {
	    char c = name.charAt(i);
	    if (upper == true) {
		sb.append(c);
		upper = false;
	    } else if (c != '_') {
		sb.append(Character.toLowerCase(c));
	    } else {
		upper = true;
	    }
	}

	sb.append("Exception");
	return (sb.toString());
    }


    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected OsidBinderFactory getOsidBinderFactory() {
	return (this.factory);
    }

    
    protected void printJDoc(PrintStream out, String margin) {

	int col = 0;
	col = org.osid.binder.Text.printPlain(out, "@throws " + getBinderType() + " ", margin, col, false);

	if (getDescription() != null) {
	    col = getDescription().printHtml(out, margin + "        ", col);
	} 

	out.println();
    }
}
	
