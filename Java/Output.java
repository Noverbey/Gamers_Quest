class Output implements Stmt {
	Expr expr;
	
	public void parse() {
		Parser.scanner.nextTok();
		Parser.expectedToken(Fun.LPAREN);
		Parser.scanner.nextTok();
		expr = new Expr();
		expr.parse();
		Parser.expectedToken(Fun.RPAREN);
		Parser.scanner.nextTok();
		Parser.expectedToken(Fun.SEMICOLON);
		Parser.scanner.nextTok();
	}
	
	public void print(int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print("\t");
		}
		System.out.print("write(");
		expr.print();
		System.out.print(")");
		System.out.println(";");
	}
	
	public void execute() {
		System.out.println(expr.execute());
	}
}
