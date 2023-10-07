import java.util.*;

class If implements Stmt {
	Cond cond;
	StmtSeq ss1;
	StmtSeq ss2;
	
	public void parse() {
		Parser.scanner.nextTok();
		cond = new Cond();
		cond.parse();
		Parser.expectedToken(Fun.THEN);
		Parser.scanner.nextTok();
		Parser.expectedToken(Fun.LBRACE);
		Parser.scanner.nextTok();
		ss1 = new StmtSeq();
		ss1.parse();
		Parser.expectedToken(Fun.RBRACE);
		Parser.scanner.nextTok();
		if (Parser.scanner.currentTok() == Fun.ELSE) {
			Parser.scanner.nextTok();
			Parser.expectedToken(Fun.LBRACE);
			Parser.scanner.nextTok();
			ss2 = new StmtSeq();
			ss2.parse();
			Parser.expectedToken(Fun.RBRACE);
			Parser.scanner.nextTok();
		}
	}
	
	public void print(int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print("\t");
		}
		System.out.print("if ");
		cond.print();
		System.out.println(" then {");
		ss1.print(indent + 1);
		if (ss2 != null) {
			for (int i = 0; i < indent; i++) {
				System.out.print("	");
			}
		System.out.println("} else {");
			ss2.print(indent + 1);
		}
		for (int i = 0; i < indent; i++) {
			System.out.print("\t");
		}
		System.out.println("}");
	}
	
	public void execute() {
		boolean condition = cond.execute();
		Executor.pushLocalScope();
		if (condition) {
			ss1.execute();
		} else if (ss2 != null) {
			ss2.execute();
		}
		Executor.popLocalScope();
	}
}
