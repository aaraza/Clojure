;; One line factorial using range and reduce => no recursion!

(defn factorial [number]
  (reduce * (range 1 (inc number))))
