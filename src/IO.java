package bearEncoder;
import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class IO {
	
	static boolean checkAlphabet(String s, int a){
		String patternRNA="^[acgturymkswbdhvnACGTURYMKSWBDHVN]*$";
		String patternSecondaryStructure="^[.()]*$";
		if(a==0){
			if(s.matches(patternRNA)){
				return true;
			}else{
				return false;
			}
		}else{
			if(s.matches(patternSecondaryStructure)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	static int readFA(String path,LinkedList<RNA> tmp){
		FileReader f = null;
		try{
			f = new FileReader(path);
		}catch (IOException ioException){
			ioException.printStackTrace();
		}
		int count=0;
		//LinkedList<RNA> tmp = new LinkedList<RNA>();
		
		try{
			BufferedReader b = new BufferedReader(f);
			String s,name="",seq="",str="";
			boolean start=true;

			while ((s=b.readLine())!=null){
				count++;
				//System.out.println(s);
				if(!(s.replaceAll("[\r\n]","").equals(""))){
					if(start){
						if((s.substring(0,1)).equals(">")){
							name=s.substring(1).replaceAll("[\r\n]","");
							start=false;
						}else{
							System.out.println("Malformed FASTA file. Please check your file format !");
							return -1;
						}					
					}else{
						String first=s.substring(0,1);
						if(first.equals(">")){
							if(!(seq.equals("")) && !(str.equals(""))){
								tmp.add(new RNA(name,seq,str));
							}else{
								System.out.println("Malformed FASTA file. Sequence of secondary structure missing.\nPlease check your file format !");
								return count;
							}
							name=s.substring(1).replaceAll("[\r\n]","");
							seq="";
							str="";
						}else if(IO.checkAlphabet(((s.trim()).replaceAll("[\r\n]","")),0)){
							if(str.equals("")){
								seq+=((s.trim()).replaceAll("[\r\n]",""));
							}else{
								System.out.println("Malformed FASTA file. Please check your file format !");
								return count;
							}
						}else if(IO.checkAlphabet(((s.trim()).replaceAll("[\r\n]","")),1)){
							if(!(seq.equals(""))){
								str+=((s.trim()).replaceAll("[\r\n]",""));
							}else{
								System.out.println("Malformed FASTA file. Please check your file format !");
								return count;
							}
								
						}else{
							System.out.println("Unknown character in the RNA or secondary structure sequence !");
							return count;
						}
						
					}
				}				
			}
			tmp.add(new RNA(name,seq,str));
		}catch(IOException ioException){ioException.printStackTrace();}
		
		try {
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//return tmp;
		return 0;
	}
	
	static void saveOutput(String s,ListIterator<BEARNA> lit){
		try {
			//What ever the file path is.
			        File statText = new File(s);
			        FileOutputStream is = new FileOutputStream(statText);
			        OutputStreamWriter osw = new OutputStreamWriter(is);    
			        Writer w = new BufferedWriter(osw);
			        while (lit.hasNext()) {
			        	 w.write(lit.next().printable());
						}
			        w.close();
			    } catch (IOException e) {
			        System.err.println("Problem writing to the file!");
			    }
		
	}
	
}
