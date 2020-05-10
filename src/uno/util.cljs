(ns uno.util)

(defn format [s & replacements]
  (reduce
    #(.replace %1 "%s" %2)
    s
    replacements))
