(ns flw.core
  (:use [incanter.stats :only (hamming-distance)])
  (:require clojure.java.io))

(def rdr (clojure.java.io/reader "four_letter_words.txt"))
(def words (line-seq rdr))
(def graph (atom {}))

(defn add-neighbor [word other]
  (if (= 1 (hamming-distance word other))
    (let [neighbors-word (conj (@graph word) other)
          neighbors-other (conj (@graph other) word)]
      (swap! graph assoc word neighbors-word)
      (swap! graph assoc other neighbors-other))))

(defn add-to-graph [word]
  (println (str "Adding " word))
  (swap! graph assoc word #{})
  (dorun (map #(add-neighbor word %1) (keys @graph)))
  true)

(dorun (map add-to-graph words))