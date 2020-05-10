(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'uno.core
   :output-to "uno.js"
   :output-dir "out"})
