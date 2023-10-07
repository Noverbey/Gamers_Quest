class Expr {
	Term term;
	Expr expr;
	int option;
	
	void parse() {
		term  = new Term();
		term.parse();
		switch (Parser.scanner.currentTok()) {
			case ADD:
				option = 1;
			break;
			case SUB:
				option = 2;
		}
		if (option != 0) {
			Parser.scanner.nextTok();
			expr = new Expr();
			expr.parse();
		}						
	}
	
	void print() {
		term.print();
		switch (option) {
			case 1:
				System.out.print("+");
				expr.print();	
			break;
			case 2:
				System.out.print("-");
				expr.print();
		}
	}
	
	int execute() {
		switch (option) {
			case 1:
				return term.execute() + expr.execute();
			case 2:
				return term.execute() - expr.execute();
		}
		return term.execute();

	}
}
