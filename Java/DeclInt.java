class DeclInt {
	IdList list;
	
	void parse() {
		Parser.expectedToken(Fun.INT);
		Parser.scanner.nextTok();
		list = new IdList();
		list.parse();
		Parser.expectedToken(Fun.SEMICOLON);
		Parser.scanner.nextTok();
	}
	
	void print(int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print("\t");
		}
		System.out.print("int ");
		list.print();
		System.out.println(";");
	}
	
	void execute() {
		list.executeIntIdList();
	}		
}
