//
// Return.java
//
//     Produces the Java binding for a return statement.
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

package org.osid.binder.php;

import java.io.PrintStream;


/**
 *  <p>
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class Return
    extends org.osid.binder.Return {

    private OsidBinderFactory factory = new OsidBinderFactory();


    /**
     *  Constructs a new <code>Return</code>
     */

    Return() {
	super();
	return;
    }


    /**
     *  Gets the type of this return translated into Java. 
     *
     *  @return the translated type
     */

    public String getBinderType() {
	String ret;
	
	if (isArray()) {
		if (isPrimitiveType()) {
			ret = "array of " + PrimitiveTranslator.translate(getType()) + "s";
		} else {
			ret = "array of " + org.osid.binder.php.Interface.getClassName(getType()) + " objects ";
		}
	} else {
		if (isPrimitiveType()) {
			ret = PrimitiveTranslator.translate(getType());
		} else {
			ret = "object " + org.osid.binder.php.Interface.getClassName(getType());
		}
	}

	return (ret);
    }
    

    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected OsidBinderFactory getOsidBinderFactory() {
        return (this.factory);
    }


    void printJDoc(PrintStream out, String margin) {
	String pre = margin + "@return " + getBinderType() + " ";
	out.print(pre);
	getDescription().printHtml(out, margin + "        ", pre.length());
	out.println();
    }
}
	
