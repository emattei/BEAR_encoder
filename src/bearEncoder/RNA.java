package bearEncoder;

public class RNA {
	private String name;
	private String sequence;
	private String secondaryStructure;
	
	public RNA(String name, String sequence, String secondaryStructure){
		if (secondaryStructure.length()==sequence.length()){
			this.name=name;
			this.sequence=sequence;
			if(this.checkSecondaryStructure(secondaryStructure)){
				this.secondaryStructure=secondaryStructure;
			}else{
				System.err.println("Error: Unbalanced parenthesis. Check the secondary structure and try again.");
			}
		}else{
			System.err.println("Sequence and Secondary Structure must have the same length.\n");
			System.exit(-1);
		}
	}
	
	public RNA(String name, String sequence){
			this.name=name;
			this.sequence=sequence;
			this.secondaryStructure="";
	}
	
	public RNA(){
		this.name="";
		this.sequence="";
		this.secondaryStructure="";
	}
	
	public void setName(String accession){
		this.name=accession;
	}
	
	public void setSequence(String sequence){
		if (this.secondaryStructure.length()==sequence.length() || this.secondaryStructure.length()==0){
			this.sequence=sequence;
		}else{
			System.err.println("Sequence and Secondary Structure must have the same length.\n");
			System.exit(-1);
		}
	}
	
	public void setSecondaryStructure(String secondaryStructure){
		if (this.sequence.length()==secondaryStructure.length() || this.sequence==""){
			this.secondaryStructure=secondaryStructure;
		}else{
			System.err.println("Sequence and Secondary Structure must have the same length.\n");
			System.exit(-1);
		}
		
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getSequence(){
		return this.sequence;
	}
	
	public String getSecondaryStructure(){
		return this.secondaryStructure;
	}
	
	public boolean checkSecondaryStructure(String str){
		char[] cbuff = new char[1024 * 1024];
		int count=0;
		int size = str.length();
        str.getChars(0, size, cbuff, 0);
        for (int i = 0; i < size; i++) {
            if(cbuff[i]<='('){
            	count++;
            }else if (cbuff[i]<=')'){
            	count--;
            	if(count<0){
            		return false;
            	}
            }
        }
        if(count==0){
        	return true;
        }else{
        	return false;
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
