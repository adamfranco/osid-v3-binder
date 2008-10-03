//
// Osid.java
//
//     Defins an OSID.
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *  Defines an OSID.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public abstract class Osid
    extends Object {
    
    private String name;
    private String title;
    private String version;
    private Text description;
    private Text copyright;
    private Text license;
    private ArrayList<Interface> interfaces = new ArrayList<Interface>();
    private ArrayList<Enumeration> enumerations = new ArrayList<Enumeration>();
    private String uri = "urn:inet:osid.org:schemas/osid/3";


    /**
     *  Constructs a new <code>Osid</code>.
     */

    protected Osid() {
	super();
	return;
    }


    /**
     *  Gets the qualified name of this OSID.
     *
     *  @return the qualified name of this OSID
     */

    public String getPackageName() {
	return (this.name);
    }


    /**
     *  Gets the name of this OSID.
     *
     *  @return the name of this OSID
     */

    public String getName() {
	int pos = this.name.lastIndexOf('.');
	if (pos < 0) {
	    return (this.name);
	} else {
	    return (this.name.substring(pos+1));
	}
    }


    /**
     *  Gets the title of this OSID.
     *
     *  @return the title of this OSID
     */

    public String getTitle() {
	return (this.title);
    }


    /**
     *  Gets the version of this OSID.
     *
     *  @return the version of this OSID
     */

    public String getVersion() {
	return (this.version);
    }


    /**
     *  Gets the description of this OSID.
     *
     *  @return the description of this OSID
     */

    public Text getDescription() {
	return (this.description);
    }


    /**
     *  Gets the copyright of this OSID.
     *
     *  @return the copyright of this OSID
     */

    public Text getCopyright() {
	return (this.copyright);
    }


    /**
     *  Gets the license of this OSID.
     *
     *  @return the license of this OSID
     */

    public Text getLicense() {
	return (this.license);
    }


    /**
     *  Gets the interfaces defined in this OSID.
     *
     *  @return the array of interfaces
     */

    public Interface[] getInterfaces() {

	return (this.interfaces.toArray(new Interface[this.interfaces.size()]));
    }


    /**
     *  Gets the enumerations defined in this OSID.
     *
     *  @return the array of enumerations
     */

    public Enumeration[] getEnumerations() {

	return (this.enumerations.toArray(new Enumeration[this.enumerations.size()]));
    }


    /**
     *  Sets the XML DOM element node and parses it into an
     *  an OSID. Called from the OsidBinder.
     *
     *  @param element the XML element node
     *  @throws OsidBinderException an error in parsing XML
     */

    protected void setElement(Element element)
	throws OsidBinderException {

	this.name    = element.getAttributeNS(this.uri, "name");
	this.version = element.getAttributeNS(this.uri, "version");

	if (this.name.length() == 0) {
	    throw new OsidBinderException("no OSID name specified (we need to know)");
	}

	if (this.version.length() == 0) {
	    throw new OsidBinderException("no OSID version specified");
	}

	NodeList nl = element.getChildNodes();
	for (int i = 0; i < nl.getLength(); i++) {

	    if (nl.item(i).getNodeType() != Node.ELEMENT_NODE) {
		continue;
	    }

	    if (!this.uri.equals(nl.item(i).getNamespaceURI())) {
		continue;
	    }

	    if (nl.item(i).getLocalName().equals("title")) {
		NodeList cl = nl.item(i).getChildNodes();
		this.title = cl.item(0).getNodeValue().trim();
	    }

	    if (nl.item(i).getLocalName().equals("copyright")) {
		if (this.copyright != null) {
		    throw new OsidBinderException("multiple copyright statements found (smoosh them together)");
		} 

		this.copyright = getOsidBinderFactory().createText();
		this.copyright.setElement((Element) nl.item(i));
	    } 
	    
	    if (nl.item(i).getLocalName().equals("license")) {
		if (this.license != null) {
		    throw new OsidBinderException("multiple license statements found (smoosh them together)");
		} 

		this.license = getOsidBinderFactory().createText();
		this.license.setElement((Element) nl.item(i));
	    } else if (nl.item(i).getLocalName().equals("description")) {
		if (this.description != null) {
		    throw new OsidBinderException("multiple description statements found (smoosh them together)");
		} 

		
		this.description = getOsidBinderFactory().createText();
		this.description.setElement((Element) nl.item(i));
	    } else if (nl.item(i).getLocalName().equals("interface")) {
		Interface iface = getOsidBinderFactory().createInterface();
		iface.setElement((Element) nl.item(i), this.copyright, this.license);
		this.interfaces.add(iface);
		Interfaces.add(iface);
	    } else if (nl.item(i).getLocalName().equals("enumeration")) {
		Enumeration en = getOsidBinderFactory().createEnumeration();
		en.setElement((Element) nl.item(i), this.copyright, this.license);
		this.enumerations.add(en);
	    }
	}

	if (this.description == null) {
	    throw new OsidBinderException("no OSID description specified (people might want to know)");
	}

	if (this.interfaces.size() == 0) {
	    throw new OsidBinderException("no interfaces specified (what's the point?)");
	}

	this.name = qualifyOsidName(this.name);
	return;
    }


    /**
     *  Qualifies the OSID package name. This method
     *  is supplied by a language binding implementation.
     *
     *  @param name package name to qualify
     *  @return the qualified name
     *  @throws OsidBinderException if something went wrong
     */

    protected abstract String qualifyOsidName(String name)
	throws OsidBinderException;


    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected abstract OsidBinderFactory getOsidBinderFactory();
}

