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

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        LOG.info("Running the example...");

        try {
            // Generate a parse tree.
            CLexer lexer = new CLexer(CharStreams.fromFileName("examples/if.c"));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CParser parser = new CParser(tokens);
            BlockItemListContext parseTree = parser.blockItemList();

            // Build an AST.
            BuildAstParseTreeVisitor buildAstVisitor = new BuildAstParseTreeVisitor();
            CAst.AstNode ast = buildAstVisitor.visitBlockItemList(parseTree);

            // Traverse and print.
            CBaseAstVisitor baseVisitor = new CBaseAstVisitor<>(); // For pretty printing.
            System.out.println("***** Printing the original AST.");
            baseVisitor.visit(ast);

            // Convert the AST to If Normal Form (INF).
            IfNormalFormAstVisitor infVisitor = new IfNormalFormAstVisitor();
            System.out.println("***** Convert to If Normal Form.");
            infVisitor.visit(ast, new ArrayList<CAst.AstNode>());
            CAst.StatementSequenceNode inf = infVisitor.INF;
            System.out.println(inf);
            System.out.println("***** Printing the AST in If Normal Form.");
            baseVisitor.visit(inf);

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
