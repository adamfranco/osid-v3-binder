

validate:
	@for file in `ls xosid/*.xosid`; do \
		echo validating $$file...; \
		java -jar lib/jing.jar -c schema/xosid.rnc $$file; \
		if [ $$? != 0 ]; then \
			echo "error validating xosids"; \
			exit 1; \
		fi \
	done

