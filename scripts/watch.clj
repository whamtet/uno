(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'uno.core
   :output-to "out/uno.js"
   :output-dir "out"})
