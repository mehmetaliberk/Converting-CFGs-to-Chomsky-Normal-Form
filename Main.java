
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

 public class Main {



    public static void main(String args[]) throws IOException
    {
        String final_string;

        File fileDir = new File("CFG.txt");
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
		int lineCount = 0;
		in.readLine();
		while (in.readLine() != null) { // Getting line amount
			lineCount++;
		}
		String Readstr;
		String[] str = new String[lineCount];
		in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
		String alp=in.readLine();
		int w = 0;
		while ((Readstr = in.readLine()) != null) {
			str[w] = Readstr;
			w++;
		}
		System.out.println("Given Context Free Grammar");
		System.out.println(alp);
		for (int j = 0; j < str.length; j++) {
			System.out.println(str[j]);
		}
		in.close();
        
        final_string=str[0]+"\n";  

        for(int i=1;i<lineCount-1;i++)
            final_string+=str[i]+"\n";

        final_string+=str[lineCount-1];  
        
          
        CFGtoCNF c= new CFGtoCNF();
        c.setInputandLineCount(final_string,lineCount);
        c.convertCFGtoCNF();
    }
 }


   

   



