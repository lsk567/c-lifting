package example.antlr.ast;

import java.util.List;

import example.antlr.AstVisitor;

public interface Visitable {
    
    /** The {@link AstVisitor} needs a double dispatch method. */
    <T> T accept(AstVisitor<? extends T> visitor);

    /** The {@link AstVisitor} needs a double dispatch method. */
    <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList);
}
