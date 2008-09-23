//
// OsidBinder.java
//
//     Parses an XOSID.
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
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;


/**
 *  <p>
 *  Thrown to indicate a problem in translating the passwd xosid..
 *  </p>
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */


public abstract class OsidBinder
    extends Object {

    private HashMap<String, Osid> osids = new HashMap<String, Osid>();
    private String uri = "urn:inet:osid.org:schemas/osid/3";
    

    /**
     *  Constructs a new <code>OsidBinder</code>. 
     */
    
    public OsidBinder() {
	super();
    }


    /**
     *  Parses an XML XOSID file. The XML must be compliant with the
     *  urn:inet:osid.org:schemas/osid/3
     *
     *  @param directory the path to the directory containing the xosid
     *         files
     *  @throws OsidBinderException if there was a problem in accessing
     *                              or parsing the xosids
     */

    public void parse(String directory)
	throws OsidBinderException {

	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	//	dbf.setValidating(true);
	dbf.setNamespaceAware(true);
	dbf.setIgnoringElementContentWhitespace(true);

	File srcDir = new File(directory);
	if (!srcDir.exists()) {
	    throw new OsidBinderException(directory + " does not exist");
	}

	if (!srcDir.isDirectory()) {
	    throw new OsidBinderException(directory + " not a typewriter");
	}

	File[] xosids = srcDir.listFiles();
	for (File xosid: xosids) {
	    
	    if (xosid.getName().endsWith(".xosid") == false) {
		continue;
	    }

	    System.err.println("processing: " + xosid.getName());

	    Document doc;

	    try {
		DocumentBuilder builder = dbf.newDocumentBuilder();
		builder.setErrorHandler(new DefaultHandler());
		InputSource is = new InputSource(xosid.getPath());
		doc = builder.parse(is);
	    } catch (Exception e) {
		System.err.println(e.getMessage());
		throw new OsidBinderException("cannot parse XOSID", e);
	    }
	    
	    Osid osid = getOsidBinderFactory().createOsid();
	    osid.setElement(doc.getDocumentElement());
	    osids.put(osid.getName(), osid);
	}

	return;
    }
    

    /**
     *  Gets the array of <code>Osid</code> objects.
     *
     *  @return the array of <code>Osid</code> objects
     */

    public Osid[] getOsids() {

	return (this.osids.values().toArray(new Osid[this.osids.size()]));
    }


    /**
     *  Outputs this OSID to the specified directory. This method 
     *  is supplied by a language binding implementation.
     *
     *  @param directory the output directory
     *  @throws OsidBinderException if something went wrong
     */

    public abstract void print(String directory)
	throws OsidBinderException;


    /**
     *  Gets the factory.
     *
     *  @return the factory
     */

    protected abstract OsidBinderFactory getOsidBinderFactory();
}	
