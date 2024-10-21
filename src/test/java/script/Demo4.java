package script;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Demo4 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties p=new Properties();
		p.load(new FileInputStream("./config.properties"));
		String v = p.getProperty("URL");
		System.out.println(v);
		
		System.out.println(p.getProperty("BROWSER"));
		
	

	}

}
