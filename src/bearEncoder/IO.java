package bearEncoder;
import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class IO {
	static LinkedList<BEARNA> readFA(String path,boolean circular){
		FileReader f = null;
		try{
			f = new FileReader(path);
		}catch (IOException ioException){
			ioException.printStackTrace();
		}
		int lineNumber=0;
		LinkedList<BEARNA> tmp = new LinkedList<BEARNA>();
		
		try{
			BufferedReader b = new BufferedReader(f);
			String s,name="",seq="",str="";
			boolean start=true;

			while ((s=b.readLine())!=null){
				//System.out.println(s);
				//if(!(s.replaceAll("[\r\n]","").equals(""))){
					if(start){
						if((s.substring(0,1)).equals(">")){
							lineNumber++;
							name=s.substring(1).replaceAll("[\r\n]","");
							start=false;
						}else{
							IO.writeErrorFile("Line "+lineNumber+": FASTA format error!");
						}					
					}else{
						String first=s.substring(0,1);
						if(first.equals(">")){
							lineNumber++;
							try{
								tmp.add(new BEARNA(name,seq,str,circular));
							}catch(ParsingException ex){
								IO.writeErrorFile("Line "+lineNumber+": FASTA format error.\n"+ex.getMessage());
							}
							name=s.substring(1).replaceAll("[\r\n]","");
							seq="";
							str="";
						}else if(IO.isNucleotide(((s.trim()).replaceAll("[\r\n]","")))){
							if(str.equals("")){
								seq+=((s.trim()).replaceAll("[\r\n]",""));
							}
						}else if(IO.isSecondaryStructure(((s.trim()).replaceAll("[\r\n]","")))){
							if(!(seq.equals(""))){
								str+=((s.trim()).replaceAll("[\r\n]",""));
							}
						}
					}
				//}else{
				//	System.out.println("manca ");
				//	IO.writeErrorFile("Line "+lineNumber+": FASTA format error!");
				//}
			}
			try{
				tmp.add(new BEARNA(name,seq,str,circular));
			}catch(ParsingException ex){
				IO.writeErrorFile("Line "+lineNumber+": FASTA format error !\n"+ex.getMessage());
			}
		}catch(IOException ioException){ioException.printStackTrace();}
		
		try {
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tmp;
	}
	
	
	static boolean isNucleotide(String s){
		String patternRNA=".*[acgturymkswbdhvnACGTURYMKSWBDHVN]+";
		if(s.matches(patternRNA)){
			return true;
		}else{
			return false;
		}
	}
	
	static boolean isSecondaryStructure(String str){
		String patternSecondaryStructure=".*[.()]*";
		if(str.matches(patternSecondaryStructure)){
			return true;
		}else{
			return false;
		}
	}
	
	static void saveOutput(String s,ListIterator<BEARNA> lit){
		try {
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
	
	static void writeErrorFile(String error){
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			File file = new File("error.encoder");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(error+"\n");

		    } catch (IOException e) {
		        System.err.println("Problem writing to the file!");
		        e.printStackTrace();
		    } finally {
				try {
					if (bw != null)
						bw.close();
					
					if (fw != null)
						fw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
		    }
	}
	
}
