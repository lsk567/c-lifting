package example.antlr.ast;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

public class StatementSequenceNode extends AstNodeDynamic {
    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitStatementSequenceNode(this);
    }
}
