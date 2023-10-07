class FuncCall implements Stmt {
    Id id;
    Formulas f;


    public void parse() {
        Parser.expectedToken(Fun.BEGIN);
        Parser.scanner.nextTok();
        id = new Id();
        id.parse();
        Parser.expectedToken(Fun.LPAREN);
        Parser.scanner.nextTok();
        f = new Formulas();
        f.parse();
        Parser.expectedToken(Fun.RPAREN);
        Parser.scanner.nextTok();
        Parser.expectedToken(Fun.SEMICOLON);
        Parser.scanner.nextTok();
    }

    public void semantic() {
        if (!Executor.funcNames.contains(id.identifier)) {
            System.out.println("ERROR: The targeted function call \"" + id.identifier + "\" was not defined!");
            System.exit(0);
        }
    }

    public void print(int indent) {
        for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
        System.out.print("begin ");
        id.print();
        System.out.print("(");
        f.print(indent);
        System.out.println(");");
    }

    public void execute() {
        this.semantic();
        Executor.copyIn(id.identifier, f);
    }
}
