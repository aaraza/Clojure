# Chapter 3: A Clojure Crash Course
The Lisp core is composed of syntax, functions, and data

## Syntax

### Forms
- form refers to valid code
- Clojure evaluates every form to produce a value
```
(operator operand1 operand2 ... operandn)
```

### Control Flow
Three basic control flow operators: `if`, `do`, and `when`.

#### If
```
(if boolean-form
  then-form
  optional-else-form)
```
- Boolean form is any form that evaluates to a truth of false value
- The else branch can be ommited. If the boolean form evaluates to false, and there is no else branch, Clojure returns `nil`
- Each branch of the conditional expression can have only one form. To execute multiple statements in a single form, we can use the `do` operator.

#### Do 
- The `do` operators lets you wrap multiple forms in parentheses and run each of them.

#### When
- The when operator is like a combination of the `if` and `do` operator, but with no else branch
- Use when if there are multiple things to do when a condition is true and we always want to return nil if the condition is false

### Special Values and Boolean Expressions
- Clojure has `true` and `false` values
- `nil` is used to indicate no value
    - Check if value is nil with the `nil?` function
    ```
    (nil? 1)
    ; => false
    ```
- Both `nil` and `false` represent falsiness and will trigger the else form of an `if` statement and not execute at `when` statement
- Clojure's equality operator is `=`
```
(= 1 1)
; => true
```
- Boolean Operators
    - `or` returns the first true value, or the last value
    - `and` returns the first falsey value, or the last truthy value
    ```
    ( or (= 0 1) (= "yes" "no"))
    ; => false

    (and "hi" false true)
    ; false    
    ```

### Naming Values with def
- The `def` keyword binds a name to a value
- `def` should be used for constants
```
(def greeting "Hello!")
```

### Naming Values with let
- The `let` keyword binds a name to a value
- `let` should be used for local variables
```
(let [name "Ali"]
  name) ;"Ali"
```

## Data Structure
All data structures are immutable

### Numbers
- Consists of integers, floats, and ratios
```
93
1.2
1/5
```
### Strings
- Represent text
- No string interpolation. Concatenation is allowed via `str` function
```
"Ali"
(str "Ali " "Raza")
```

### Maps
- Clojure has two kinds of maps: hash-maps and sorted maps.
- Maps values can be of any type - string numbers, maps, vectors, and even functions
```
{} ;empty map

{:first-name "Ali"
 :last-name "Raza"}
```
- You can retrieve hashmap values with `get`
 ```
(def name-map 
  (hash-map :first-name "Ali"
            :last-name "Raza"))

(get name-map :first-name)
;"Ali"
```
- Get will return `nil` if it does not find a key. It can be given an default value (specified as the last parameter in the get call)
- Map values can be retrieved through a function call 
```
(name-map :first-name)
;"Ali"
```

### Keywords
- Keywords can be used as functions that look up corresponding values in a data structure
```
(:first-name name-map "name not found") ; name not found is default value if first-name key doesn't exist
```

### Vectors
- 0-indexed collection
- Vectors can be of any type and types can be mixed
- We can get vector elements using the `get` function
- We can add additional elements to a vector using the `conj` function
```
(def ali ["Ali" "Raza"])

(get ali 0) ;"Ali"

(conj ali 21) ;["Ali" "Raza" 21]
```

### Lists
- Lists are similar to vectors - they are linear collections of values
- Retrieve elements using `nth` function
- Elements are added to the beginning of a list
```
(def numbers '(1 2 3 4))

(nth numbers 0) ;1

(conj numbers 0) ;(0 1 2 3 4)
```
- Use lists when you need to add elements to beginning frequently or if you are writing a macro

### Sets
- Collections of unique value
- Clojure has hash sets and sorted sets
- Can use `hash-set` function to create new hash-set or `set` function to create set from vectors/list
- Can use `conj` to create new set from existing set
- Can use `contains` or `get` to check for member existince
```
(def numbers #{1 2 3})) ;#{1 2 3}

(def numbers (hash-set 1 1 2 3)) ;#{1 2 3}

(conj numbers 4) ;#{1 3 2 4} ;;Note order not preserved

(contains? number 3) ;true
(contains? number 4) ;false

(get number 3) ;3
(get number 4) ;nil
```

## Functions

### Calling Functions
- A function call is simply an operation where the operator is a function
- Functions can take any expression as arguments, including other functions
- Functions that take functions as arguments or return functions are called *higher order functions*
- Clojure evaluates all function arguments recursively before passing them to a function
```
(+ (inc 9) (- 11 1))
;(+ 10 (- 11 1))
;(+ 10 10)
;20
```

### Defining Functions
- Function definition consists of:
    - `defn`
    - Function name
    - Docstring
    - Parameters listed in brackets
    - Function body
```
(defn name
  "This is the docstring"
  [arg1 arg2 ... argn]
  (expression)
  (expression))
```
- Functions can be overloaded by number of parameters
```
(defn increase-value
  ([]
    (inc 0))
  ([number]
    (inc number)))
```

### Destructing
- Lets you bind names to values in a collection
- Can bind all subsequent arguments using `&`
```
(defn my-first
  [[first second & rest]]
  (println first)
  (println second)
  (println rest))

(my-first [1 2 3 4 5])
;1
;2
;(3 4 5)
```

### Anonyomous Functions
- Created using `fn` keyword or `#`
```
(fn [param-list
  function body)

#(* % 3)
; (#(* % 3) 2) ;; 6
```
- `%` indicates the argument passed to function
  - If anonymous function takes multiple arguments `%1`, `%2`
