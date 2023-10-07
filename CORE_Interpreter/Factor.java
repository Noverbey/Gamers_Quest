class Factor {
	Id id;
	int constant;
	Expr expr;
	Boolean input;
	
	void parse() {
		switch (Parser.scanner.currentTok()) {
			case ID:
				id = new Id();
				id.parse();
			break;
			case CONST:
				constant = Parser.scanner.getCONST();
				Parser.scanner.nextTok();
			break;
			case LPAREN:
				Parser.scanner.nextTok();
				expr = new Expr();
				expr.parse();
				Parser.expectedToken(Fun.RPAREN);
				Parser.scanner.nextTok();
			break;
			case READ:
				Parser.scanner.nextTok();
				Parser.expectedToken(Fun.LPAREN);
				Parser.scanner.nextTok();
				Parser.expectedToken(Fun.RPAREN);
				Parser.scanner.nextTok();
				input = true;
			break;
			default:
				System.out.println("ERROR: Expected ID, CONST, LPAREN, or READ, received " + 			
						   Parser.scanner.currentTok());
				System.exit(0);
		}
	}
	
	void print() {
		if (id != null) {
			id.print();
		} else if (expr != null) {
			System.out.print("(");
			expr.print();
			System.out.print(")");
		} else if (input != null) {
			System.out.print("read()");
		} else {
			System.out.print(constant);
		}
	}
	
	int execute() {
		int result = constant;
		if (id != null) {
			try {
				result = (int) id.getValue();
			} catch (Exception e) {
				System.out.println("ERROR: " + id.getString() + "is null");
				System.exit(0);
			}
		} else if (expr != null) {
			result = expr.execute();
		} else if (input != null) {
			if (Executor.dataFile.currentTok() == Fun.EOS) {
				System.out.println("ERROR: Data file ran out of values!");
				System.exit(0);
			}
			result = Executor.dataFile.getCONST();
			Executor.dataFile.nextTok();
		}
		return result;
	}
}
