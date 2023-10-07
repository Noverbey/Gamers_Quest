class StmtSeq {
	Stmt stmt;
	StmtSeq ss;
	
	void parse() {
		switch (Parser.scanner.currentTok()) {
			case ID:
				stmt = new Assign();
			break;
			case WRITE:
				stmt = new Output();
			break;
			case IF:
				stmt = new If();
			break;
			case WHILE:
				stmt = new Loop();
			break;
			case INT:
			case REF:
				stmt = new Decl();
			break;
			case BEGIN:
				stmt = new FuncCall();
			break;
			default:
				System.out.println("ERROR: Bad start to statement: " + Parser.scanner.currentTok());
				System.exit(0);	
		}
		stmt.parse();
		if ((Parser.scanner.currentTok() != Fun.END) && 
		    (Parser.scanner.currentTok() != Fun.RBRACE)) {
			ss = new StmtSeq();
			ss.parse();
		}
	}
			
	void print(int indent) {
		stmt.print(indent);
		if (ss != null) {
			ss.print(indent);
		}
	}
	
	void execute() {
		stmt.execute();
		if (ss != null) {
			ss.execute();
		}
	}
}
