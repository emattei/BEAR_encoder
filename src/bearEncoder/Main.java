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
import java.util.LinkedList;
import java.util.ListIterator;

public class Main {

	public static void main(String[] args) {
		CommandLineParser clp=new CommandLineParser(args);

		if(args.length==0 || clp.containsKey("-h")){
			IO help= new IO();
			help.printHelp();
			System.exit(-1);
		}

		boolean circular=false;
		if(clp.containsKey("c")||clp.containsKey("-c")){
			circular=true;
		}

		String path=null;

		if(clp.containsKey("o")){
			path=clp.getValue("o");
		}
		
		LinkedList<BEARNA> bearList= new LinkedList<BEARNA>();
		if(clp.containsKey("i")){
			bearList=IO.readFA(clp.getValue("i"),circular);
		}else{
			System.out.println("\'-i\' argument is missing!\nPlease check your input.");
			System.exit(-1);
		}

		
		if( path != null){
			ListIterator<BEARNA> lit=bearList.listIterator();
			IO.saveOutput(path, lit);
		}else{
			for (BEARNA b:bearList){
				b.print();
			}
		}
	}
}
