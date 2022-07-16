package example.antlr.ast;

import example.antlr.AstVisitor;

public class AstNode implements Visitable {
    
    @Override
	public <T> T accept(AstVisitor<? extends T> visitor) { return visitor.visitChildren(this); } 
}
