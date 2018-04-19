(defn map' 
  "How the annoymous function works:
    1. Apply function to current element of second argument
    2. Join result of (1) to an first argument
    3. First argument is an empty list, 
       second argument is collection passed to function."
  [f coll]
  (reduce #(conj %1 (f %2)) [] coll))

(defn filter'
  "How the annoymous function works:
    1. Check if the predicate function is true 
       for the current element of the second argument
    2. If true, add the element to the first argument
    3. First argument is an empty list, 
      second argument is collection passed to function."
  [pred coll]
  (reduce #(if (true? (pred %2))
             (conj %1 %2) %1) [] coll))

(defn some'
  "This is where we start to see the power of fp.
    All I need to do for some if some predicate is
    true is to filter by that predicate and then
    check if the result is empty or function."
  [pred coll]
  (not (empty? (filter' pred coll))))
