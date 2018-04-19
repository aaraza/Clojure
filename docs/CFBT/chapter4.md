# Chapter 4

## Programming to Abstractions

- In elisp, you can use the `mapcar` function to derive a new list. You can use the `maphash` function to map over a hash map.
- In Clojure, there is a single `map` function that can be used on all data structures (that are sequences), because  functions such as `map` and `reduce` are implemented in terms of the **sequence abstractions** rather than individual data structures.
- Abstractions are named collections of operations. If an object can perform all of an abstraction's operations, the object is an instance of the abstraction.

### Treating Collections as Sequences
- A `map` operation produces a collection *y* from a collection *x*, using a function *f*, such that y1=f(x1), y2=f(x2) ...
- This operation is not defined in terms of data structures. Clojure allows us to think in similarly abstract terms. Thus, the `map` function does not take in a specific structure, rather it takes a *sequence* or *seq*.
- If the core sequence functions, `first`, `rest`, and `cons` are defined for a data structure, the data structure implements the sequence abstraction.
- Furthermore, functions such as `map` and `reduce` are built using the core functions. It does not matter how a particular data structure is implemented. All Clojure asks is "can I use `first`, `rest`, and `cons`.

### Abstraction through Indirection
- The question remains, how does a function like as `first` work with different data structures. Clojure does this using two forms of indirection. 
- *Polymorphism* is one form. Polymorphic functions dispatch to different function bodies based on the type of the argument supplied. 
- Clojure also performs lightweight type conversion - when you call `map`, it calls the `seq` function on the input to produce a data structure that allows for `first`, `rest`, and `cons`.
- Clojure's sequence functions use `seq` on their arguments. The sequence functions are defined in terms of the sequence abstraction, using `first`, `rest`, and `cons`. 
- A data structure that implements the sequence abstraction can use the seq library that includes functions such as `map`, `reduce`, `filter`, `distinct`, and `group-by`.

## Seq Function Examples
- Clojure's [seq](https://clojure.org/reference/sequences) library is full of useful functions.

### map
Map can be given multiple collections. The elements from the first collection will be the first argument of the mapping function, the elements from the second collection will be the second argument, and so on.
```
(map + [1 2 3] [1 2 3])
;; (2 4 6) 
;; [ (+ 1 1) (+ 2 2) (+ 3 3) ]
```

Map can be used to call multiple functions on a single collection:
```
(def sum #(reduce + %))
(def difference #(reduce - %))

(defn sum-and-diff
  [numbers]
  (map #(% numbers) [sum difference))

(sum-and-diff [1 2])
;; (3 -1)
```

The map function is also often used to retrieve the value associated with a keyword from a collection of map data structures
```
(def people
  [{:name "Ali" :age 23}
   {:name "Candace" :age 22}])

(map :name people)
;; ("Ali" "Candace")
```

### reduce
`reduce` can be used in a variety of unexpected ways. For example, here `reduce` is used to filter from a map based on a predicate
```
(reduce (fn [new-map [key val]]
          (if (>= val 23)
          (assoc new-map key val)
          new-map))
          {}
          people)
;; {:Ali 23}
```

### filter and some
`filter` can be used to return all elements of a sequence that are true for a predicate
```
(filter #(>= (:age %) 22) people)
; {:name "Ali", :age 23}
```
Note, that `filter` processes all data. If the peple map was sorted by age, we could use `drop-while` to drop all elements until the predicate `(:age >= 21)` is met. Once the predicate is met, there would be non additional filteration, thus `take-while` and `drop-while` can be used to efficiently filter sorted collections.

`some` can be used to check a collection to see if it contains a value that meets a given predicate.
```
(some #(< (:age %) 21) people)
; true
```

### sort and sort-by
`sort` sorts elements in ascending order. `sort-by` allows you  to apply a function to the elements of a sequence
```
(sort [3 2 1])
; [1 2 3]
(sort-by :age people)
; ({:name "Candace", :age 22} {:name "Ali", :age 23})
```

## Lazy Seq
- Many functions such as `map` and `filter` return a *lazy seq*, which is a sequence whose members aren't computed till they are accessed. 
- Accessing values of a lazy sequence (and consequently having them evaluated) is known as *realizing*. 
- Clojure chunks lazy seqs: when a value is realized, Clojure preemptively realizes some of the next elements as well.

### Infinite Sequences
`repeat` creates a sequence whose every member is the argument passed. `repeatedly` will call the provided function to generate the sequence
```
(take 3 (repeat 1))
; [1 1 1]

(take 3 (repeatedely (fn [] (rand-int 10))))
; [3 random integers between 0-9]
```

## The Collection Abstraction
The sequence abstraction is about operating on members of a collection. The collection abstraction deals with the data structure as a whole. It has functions such as `count`, `empty?`, and `every?`.
