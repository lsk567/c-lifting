package example.antlr.ast;

import example.antlr.AstVisitor;

public interface Visitable {
    
    /** The {@link AstVisitor} needs a double dispatch method. */
    <T> T accept(AstVisitor<? extends T> visitor);
}
