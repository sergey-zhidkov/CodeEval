(ns interrupted-bubble-sort
    (:require [clojure.string :as str]))



(defn parse-int [s]
      (Integer. (re-find  #"\d+" s )))

(defn swap [v i1 i2]
  (def temp (.get v i1))
  (.set v i1 (.get v i2))
  (.set v i2 temp)
  v)

(defn smaller? [vec index1 index2]
      ;(println "smaller" (get vec index1) (get vec index2))
  (< (.get vec index2) (.get vec index1)))


(defn bubble-sort-one-iteration [arr]
      (def len (.size arr))
      ;(println "do sort for arr: " arr " with len: " len)
      (if (< len 2)
        arr
        (loop [i 1]
          (if (= i len)
            arr
            (do
              (if (smaller? arr (- i 1) i)
                (swap arr (- i 1) i))
              (recur (+ i 1) )))
          ))
  )

(defn bubble-sort [arr loop-count]
      (loop [i loop-count]
            (if (< i 1)
              arr
              (do
                (if (> loop-count (* (.size arr) (.size arr)))
                  (do (java.util.Collections/sort arr)
                    arr)
                  (do (bubble-sort-one-iteration arr) (recur (- i 1))
                ))
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

      (def result-arr (bubble-sort (java.util.ArrayList. arr) loop-count))

      (print-result result-arr))

;(with-open [rdr (clojure.java.io/reader (first *command-line-args*))]
(with-open [rdr (clojure.java.io/reader (first *command-line-args*))]
           ; Read each line ignoring empty ones
           (doseq [line (remove empty? (line-seq rdr))]
                  (parse-line line)))
