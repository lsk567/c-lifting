package example.antlr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.antlr.CParser.BlockItemContext;
import example.antlr.CParser.DeclarationSpecifierContext;
import example.antlr.CParser.DeclaratorContext;
import example.antlr.CParser.DirectDeclaratorContext;
import example.antlr.CParser.InitDeclaratorContext;
import example.antlr.CParser.PrimaryExpressionContext;

public class BuildAstParseTreeVisitor extends CBaseVisitor<CAst.AstNode> {
    private static final Logger LOG = LoggerFactory.getLogger(BuildAstParseTreeVisitor.class);

    @Override
    public CAst.AstNode visitBlockItemList(CParser.BlockItemListContext ctx) {
        CAst.StatementSequenceNode stmtSeq = new CAst.StatementSequenceNode();
        
        // Populate the children.
        for (BlockItemContext blockItem : ctx.blockItem()) {
            // System.out.println(blockItem);
            stmtSeq.children.add(visit(blockItem));
        }

        System.out.println(stmtSeq.children);

        return stmtSeq;
    }

    @Override
    public CAst.AstNode visitBlockItem(CParser.BlockItemContext ctx) {
        if (ctx.statement() != null)
            return visit(ctx.statement());
        else
            return visit(ctx.declaration());
    }

    @Override
    public CAst.AstNode visitDeclaration(CParser.DeclarationContext ctx) {
        if (ctx.declarationSpecifiers() != null 
            && ctx.initDeclaratorList() != null) {
            //// Extract type from declarationSpecifiers.
            List<DeclarationSpecifierContext> declSpecList 
                = ctx.declarationSpecifiers().declarationSpecifier();
            
            // Cannot handle more than 1 specifiers, e.g. static const int.
            // We can augment the analytical capability later.
            if (declSpecList.size() > 1) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "the analyzer cannot handle more than 1 specifiers,",
                    "e.g. static const int.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();
            }
            
            // Check if the declaration specifier is a type specifier: e.g. int or long.
            DeclarationSpecifierContext declSpec = declSpecList.get(0);
            if (declSpec.typeSpecifier() == null) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "only type specifiers are supported.",
                    "e.g. \"static const int\" is not analyzable.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();
            }
            
            // Check if the type specifier is what we currently support.
            // Right now we only support int, long, & double.
            CAst.VariableNode.Type type;
            ArrayList<String> supportedTypes = new ArrayList<String>(
                Arrays.asList(
                    "int",
                    "long",
                    "double"
                )
            );
            if (declSpec.typeSpecifier().Int() != null
                || declSpec.typeSpecifier().Long() != null
                || declSpec.typeSpecifier().Double() != null) 
                type = CAst.VariableNode.Type.INT;
            // Mark the declaration unanalyzable if the type is unsupported.
            else {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "unsupported type detected at " + declSpec.typeSpecifier(),
                    "Only " + supportedTypes + " are supported.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();
            }

            //// Extract variable name and value from initDeclaratorList.
            List<InitDeclaratorContext> initDeclList
                = ctx.initDeclaratorList().initDeclarator();
            
            // Cannot handle more than 1 initDeclarator: e.g. x = 1, y = 2;
            // We can augment the analytical capability later.
            if (initDeclList.size() > 1) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "more than 1 declarators are detected on a single line,",
                    "e.g. \"int x = 1, y = 2;\" is not yet analyzable.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();
            }

            // Get the variable name from the declarator.
            DeclaratorContext decl = initDeclList.get(0).declarator();
            if (decl.pointer() != null) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "pointers are currently not supported,",
                    "e.g. \"int *x;\" is not yet analyzable.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();
            }
            if (decl.gccDeclaratorExtension().size() > 0) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "GCC declarator extensions are currently not supported,",
                    "e.g. \"__asm\" and \"__attribute__\" are not yet analyzable.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();
            }
            DirectDeclaratorContext directDecl = decl.directDeclarator();
            if (directDecl.Identifier() == null) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "the variable identifier is missing.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();
            }

            // Extract the name of the variable.
            String name = directDecl.Identifier().getText();             
            // Create a variable Ast node.
            CAst.VariableNode variable = new CAst.VariableNode(type, name);


            //// Convert the initializer to a value.

            // Make sure that there is an initializer.
            InitDeclaratorContext initDecl = initDeclList.get(0);
            if (initDecl.initializer() == null) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "the initializer is missing,",
                    "e.g. \"int x;\" is not yet analyzable.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();

                // FIXME: Use UninitCAst.VariableNode to perform special encoding.
                // return new UninitCAst.VariableNode(type, name);
            }

            // Extract the primaryExpression from the initializer.
            if (initDecl.initializer().assignmentExpression() == null
                || initDecl.initializer().assignmentExpression()
                    .conditionalExpression() == null) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "assignmentExpression or conditionalExpression is missing.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();
            }

            //// Process the right hand side of the assignment.
            PrimaryExpressionContext primaryExpr;
            CAst.AstNode initializerNode;
            try {
                // FIXME: This is cutting some corners with interpreting
                // the program. Currently, inline arithmetic operations
                // will not be handled and have no warnings at all.
                // This part needs more work.
                primaryExpr = initDecl.initializer().assignmentExpression()
                                .conditionalExpression().logicalOrExpression()
                                .logicalAndExpression(0).inclusiveOrExpression(0)
                                .exclusiveOrExpression(0).andExpression(0)
                                .equalityExpression(0).relationalExpression(0)
                                .shiftExpression(0).additiveExpression(0)
                                .multiplicativeExpression(0).castExpression(0)
                                .unaryExpression().postfixExpression()
                                .primaryExpression();
            } catch (NullPointerException e) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "unable to extract a primary expression from the initializer.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();
            }
            if (primaryExpr.Identifier() != null) {
                initializerNode = new CAst.VariableNode(primaryExpr.Identifier().getText());
            } else if (primaryExpr.Constant() != null) {
                initializerNode = new CAst.LiteralNode(primaryExpr.Constant().getText());
            } else {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "only identifier and constant are supported on the RHS of the declaration.",
                    "Marking the declaration as opaque."
                ));
                return new CAst.OpaqueNode();
            }

            // Finally return the assignment node.
            CAst.AssignmentNode assignmentNode = new CAst.AssignmentNode();
            assignmentNode.left = variable;
            assignmentNode.right = initializerNode;
            return assignmentNode;
        }
        // Return CAst.OpaqueNode as default.
        return new CAst.OpaqueNode();
    }

    /**
     * This visit function builds StatementSequenceNode, AssignmentNode,
     * OpaqueNode, IfBlockNode,
     * AdditionNode, SubtractionNode, MultiplicationNode, DivisionNode,
     * EqualNode, NotEqualNode, LessThanNode, GreaterThanNode, LessEqualNode, GreaterEqualNode,
     * SetPortNode, ScheduleNode.
     * 
     * @param ctx
     * @return
     */
    @Override
    public CAst.AstNode visitStatement(CParser.StatementContext ctx) {
        if (ctx.compoundStatement() != null) {
            CParser.BlockItemListContext bilCtx = ctx.compoundStatement().blockItemList();
            if (bilCtx != null) {
                return visitBlockItemList(bilCtx);
            }
        } else if (ctx.expressionStatement() != null) {
            CParser.ExpressionContext exprCtx = ctx.expressionStatement().expression();
            if (exprCtx != null) {
                return visitExpression(exprCtx);
            }
        } else if (ctx.selectionStatement() != null) {
            return visitSelectionStatement(ctx.selectionStatement());
        }
        return new CAst.OpaqueNode();
    }

    @Override
    public CAst.AstNode visitExpression(CParser.ExpressionContext ctx) {
        if (ctx.assignmentExpression().size() == 1) {
            return visitAssignmentExpression(ctx.assignmentExpression().get(0));
        }
        System.out.println(String.join(" ", 
            "Warning (line " + ctx.getStart().getLine() + "):",
            "only one assignmentExpression in an expression is currently supported.",
            "Marking the statement as opaque."
        ));
        return new CAst.OpaqueNode();
    }

    @Override
    public CAst.AstNode visitAssignmentExpression(CParser.AssignmentExpressionContext ctx) {
        if (ctx.conditionalExpression() != null) {
            return visitConditionalExpression(ctx.conditionalExpression());
        }
        if (ctx.unaryExpression() != null
            && ctx.assignmentOperator() != null
            && ctx.assignmentExpression() != null) {
            CAst.AssignmentNode assignmentNode = new CAst.AssignmentNode();
            assignmentNode.left = visitUnaryExpression(ctx.unaryExpression());
            assignmentNode.right = visitAssignmentExpression(ctx.assignmentExpression());
            return assignmentNode;
        }
        System.out.println(String.join(" ", 
            "Warning (line " + ctx.getStart().getLine() + "):",
            "DigitSequence in an assignmentExpression is currently not allowed.",
            "Marking the expression as opaque."
        ));
        return new CAst.OpaqueNode();
    }

    @Override
    public CAst.AstNode visitRelationalExpression(CParser.RelationalExpressionContext ctx) {
        CAst.AstNodeBinary node;
        if (ctx.Less() != null) {
            node = new CAst.LessThanNode();
        } else if (ctx.LessEqual() != null) {
            node = new CAst.LessEqualNode();
        } else if (ctx.Greater() != null) { 
            node = new CAst.GreaterThanNode();
        } else if (ctx.GreaterEqual() != null) {
            node = new CAst.GreaterEqualNode();
        } else {
            node = new CAst.AstNodeBinary();
        }
        node.left = visitShiftExpression(ctx.shiftExpression().get(0));
        if (ctx.shiftExpression().size() > 1) {
            node.right = visitShiftExpression(ctx.shiftExpression().get(1));
        }
        return node;
    }

    @Override
    public CAst.AstNode visitEqualityExpression(CParser.EqualityExpressionContext ctx) {
        CAst.AstNodeBinary node;
        if (ctx.Equal() != null) {
            node = new CAst.EqualNode();
        } else if (ctx.NotEqual() != null) {
            node = new CAst.NotEqualNode();
        } else {
            node = new CAst.AstNodeBinary();
        }
        node.left = visitRelationalExpression(ctx.relationalExpression().get(0));
        if (ctx.relationalExpression().size() > 1) {
            node.right = visitRelationalExpression(ctx.relationalExpression().get(1));
        }
        return node;
    }

    @Override
    public CAst.AstNode visitAndExpression(CParser.AndExpressionContext ctx) {
        if (ctx.equalityExpression().size() > 1) {
            System.out.println(String.join(" ", 
                "Warning (line " + ctx.getStart().getLine() + "):",
                "And expression '&' is currently unsupported.",
                "Marking the expression as opaque."
            ));
            return new CAst.OpaqueNode();
        }
        return visitEqualityExpression(ctx.equalityExpression().get(0));
    }

    @Override
    public CAst.AstNode visitExclusiveOrExpression(CParser.ExclusiveOrExpressionContext ctx) {
        if (ctx.andExpression().size() > 1) {
            System.out.println(String.join(" ", 
                "Warning (line " + ctx.getStart().getLine() + "):",
                "Exclusive Or '^' is currently unsupported.",
                "Marking the expression as opaque."
            ));
            return new CAst.OpaqueNode();
        }
        return visitAndExpression(ctx.andExpression().get(0));
    }

    @Override
    public CAst.AstNode visitInclusiveOrExpression(CParser.InclusiveOrExpressionContext ctx) {
        if (ctx.exclusiveOrExpression().size() > 1) {
            System.out.println(String.join(" ", 
                "Warning (line " + ctx.getStart().getLine() + "):",
                "Inclusive Or '|' is currently unsupported.",
                "Marking the expression as opaque."
            ));
            return new CAst.OpaqueNode();
        }
        return visitExclusiveOrExpression(ctx.exclusiveOrExpression().get(0));
    }

    @Override
    public CAst.AstNode visitLogicalAndExpression(CParser.LogicalAndExpressionContext ctx) {
        CAst.LogicalAndNode node = new CAst.LogicalAndNode();
        node.left = visitInclusiveOrExpression(ctx.inclusiveOrExpression().get(0));
        if (ctx.inclusiveOrExpression().size() > 1) {
            node.right = visitInclusiveOrExpression(ctx.inclusiveOrExpression().get(1));
        }
        return node;
    }

    @Override
    public CAst.AstNode visitLogicalOrExpression(CParser.LogicalOrExpressionContext ctx) {
        CAst.LogicalOrNode node = new CAst.LogicalOrNode();
        node.left = visitLogicalAndExpression(ctx.logicalAndExpression().get(0));
        if (ctx.logicalAndExpression().size() > 1) {
            node.right = visitLogicalAndExpression(ctx.logicalAndExpression().get(1));
        }
        return node;
    }

    @Override
    public CAst.AstNode visitConditionalExpression(CParser.ConditionalExpressionContext ctx) {
        if (ctx.expression() != null) {
            System.out.println(String.join(" ", 
                "Warning (line " + ctx.getStart().getLine() + "):",
                "Currently do not support inline conditional expression.",
                "Marking the expression as opaque."
            ));
            return new CAst.OpaqueNode();
        }
        return visitLogicalOrExpression(ctx.logicalOrExpression());
    }

    

    @Override
    public CAst.AstNode visitUnaryExpression(CParser.UnaryExpressionContext ctx) {
        return null;
    }
}
