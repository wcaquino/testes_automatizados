import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class Teste {

	@Spy
	public LerArquivo fileClass;
	
	@Mock
	public BufferedReader reader;
	
	@Test(expected=RuntimeException.class)
	public void test() throws IOException{
		MockitoAnnotations.initMocks(this);
		Mockito.doReturn(reader).when(fileClass).getBuferredReader(Mockito.anyString());
		Mockito.doReturn(true).when(reader).ready();
		Mockito.doThrow(new IOException("exceção")).when(reader).readLine();
		fileClass.lerArquivo("arquivoQualquer.txt");
	}
}
