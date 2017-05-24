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


public class RNA {
	private String name;
	private String sequence;
	private String secondaryStructure;
	
	public RNA(String name, String sequence, String secondaryStructure) throws ParsingException{
		this.setName(name);
		this.setSequence(sequence);
		if( !secondaryStructure.equals("")){
			this.setSecondaryStructure(secondaryStructure);
		}else{
			this.secondaryStructure="";
		}
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
		if( this.sequence.length() == secondaryStructure.length() ){
			this.secondaryStructure=secondaryStructure;
		}else{
			throw new ParsingException("Sequence and Seconddary Structure must have the same length!");
		}
	
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
	static boolean checkSecondaryStructure(String str) throws ParsingException{
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
        return true;
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
