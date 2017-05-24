package bearEncoder;

/*
 * BEAR encoder
 * 
 * University of Rome "Tor Vergata"
 *
 *Developer:
 * Eugenio Mattei : emattei.phd[at]gmail.com
 * 
 *Publication:
 *	Nucleic Acids Res. 2014 Jun 1. DOI : 10.1093/nar/gku283
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

import java.util.*;

public class BEARNA extends RNA {
	private String bear;
	
	public BEARNA(String name, String sequence, String secondaryStructure,String bear,boolean circular) throws ParsingException{
		super (name, sequence, secondaryStructure);
		if( !bear.equals("") ){
			this.setBEAR(bear);
		}else{
			this.setBEAR(BEARNA.encodeUsingBEAR_upgrade(secondaryStructure, circular));
		}
	}
	
	public BEARNA(String name, String sequence, String secondaryStructure, boolean circular) throws ParsingException{
		super(name, sequence, secondaryStructure);
		this.setBEAR(BEARNA.encodeUsingBEAR_upgrade(secondaryStructure, circular));
	}
	
	
	public BEARNA(RNA rna,boolean circular) throws ParsingException{
		super(rna.getName(),rna.getSequence(),rna.getSecondaryStructure());
		this.setBEAR(BEARNA.encodeUsingBEAR_upgrade(rna.getSecondaryStructure(),circular));
	}
	

	public void setBEAR(String bear) throws ParsingException{
		if( bear.equals("") ){
			this.bear = bear;
		}else if (BEARNA.checkAlphabetBear(bear) ){
			this.bear = bear;
		}else{
			throw new ParsingException("Format error for BEAR encoding!");
		}
	}
	
	public String getBEAR(){
		return this.bear;
	}
	
	static boolean checkAlphabetBear(String s){
		String patternBear="^[abcdefghi=jklmnopqrstuvwxyz^!\\\"#$%&\\'\\(\\)+234567890>\\[\\]:ABCDEFGHIJKLMNOPQRSTUVW{YZ~?_|/\\\\}@]*$";
		if(s.matches(patternBear)){
			return true;
		}else{
			return false;
		}
	}
	
	public void print(){
		if (this.getSequence()!=null){
			System.out.println(">"+this.getName()+"\n"+this.getSequence()+"\n"+this.getSecondaryStructure()+"\n"+this.getBEAR());
		}else{
			System.out.println("Empty");
		}
	}
	
	public String printable(){
		String tmp=">"+this.getName()+"\n"+this.getSequence()+"\n"+this.getSecondaryStructure()+"\n"+this.getBEAR()+"\n";
		return tmp;
	}
	
	static char[] getLoop(char[] encoding,int start, int end){
		char[] LOOP={'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		char[] MAXLOOP={'^'};
		if((end-start-2)<=LOOP.length-1){
			for(int i=start+1;i<=end-1;i++){
				encoding[i]=LOOP[end-start-2];
			}
		}else{
			for(int i=start+1;i<=end-1;i++){
				encoding[i]=MAXLOOP[0];
			}
		}
		return encoding;
	}
	
	static char[] getLoop(char[] encoding,int start, int end,int length){
		char[] LOOP={'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		char[] MAXLOOP={'^'};
		if(length-1<=LOOP.length-1){
			for(int i=start;i<=end-1;i++){
				encoding[i]=LOOP[length-1];
			}
		}else{
			for(int i=start;i<=end-1;i++){
				encoding[i]=MAXLOOP[0];
			}
		}
		return encoding;
	}
	
	static boolean isStemBranch(char encoding){	
		char[] STEMBRANCH={'A','B','C','D','E','F','G','H','I','J'};
		if(new String(STEMBRANCH).indexOf(encoding) == -1){		
			return false;
		}else{
			return true;
		}
	}
	
	static boolean isStem(char encoding){	
		char[] STEM={'a','b','c','d','e','f','g','h','i','='};
		if(new String(STEM).indexOf(encoding) == -1){		
			return false;
		}else{
			return true;
		}
	}
	
	static boolean isPaired(char encoding){	
		char[] STEM={'a','b','c','d','e','f','g','h','i','=','A','B','C','D','E','F','G','H','I','J'};
		if(new String(STEM).indexOf(encoding) == -1){		
			return false;
		}else{
			return true;
		}
	}
	
	static char[] getStem(char[] encoding,int start,int end,int length){
		char[] STEM={'a','b','c','d','e','f','g','h','i'};
		char[] MAXSTEM={'='};
		if(length-1<=STEM.length-1){
			for(int i=0;i<=length-1;i++){
				//System.out.println(end+"\t"+start);
				encoding[start+i]=STEM[length-1];
				encoding[end-i]=STEM[length-1];
			}
		}else{
			for(int i=0;i<=length-1;i++){
				encoding[start+i]=MAXSTEM[0];
				encoding[end-i]=MAXSTEM[0];
			}
		}
		return encoding;
	}
	
	static char[] getStemBranch(char[] encoding,int start,int end,int length){
		char[] STEM={'A','B','C','D','E','F','G','H','I'};
		//char[] STEM={'A','B','C','D','E','F','G','H'};
		char[] MAXSTEM={'J'};
		if(length-1<=STEM.length-1){
			for(int i=0;i<=length-1;i++){
				//System.out.println(end+"\t"+start);
				encoding[start+i]=STEM[length-1];
				encoding[end-i]=STEM[length-1];
			}
		}else{
			for(int i=0;i<=length-1;i++){
				encoding[start+i]=MAXSTEM[0];
				encoding[end-i]=MAXSTEM[0];
			}
		}
		return encoding;
	}
	
	static char[] getInternalLoopLeft(char[] encoding,int start, int end){
		char[] LEFTINTERNALLOOP={'?','!','\"','#','$','%','&','\'','(',')'};
		char MAXLEFTINTERNALLOOP='+';
		char LEFTBULGE='[';
		if (start-end==2){
			encoding[start-1]=LEFTBULGE;
		} else if(start-end-2<LEFTINTERNALLOOP.length){
			for(int i=0;i<=start-end-2;i++){
				encoding[start-1-i]=LEFTINTERNALLOOP[start-end-2];
			}
		}else{
			for(int i=0;i<=start-end-2;i++){
				encoding[start-1-i]=MAXLEFTINTERNALLOOP;
			}
		}
		return encoding;
	}
	
	static char[] getInternalLoopLeftBranch(char[] encoding,int start, int end){
		char[] LEFTINTERNALLOOP={'?','K','L','M','N','O','P','Q','R','S','T','U','V'};
		//char[] LEFTINTERNALLOOP={'?','K','L','M','N','O','P','Q','R','S','T','U'};
		char MAXLEFTINTERNALLOOP='W';
		char LEFTBULGE='{';
		//char[] LEFTINTERNALLOOP={'?','!','\"','#','$','%','&','\'','(',')'};
		//char MAXLEFTINTERNALLOOP='+';
		//char LEFTBULGE='[';
		char[] tmp= new char[(start-end-1)];
		boolean copy=true;
		if (start-end==2){
			encoding[start-1]=LEFTBULGE;
			copy=false;
		} else if(start-end-2<LEFTINTERNALLOOP.length){
			for(int i=0;i<=start-end-2;i++){
				if (encoding[start-1-i]!=':'){
					copy=false;
				}else{
					tmp[i]=LEFTINTERNALLOOP[start-end-2];
				}
				//encoding[start-1-i]=LEFTINTERNALLOOP[start-end-2];
			}
		}else{
			for(int i=0;i<=(start-end-2);i++){
				if (encoding[start-1-i]!=':'){
					copy=false;
				}else{
					tmp[i]=MAXLEFTINTERNALLOOP;
				}
				//encoding[start-1-i]=MAXLEFTINTERNALLOOP;
			}
		}
		if(copy){
			System.arraycopy(tmp,0,encoding,end+1,(start-end-1));
			return encoding;
		}else{
			return encoding;
		}
	}
	
	static char[] getInternalLoopRight(char [] encoding,int start, int end){
		char[] RIGHTINTERNALLOOP={'?','2','3','4','5','6','7','8','9','0'};
		//char[] RIGHTINTERNALLOOP={'?','2','3','4','5','6','7','0'};
		char RIGHTBULGE=']';
		char MAXRIGHTINTERNALLOOP='>';
		if(start-end==2){
			encoding[start-1]=RIGHTBULGE;
		}else if(start-end-2<RIGHTINTERNALLOOP.length){
			for(int i=0;i<=start-end-2;i++){
				encoding[start-1-i]=RIGHTINTERNALLOOP[start-end-2];
			}
		}else{
			for(int i=0;i<=start-end-2;i++){
				encoding[start-1-i]=MAXRIGHTINTERNALLOOP;
			}			
		}
		return encoding;
	}
	
	static char[] getInternalLoopRightBranch(char[] encoding,int start, int end){
		char[] RIGHTINTERNALLOOP={'?','Y','Z','~','?','_','|','/','\\'};
		char MAXRIGHTINTERNALLOOP='@';
		char RIGHTBULGE='}';
		//char[] RIGHTINTERNALLOOP={'?','2','3','4','5','6','7','8','9','0'};
		//char MAXRIGHTINTERNALLOOP='>';
		//char RIGHTBULGE=']';
		char[] tmp= new char[(start-end-1)];
		boolean copy=true;
		
		if (start-end==2){
			encoding[start-1]=RIGHTBULGE;
			copy=false;
		} else if(start-end-2<RIGHTINTERNALLOOP.length){
			for(int i=0;i<=start-end-2;i++){
				if (encoding[start-1-i]!=':'){
					copy=false;
				}else{
					tmp[i]=RIGHTINTERNALLOOP[start-end-2];
				}
				//encoding[start-1-i]=LEFTINTERNALLOOP[start-end-2];
			}
		}else{
			for(int i=0;i<=(start-end-2);i++){
				if (encoding[start-1-i]!=':'){
					copy=false;
				}else{
					tmp[i]=MAXRIGHTINTERNALLOOP;
				}
				//encoding[start-1-i]=MAXLEFTINTERNALLOOP;
			}
		}
		if(copy){
			System.arraycopy(tmp,0,encoding,end+1,(start-end-1));
			return encoding;
		}else{
			return encoding;
		}
	}
	
	
	/* Altro modo per iterare TreeMap
	Set keys=pair.keySet();
	for (Iterator i=keys.iterator();i.hasNext();){
		Integer key=(Integer)i.next();
		System.out.println(key);
	}*/
	//Set keys=pair.keySet();
	//first_father=(pair.get(keys.iterator().next()));
	//System.out.println(first_father);
	
	
	
	//New Version including the BRANCHING structures
	public static String encodeUsingBEAR_upgrade(String strInput, boolean circular){

		String structure=strInput;//input secondary structure
		Integer firstFather=0;//Index 1 for Branching structures
		Integer secondFather=-1;//Index 2 for branching structures
		Integer open=0;//open bracket index
		Integer close=0;//closed bracket index
		Integer count=0;
		Boolean exit=false;
		Boolean branch=false;
		char[] encoding=new char[structure.length()];//BEAR eccoding
		
		for (int z=0;z<encoding.length;z++){
			encoding[z]=':';
		}

		LinkedList<Integer> openBrackets=new LinkedList<Integer>();
		TreeMap<Integer,Integer> pair=new TreeMap<Integer,Integer>();
		//String[] seq=sequence.split("");
		String[] str=structure.split("");
		
		
		for (int i=0;i<str.length;i++){
			if (str[i].equals("(")){
				openBrackets.addFirst(i+1);
			}else if (str[i].equals(")")){
				int tmp=openBrackets.pop();
				pair.put(i+1, tmp);
			}
		}

		
		for (Map.Entry<Integer, Integer> entry : pair.entrySet()){
			
			//System.out.println("chiuso: "+(entry.getKey()-1)+"	aperto: "+(entry.getValue()-1)+"\n");
			//System.out.println("chiuso: "+(entry.getKey())+"	aperto: "+(entry.getValue())+"\n");
			if (secondFather==-1){
				open = entry.getValue()-1;
				close = entry.getKey()-1;
				secondFather = open;
				getLoop(encoding, open, close);
				count=1;
				/*for (int z=0;z<encoding.length;z++){
					System.out.println(z+"\t"+encoding[z]);
				}*/
					
			}else{
				if(!exit){
					if(entry.getValue()-1>open){
						if(count!=0){
							getStem(encoding,open,close,count);
						}
						count=1;
						//qui finisce una prima non branching
						open = (entry.getValue()-1);
						close = (entry.getKey()-1);
						firstFather=secondFather;
						secondFather = open;
						/*for (int z=0;z<encoding.length;z++){
							System.out.println(z+"\t"+encoding[z]);
						}*/
						//System.out.println("nuova");
						getLoop(encoding, open, close);
						/*for (int z=0;z<encoding.length;z++){
							System.out.println(z+"\t"+encoding[z]);
						}*/
					}else if(firstFather>(entry.getValue()-1)){
						exit=true;
						if (count!=0){
							//System.out.println("qui"+open+"\t"+close);
							getStem(encoding,open,close,count);
						}
						open = (entry.getValue()-1);
						close = (entry.getKey()-1);
						count=1;
						//fine di un' altra braching e non ce ne sono altre dopo
					}else{
						//System.out.println("sonoqui1");
						//System.out.println((open-(entry.getValue()-1))+"\t"+close+"\t"+(entry.getKey()-1));
						if((open-(entry.getValue()-1))>=2 && ((entry.getKey()-1)-close)>=2){
							//System.out.println("Case1");
							if (count!=0){
								getStem(encoding,open,close,count);
							}
							count=1;
							getInternalLoopLeft(encoding,open,(entry.getValue()-1));
							getInternalLoopRight(encoding,(entry.getKey()-1),close);
							/*for (int z=0;z<encoding.length;z++){
								System.out.println(z+"\t"+encoding[z]);
							}*/
						}else if((open-(entry.getValue()-1))==1 && ((entry.getKey()-1)-close)>=2){
							//System.out.println("Case2");
							if (count!=0){
								getStem(encoding,open,close,count);
							}
							count=1;
							getInternalLoopRight(encoding,(entry.getKey()-1),close);
							/*for (int z=0;z<encoding.length;z++){
								System.out.println(z+"\t"+encoding[z]);
							}*/
						}else if((open-(entry.getValue()-1))>=2 && ((entry.getKey()-1)-close)==1){
							//System.out.println("Case3");
							if (count!=0){
								getStem(encoding,open,close,count);
							}
							count=1;
							getInternalLoopLeft(encoding,open,(entry.getValue()-1));
							/*for (int z=0;z<encoding.length;z++){
								System.out.println(z+"\t"+encoding[z]);
							}*/
						}else{
							//System.out.println("Case4");
							count++;
						}
						open=(entry.getValue()-1);
						close=(entry.getKey()-1);
					}
				}else{
					
					//comincia una nuova Non Branching
					if(firstFather<(entry.getValue()-1)){
						branch=false;
						if(count!=0){
							getStemBranch(encoding,open,close,count);
						}
						open = (entry.getValue()-1);
						close = (entry.getKey()-1);
						firstFather=secondFather;
						secondFather = open;
						exit=false;
						getLoop(encoding, open, close);
						count=1;
					}else{
						branch=true;
						if((open-(entry.getValue()-1))>=2 && ((entry.getKey()-1)-close)>=2){
							//System.out.println("Case1");
							if (count!=0){
								getStemBranch(encoding,open,close,count);
							}
							count=1;
							getInternalLoopLeftBranch(encoding,open,(entry.getValue()-1));
							getInternalLoopRightBranch(encoding,(entry.getKey()-1),close);
							/*for (int z=0;z<encoding.length;z++){
								System.out.println(z+"\t"+encoding[z]);
							}*/
						}else if((open-(entry.getValue()-1))==1 && ((entry.getKey()-1)-close)>=2){
							//System.out.println("Case2");
							if (count!=0){
								getStemBranch(encoding,open,close,count);
							}
							count=1;
							getInternalLoopRightBranch(encoding,(entry.getKey()-1),close);
							/*for (int z=0;z<encoding.length;z++){
								System.out.println(z+"\t"+encoding[z]);
							}*/
						}else if((open-(entry.getValue()-1))>=2 && ((entry.getKey()-1)-close)==1){
							//System.out.println("Case3");
							if (count!=0){
								getStemBranch(encoding,open,close,count);
							}
							count=1;
							getInternalLoopLeftBranch(encoding,open,(entry.getValue()-1));
							/*for (int z=0;z<encoding.length;z++){
								System.out.println(z+"\t"+encoding[z]);
							}*/
						}else{
							//System.out.println("Case4");
							count++;
						}
						open=(entry.getValue()-1);
						close=(entry.getKey()-1);
					}
				}
			}
		}
		//controllo se ci sono non branching da scrivere
		//if(!exit){
			if(count!=0){
				if (branch || exit){
					getStemBranch(encoding,open,close,count);
				}else{
					getStem(encoding,open,close,count);
				}
			}
		//fine ultima non branching
		//}
		if(circular && checkCircular(encoding)){
			fixFinalBranch(encoding);
			return new String(encoding);
		}else{
			return new String(encoding);
		}
	}
	
	static boolean checkCircular(char[] encoding) {
		int start=0;
		while(encoding[start]==':'){
			start += 1;
		}
		if(isStemBranch(encoding[start])){
			return true;
		}else{
			return false;
		}
	}


	static char[] fixFinalBranch(char[] encoding){
		int start = 0;
		int end = encoding.length-1;
		int len = 0;
		while(encoding[start]==':'){
			len += 1;
			start += 1;
		}
		while(encoding[end]==':'){
			len += 1;
			end -= 1;
		}
		getLoop(encoding,0,start,len);
		getLoop(encoding,end+1,encoding.length,len);
		
		return encoding;
	}
	
	public void decode() throws ParsingException{
		String secondaryStructure="";
		this.setSecondaryStructure(secondaryStructure);
	}
	
	private static String decoder(String bearString){
		String[] bearArray=bearString.split("");
		String decoded="";
		for(int i=0;i<bearArray.length;i++){
			if (isPaired(bearArray[i].charAt(0))){
				decoded+="";
			}else{
				decoded+=".";				
			}
		}
		return decoded;
	}
	
	
	//Previous version without the BRANCHING
	public static String encodeUsingBEAR(String seqInput,String strInput){

		
		//String sequence=seqInput;//input rna
		String structure=strInput;//input secondary structure
		Integer firstFather=0;//serve per tener traccia delle branching
		Integer secondFather=-1;//serve per tener traccia delle branching
		Integer open=0;//indice della parentesi aperta
		Integer close=0;//indice della parentesi chiusa
		Integer count=0;
		Boolean exit=false;
		char[] encoding=new char[structure.length()];//encoding finale
		
		for (int z=0;z<encoding.length;z++){
			encoding[z]=':';
		}
		
		LinkedList<Integer> openBrackets=new LinkedList<Integer>();
		TreeMap<Integer,Integer> pair=new TreeMap<Integer,Integer>();
		//String[] seq=sequence.split("");
		String[] str=structure.split("");
		
		for (int i=1;i<str.length;i++){
			if (str[i].equals("(")){
				openBrackets.addFirst(i);
			}else if (str[i].equals(")")){
				int tmp=openBrackets.pop();
				pair.put(i, tmp);
			}
		}

		for (Map.Entry<Integer, Integer> entry : pair.entrySet()){
			
			//System.out.println("chiuso: "+(entry.getKey()-1)+"	aperto: "+(entry.getValue()-1)+"\n");
			if (secondFather==-1){
				open = entry.getValue()-1;
				close = entry.getKey()-1;
				secondFather = open;
				getLoop(encoding, open, close);
				count=1;
				/*for (int z=0;z<encoding.length;z++){
					System.out.println(z+"\t"+encoding[z]);
				}*/
					
			}else{
				if(!exit){
					if(entry.getValue()-1>open){
						if(count!=0){
							getStem(encoding,open,close,count);
						}
						count=1;
						//qui finisce una prima non branching
						open = (entry.getValue()-1);
						close = (entry.getKey()-1);
						firstFather=secondFather;
						secondFather = open;
						/*for (int z=0;z<encoding.length;z++){
							System.out.println(z+"\t"+encoding[z]);
						}*/
						//System.out.println("nuova");
						getLoop(encoding, open, close);
						/*for (int z=0;z<encoding.length;z++){
							System.out.println(z+"\t"+encoding[z]);
						}*/
					}else if(firstFather>(entry.getValue()-1)){
						exit=true;
						if (count!=0){
							//System.out.println("qui"+open+"\t"+close);
							getStem(encoding,open,close,count);
						}
						count=0;
						//fine di un' altra braching e non ce ne sono altre dopo
					}else{
						//System.out.println("sonoqui1");
						//System.out.println((open-(entry.getValue()-1))+"\t"+close+"\t"+(entry.getKey()-1));
						if((open-(entry.getValue()-1))>=2 && ((entry.getKey()-1)-close)>=2){
							//System.out.println("Case1");
							if (count!=0){
								getStem(encoding,open,close,count);
							}
							count=1;
							getInternalLoopLeft(encoding,open,(entry.getValue()-1));
							getInternalLoopRight(encoding,(entry.getKey()-1),close);
							/*for (int z=0;z<encoding.length;z++){
								System.out.println(z+"\t"+encoding[z]);
							}*/
						}else if((open-(entry.getValue()-1))==1 && ((entry.getKey()-1)-close)>=2){
							//System.out.println("Case2");
							if (count!=0){
								getStem(encoding,open,close,count);
							}
							count=1;
							getInternalLoopRight(encoding,(entry.getKey()-1),close);
							/*for (int z=0;z<encoding.length;z++){
								System.out.println(z+"\t"+encoding[z]);
							}*/
						}else if((open-(entry.getValue()-1))>=2 && ((entry.getKey()-1)-close)==1){
							//System.out.println("Case3");
							if (count!=0){
								getStem(encoding,open,close,count);
							}
							count=1;
							getInternalLoopLeft(encoding,open,(entry.getValue()-1));
							/*for (int z=0;z<encoding.length;z++){
								System.out.println(z+"\t"+encoding[z]);
							}*/
						}else{
							//System.out.println("Case4");
							count++;
						}
						open=(entry.getValue()-1);
						close=(entry.getKey()-1);
					}
				}else{
					//comincia una nuova non branching
					if(firstFather<(entry.getValue()-1)){
						open = (entry.getValue()-1);
						close = (entry.getKey()-1);
						firstFather=secondFather;
						secondFather = open;
						exit=false;
						getLoop(encoding, open, close);
						count=1;
					}
				}
			}
		}
		//controllo se ci sono non branching da scrivere
		if(!exit){
			if(count!=0){
				getStem(encoding,open,close,count);
			}
		//fine ultima non branching
		}
		return new String(encoding);
	}

}