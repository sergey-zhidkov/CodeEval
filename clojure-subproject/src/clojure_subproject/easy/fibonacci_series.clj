 (ns fibonacci-series)

(def calc-fibonacci
    (memoize
        (fn [n]
            (if (>= 1 n)
                  n
                  (+ (calc-fibonacci (- n 1)) (calc-fibonacci (- n 2)))
                  )))
)

(def calc-fibonacci-memoized
      (memoize calc-fibonacci))

(defn print-fibonacci-series [line]
  (println (calc-fibonacci-memoized (read-string line)))
)

;(with-open [rdr (clojure.java.io/reader (first *command-line-args*))]
(with-open [rdr (clojure.java.io/reader (first *command-line-args*))]
           ; Read each line ignoring empty ones
           (doseq [line (remove empty? (line-seq rdr))]
                  (print-fibonacci-series line)))
