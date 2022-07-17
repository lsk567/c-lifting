package example.antlr.ast;

import java.util.List;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

public class StatementSequenceNode extends AstNodeDynamic implements Visitable {
    
    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitStatementSequenceNode(this);
    }

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
        return ((CAstVisitor<? extends T>)visitor).visitStatementSequenceNode(this, nodeList);
    }
}
