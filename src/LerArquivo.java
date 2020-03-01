import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LerArquivo {

	public void lerArquivo(String fileName) {
		try {
			BufferedReader reader = getBuferredReader(fileName);
			while(reader.ready()) {
				System.out.println(reader.readLine());
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	protected BufferedReader getBuferredReader(String fileName) throws FileNotFoundException {
		return new BufferedReader(new FileReader(fileName));
	}
}
