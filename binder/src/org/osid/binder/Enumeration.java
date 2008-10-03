//
// Enumeration.java
//
//     Defines an enumeration.
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
 *  Defines an OSID Enumeration.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */


public abstract class Enumeration
    extends Object {
    
    private String name;
    private Text description = null;
    private Text copyright   = null;
    private Text license     = null;
    private ArrayList<EnumerationItem> items = new ArrayList<EnumerationItem>();


    /**
     *  Constructs a new <code>Enumeration</code> object. 
     */
    
    protected Enumeration() {
	super();
	return;
    }


    /**
     *  Gets the name of this enumeration.
     *
     *  @return the name of this enumeration
     */

    public String getName() {
	return (this.name);
    }


    /**
     *  Gets the description of this enumeration.
     *
     *  @return the description of this enumeration
     */

    public Text getDescription() {
	return (this.description);
    }


    /**
     *  Gets the copyright of this enumeration.
     *
     *  @return the copyright of this enumeration
     */

    public Text getCopyright() {
	return (this.copyright);
    }


    /**
     *  Gets the license of this enumeration.
     *
     *  @return the license of this enumeration
     */

    public Text getLicense() {
	return (this.license);
    }


    /**
     *  Gets the items defined in this enumeration.
     *
     *  @return an array of items
     */

    public EnumerationItem[] getItems() {
	return (this.items.toArray(new EnumerationItem[this.items.size()]));
    }


    /**
     *  Sets the XML DOM element node and parses it into an
     *  an Interface. Called from the Osid object.
     *
     *  @param element the XML element node
     *  @throws OsidBinderException an error in parsing XML
     */

    protected void setElement(Element element, Text copyright, Text license)
	throws OsidBinderException {

	this.copyright = copyright;
	this.license   = license;

	this.name = element.getAttributeNS(element.getNamespaceURI(), "name");

	if (this.name.length() == 0) {
	    throw new OsidBinderException("no enumeration name specified");
	}
	
	NodeList nl = element.getChildNodes();
	for (int i = 0; i < nl.getLength(); i++) {
	 
	    if (nl.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
	    
	    if (!element.getNamespaceURI().equals((nl.item(i).getNamespaceURI()))) {
		continue;
	    }

            if (nl.item(i).getLocalName().equals("description")) {
                if (this.description != null) {
                    throw new OsidBinderException("too many enumeration descriptions found");
                }

                this.description = getOsidBinderFactory().createText();
		this.description.setElement((Element) nl.item(i));
            } else if (nl.item(i).getLocalName().equals("item")) {
		EnumerationItem item = getOsidBinderFactory().createEnumerationItem();
		item.setElement((Element) nl.item(i));
		this.items.add(item);
            }
	}

	if (this.description == null) {
	    throw new OsidBinderException("no enumeraton description found");
	}
	
	return;
    }


    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected abstract OsidBinderFactory getOsidBinderFactory();
}
	
