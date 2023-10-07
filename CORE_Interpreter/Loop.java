import java.util.*;

class Loop implements Stmt {
	Cond cond;
	StmtSeq ss;
	
	public void parse() {
		Parser.scanner.nextTok();
		cond = new Cond();
		cond.parse();
		Parser.expectedToken(Fun.LBRACE);
		Parser.scanner.nextTok();
		ss = new StmtSeq();
		ss.parse();
		Parser.expectedToken(Fun.RBRACE);
		Parser.scanner.nextTok();
	}
	
	public void print(int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print("\t");
		}
		System.out.print("while ");
		cond.print();
		System.out.println(" {");
		ss.print(indent + 1);
		for (int i = 0; i < indent; i++) {
			System.out.print("\t");
		}
		System.out.println("}");
	}
	
	public void execute() {
		while (cond.execute()) {
			Executor.pushLocalScope();
			ss.execute();
			Executor.popLocalScope();
		}
		
	}
}
