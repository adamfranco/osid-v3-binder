//
// Return.java
//
//     Defines a method return.
//  
//
// Tom Coppeto
// OnTapSolutions
// 20 May 2006
//
//
// Copyright (c) 2006 Massachusetts Institute of Technology
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
 *  Defines an OSID method.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public abstract class Return
    extends Object {
    
    private String interfaceType;
    private String primitiveType;
    private Text description;
    private boolean array;


    /**
     *  Constructs a new <code>Return</code> object. 
     */
    
    protected Return() {
	super();
	return;
    }


    /**
     *  Tests if this return type is a primitive type.
     *
     *  @return <code>true</code> if this return type is a primitive
     *          type, or <code>false</code> if this return type is
     *          an interface type
     */

    public boolean isPrimitiveType() {
	if (this.primitiveType != null) {
	    return (true);
	} else {
	    return (false);
	}
    }


    /**
     *  Tests if this is an array.
     *
     *  @return true is this is an array
     */
     
    public boolean isArray() {
	return (this.array);
    }


    /**
     *  Gets the type of this return.
     *
     *  @return a string representing the primitive or interface type
     */

    public String getType() {
	if (isPrimitiveType()) {
	    return (this.primitiveType);
	} else {
	    return (this.interfaceType);
	}
    }


    /**
     *  Translates this return type into the appropriate language binding.
     *
     *  @return the translated return type
     */

    public abstract String getBinderType();


    /**
     *  Gets the parameter description.
     *
     *  @return the parameter description
     */

    public Text getDescription() {
	return (this.description);
    }


    /**
     *  Sets the XML DOM element node and parses it into an
     *  a Return. Called from the Method object.
     *
     *  @param element the XML element node
     *  @throws OsidBinderException an error in parsing XML
     */

    protected void setElement(Element element)
	throws OsidBinderException {

	NodeList nl = element.getElementsByTagNameNS(element.getNamespaceURI(), "interfaceType");
	if (nl.getLength() > 1) {
	    throw new OsidBinderException("too many interface types in return found");
	} else if (nl.getLength() == 1) {
	    this.interfaceType = ((Element) nl.item(0)).getAttributeNS(element.getNamespaceURI(), "type").trim();
	    String b = ((Element) nl.item(0)).getAttributeNS(element.getNamespaceURI(), "array");
	    if ((b != null) && b.equals("true")) {
		this.array = true;
	    } else {
		this.array = false;
	    }
	}

	nl = element.getElementsByTagNameNS(element.getNamespaceURI(), "primitiveType");
	if (nl.getLength() > 1) {
	    throw new OsidBinderException("too many primitive types in return found");
	} else if (nl.getLength() == 1) {
	    this.primitiveType = ((Element) nl.item(0)).getAttributeNS(element.getNamespaceURI(), "type").trim();
	    String b = ((Element) nl.item(0)).getAttributeNS(element.getNamespaceURI(), "array");
	    if ((b != null) && b.equals("true")) {
		this.array = true;
	    } else {
		this.array = false;
	    }
	}
	    
	if ((primitiveType != null) && (interfaceType != null)) {
	    throw new OsidBinderException("do we really need both types?");
	}
	
	nl = element.getElementsByTagNameNS(element.getNamespaceURI(), "description");
	if (nl.getLength() == 0) {
	    throw new OsidBinderException("no description for return found");
	}

	this.description = getOsidBinderFactory().createText();
	this.description.setElement((Element) nl.item(0));
    }


    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected abstract OsidBinderFactory getOsidBinderFactory();
}
	
