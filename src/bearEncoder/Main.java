package bearEncoder;
import java.util.LinkedList;
import java.util.ListIterator;

public class Main {

	public static void main(String[] args) {
		LinkedList<RNA> t=new LinkedList<RNA>();
		LinkedList<BEARNA> bearList= new LinkedList<BEARNA>();
		//args[0]è il file di input
		//args[1] è quello di output
		if(args.length!=2){
			System.out.println("java -jar BEAR_Encoder.jar input.fa output");
			System.exit(-1);
		}else{
			int res=IO.readFA(args[0],t);
			if (res==0){
				for(RNA r:t){
					//r.print();
					BEARNA tmp=new BEARNA(r);
					bearList.add(tmp);
				}
				ListIterator<BEARNA> lit=bearList.listIterator();
				IO.saveOutput(args[1], lit);
				//while (lit.hasNext()) {
				//	lit.next().print();
				//	}
			}else if(res!=-1){
				System.out.println("Line: "+res);
			}
		}
	}
}
