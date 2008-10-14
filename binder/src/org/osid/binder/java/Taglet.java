//
// Taglet.java
//
//     Defines a generic taglet.
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

package org.osid.binder.java;


/**
 *  Defines a generic taglet.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public abstract class Taglet
    implements com.sun.tools.doclets.Taglet {


    /**
     *  Gets the name of this tag.
     *
     *  @return the tag name
     */

    public abstract String getName();


    /**
     *  Gets the header of this tag.
     *
     *  @return the tag header
     */

    protected abstract String getHeader();


    /**
     *  Tests if tag may appear in a field.
     * 
     *  @return <code>false</code>
     */
    
    public boolean inField() {

	return (false);
    }


    /**
     *  Tests if tag may appear in a constructor.
     * 
     *  @return <code>false</code>
     */
    
    public boolean inConstructor() {

	return (false);
    }


    /**
     *  Tests if tag may appear in a method.
     * 
     *  @return <code>true</code>
     */
    
    public boolean inMethod() {

	return (true);
    }


    /**
     *  Tests if tag may appear in an overview.
     * 
     *  @return <code>false</code>
     */
    
    public boolean inOverview() {

	return (false);
    }


    /**
     *  Tests if tag may appear in a package.
     * 
     *  @return <code>false</code>
     */
    
    public boolean inPackage() {

	return (false);
    }


    /**
     *  Tests if tag may appear in a type.
     * 
     *  @return <code>false</code>
     */
    
    public boolean inType() {

	return (false);
    }

    /**
     *  Tests if tag is inline.
     * 
     *  @return <code>false</code>
     */
    
    public boolean isInlineTag() {

	return (false);
    }


    /**
     *  Converts tag to html.
     *
     *  @param tag tag to convert
     *  @return html
     */

    public String toString(com.sun.javadoc.Tag tag) {
	
	return ("<DT><B>" + getHeader() + ":</B><DD>"
		+ "<code>" + getFirstWord(tag.text()) + "</code> - " 
		+ getRestOfWords(tag.text()) + "</DD>\n");
    }


    /**
     *  Converts array of tags to html.
     *
     *  @param tags array of tags to convert
     *  @return html
     */

    public String toString(com.sun.javadoc.Tag[] tags) {

	if (tags.length == 0) {
	    return null;
	}

	String result = "\n<DT><B>" + getHeader() + ": </B><DD>";
	for (int i = 0; i < tags.length; i++) {
	    if (i > 0) {
		result += "</DD><DD>";
	    }
	    result += "<code>" + getFirstWord(tags[i].text()) + "</code> - " 
		+ getRestOfWords(tags[i].text()) + " ";
	}

	result = result + "</DD>\n";
	return (result);
    }


    private String getFirstWord(String text) {
	
	int pos = text.indexOf(' ');
	if (pos < 0) {
	    return (text);
	}

	return (text.substring(0, pos));
    }


    private String getRestOfWords(String text) {
	
	int pos = text.indexOf(' ');
	if (pos < 0) {
	    return (text);
	}

	return (text.substring(pos + 1));
    }

}
