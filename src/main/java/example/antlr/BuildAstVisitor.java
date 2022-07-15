package example.antlr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.antlr.ast.*;

public class BuildAstVisitor extends CBaseVisitor<ASTNode> {
    private static final Logger LOG = LoggerFactory.getLogger(BuildAstVisitor.class);

    @Override
    public ASTNode visitBlockItemList(CParser.BlockItemListContext ctx) {
        StatementSequenceNode stmtSeq = new StatementSequenceNode();
        
        // Populate the children.

        return stmtSeq;
    }
}
