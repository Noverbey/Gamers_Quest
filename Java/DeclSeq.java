class DeclSeq {
	Decl decl;
	DeclSeq ds;
	FuncDecl fd;
	
	void parse() {
		if (Parser.scanner.currentTok() == Fun.ID) {
			fd = new FuncDecl();
			fd.parse();
		} else {
			decl = new Decl();
			decl.parse();
		}
		if (Parser.scanner.currentTok() != Fun.BEGIN) {
			ds = new DeclSeq();
			ds.parse();
		}
	}
	
	void print(int indent) {
		if (fd != null) {
			fd.print(indent);
		} else {
			decl.print(indent);
		}
		if (ds != null) {
			ds.print(indent);
		}
	}
	
	void execute() {
		if (decl != null) {
			decl.execute();
		} else {
			fd.execute(0);
		}
		if (ds != null) {
			ds.execute();
		}
	}
}
