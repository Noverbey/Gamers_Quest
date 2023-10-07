class DeclRef {
	IdList list;
	
	void parse() {
		Parser.expectedToken(Fun.REF);
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
		System.out.print("reference ");
		list.print();
		System.out.println(";");
	}
	
	void execute() {
		list.executeRefIdList();
	}		
}
