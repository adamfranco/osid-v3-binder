//
// Error.java
//
//     Defines an Error element.
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
 *  Defines an OSID error.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public abstract class Error
    extends Object {
    
    private String type;
    private String category;
    private Text description;


    /**
     *  Constructs a new <code>Error</code>.
     */

    protected Error() {
	super();
	return;
    }

    
    /**
     *  Gets the type of this error.
     *
     *  @return the error type
     */

    public String getType() {
	return (this.type);
    }


    /**
     * Sets the error type.
     *
     * @param type the error type
     */

    public void setType(String type) {
	this.type = type;
	return;
    }

    
    /**
     *  Gets the category of this error.
     *
     *  @return the error category
     */
 
    public String getCategory() {
	return (this.category);
    }


    /**
     * Sets the category.
     *
     * @param category the error category
     */

    public void setCategory(String category) {
	this.category = category;
	return;
    }

    
    /**
     *  Gets the description of this error.
     *
     *  @return the description
     */

    public Text getDescription() {
	return (this.description);
    }


    /**
     * Sets the description and clears the previous description.
     *
     * @param description some text about the error
     */

    public void setDescription(org.w3c.dom.Document doc, String description) {
	if (this.description == null) {
	    this.description = getOsidBinderFactory().createText();
	}	    

	this.description.setText(doc, description);
	return;
    }


    /**
     * Appends a description.
     *
     * @param description some text about the error
     */

    public void addDescription(String description) {
	this.description.addText(description);
	return;
    }


    /**
     *  Gets the type of this error translated into the binding.
     *
     *  @return the error type
     */

    public abstract String getBinderType();


    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected abstract OsidBinderFactory getOsidBinderFactory();


    /**
     *  Sets the XML DOM element node and parses it into an
     *  an Error. Called from the Method object.
     *
     *  @param element the XML element node
     *  @throws OsidBinderException an error in parsing XML
     */

    protected void setElement(Element element)
	throws OsidBinderException {

	this.type = element.getAttributeNS(element.getNamespaceURI(), "type").trim();
	if (this.type.length() == 0) {
	    throw new OsidBinderException("no error type specified");
	}

	this.category = element.getAttributeNS(element.getNamespaceURI(), "category").trim();
	if (this.category.length() == 0) {
	    throw new OsidBinderException("no error category specified");
	}

	NodeList nl = element.getElementsByTagNameNS(element.getNamespaceURI(), "description");
	if (nl.getLength() == 0) {
	    throw new OsidBinderException("no description for error found");
	}

	this.description = getOsidBinderFactory().createText();
	this.description.setElement((Element) nl.item(0));
    }
}
	
