package bearEncoder;

public class RNA {
	private String name;
	private String sequence;
	private String secondaryStructure;
	
	public RNA(String name, String sequence, String secondaryStructure) throws ParsingException{
		this.setName(name);
		this.setSequence(sequence);
		this.setSecondaryStructure(secondaryStructure);
	}
	
	public RNA(String name, String sequence) throws ParsingException{
		this.setName(name);
		this.setSequence(sequence);
		this.secondaryStructure="";
	}
	
	public RNA(){
		this.name="";
		this.sequence="";
		this.secondaryStructure="";
	}
	
	//set Functions
	public void setName(String accession){
		this.name=accession;
	}
	
	public void setSequence(String sequence) throws ParsingException{
		RNA.checkAlphabet(sequence);
		this.sequence=sequence;
	}
	
	public void setSecondaryStructure(String secondaryStructure) throws ParsingException{
		RNA.checkSecondaryStructure(secondaryStructure);
		this.secondaryStructure=secondaryStructure;
	
	}
	
	//get Functions
	public String getName(){
		return this.name;
	}
	
	public String getSequence(){
		return this.sequence;
	}
	
	public String getSecondaryStructure(){
		return this.secondaryStructure;
	}
	
	//Check secondary structure consistency
	static void checkSecondaryStructure(String str) throws ParsingException{
		char[] cbuff = new char[1024 * 1024];
		String patternSecondaryStructure="^[.()]*$";
		
		if(!str.matches(patternSecondaryStructure)){
			throw new ParsingException("Unrecognize charachter in secondary structure!");
		}
		
		int count=0;
		int size = str.length();
        str.getChars(0, size, cbuff, 0);
        for (int i = 0; i < size; i++) {
            if(cbuff[i]<='('){
            	count++;
            }else if (cbuff[i]<=')'){
            	count--;
            	if(count<0){
            		throw new ParsingException("Unbalanced parenthesis in secondary structure!");
            	}
            }
        }
        if(count!=0){
        	throw new ParsingException("Unbalanced parenthesis in secondary structure!");
        }
	}
	
	//Check for nucleotides not present in IUPAC notation
	static void checkAlphabet(String s) throws ParsingException{
		String patternRNA="^[acgturymkswbdhvnACGTURYMKSWBDHVN]*$";
		if(!s.matches(patternRNA) && !s.equals("")){
			throw new ParsingException("Sequence nucleotides not in the IUPAC notation!");
		}
	}
	
	
	public void print(){
		if (this.getSequence()!=null){
			System.out.println(">"+this.getName()+"\n"+this.getSequence()+"\n"+this.getSecondaryStructure());
		}else{
			System.out.println("Empty");
		}
	}

}
