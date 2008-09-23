//
// Method.java
//
//     Defines a method.
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
 *  <p>
 *  Defines a method.
 *  </p>
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public abstract class Method
    extends Object {

    private org.w3c.dom.Document doc;
    private String name;
    private Text description;
    private Return ret;
    private Compliance compliance;
    private Text notes;
    private ArrayList<Parameter> parameters = new ArrayList<Parameter>();
    private ArrayList<Error> errors = new ArrayList<Error>();


    /**
     *  Constructs a new <code>Method</code> object. 
     */

    protected Method() {
	super();
    }


    /**
     *  Gets the name of this method.
     *
     *  @return the method name
     */

    public String getName() {
	return (this.name);
    }


    /**
     *  Gets the description of this method.
     *
     *  @return the method description
     */

    public Text getDescription() {
	return (this.description);
    }


    /**
     *  Gets the compliance statement for this method.
     *
     *  @return the compliance
     */

    public Compliance getCompliance() {
	return (this.compliance);
    }


    /**
     *  Gets the notes statement for this method.
     *
     *  @return the notes
     */

    public Text getNotes() {
	return (this.notes);
    }


    /**
     *  Gets the parameters of this method.
     *
     *  @return the array of method parameters
     */

    public Parameter[] getParameters() {
	return (this.parameters.toArray(new Parameter[this.parameters.size()]));
    }


    /**
     *  Gets the return of this method.
     *
     *  @return the method return
     */

    public Return getReturn() {
	return (this.ret);
    }


    /**
     *  Gets the errors of this method.
     *
     *  @return the array of method errors
     */

    public Error[] getErrors() {
	return (this.errors.toArray(new Error[this.errors.size()]));
    }

    
    /**
     *  Adds an error to this method.
     */

    public void addError(String type, String category, String description) {
	Error error = getOsidBinderFactory().createError();
	error.setType(type);
	error.setCategory(category);
	error.setDescription(doc, description);
	
	this.errors.add(error);
	return;	
    }


    /**
     *  Sets the XML DOM element node and parses it into an
     *  a Method. Called from the Interface object.
     *
     *  @param element the XML element node
     *  @throws OsidBinderException an error in parsing XML
     */

    protected void setElement(Element element)
	throws OsidBinderException {

	this.doc = element.getOwnerDocument();
	this.name = element.getAttributeNS(element.getNamespaceURI(), "name");

	if (this.name.length() == 0) {
	    throw new OsidBinderException("no method name specified");
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
                    throw new OsidBinderException("too many interface descriptions found");
                }

		this.description = getOsidBinderFactory().createText();
                this.description.setElement((Element) nl.item(i));
            } else if (nl.item(i).getLocalName().equals("parameter")) {
		Parameter parameter = getOsidBinderFactory().createParameter();
		parameter.setElement((Element) nl.item(i));
		this.parameters.add(parameter);
            } else if (nl.item(i).getLocalName().equals("return")) {
		if (this.ret != null) {
		    throw new OsidBinderException("too many return statements in method");
		}

		this.ret = getOsidBinderFactory().createReturn();
		this.ret.setElement((Element) nl.item(i));
            } else if (nl.item(i).getLocalName().equals("error")) {
		Error error = getOsidBinderFactory().createError();
		error.setElement((Element) (nl.item(i)));
		this.errors.add(error);
	    } else if (nl.item(i).getLocalName().equals("compliance")) {
		if (this.compliance != null) {
		    throw new OsidBinderException("too many compliance statements in method");
		}
		
		this.compliance = getOsidBinderFactory().createCompliance();
		this.compliance.setElement((Element) nl.item(i));
	    } else if (nl.item(i).getLocalName().equals("implNotes")) {
		this.notes = getOsidBinderFactory().createText();
		this.notes.setElement((Element) nl.item(i));
	    }
	}
	
	if (this.description == null) {
	    throw new OsidBinderException("no method description found");
	}
	
	if (this.compliance == null) {
	    throw new OsidBinderException("no method compliance found");
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
	
