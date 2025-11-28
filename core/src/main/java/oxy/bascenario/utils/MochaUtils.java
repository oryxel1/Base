package oxy.bascenario.utils;

import team.unnamed.mocha.parser.MolangParser;
import team.unnamed.mocha.parser.ast.Expression;
import team.unnamed.mocha.runtime.ExpressionInterpreter;
import team.unnamed.mocha.runtime.Scope;
import team.unnamed.mocha.runtime.binding.JavaObjectBinding;
import team.unnamed.mocha.runtime.standard.MochaMath;
import team.unnamed.mocha.runtime.value.MutableObjectBinding;
import team.unnamed.mocha.runtime.value.NumberValue;
import team.unnamed.mocha.runtime.value.Value;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

@SuppressWarnings("ALL")
public class MochaUtils {
    public static final Scope BASE_SCOPE = Scope.create();

    static {
        BASE_SCOPE.set("math", JavaObjectBinding.of(MochaMath.class, null, new MochaMath()));
        BASE_SCOPE.readOnly(true);
    }

    public static Value eval(final Scope scope, final String expression) throws IOException {
        return eval(scope, parse(expression));
    }

    public static Value eval(final Scope scope, final List<Expression> expressions) {
        final Scope localScope = scope.copy();
        final MutableObjectBinding tempBinding = new MutableObjectBinding();
        localScope.set("temp", tempBinding);
        localScope.set("t", tempBinding);
        localScope.readOnly(true);

        final ExpressionInterpreter<Void> evaluator = new ExpressionInterpreter<>(null, localScope);
        evaluator.warnOnReflectiveFunctionUsage(false);

        Value lastResult = NumberValue.zero();
        for (Expression expression : expressions) {
            lastResult = expression.visit(evaluator);
            Value returnValue = evaluator.popReturnValue();
            if (returnValue != null) {
                lastResult = returnValue;
                break;
            }
        }

        return lastResult;
    }

    public static List<Expression> parse(final String expression) throws IOException {
        try (final StringReader reader = new StringReader(expression)) {
            return parse(reader);
        }
    }

    public static List<Expression> parse(final Reader reader) throws IOException {
        return MolangParser.parser(reader).parseAll();
    }
}
