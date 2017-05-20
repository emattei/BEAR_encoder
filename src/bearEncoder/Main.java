package bearEncoder;
import java.util.LinkedList;
import java.util.ListIterator;

public class Main {

	public static void main(String[] args) {
		LinkedList<BEARNA> bearList= new LinkedList<BEARNA>();
		if(args.length < 2 || args.length > 3){
			System.out.println("java -jar BEAR_Encoder.jar input.fa output circular");
			System.exit(-1);
		}else if(args.length==2){
			bearList = IO.readFA(args[0],Boolean.valueOf("false"));
			ListIterator<BEARNA> lit=bearList.listIterator();
			IO.saveOutput(args[1], lit);
		}else{
			bearList = IO.readFA(args[0],Boolean.valueOf(args[2]));
			ListIterator<BEARNA> lit=bearList.listIterator();
			IO.saveOutput(args[1], lit);
		}
	}
}
//while ( ((myLine = bufRead.readLine()) != null) && (!myLine.startsWith(">")))
