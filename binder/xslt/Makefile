
all:
	@for file in xosid/*.xosid; do \
		java org.apache.xalan.xslt.Process -IN $$file -XSL xosid2Java.xsl; \
	done
