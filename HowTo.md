# How to use Lispel #

For the basic functionality you only need the classes in the `lispel` package which gives you a tokenizer, a parser and a Lisp implementation with `quote`, `cond`, and `lambda`.
The truth values `true` and `false` are defined in the expression class as well as the `undefined` value.

  * Axioms can be loaded into the environment as shown in the test main method of the parser class by instantiating a new `Function` object and binding it to a name in the global environment.
  * The predefined basic axioms `eq`, `atom`, `eval`, and `set` can be found in the `lispel.axioms.basic` package.
  * The list primitives `car`, `cdr`, and `cons` are defined in the `lispel.axioms.lists` package.
  * In `lispel.axioms.integers` you will find a loadable integer theory with `+`, `-`, `*`, `/`, `=`, and `<` for illustration purposes. Note that using `cond`, you can define the logical operators (`and`, `or`, `not`) which can be used to extend the integer theory in order to get `>`, `<=`, `>=`. However, for convenience, these functions could also be added by directly implementing them as functions like the axioms if needed.

In contrast to the paper, a lambda expression like

```lisp

(lambda (a_1 ... a_n) e)
```

evaluates to a function which can be applied using

```lisp

((lambda (a_1 ... a_n) e) b_1 ... b_n)
```

Consequently, in contrast to the paper,  if you want to pass a function defined via lambda as a parameter to another function, you must not quote it. For example, you should write

```lisp

((lambda (f) (f '(b c))) (lambda (x) (cons 'a x)))
```

instead of

```lisp

((lambda (f) (f '(b c))) '(lambda (x) (cons 'a x)))
```