(ns uno.state
  (:require-macros
    [uno.util :as util])
  (:require
    clojure.set))

(def ^:private game-state (atom nil))
(util/setget game-state)

(def ^:private hand (atom #{}))

(defn conj-hand! [item]
  (swap! hand conj item))

(defn disj-hand! [item]
  (swap! hand disj item))

(defn union-hand! [items]
  (swap! hand clojure.set/union items))

(defn get-hand []
  (sort-by #(str (:color %) (:number %)) @hand))
