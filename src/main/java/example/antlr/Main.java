package example.antlr;

import java.io.IOException;
import java.util.ArrayList;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.antlr.CParser.BlockItemListContext;
import example.antlr.ast.AstNode;
import example.antlr.ast.StatementSequenceNode;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        LOG.info("Running the example...");

        try {
            // Generate a parse tree.
            CLexer lexer = new CLexer(CharStreams.fromFileName("examples/decls.c"));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CParser parser = new CParser(tokens);
            BlockItemListContext parseTree = parser.blockItemList();

            // Build an AST.
            BuildAstParseTreeVisitor buildAstVisitor = new BuildAstParseTreeVisitor();
            AstNode ast = buildAstVisitor.visitBlockItemList(parseTree);

            // Convert the AST to If Normal Form (INF).
            IfNormalFormAstVisitor infVisitor = new IfNormalFormAstVisitor();
            infVisitor.visit(ast, new ArrayList<AstNode>());
            StatementSequenceNode inf = infVisitor.INF;
            System.out.println(inf);

            // Traverse and print.
            CBaseAstVisitor baseVisitor = new CBaseAstVisitor<>(); // For pretty printing.
            baseVisitor.visit(inf);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
