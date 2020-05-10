(ns uno.util)

(defmacro setget [atom]
  (let [setter-name (symbol (str "set-" atom))
        getter-name (symbol (str "get-" atom))]
    `(do
       (defn ~setter-name [x#]
         (reset! ~atom x#))
       (defn ~getter-name [] @~atom))))

(defmacro kz [& syms]
  (zipmap (map keyword syms) syms))
