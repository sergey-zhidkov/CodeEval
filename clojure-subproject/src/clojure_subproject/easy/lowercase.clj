 (ns lowercase)

;(with-open [rdr (clojure.java.io/reader (first *command-line-args*))]
(with-open [rdr (clojure.java.io/reader (first *command-line-args*))]
           ; Read each line ignoring empty ones
           (doseq [line (remove empty? (line-seq rdr))]
                  (println (clojure.string/lower-case line))))
