(ns uno.state
  (:require-macros
    [uno.util :as util]
    [clojure.set :as set]))

(def ^:private username (atom nil))
(util/setget username)

(def ^:private game-state (atom nil))
(util/setget game-state)

(def ^:private hand (atom #{}))

(defn conj-hand! [item]
  (swap! hand conj item))

(defn disj-hand! [item]
  (swap! hand disj item))

(defn union-hand! [items]
  (swap! hand set/union items))

(defn- format-card [{:keys [color number]}]
  (if (< number 10)
    (str color "0" number)
    (str color number)))

(defn get-hand []
  (sort-by format-card @hand))
