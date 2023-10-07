import java.util.*;

class Program {
	DeclSeq ds;
	StmtSeq ss;
	
	void parse() {
		Parser.expectedToken(Fun.PROGRAM);
		Parser.scanner.nextTok();
		Parser.expectedToken(Fun.LBRACE);
		Parser.scanner.nextTok();
		if (Parser.scanner.currentTok() != Fun.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
		Parser.expectedToken(Fun.BEGIN);
		Parser.scanner.nextTok();
		Parser.expectedToken(Fun.LBRACE);
		Parser.scanner.nextTok();
		ss = new StmtSeq();
		ss.parse();
		Parser.expectedToken(Fun.RBRACE);
		Parser.scanner.nextTok();
		Parser.expectedToken(Fun.RBRACE);
		Parser.scanner.nextTok();
		Parser.expectedToken(Fun.EOS);
	}
	
	void print() {
		System.out.println("program {");
		if (ds != null) {
			ds.print(1);
		}
		System.out.println("begin {");
		ss.print(1);
		System.out.println("} }");
	}
	
	void execute(String dataFileName) {
		Executor.initialize(dataFileName);
		if (ds != null) {
			ds.execute();
		}
		Executor.pushLocalScope();
		ss.execute();
		Executor.popLocalScope();
	}
}
