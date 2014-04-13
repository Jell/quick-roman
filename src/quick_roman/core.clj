(ns quick-roman.core
  (require [clojure.string :as string]))

(def roman-factors ["I" "V" "X" "L" "C" "D" "M"])

(def roman-factorizations
  [["IIIII" "V"]
   ["VV"    "X"]
   ["XXXXX" "L"]
   ["LL"    "C"]
   ["CCCCC" "D"]
   ["DD"    "M"]])

(def roman-simplifications
  [["IIII"  "IV"]
   ["VIIII" "IX"]
   ["XXXX"  "XL"]
   ["LXXXX" "XC"]
   ["CCCC"  "CD"]
   ["DCCCC" "CM"]])

(defn roman-factorize [n]
  (reduce (fn [s [long short]]
            (string/replace s long short))
          n
          roman-factorizations))

(defn roman-simplify [n]
  (reduce (fn [s [long short]]
            (string/replace s long short))
          (roman-factorize n)
          (reverse roman-simplifications)))

(defn roman-expand [n]
  (reduce (fn [s [long short]]
            (string/replace s short long))
          n
          roman-simplifications))

(defn roman-unfactorize [n]
  (reduce (fn [s [long short]]
            (string/replace s short long))
          (roman-expand n)
          (reverse roman-factorizations)))

(defn roman-expanded-sort [n]
  (string/join
   (sort-by (fn [c] (- (.indexOf roman-factors (str c))))
            n)))

(defn roman-add [m n]
  (roman-simplify
   (roman-expanded-sort
    (str (roman-expand m) (roman-expand n)))))

(defn decimal->roman [n]
  (roman-simplify
   (apply str (repeat n "I"))))

(defn roman->decimal [x]
  (count (roman-unfactorize x)))
