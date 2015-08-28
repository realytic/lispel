Lispel is a lightweight Lisp implementation written in Java in less than 400 lines or 11kb of code. The key idea is to keep the language as minimal as possible, where even the usual Lisp axioms `eq`, `atom`, `car`, `cdr`, `cons` and `set` are functions that can be loaded into the environment on demand. The typical use case of lispel is as a scripting language since its syntax and semantics can be extended easily giving the user exactly the amount of expressiveness that is needed.

The idea to write a minimal LISP implementation is taken from the excellent paper "The Roots of Lisp" of Paul Graham which can be found at http://www.paulgraham.com/rootsoflisp.html.