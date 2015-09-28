(ns interrupted-bubble-sort
    (:require [clojure.string :as str]))

(defn parse-int [s]
      (Integer. (re-find  #"\d+" s )))

(defn swap [v i1 i2]
   (assoc v i2 (v i1) i1 (v i2)))

(defn smaller? [vec index1 index2]
      ;(println "smaller" (get vec index1) (get vec index2))
  (< (get vec index2) (get vec index1)))


(defn bubble-sort-one-iteration [arr]
      (def len (count arr))
      ;(println "do sort for arr: " arr " with len: " len)
      (if (< len 2)
        arr
        (loop [i 1 new-arr arr]
          (if (= i len)
            new-arr
            (do
              (recur (+ i 1) (if (smaller? new-arr (- i 1) i)
                              (swap new-arr (- i 1) i)
                              new-arr))))
          ))
  )

(defn bubble-sort [arr loop-count]
      (loop [i loop-count arr-to-change arr]
            (if (< i 1)
              arr-to-change
              (do
                (recur (- i 1) (bubble-sort-one-iteration arr-to-change))
              )
        )))

(defn print-result [vector]
      (apply println vector))

(defn parse-line [line]

      ; split up line with "|" into vector and number of loops
      ; do loop
      ; print result


      (def vector-to-parse (str/split line #"\|"))
      (def arr (mapv read-string (str/split (str/trim (get vector-to-parse 0)) #" ")))
      (def loop-count (read-string (get vector-to-parse 1)))

      (def result-arr (bubble-sort arr loop-count))

      (print-result result-arr))

;(with-open [rdr (clojure.java.io/reader (first *command-line-args*))]
(with-open [rdr (clojure.java.io/reader (first *command-line-args*))]
           ; Read each line ignoring empty ones
           (doseq [line (remove empty? (line-seq rdr))]
                  (parse-line line)))
