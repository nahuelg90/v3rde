package core.exception;

import java.io.IOException;


@SuppressWarnings("serial")
public class ServerFailedException extends RuntimeException {

	public ServerFailedException(IOException ex) {
		super(ex);
	}

}
