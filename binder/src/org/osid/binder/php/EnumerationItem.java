//
// EnumerationItem.java
//
//     Produces the PHP binding for an enumeration item.
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
 *  Produces the PHP binding for an enumeration item.
 *  </p>
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */


public class EnumerationItem
    extends org.osid.binder.EnumerationItem {

    private OsidBinderFactory factory = new OsidBinderFactory();


    /**
     *  Constructs a new <code>EnumerationItem</code> object. 
     */
    
    protected EnumerationItem() {
	super();
	return;
    }


    /**
     *  Gets the binder version of the name.
     *
     *  @return the binder version of the name.
     */

    public String getBinderName() {
	StringBuffer sb = new StringBuffer();
	String name = getName();

	for (int i = 0; i < name.length(); i++) {
	    char c = name.charAt(i);
	    if (c == '-') {
		c = '_';
	    }

	    sb.append(c);
	}

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
}
	
