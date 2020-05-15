(ns uno.state
  (:require-macros
    [uno.util :as util])
  (:require
    clojure.set))

(def ^:private username (atom nil))
(util/setget username)

(def ^:private game-state (atom nil))
(defn- merge-game-state [s1 s2]
  (update s2 :hands #(merge (:hands s1) %)))
(defn set-game-state [new-state]
  (swap! game-state merge-game-state new-state))

(defn get-game-state [] @game-state)
(defn get-game-state-msg []
  (update @game-state :hands #(select-keys % [@username])))

(def ^:private hand (atom #{}))
(defn- update-hands []
  (swap! game-state assoc-in [:hands @username] (count @hand)))

(defn conj-hand! [item]
  (swap! hand conj item)
  (update-hands))

(defn disj-hand! [item]
  (swap! hand disj item)
  (update-hands))

(defn union-hand! [items]
  (swap! hand clojure.set/union items)
  (update-hands))

(defn empty-hand! []
  (reset! hand #{})
  (update-hands))

(defn get-hand []
  (sort-by #(str (:color %) (:number %)) @hand))
