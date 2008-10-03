//
// Compliance.java
//
//     Defines a compliance statement.
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

package org.osid.binder;

import java.io.PrintStream;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *  Defines an OSID compliance statement.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public abstract class Compliance
    extends Object {
    
    private String type;
    private Text description;


    /**
     *  Constructs a new <code>Compliance</code> object. 
     */

    protected Compliance() {
	super();
	return;
    }


    /**
     *  Sets the XML DOM element node and parses it into
     *  a Compliance. Called from the Method object.
     *
     *  @param element the XML element node
     *  @throws OsidBinderException an error in parsing XML
     */

    protected void setElement(Element element)
	throws OsidBinderException {

	this.type = element.getAttributeNS(element.getNamespaceURI(), "type").trim();
	if (this.type.length() == 0) {
	    throw new OsidBinderException("no compliance type specified");
	}

	NodeList nl = element.getElementsByTagNameNS(element.getNamespaceURI(), "description");
	if (nl.getLength() == 0) {
	    throw new OsidBinderException("no description for compliance found");
	}

	this.description = getOsidBinderFactory().createText();
	this.description.setElement((Element) nl.item(0));
    }


    /**
     *  Gets the type of this compliance statement.
     *
     *  @return the type of this compliance statement
     */

    public String getType() {
	return (this.type);
    }


    /**
     *  Gets the type of this compliance statement.
     *
     *  @param type the new type
     */

    public void setType(String type) {
	this.type = type;
	return;
    }


    /**
     *  Gets the description of this compliance statement.
     *
     *  @return the description of this compliance statement
     */
    
    public Text getDescription() {
	return (this.description);
    }


    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected abstract OsidBinderFactory getOsidBinderFactory();
}
	
