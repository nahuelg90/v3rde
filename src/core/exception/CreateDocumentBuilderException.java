package core.exception;
import javax.xml.parsers.ParserConfigurationException;


@SuppressWarnings("serial")
public class CreateDocumentBuilderException extends RuntimeException {

	public CreateDocumentBuilderException(ParserConfigurationException ex) {
		super(ex);
	}




}
