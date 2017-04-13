package bearEncoder;
import java.util.*;


public class BEARNA extends RNA {
	private String bear;
	
	public BEARNA(String name, String sequence, String secondaryStructure){
		super (name, sequence, secondaryStructure);
		this.bear=BEARNA.encodeUsingBEAR_upgrade(sequence,secondaryStructure);
	}
	
	
	public BEARNA(RNA rna){
		super(rna.getName(),rna.getSequence(),rna.getSecondaryStructure());
		this.bear=BEARNA.encodeUsingBEAR_upgrade(rna.getSequence(),rna.getSecondaryStructure());
	}
	
	public BEARNA(){
		this.setName("");
		this.setSequence("");
		this.setSecondaryStructure("");
		this.bear="";
	}
	
	public void setBEAR(String bear){
		if (this.getSequence().length()==bear.length()){
			this.bear=bear;
		}else{
			System.err.println("Secondary Structure and BEAR must have the same length.\n");
		}
	}
	
	public String getBEAR(){
		return this.bear;
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
	
	public static String encodeUsingBEAR(String seqInput,String strInput){

		
		String sequence=seqInput;//input rna
		String structure=strInput;//input secondary structure
		Integer firstFather=0;//serve per tener traccia delle branching
		Integer secondFather=-1;//serve per tener traccia delle branching
		Integer open=0;//indice della parentesi aperta
		Integer close=0;//indice della parentesi chiusa
		Integer count=0;
		Boolean exit=false;
		char[] encoding=new char[sequence.length()];//encoding finale
		
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
	
	
	//Comprende Pure le branching
	public static String encodeUsingBEAR_upgrade(String seqInput,String strInput){

		
		String sequence=seqInput;//input rna
		String structure=strInput;//input secondary structure
		Integer firstFather=0;//serve per tener traccia delle branching
		Integer secondFather=-1;//serve per tener traccia delle branching
		Integer open=0;//indice della parentesi aperta
		Integer close=0;//indice della parentesi chiusa
		Integer count=0;
		Boolean exit=false;
		Boolean branch=false;
		char[] encoding=new char[sequence.length()];//encoding finale
		
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
		return new String(encoding);
	}
	
}