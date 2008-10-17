//
// OsidError.java
//
//     Represents the OSID exception all other exceptions extend.
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 *  Represents the OSID exception all other exceptions extend.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class OsidError
    extends org.osid.binder.Error {

    private OsidBinderFactory factory = new OsidBinderFactory();
    private org.osid.binder.Text description;
    
   
    /**
     *  Constructs a new <code>OsidError</code>.
     *
     *  @throws org.osid.binder.OsidBinderException
     */

    OsidError()
	throws org.osid.binder.OsidBinderException {

	super();

	try {

	    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = builder.newDocument();

	    this.description = new org.osid.binder.Text();

	    Element element = doc.createElement("description");
	    element.appendChild(doc.createTextNode("OsidException and its subclasses define OSID checked exceptions. OSID methods may throw a subclass of OsidException to indicate a non-programmatic failure that should be caught and handled by the application."));
	    this.description.setElement(element);

	} catch (Exception e) {
	    throw new org.osid.binder.OsidBinderException("cannot create text node" , e);
	}
    }


    /**
     *  Gets the type of this error translated into Java. 
     *
     *  @return the translated type
     */

    public String getBinderType() {
	return ("org.osid.OsidException");
    }

    
    /**
     *  Gets the category of this error.
     *
     *  @return the error category
     */
 
    public String getCategory() {
	return ("");
    }


    /**
     *  Gets the type of this error.
     *
     *  @return the error type
     */

    public String getType() {
	return ("osid.OsidError");
    }


    /**
     *  Gets the description of this error.
     *
     *  @return the error description
     */

    public org.osid.binder.Text getDescription() {
	return (this.description);
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
	String pre = margin + "@throws " + getType() + " ";
	out.print(pre);
	getDescription().printHtml(out, margin + "        ", pre.length());
	out.println();
    }
}
	
