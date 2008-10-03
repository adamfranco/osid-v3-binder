//
// PrimitiveTranslator.java
//
//     Converts OSID primitive types into Java types.
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
 *  Converts OSID primitive Types into Java types.
 *  
 *  @author  Tom Coppeto
 *  @version 3.0.0
 */

public class PrimitiveTranslator {


    static String translate(String primitive) {
	if (primitive.equals("boolean")) {
	    return ("boolean");
	}

	if (primitive.equals("cardinal")) {
	    return ("long");
	}

	if (primitive.equals("float")) {
	    return ("double");
	}

	if (primitive.equals("integer")) {
	    return ("long");
	}

	if (primitive.equals("interface")) {
	    return ("java.lang.Object");
	}

	if (primitive.equals("object")) {
	    return ("java.lang.Object");
	}

	if (primitive.equals("string")) {
	    return ("String");
	}

	if (primitive.equals("timestamp")) {
	    return ("java.util.Date");
	}

	return (primitive);
    }
}
	
