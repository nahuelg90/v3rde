package core.exception;

import org.xml.sax.SAXException;


@SuppressWarnings("serial")
public class ParserException extends RuntimeException {

	public ParserException(SAXException ex) {
		super(ex);
	}

}
