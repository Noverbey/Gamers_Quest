class Cond {
	Cmpr cmpr;
	Cond cond;
	
	void parse() {
		if (Parser.scanner.currentTok() == Fun.NEGATION){
			Parser.scanner.nextTok();
			Parser.expectedToken(Fun.LPAREN);
			Parser.scanner.nextTok();
			cond = new Cond();
			cond.parse();
			Parser.expectedToken(Fun.RPAREN);
			Parser.scanner.nextTok();
		} else {
			cmpr = new Cmpr();
			cmpr.parse();
			if (Parser.scanner.currentTok() == Fun.OR) {
				Parser.scanner.nextTok();
				cond = new Cond();
				cond.parse();
			}
		}
	}
	
	void print() {
		if (cmpr == null) {
			System.out.print("!(");
			cond.print();
			System.out.print(")");
		} else {
			cmpr.print();
			if (cond != null) {
				System.out.print(" or ");
				cond.print();
			}
		}
	}
	
	boolean execute() {
		if (cmpr == null) {
			return !cond.execute();
		}
		if (cond != null) {
			return cmpr.execute() || cond.execute();
		}
		return cmpr.execute();
	}
}
