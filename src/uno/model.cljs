(ns uno.model
  (:require-macros
    [uno.util :as util])
  (:require
    [uno.state :as state]))

(def colors ["red" "green" "blue" "yellow"])
(defn card [color number]
  (util/kz color number))

(state/set-game-state
  {:pool
   (for [color colors
         number (range 1 14)]
     (card color number))
   :stack []})
