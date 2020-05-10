(ns uno.state
  (:require-macros
    [uno.util :as util]
    clojure.set))

(def ^:private new-game (atom false))
(defn new-game! []
  (reset! new-game true))
(defn new-game? [] @new-game)

(def ^:private game-state (atom nil))
(util/setget game-state)

(def ^:private hand (atom #{}))

(defn conj-hand! [item]
  (swap! hand conj item))

(defn disj-hand! [item]
  (swap! hand disj item))

(defn union-hand! [items]
  (swap! hand clojure.set/union items))

(defn- format-card [{:keys [color number]}]
  (if (< number 10)
    (str color "0" number)
    (str color number)))

(defn get-hand []
  (sort-by format-card @hand))
