class Assign implements Stmt {
	int type;
	Id assignTo;
	Id assignFrom;
	Expr expr;
	
	public void parse() {
		assignTo = new Id();
		assignTo.parse();
		Parser.expectedToken(Fun.ASSIGN);
		Parser.scanner.nextTok();
		switch (Parser.scanner.currentTok()) {
			case NEW:
				type = 1;
				Parser.scanner.nextTok();
				Parser.expectedToken(Fun.INSTANCE);
				Parser.scanner.nextTok();
			break;
			case SHARE:
				type = 2;
				Parser.scanner.nextTok();
				assignFrom = new Id();
				assignFrom.parse();
			break;
			default:
				type = 3;
				expr = new Expr();
				expr.parse();
		}			

		Parser.expectedToken(Fun.SEMICOLON);
		Parser.scanner.nextTok();
	}
	
	public void print(int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print("\t");
		}

		assignTo.print();
		System.out.print("=");

		switch (type) {
			case 0:
				System.out.print("read()");
			break;
			case 1:
				System.out.print("new inst");
			break;
			case 2:
				System.out.print("share ");
				assignFrom.print();
			break;
			default:
				expr.print();
		}			
		System.out.println(";");
	}
	
	public void execute() {
		switch (type) {
			case 1:
				// Doing a "new inst"-assign
				assignTo.heapAllocate();
			break;
			case 2:
				// Doing a "share"-assign
				assignTo.referenceCopy(assignFrom);
			break;
			default:
				// Doing a regular assign
				assignTo.storeValue(expr.execute());
		}
	}
}



