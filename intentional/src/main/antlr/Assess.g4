grammar Assess;
@header {
package it.unibo.antlr.gen;
}

assess : ('with' | 'WITH') cube=id
         (('for' | 'FOR') sc=clause)? ('by' gc+=id (',' gc+=id)*)? (('for' | 'FOR') sc=clause)?
         ('assess' | 'ASSESS') mcs+=id (',' mcs+=id)*
         (('against' | 'AGAINST') bc=benchmark)?
         (('using' | 'USING') as=function)?
         (('labels' | 'labels') l=label)?
          EOF;

id locals[String name] : ID { $name = $ID.text; };

benchmark
    : val=DECIMAL                                            # target
    | attr=id op=comparator val+=value ('scaled' weight=id)? # sibling
    | attr=id ('scaled' weight=id)?                          # parent
    | 'past' val=DECIMAL                                     # past
    ;

function
    : fun=id '(' functions+=function (',' functions+=function)* ')'     # nestedFunction
    | fun=id '(' params+=(ID|DECIMAL) (',' params+=(ID|DECIMAL))* ')'   # parametricFunction
    ;

label
    : lbl=id                           # label_int
    | '{' interval (',' interval)* '}' # useless
    ;

interval
    : ('(' | '[') fr=DECIMAL ',' to=DECIMAL (']' | ')') ':' l=id # label_fun
    ;

clause : condition (binary condition)*;

condition
  : attr=id op=comparator val+=value
  | attr=id in=IN '(' val+=value (',' val+=value)* ')';

value
  : ID
  | DECIMAL
  | INT
  | bool;

comparator
  : GE
  | LE
  | EQ
  | GT
  | LT;

binary: AND;
bool: TRUE | FALSE;

IN      : 'IN' | 'in';
AND     : 'AND' | 'and';
NOT     : 'NOT' | 'not';
TRUE    : 'TRUE' | 'true';
FALSE   : 'FALSE' | 'false';
GT      : '>';
GE      : '>=';
LT      : '<';
LE      : '<=';
EQ      : '=';
DECIMAL : '-'? (([0-9]+ ('.' [0-9]+)?) | 'inf');
INT     : '-'? [0-9]+;
ID
  : '\'' [a-zA-Z0-9'_'\-'/''.'# ()]+ '\''
  |      [a-zA-Z0-9'_'\-'/''.']+ ;
WS         : [ \t\r\n]+ -> skip;
