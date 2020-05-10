(ns uno.model
  (:require-macros
    [uno.util :refer [kz]])
  (:require
    [uno.state :as state]))

(def colors ["red" "green" "blue" "yellow"])
(def queue-size 10)

(let [pool (for [color colors
                 number (range 1 14)]
             (kz color number))
      card (rand-nth pool)]
  (state/set-game-state
    {:pool (remove #(= card %) pool)
     :stack [card]}))

(defn pickup! []
  (let [{:keys [pool stack]} (state/get-game-state)
        card (rand-nth pool)]
    (state/set-game-state
      {:pool (remove #(= card %) pool)
       :stack stack})
    (state/conj-hand! card)))

(defn pickup-many! [i]
  (let [{:keys [pool stack]} (state/get-game-state)
        cards (->> pool shuffle (take i) set)]
    (state/set-game-state
      {:pool (remove cards pool)
       :stack stack})
    (state/union-hand! cards)))

(defn pickup-table! []
  (let [{:keys [pool stack]} (state/get-game-state)]
    (when (> (count stack) 1)
      (state/set-game-state
        {:pool pool
         :stack (pop stack)})
      (state/conj-hand! (peek stack)))))

(defn put-down! [card]
  (let [{:keys [pool stack]} (state/get-game-state)
        stack-full? (= queue-size (count stack))]
    (state/set-game-state
      {:pool (if stack-full? (conj pool (first stack)) pool)
       :stack (conj
                (if stack-full? (vec (rest stack)) stack)
                card)})
    (state/disj-hand! card)))
