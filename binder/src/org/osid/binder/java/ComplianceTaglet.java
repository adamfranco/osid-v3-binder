//
// ComplianceTaglet.java
//
//     Defines the javadoc @compliance taglet.
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

package org.osid.binder.java;

import java.util.Map;


/**
 *  Defines the javadoc @compliance taglet.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class ComplianceTaglet
    extends Taglet {

    private static final String name   = "compliance";
    private static final String header = "Compliance";


    /**
     *  Gets the name of this tag.
     * 
     *  @return the tag name
     */
    
    public String getName() {
	return (this.name);
    }


    /**
     *  Gets the header of this tag.
     * 
     *  @return the tag header
     */
    
    protected String getHeader() {
	return (this.header);
    }


    /**
     *  Registers a tag.
     * 
     *  @param tagletMap
     */

    public static void register(Map<String, com.sun.tools.doclets.Taglet> tagletMap) {
	com.sun.tools.doclets.Taglet tag = new ComplianceTaglet();
	tagletMap.put(tag.getName(), tag);
	return;
    }
}
