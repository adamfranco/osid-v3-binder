//
// Text.java
//
//     Defines a text element and methods to pretty print.
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
import java.util.StringTokenizer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *  <p>
 *  Defines a text element and methods to format output.
 *  </p>
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class Text
    extends Object {
    
    private Element text;
    private String formatForJDoc;
    private String formatForHeader;
    private boolean paragraph;


    /**
     *  Constructs a new <code>Text</code> object. 
     */

    public Text() {
	super();
    }


    /**
     *  Sets the XML DOM element node.
     *
     *  @param element the XML element node
     *  @throws OsidBinderException an error in parsing XML
     */

    public void setElement(Element element)
	throws OsidBinderException {

	this.text = element;
	return;
    }


    /**
     *  Sets the text of this node.
     *
     *  @param doc the dom document
     *  @param s string to set
     */

    public void setText(Document doc, String s) {
	org.w3c.dom.Text t = doc.createTextNode(s);
	if (this.text == null) {
	    this.text = doc.createElement("node");
	}
	while (this.text.hasChildNodes()) {
	    this.text.removeChild(this.text.getLastChild());
	}
	this.text.appendChild(t);
	return;
    }


    /**
     *  Adds a node to this text node.
     *
     *  @param element the XML element node
     */

    public void addElement(Element element) {

	if (this.text == null) {
	    this.text = element;
	} else {
	    this.text.appendChild(element);
	}
	return;
    }


    /**
     *  Adds some text to this text node.
     *
     *  @param text string to append
     */

    public void addText(String text) {

	Document doc = this.text.getOwnerDocument();
	org.w3c.dom.Text t = doc.createTextNode(text);
	this.text.appendChild(t);

	return;
    }


    /**
     *  Outputs this text using the specified margin.
     *
     *  @param out the output stream
     *  @param margin the size of the left margin
     */

    public void printPlain(PrintStream out, String margin) {
	printPlain(out, this.text, margin, 0, false);
	return;
    }


    /**
     *  Outputs this text as html using the specified margin.
     *
     *  @param out the output stream
     *  @param margin the size of the left margin
     */

    public void printHtmlParagraph(PrintStream out, String margin) {
	int cc;

	this.paragraph = true;
	out.print(margin + "<p>");
	cc = printHtml(out, this.text, margin, margin.length() + 3);
	if (cc > 74) {
	    out.println();
	    out.print(margin + "</p>");
	} else {
	    out.print("</p>");
	}
	return;
    }


    /**
     *  Outputs this text as html using the specified margin.
     *
     *  @param out the output stream
     *  @param margin the size of the left margin
     */

    public void printHtml(PrintStream out, String margin) {
	int cc;

	this.paragraph = false;
	cc = printHtml(out, this.text, margin, margin.length() + 3);

	return;
    }


    /**
     *  Outputs this text as html using the specified margin. The starting
     *  column is for text that may already appear on the line that should
     *  be taken into account for wrapping.
     *
     *  @param out the output stream
     *  @param margin the size of the left margin
     *  @param col the starting column of the first line
     */

    public int printHtml(PrintStream out, String margin, int col) {
	return(printHtml(out, this.text, margin, col));
    }


    private int printPlain(PrintStream out, Element element, String margin, int col, boolean code) {

	NodeList nl = element.getChildNodes();
	for (int i = 0; i < nl.getLength(); i++) {
	    if (nl.item(i).getNodeType() == Node.TEXT_NODE) {
		col = printPlain(out, nl.item(i).getNodeValue(), margin, col, code);
	    } else if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
		if (nl.item(i).getLocalName().equals("code")) {
		    out.println();
		    out.print(margin + "    ");
		    col = margin.length() + 4;
		    printPlain(out, (Element) nl.item(i), margin + "    ", col, true);
		    out.println();
		    out.print(margin);
		    col = margin.length();
		} else if (nl.item(i).getLocalName().equals("pbreak")) {
		    out.println();
		    out.println(margin);
		    out.print(margin);
		    col = margin.length();
		} else if (nl.item(i).getLocalName().equals("copyrightSymbol")) {
		    out.print("(C) ");
		    col += 4;
		} else if (nl.item(i).getLocalName().equals("element")) {
		    out.println();
		    out.print(margin + "    * ");
		    col = margin.length() + 6;
		    col = printPlain(out, (Element) nl.item(i), margin + "      ", col, code);
		    out.println();
		    out.print(margin + "    * ");
		    col = margin.length() + 6;		    
		} else if (nl.item(i).getLocalName().equals("outline")) {
		    out.println();
		    out.print(margin);
		    col = margin.length();
		    col = printPlain(out, (Element) nl.item(i), margin, col, code);
		    out.println();
		    out.print(margin);
		    col = margin.length();
		} else if (nl.item(i).getLocalName().equals("token")) {
		    col = printPlain(out, (Element) nl.item(i), margin, col, code);
		}
	    }
	}

	return (col);
    }


    public static int printPlain(PrintStream out, String text, String margin, int col, boolean code) {

	if (code) {
	    text = stripCode(text);
	    out.print(margin);
	    for (int i = 0; i < text.length(); i++) {
		char c = text.charAt(i);
		out.print(c);
		if (c == '\n') {
		    out.print(margin);
		}
	    }
	    return (0);
	}

	StringTokenizer st = new StringTokenizer(text);

	if (col == 0) {
	    out.print(margin);
	    col += margin.length();
	}

	while(st.hasMoreTokens()) {
	    String s = st.nextToken();
	    if ((s.length() + col) > 78) {
		out.println();
		out.print(margin);
		col = margin.length();
	    }

	    out.print(s);
	    out.print(" ");
	    col = col + s.length() + 1;
	}
	
	return (col);
    }


    private int printHtml(PrintStream out, Element element, String margin, int col) {

	NodeList nl = element.getChildNodes();
	for (int i = 0; i < nl.getLength(); i++) {
	    if (nl.item(i).getNodeType() == Node.TEXT_NODE) {
		col = printHtml(out, nl.item(i).getNodeValue(), margin, col);
	    } else if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
		if (nl.item(i).getLocalName().equals("code")) {
		    out.println();
		    out.println(margin + "<pre>");
		    out.println(margin + "     ");
		    col = margin.length() + 5;
		    printPlain(out, (Element) nl.item(i), margin + "     ", col, true);
		    out.println();
		    out.println(margin + "</pre>");
		    out.print(margin);
		    col = margin.length();
		} else if (nl.item(i).getLocalName().equals("pbreak")) {
		    if (this.paragraph) {
			col = printPlain(out, "</p>", margin, col, false);
			out.println();
			out.println(margin);
			out.print(margin);
			col = printPlain(out, "<p>", margin, margin.length(), false);
		    } else {
			out.println();
			out.println(margin + "<br/><br/>");
			out.print(margin);
			col = margin.length();
		    }
		} else if (nl.item(i).getLocalName().equals("copyrightSymbol")) {
		    col = printPlain(out, "&copy", margin, col, false);
		} else if (nl.item(i).getLocalName().equals("element")) {
		    col = printPlain(out, "<li>", margin, col, false);
		    col = printHtml(out, (Element) nl.item(i), margin, col);
		    col = printPlain(out, "</li>", margin, col, false);
		    out.println();
		    if (i < (nl.getLength() - 2)) {
			out.print(margin);
			col = margin.length();
		    }
		} else if (nl.item(i).getLocalName().equals("outline")) {
		    out.println();
		    out.println(margin + "<ul>");
		    out.print(margin + "    ");
		    col = margin.length() + 4;
		    col = printHtml(out, (Element) nl.item(i), margin + "    ", col);
		    out.println(margin + "</ul>");
		    out.print(margin);
		    col = margin.length();
		} else if (nl.item(i).getLocalName().equals("token")) {
		    col = printPlain(out, "<code>", margin, col, false);
		    col = printPlain(out, (Element) nl.item(i), margin, col, false);
		    col = printPlain(out, "</code>", margin, col, false);
		}
	    }
	}

	return (col);
    }


    private int printHtml(PrintStream out, String text, String margin, int col) {
	StringTokenizer st = new StringTokenizer(text);

	if (col == 0) {
	    out.print(margin);
	    col += margin.length();
	}
	
	while(st.hasMoreTokens()) {
	    String s = st.nextToken();
	    if ((s.length() + col) > 78) {
		out.println();
		out.print(margin);
		col = margin.length();
	    }

	    out.print(s);
	    out.print(" ");
	    col = col + s.length() + 1;
	}
	
	return (col);
    }


    private static String stripCode(final String code) {
	String res = code;

	while (true) {
	    if (res.startsWith("\n")) {
		res = res.substring(1);
	    } else {
		break;
	    }
	}
	    
	while(true) {
	    if (res.endsWith("\n\n")) {
		res = res.substring(0, res.length() - 1);
	    } else {
		break;
	    }
	}

	return (res);
    }
}